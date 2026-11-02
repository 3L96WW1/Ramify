package com.ginka.ramify;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/** 烧烤架方块实体：4 个十字原料格、2 个燃料格、8 个成品格喵 */
public class GrillBlockEntity extends BlockEntity implements Inventory, ExtendedScreenHandlerFactory<BlockPos> {
    public static final int INGREDIENT_SLOTS = 4;
    public static final int FUEL_SLOTS = 2;
    public static final int OUTPUT_SLOTS = 8;
    public static final int SIZE = INGREDIENT_SLOTS + FUEL_SLOTS + OUTPUT_SLOTS;
    private final DefaultedList<ItemStack> items = DefaultedList.ofSize(SIZE, ItemStack.EMPTY);
    private int progress;
    private int maxProgress = 100;
    private int burnTime;
    private int fuelTotal;

    public GrillBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.GRILL_ENTITY, pos, state);
    }

    public static void tick(World world, BlockPos pos, BlockState state, GrillBlockEntity be) {
        if (world.isClient) return;
        boolean changed = false;
        GrillRecipes.Recipe recipe = GrillRecipes.find(be);
        if (recipe == null && be.hasIngredients()) {
            recipe = GrillRecipes.mystery();
        }
        if (recipe == null || !be.hasOutputSpace(recipe.output())) {
            // 没有匹配配方或成品格满：不消耗燃料，进度归零
            if (be.progress != 0) {
                be.progress = 0;
                changed = true;
            }
            if (changed) be.markDirty();
            return;
        }
        if (be.burnTime > 0) {
            be.burnTime--;
            changed = true;
        } else {
            ItemStack fuel = be.getStack(4);
            if (fuel.isEmpty()) fuel = be.getStack(5);
            if (!fuel.isEmpty()) {
                fuel.decrement(1);
                be.burnTime = 400; // 一个燃料烤 4 个，烟熏炉 100 tick/个
                be.fuelTotal = 400;
                changed = true;
            }
        }
        if (be.burnTime > 0) {
            be.progress++;
            if (be.progress >= be.maxProgress) {
                be.craft(recipe);
                be.progress = 0;
            }
            changed = true;
        }
        if (changed) be.markDirty();
    }

    private boolean hasOutputSpace(ItemStack output) {
        for (int i = 6; i < SIZE; i++) {
            ItemStack slot = getStack(i);
            if (slot.isEmpty()) return true;
            if (slot.isOf(output.getItem()) && slot.getCount() + output.getCount() <= slot.getMaxCount()) return true;
        }
        return false;
    }

    private void craft(GrillRecipes.Recipe recipe) {
        for (int i = 6; i < SIZE; i++) {
            ItemStack slot = getStack(i);
            if (slot.isEmpty()) {
                setStack(i, recipe.output().copy());
                break;
            }
            if (slot.isOf(recipe.output().getItem()) && slot.getCount() + recipe.output().getCount() <= slot.getMaxCount()) {
                slot.increment(recipe.output().getCount());
                markDirty();
                break;
            }
        }
        for (Item remainder : recipe.remainders()) {
            addOutput(new ItemStack(remainder));
        }
        GrillRecipes.consume(this, recipe);
    }

    private void addOutput(ItemStack stack) {
        for (int i = 6; i < SIZE; i++) {
            ItemStack slot = getStack(i);
            if (slot.isEmpty()) { setStack(i, stack.copy()); return; }
            if (slot.isOf(stack.getItem()) && slot.getCount() + stack.getCount() <= slot.getMaxCount()) {
                slot.increment(stack.getCount()); markDirty(); return;
            }
        }
    }

    private boolean hasIngredients() {
        for (int i = 0; i < INGREDIENT_SLOTS; i++) {
            if (getStack(i).isEmpty()) return false;
        }
        return true;
    }

    public int getProgress() { return progress; }
    public int getMaxProgress() { return maxProgress; }
    public int getBurnTime() { return burnTime; }
    public int getFuelTotal() { return fuelTotal; }

    @Override public int size() { return SIZE; }
    @Override public boolean isEmpty() { return items.stream().allMatch(ItemStack::isEmpty); }
    @Override public ItemStack getStack(int slot) { return items.get(slot); }
    @Override public ItemStack removeStack(int slot, int amount) {
        ItemStack result = Inventories.splitStack(items, slot, amount);
        if (!result.isEmpty()) markDirty();
        return result;
    }
    @Override public ItemStack removeStack(int slot) { return Inventories.removeStack(items, slot); }
    @Override public void setStack(int slot, ItemStack stack) {
        items.set(slot, stack);
        if (stack.getCount() > getMaxCountPerStack()) stack.setCount(getMaxCountPerStack());
        markDirty();
    }
    @Override public boolean canPlayerUse(PlayerEntity player) { return true; }
    @Override public void clear() { items.clear(); }

    @Override
    public void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.readNbt(nbt, registries);
        Inventories.readNbt(nbt, items, registries);
        progress = nbt.getInt("Progress");
        burnTime = nbt.getInt("BurnTime");
        fuelTotal = nbt.getInt("FuelTotal");
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.writeNbt(nbt, registries);
        Inventories.writeNbt(nbt, items, registries);
        nbt.putInt("Progress", progress);
        nbt.putInt("BurnTime", burnTime);
        nbt.putInt("FuelTotal", fuelTotal);
    }

    @Override public Text getDisplayName() { return Text.translatable("container.ramify.grill"); }
    @Override public BlockPos getScreenOpeningData(ServerPlayerEntity player) { return pos; }
    @Override public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new GrillScreenHandler(syncId, playerInventory, this);
    }
}
