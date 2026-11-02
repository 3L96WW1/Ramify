package com.ginka.ramify;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.List;
import java.util.function.Predicate;

/** 烧烤架配方：25 个明日之后风格基础配方，四个原料槽无序匹配喵 */
public final class GrillRecipes {
    public record Recipe(List<Predicate<ItemStack>> inputs, ItemStack output, List<Item> remainders) {
    }

    private static Predicate<ItemStack> any(Item... items) {
        return stack -> {
            for (Item item : items) if (stack.isOf(item)) return true;
            return false;
        };
    }

    private static Recipe r(Item output, int count, List<Item> remainders, Predicate<ItemStack>... inputs) {
        return new Recipe(List.of(inputs), new ItemStack(output, count), remainders);
    }

    private static Recipe r(Item output, Predicate<ItemStack>... inputs) {
        return r(output, 1, List.of(), inputs);
    }

    private static final Predicate<ItemStack> MEAT = any(Items.BEEF, Items.PORKCHOP);
    private static final Predicate<ItemStack> MUSHROOM = any(Items.BROWN_MUSHROOM, Items.RED_MUSHROOM,
            ModBlocks.STRAW_MUSHROOM.asItem(), ModBlocks.MATSUTAKE.asItem());
    private static final Predicate<ItemStack> BERRY = any(Items.SWEET_BERRIES, Items.GLOW_BERRIES, ModItems.STRAWBERRY);
    private static final Predicate<ItemStack> FRUIT = any(Items.APPLE, Items.SWEET_BERRIES, Items.GLOW_BERRIES, ModItems.STRAWBERRY);
    private static final Predicate<ItemStack> VEGETABLE = any(Items.CARROT, ModItems.RAPESEED);
    private static final Predicate<ItemStack> FISH = any(Items.COD, Items.SALMON);
    private static final Predicate<ItemStack> RAW_LOBSTER = any(ModItems.RAW_LOBSTER);
    private static final Predicate<ItemStack> HONEY = any(Items.HONEY_BOTTLE);
    private static final Predicate<ItemStack> ANY = stack -> !stack.isEmpty();

    private static final List<Recipe> RECIPES = List.of(
            // 18 草莓果酱必须排在 3 普通果酱之前
            r(ModItems.STRAWBERRY_JAM, any(ModItems.STRAWBERRY), any(ModItems.STRAWBERRY),
                    any(ModItems.STRAWBERRY), any(ModItems.STRAWBERRY)),
            r(ModItems.BERRY_JAM, BERRY, BERRY, BERRY, BERRY),
            r(ModItems.COOKED_LOBSTER, RAW_LOBSTER, BERRY, BERRY, BERRY),
            r(ModItems.ROASTED_CORN, 4, List.of(), any(ModItems.CORN), any(ModItems.CORN),
                    any(ModItems.CORN), any(ModItems.CORN)),
            r(ModItems.GRILLED_MEAT, MEAT, MEAT, MEAT, MEAT),
            r(ModItems.GRILLED_MUSHROOM, MUSHROOM, MUSHROOM, MUSHROOM, MUSHROOM),
            r(ModItems.STEAMED_VEGETABLES, VEGETABLE, VEGETABLE, VEGETABLE, VEGETABLE),
            r(ModItems.VEGETABLE_SALAD, VEGETABLE, VEGETABLE, FRUIT, FRUIT),
            r(ModItems.HONEY_MUSHROOM, MUSHROOM, MUSHROOM, HONEY, HONEY),
            r(ModItems.MIXED_PLATTER, MEAT, VEGETABLE, BERRY, MUSHROOM),
            r(ModItems.STRAWBERRY_JELLY, 1, List.of(Items.BUCKET),
                    any(ModItems.STRAWBERRY), any(ModItems.STRAWBERRY), any(Items.MILK_BUCKET), any(Items.ICE)),
            r(ModItems.MEATBALL, any(ModItems.RAPESEED), any(ModItems.RAPESEED), any(Items.PORKCHOP), any(Items.PORKCHOP)),
            r(ModItems.HONEY_ROAST_MEAT, MEAT, MEAT, HONEY, HONEY),
            r(ModItems.MUSHROOM_MEAT_SKEWER, MEAT, MEAT, MUSHROOM, MUSHROOM),
            r(ModItems.MEAT_SKEWER, MEAT, MEAT, FRUIT, FRUIT),
            r(ModItems.MEAT_LAYERED_PIE, MEAT, MEAT, any(Items.WHEAT), any(Items.WHEAT)),
            r(ModItems.MUSHROOM_LAYERED_PIE, MUSHROOM, MUSHROOM, any(Items.WHEAT), any(Items.WHEAT)),
            r(ModItems.FRUIT_PIE, FRUIT, HONEY, any(Items.WHEAT), any(Items.WHEAT)),
            r(ModItems.DUMPLING, MEAT, MUSHROOM, any(Items.WHEAT), any(Items.WHEAT)),
            r(ModItems.FRUIT_JUICE, 1, List.of(Items.BUCKET),
                    FRUIT, FRUIT, any(Items.ICE), any(Items.WATER_BUCKET)),
            r(ModItems.HONEY_CROISSANT, HONEY, HONEY, any(Items.WHEAT), any(Items.WHEAT)),
            r(ModItems.VEGETABLE_LAYERED_PIE, any(ModItems.RAPESEED), any(ModItems.RAPESEED),
                    any(Items.WHEAT), any(Items.WHEAT)),
            r(ModItems.FRUIT_PRESERVE, BERRY, BERRY, HONEY, any(Items.SUGAR)),
            r(ModItems.JAM_BREAD, BERRY, BERRY, any(Items.WHEAT), any(Items.WHEAT)),
            r(ModItems.PAN_FRIED_FISH, FISH, FISH, any(ModItems.RAPESEED), any(ModItems.RAPESEED)),
            r(ModItems.MILK_CANDY, 1, List.of(Items.BUCKET, Items.BUCKET),
                    HONEY, HONEY, any(Items.MILK_BUCKET), any(Items.MILK_BUCKET))
    );

    public static Recipe mystery() {
        return r(ModItems.MYSTERY_STEW, ANY, ANY, ANY, ANY);
    }

    public static Recipe find(Inventory inventory) {
        for (Recipe recipe : RECIPES) {
            if (matches(inventory, recipe)) return recipe;
        }
        return null;
    }

    private static boolean matches(Inventory inventory, Recipe recipe) {
        boolean[] used = new boolean[4];
        for (Predicate<ItemStack> input : recipe.inputs()) {
            boolean found = false;
            for (int i = 0; i < 4; i++) {
                if (!used[i] && input.test(inventory.getStack(i))) {
                    used[i] = true;
                    found = true;
                    break;
                }
            }
            if (!found) return false;
        }
        return true;
    }

    public static void consume(Inventory inventory, Recipe recipe) {
        boolean[] used = new boolean[4];
        for (Predicate<ItemStack> input : recipe.inputs()) {
            for (int i = 0; i < 4; i++) {
                if (!used[i] && input.test(inventory.getStack(i))) {
                    used[i] = true;
                    inventory.getStack(i).decrement(1);
                    inventory.setStack(i, inventory.getStack(i));
                    break;
                }
            }
        }
    }

    private GrillRecipes() {
    }
}
