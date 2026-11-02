package com.ginka.ramify;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
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
    public static final Item RAPESEED = reg("rapeseed", new Item(new Item.Settings()));
    public static final Item MURMUR_BOT = reg("murmur_bot",
            new MurmurBotItem(new Item.Settings().rarity(net.minecraft.util.Rarity.EPIC)));
    public static final Item ROSE = reg("rose", new RoseItem(new Item.Settings()));
    public static final Item ROSE_SEEDS = reg("rose_seeds", new RoseSeedsItem(new Item.Settings(), ModBlocks.ROSE_BUSH));

    // 药品
    public static final Item BANDAGE = reg("bandage", new BandageItem(new Item.Settings().maxCount(16)));
    public static final Item ANTI_INFECTION = reg("anti_infection", new CureItem(new Item.Settings().maxCount(8)));
    public static final Item ADRENALINE = reg("adrenaline", new AdrenalineItem(new Item.Settings().maxCount(8)));

    // 食物
    public static final Item STRAWBERRY = reg("strawberry", new Item(new Item.Settings()
            .food(new FoodComponent.Builder().nutrition(2).saturationModifier(0.3f).build())));
    public static final Item CORN = reg("corn", new Item(new Item.Settings()
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
                    .usingConvertsTo(Items.BOWL)
                    .build())));
    public static final Item RAPESEED_SOUP = reg("rapeseed_soup", new Item(new Item.Settings().maxCount(1)
            .recipeRemainder(Items.BOWL)
            .food(new FoodComponent.Builder().nutrition(6).saturationModifier(0.6f)
                    .statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 180, 0), 1.0f)
                    .statusEffect(new StatusEffectInstance(ModEffects.warmth(), 180, 0), 1.0f)
                    .usingConvertsTo(Items.BOWL)
                    .build())));
    public static final Item RAW_SKEWER = reg("raw_skewer", new Item(new Item.Settings()
            .food(new FoodComponent.Builder().nutrition(4).saturationModifier(0.3f).build())));
    public static final Item CORN_CAKE = reg("corn_cake", new Item(new Item.Settings()
            .food(new FoodComponent.Builder().nutrition(7).saturationModifier(0.7f).build())));
    public static final Item HONEY_BERRY_CAKE = reg("honey_berry_cake", new Item(new Item.Settings()
            .food(new FoodComponent.Builder().nutrition(7).saturationModifier(0.7f).build())));
    public static final Item STRAWBERRY_CAKE = reg("strawberry_cake", new Item(new Item.Settings()
            .food(new FoodComponent.Builder().nutrition(6).saturationModifier(0.6f).build())));
    public static final Item GRILLED_BEEF_SKEWER = reg("grilled_beef_skewer", new Item(new Item.Settings()
            .food(new FoodComponent.Builder().nutrition(7).saturationModifier(0.7f)
                    .statusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 600, 0), 1.0f).build())));
    public static final Item GRILLED_CHICKEN_SKEWER = reg("grilled_chicken_skewer", new Item(new Item.Settings()
            .food(new FoodComponent.Builder().nutrition(6).saturationModifier(0.6f)
                    .statusEffect(new StatusEffectInstance(StatusEffects.SPEED, 600, 0), 1.0f).build())));
    public static final Item GRILLED_FISH = reg("grilled_fish", new Item(new Item.Settings()
            .food(new FoodComponent.Builder().nutrition(6).saturationModifier(0.6f)
                    .statusEffect(new StatusEffectInstance(StatusEffects.WATER_BREATHING, 600, 0), 1.0f)
                    .statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 100, 0), 1.0f).build())));
    public static final Item GRILLED_POTATO = reg("grilled_potato", new Item(new Item.Settings()
            .food(new FoodComponent.Builder().nutrition(5).saturationModifier(0.5f)
                    .statusEffect(new StatusEffectInstance(StatusEffects.HASTE, 600, 0), 1.0f).build())));
    public static final Item GRILLED_MUSHROOM = reg("grilled_mushroom", new Item(new Item.Settings()
            .food(new FoodComponent.Builder().nutrition(5).saturationModifier(0.5f)
                    .statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 200, 0), 1.0f).build())));
    public static final Item GRILLED_APPLE = reg("grilled_apple", new Item(new Item.Settings()
            .food(new FoodComponent.Builder().nutrition(4).saturationModifier(0.4f)
                    .statusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 600, 0), 1.0f).build())));
    public static final Item COOKED_SKEWER = reg("cooked_skewer", new Item(new Item.Settings()
            .food(new FoodComponent.Builder().nutrition(8).saturationModifier(0.8f).build())));
    public static final Item RAW_CRAB = reg("raw_crab", new Item(new Item.Settings()
            .food(new FoodComponent.Builder().nutrition(2).saturationModifier(0.1f)
                    .statusEffect(new StatusEffectInstance(StatusEffects.POISON, 60, 0), 1.0f)
                    .statusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 200, 0), 1.0f)
                    .build())));
    public static final Item COOKED_CRAB = reg("cooked_crab", new Item(new Item.Settings()
            .food(new FoodComponent.Builder().nutrition(6).saturationModifier(0.6f)
                    .statusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 1200, 0), 1.0f)
                    .build())));
    public static final Item RAW_LOBSTER = reg("raw_lobster", new Item(new Item.Settings()
            .food(new FoodComponent.Builder().nutrition(2).saturationModifier(0.1f)
                    .statusEffect(new StatusEffectInstance(StatusEffects.POISON, 60, 0), 1.0f)
                    .statusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 200, 0), 1.0f)
                    .build())));
    public static final Item COOKED_LOBSTER = reg("cooked_lobster", new Item(new Item.Settings()
            .food(new FoodComponent.Builder().nutrition(6).saturationModifier(0.6f).build())));
    public static final Item LOBSTER_SPAWN_EGG = reg("lobster_spawn_egg",
            new net.minecraft.item.SpawnEggItem(ModEntities.LOBSTER, 0xA83A24, 0xE0A060, new Item.Settings()));
    public static final Item CRAB_SPAWN_EGG = reg("crab_spawn_egg",
            new net.minecraft.item.SpawnEggItem(ModEntities.CRAB, 0xE06A3A, 0xF2C28A, new Item.Settings()));
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
            entries.add(RAPESEED);
            entries.add(HEMP_PAPER);
            entries.add(ROSE);
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(entries -> {
            entries.add(STRAWBERRY);
            entries.add(CORN);
            entries.add(ROASTED_CORN);
            entries.add(MUSHROOM_SOUP);
            entries.add(RAPESEED_SOUP);
            entries.add(RAW_SKEWER);
            entries.add(COOKED_SKEWER);
            entries.add(GRILLED_BEEF_SKEWER);
            entries.add(GRILLED_CHICKEN_SKEWER);
            entries.add(GRILLED_FISH);
            entries.add(GRILLED_POTATO);
            entries.add(GRILLED_MUSHROOM);
            entries.add(GRILLED_APPLE);
            entries.add(STRAWBERRY_CAKE);
            entries.add(HONEY_BERRY_CAKE);
            entries.add(CORN_CAKE);
            entries.add(RAW_CRAB);
            entries.add(COOKED_CRAB);
            entries.add(RAW_LOBSTER);
            entries.add(COOKED_LOBSTER);
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(entries -> entries.add(ModBlocks.GRILL_ITEM));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(entries -> {
            entries.add(CRAB_SPAWN_EGG);
            entries.add(LOBSTER_SPAWN_EGG);
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(entries -> {
            entries.add(ModBlocks.HEMP);
            entries.add(ModBlocks.JUTE);
            entries.add(ModBlocks.SISAL);
            entries.add(ModBlocks.COLD_HEMP);
            entries.add(ModBlocks.ALPINE_FLAX);
            entries.add(ModBlocks.STRAWBERRY_BUSH);
            entries.add(ModBlocks.CORN_BLOCK);
            entries.add(ROSE_SEEDS);
            entries.add(CORN_SEEDS);
            entries.add(ModBlocks.ROSE_BUSH);
            entries.add(ModBlocks.STRAW_MUSHROOM);
            entries.add(ModBlocks.MATSUTAKE);
        });
    }

    private ModItems() {
    }
}
