package com.ginka.ramify;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public final class ModItems {
    // 基础材料
    public static final Item HEMP_LEAF = reg("hemp_leaf", new Item(new Item.Settings()));
    public static final Item JUTE_LEAF = reg("jute_leaf", new Item(new Item.Settings()));
    public static final Item SISAL_LEAF = reg("sisal_leaf", new Item(new Item.Settings()));
    public static final Item HEMP_FIBER = reg("hemp_fiber", new Item(new Item.Settings()));
    public static final Item HEMP_PAPER = reg("hemp_paper", new Item(new Item.Settings()));

    // 药品
    public static final Item BANDAGE = reg("bandage", new BandageItem(new Item.Settings().maxCount(16)));
    public static final Item ANTI_INFECTION = reg("anti_infection", new CureItem(new Item.Settings().maxCount(8)));
    public static final Item ADRENALINE = reg("adrenaline", new AdrenalineItem(new Item.Settings().maxCount(8)));

    // 食物
    public static final Item STRAWBERRY = reg("strawberry", new Item(new Item.Settings()
            .food(new FoodComponent.Builder().nutrition(2).saturationModifier(0.3f).build())));
    public static final Item CORN = reg("corn", new BlockItem(ModBlocks.CORN, new Item.Settings()
            .food(new FoodComponent.Builder().nutrition(4).saturationModifier(0.4f).build())));
    public static final Item ROASTED_CORN = reg("roasted_corn", new Item(new Item.Settings()
            .food(new FoodComponent.Builder().nutrition(6).saturationModifier(0.6f).build())));
    public static final Item MUSHROOM_SOUP = reg("mushroom_soup", new Item(new Item.Settings().maxCount(1)
            .recipeRemainder(Items.BOWL)
            .food(new FoodComponent.Builder()
                    .nutrition(8).saturationModifier(0.8f)
                    .statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 120, 0), 1.0f)
                    .statusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 240, 0), 1.0f)
                    .statusEffect(new StatusEffectInstance(StatusEffects.SPEED, 240, 0), 1.0f)
                    .build())));
    public static final Item RAPESEED_SOUP = reg("rapeseed_soup", new Item(new Item.Settings().maxCount(1)
            .recipeRemainder(Items.BOWL)
            .food(new FoodComponent.Builder().nutrition(6).saturationModifier(0.6f)
                    .statusEffect(new StatusEffectInstance(StatusEffects.SPEED, 200, 0), 1.0f)
                    .build())));
    public static final Item RAW_SKEWER = reg("raw_skewer", new Item(new Item.Settings()
            .food(new FoodComponent.Builder().nutrition(4).saturationModifier(0.3f).build())));
    public static final Item CORN_SEEDS = reg("corn_seeds", new SeedsItem(new Item.Settings(), ModBlocks.CORN));

    private static Item reg(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(Ramify.MOD_ID, name), item);
    }

    public static void register() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> {
            entries.add(BANDAGE);
            entries.add(ANTI_INFECTION);
            entries.add(ADRENALINE);
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
            entries.add(HEMP_LEAF);
            entries.add(JUTE_LEAF);
            entries.add(SISAL_LEAF);
            entries.add(HEMP_FIBER);
            entries.add(HEMP_PAPER);
            entries.add(CORN_SEEDS);
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(entries -> {
            entries.add(STRAWBERRY);
            entries.add(CORN);
            entries.add(ROASTED_CORN);
            entries.add(MUSHROOM_SOUP);
            entries.add(RAPESEED_SOUP);
            entries.add(RAW_SKEWER);
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(entries -> {
            entries.add(ModBlocks.HEMP);
            entries.add(ModBlocks.JUTE);
            entries.add(ModBlocks.SISAL);
            entries.add(ModBlocks.STRAWBERRY_BUSH);
            entries.add(ModBlocks.CORN);
            entries.add(ModBlocks.STRAW_MUSHROOM);
            entries.add(ModBlocks.MATSUTAKE);
            entries.add(ModBlocks.RAPESEED);
        });
    }

    private ModItems() {
    }
}
