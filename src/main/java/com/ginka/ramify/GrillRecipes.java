package com.ginka.ramify;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.List;

/** 烧烤架的 10 个基础配方，四个原料槽无序匹配喵 */
public final class GrillRecipes {
    public record Recipe(List<Item> ingredients, ItemStack output) {
    }

    private static Recipe recipe(List<Item> ingredients, Item output) {
        return new Recipe(ingredients, new ItemStack(output));
    }

    private static Recipe recipe(List<Item> ingredients, Item output, int count) {
        return new Recipe(ingredients, new ItemStack(output, count));
    }

    private static final List<Recipe> RECIPES = List.of(
            recipe(List.of(ModItems.CORN, ModItems.CORN, ModItems.CORN, ModItems.CORN), ModItems.ROASTED_CORN, 4),
            recipe(List.of(ModItems.RAW_SKEWER, Items.SWEET_BERRIES, Items.SWEET_BERRIES, Items.SWEET_BERRIES), ModItems.COOKED_SKEWER),
            recipe(List.of(ModItems.RAW_CRAB, Items.SWEET_BERRIES, Items.SWEET_BERRIES, Items.SWEET_BERRIES), ModItems.COOKED_CRAB),
            recipe(List.of(ModItems.RAW_LOBSTER, Items.SWEET_BERRIES, Items.SWEET_BERRIES, Items.SWEET_BERRIES), ModItems.COOKED_LOBSTER),
            recipe(List.of(Items.BEEF, Items.STICK, Items.BROWN_MUSHROOM, Items.BROWN_MUSHROOM), ModItems.GRILLED_BEEF_SKEWER),
            recipe(List.of(Items.CHICKEN, Items.STICK, Items.CARROT, Items.CARROT), ModItems.GRILLED_CHICKEN_SKEWER),
            recipe(List.of(Items.COD, Items.SALMON, Items.SWEET_BERRIES, Items.SWEET_BERRIES), ModItems.GRILLED_FISH),
            recipe(List.of(Items.POTATO, Items.POTATO, Items.POTATO, Items.POTATO), ModItems.GRILLED_POTATO),
            recipe(List.of(Items.BROWN_MUSHROOM, Items.BROWN_MUSHROOM, Items.BROWN_MUSHROOM, Items.BROWN_MUSHROOM), ModItems.GRILLED_MUSHROOM),
            recipe(List.of(Items.APPLE, Items.SUGAR, Items.HONEY_BOTTLE, Items.BREAD), ModItems.GRILLED_APPLE)
    );

    public static Recipe find(Inventory inventory) {
        for (Recipe recipe : RECIPES) {
            if (matches(inventory, recipe)) return recipe;
        }
        return null;
    }

    private static boolean matches(Inventory inventory, Recipe recipe) {
        boolean[] used = new boolean[4];
        for (Item ingredient : recipe.ingredients()) {
            boolean found = false;
            for (int i = 0; i < 4; i++) {
                if (!used[i] && inventory.getStack(i).isOf(ingredient)) {
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
        for (Item ingredient : recipe.ingredients()) {
            for (int i = 0; i < 4; i++) {
                if (!used[i] && inventory.getStack(i).isOf(ingredient)) {
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
