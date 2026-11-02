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

    public static final Item MYSTERY_STEW = food("mystery_stew", 4, 0.4f,
            new StatusEffectInstance(StatusEffects.POISON, 120, 0),
            new StatusEffectInstance(StatusEffects.NAUSEA, 200, 0),
            new StatusEffectInstance(StatusEffects.HUNGER, 200, 0),
            new StatusEffectInstance(StatusEffects.WEAKNESS, 200, 0));
    public static final Item GRILLED_MEAT = food("grilled_meat", 8, 0.8f);
    public static final Item BERRY_JAM = food("berry_jam", 4, 0.3f);
    public static final Item STEAMED_VEGETABLES = food("steamed_vegetables", 6, 0.6f,
            new StatusEffectInstance(ModEffects.harvestCrit(), 1200, 0));
    public static final Item VEGETABLE_SALAD = food("vegetable_salad", 6, 0.6f,
            new StatusEffectInstance(ModEffects.harvestCrit(), 1200, 0));
    public static final Item HONEY_MUSHROOM = food("honey_mushroom", 6, 0.6f,
            new StatusEffectInstance(StatusEffects.HASTE, 1200, 0));
    public static final Item MIXED_PLATTER = food("mixed_platter", 8, 0.8f,
            new StatusEffectInstance(StatusEffects.HASTE, 1200, 0),
            new StatusEffectInstance(StatusEffects.LUCK, 1200, 0));
    public static final Item STRAWBERRY_JELLY = food("strawberry_jelly", 5, 0.5f,
            new StatusEffectInstance(StatusEffects.SPEED, 1200, 0));
    public static final Item MEATBALL = food("meatball", 7, 0.7f,
            new StatusEffectInstance(ModEffects.antiInfection(), 4800, 0),
            new StatusEffectInstance(StatusEffects.STRENGTH, 600, 0));
    public static final Item HONEY_ROAST_MEAT = food("honey_roast_meat", 8, 0.8f,
            new StatusEffectInstance(StatusEffects.REGENERATION, 120, 0),
            new StatusEffectInstance(StatusEffects.STRENGTH, 600, 0));
    public static final Item MUSHROOM_MEAT_SKEWER = food("mushroom_meat_skewer", 8, 0.8f,
            new StatusEffectInstance(StatusEffects.STRENGTH, 600, 0));
    public static final Item MEAT_SKEWER = food("meat_skewer", 8, 0.8f,
            new StatusEffectInstance(ModEffects.rangedPower(), 1200, 0),
            new StatusEffectInstance(StatusEffects.STRENGTH, 600, 0));
    public static final Item MEAT_LAYERED_PIE = food("meat_layered_pie", 8, 0.8f,
            new StatusEffectInstance(ModEffects.undeadPower(), 1200, 0),
            new StatusEffectInstance(StatusEffects.STRENGTH, 600, 0));
    public static final Item MUSHROOM_LAYERED_PIE = food("mushroom_layered_pie", 7, 0.7f,
            new StatusEffectInstance(StatusEffects.HASTE, 1200, 0));
    public static final Item FRUIT_PIE = food("fruit_pie", 7, 0.7f,
            new StatusEffectInstance(StatusEffects.STRENGTH, 600, 0));
    public static final Item DUMPLING = food("dumpling", 8, 0.8f,
            new StatusEffectInstance(ModEffects.warmth(), 2400, 0));
    public static final Item FRUIT_JUICE = food("fruit_juice", 4, 0.4f,
            new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 1200, 0));
    public static final Item STRAWBERRY_JAM = food("strawberry_jam", 5, 0.5f);
    public static final Item HONEY_CROISSANT = food("honey_croissant", 6, 0.6f);
    public static final Item VEGETABLE_LAYERED_PIE = food("vegetable_layered_pie", 7, 0.7f,
            new StatusEffectInstance(StatusEffects.HASTE, 1200, 0));
    public static final Item FRUIT_PRESERVE = food("fruit_preserve", 5, 0.5f,
            new StatusEffectInstance(StatusEffects.REGENERATION, 200, 0));
    public static final Item JAM_BREAD = food("jam_bread", 10, 1.0f);
    public static final Item PAN_FRIED_FISH = food("pan_fried_fish", 8, 0.8f);
    public static final Item MILK_CANDY = food("milk_candy", 4, 0.4f,
            new StatusEffectInstance(StatusEffects.SPEED, 1200, 0),
            new StatusEffectInstance(ModEffects.antiInfection(), 2400, 0));

    private static Item food(String name, int nutrition, float saturation, StatusEffectInstance... effects) {
        FoodComponent.Builder builder = new FoodComponent.Builder().nutrition(nutrition).saturationModifier(saturation);
        for (StatusEffectInstance effect : effects) builder.statusEffect(effect, 1.0f);
        return reg(name, new Item(new Item.Settings().food(builder.build())));
    }
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
            .rarity(net.minecraft.util.Rarity.UNCOMMON)
            .food(new FoodComponent.Builder().nutrition(6).saturationModifier(0.6f)
                    .statusEffect(new StatusEffectInstance(StatusEffects.HEALTH_BOOST, 1200, 1), 1.0f)
                    .statusEffect(new StatusEffectInstance(ModEffects.hearty(), 1200, 0), 1.0f)
                    .build())));
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
            entries.add(GRILLED_MEAT);
            entries.add(MYSTERY_STEW);
            entries.add(BERRY_JAM);
            entries.add(STEAMED_VEGETABLES);
            entries.add(VEGETABLE_SALAD);
            entries.add(HONEY_MUSHROOM);
            entries.add(MIXED_PLATTER);
            entries.add(STRAWBERRY_JELLY);
            entries.add(MEATBALL);
            entries.add(HONEY_ROAST_MEAT);
            entries.add(MUSHROOM_MEAT_SKEWER);
            entries.add(MEAT_SKEWER);
            entries.add(MEAT_LAYERED_PIE);
            entries.add(MUSHROOM_LAYERED_PIE);
            entries.add(FRUIT_PIE);
            entries.add(DUMPLING);
            entries.add(FRUIT_JUICE);
            entries.add(STRAWBERRY_JAM);
            entries.add(HONEY_CROISSANT);
            entries.add(VEGETABLE_LAYERED_PIE);
            entries.add(FRUIT_PRESERVE);
            entries.add(JAM_BREAD);
            entries.add(PAN_FRIED_FISH);
            entries.add(MILK_CANDY);
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
