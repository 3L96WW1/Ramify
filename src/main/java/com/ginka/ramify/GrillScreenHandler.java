package com.ginka.ramify;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.screen.slot.CraftingResultSlot;
import net.minecraft.screen.slot.Slot;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.util.math.BlockPos;

public class GrillScreenHandler extends ScreenHandler {
    public static final ScreenHandlerType<GrillScreenHandler> TYPE = Registry.register(
            Registries.SCREEN_HANDLER, Identifier.of(Ramify.MOD_ID, "grill"),
            new ExtendedScreenHandlerType<>((syncId, inv, pos) -> new GrillScreenHandler(syncId, inv, pos),
                    BlockPos.PACKET_CODEC));

    private final Inventory inventory;
    private final PropertyDelegate properties;
    private final CraftingInventory craftingInventory;
    private final SimpleInventory craftingResult;
    private final PlayerEntity owner;

    // 客户端构造函数（数据同步用空库存/属性）
    public GrillScreenHandler(int syncId, PlayerInventory playerInventory, BlockPos pos) {
        this(syncId, playerInventory, new SimpleInventory(GrillBlockEntity.SIZE), new ArrayPropertyDelegate(4));
    }

    // 服务端构造函数
    public GrillScreenHandler(int syncId, PlayerInventory playerInventory, GrillBlockEntity be) {
        this(syncId, playerInventory, be, new PropertyDelegate() {
            @Override public int size() { return 4; }
            @Override public int get(int index) {
                return switch (index) {
                    case 0 -> be.getProgress();
                    case 1 -> be.getMaxProgress();
                    case 2 -> be.getBurnTime();
                    default -> be.getFuelTotal();
                };
            }
            @Override public void set(int index, int value) { }
        });
    }

    private GrillScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate properties) {
        super(TYPE, syncId);
        this.inventory = inventory;
        this.properties = properties;
        this.owner = playerInventory.player;
        this.craftingInventory = new CraftingInventory(this, 3, 3);
        this.craftingResult = new SimpleInventory(1);
        this.addProperties(properties);

        // 十字形原料：上、左、右、下
        this.addSlot(new Slot(inventory, 0, 80, 36));
        this.addSlot(new Slot(inventory, 1, 56, 60));
        this.addSlot(new Slot(inventory, 2, 104, 60));
        this.addSlot(new Slot(inventory, 3, 80, 84));

        // 两个燃料槽
        this.addSlot(new Slot(inventory, 4, 56, 120) {
            @Override public boolean canInsert(ItemStack stack) { return isGrillFuel(stack); }
        });
        this.addSlot(new Slot(inventory, 5, 104, 120) {
            @Override public boolean canInsert(ItemStack stack) { return isGrillFuel(stack); }
        });

        // 8 个成品槽，不能放入
        for (int i = 0; i < 8; i++) {
            this.addSlot(new Slot(inventory, 6 + i, 154, 38 + i * 16) {
                @Override public boolean canInsert(ItemStack stack) { return false; }
            });
        }

        // 3×3 工作台面板
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                this.addSlot(new Slot(craftingInventory, col + row * 3, 182 + col * 18, 194 + row * 18));
            }
        }
        this.addSlot(new CraftingResultSlot(owner, craftingInventory, craftingResult, 0, 238, 212));

        // 玩家背包
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 190 + row * 18));
            }
        }
        for (int col = 0; col < 9; col++) {
            this.addSlot(new Slot(playerInventory, col, 8 + col * 18, 248));
        }
    }

    @Override
    public void onContentChanged(Inventory inventory) {
        super.onContentChanged(inventory);
        if (inventory == this.craftingInventory && this.owner != null) {
            var world = this.owner.getWorld();
            java.util.List<ItemStack> stacks = new java.util.ArrayList<>();
            for (int i = 0; i < 9; i++) stacks.add(this.craftingInventory.getStack(i));
            var input = CraftingRecipeInput.create(3, 3, stacks);
            ItemStack result = world.getRecipeManager()
                    .getFirstMatch(RecipeType.CRAFTING, input, world)
                    .map(entry -> entry.value().craft(input, world.getRegistryManager()))
                    .orElse(ItemStack.EMPTY);
            this.craftingResult.setStack(0, result);
        }
    }

    public ItemStack getOutputStack(int index) {
        return inventory.getStack(6 + index);
    }

    private static boolean isGrillFuel(ItemStack stack) {
        return stack.isIn(ItemTags.COALS) || stack.isOf(Items.CHARCOAL)
                || stack.isIn(ItemTags.LOGS) || stack.isIn(ItemTags.PLANKS)
                || stack.isOf(Items.STICK) || stack.isOf(Items.BLAZE_ROD);
    }

    public int getProgress() { return properties.get(0); }
    public int getMaxProgress() { return Math.max(1, properties.get(1)); }
    public int getBurnTime() { return properties.get(2); }
    public int getFuelTotal() { return Math.max(1, properties.get(3)); }

    @Override
    public boolean canUse(PlayerEntity player) { return true; }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        ItemStack original = ItemStack.EMPTY;
        Slot s = this.slots.get(slot);
        if (s != null && s.hasStack()) {
            ItemStack stack = s.getStack();
            original = stack.copy();
            if (slot == 23) {
                return ItemStack.EMPTY;
            }
            if (slot < 23) {
                if (!this.insertItem(stack, 24, this.slots.size(), true)) return ItemStack.EMPTY;
            } else if (!this.insertItem(stack, 0, 6, false)) {
                return ItemStack.EMPTY;
            }
            if (stack.isEmpty()) s.setStack(ItemStack.EMPTY); else s.markDirty();
        }
        return original;
    }
}
