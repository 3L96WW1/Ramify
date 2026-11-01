package com.ginka.ramify;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public final class ModItems {
    public static final Item HEMP_FIBER = reg("hemp_fiber", new Item(new Item.Settings()));
    public static final Item BANDAGE = reg("bandage", new BandageItem(new Item.Settings().maxCount(16)));
    public static final Item ANTI_INFECTION = reg("anti_infection", new CureItem(new Item.Settings().maxCount(8)));
    public static final Item ADRENALINE = reg("adrenaline", new AdrenalineItem(new Item.Settings().maxCount(8)));

    public static final Item STRAWBERRY = reg("strawberry", new Item(new Item.Settings()
            .food(new FoodComponent.Builder().nutrition(2).saturationModifier(0.3f).build())));
    public static final Item CORN = reg("corn", new Item(new Item.Settings()
            .food(new FoodComponent.Builder().nutrition(4).saturationModifier(0.4f).build())));
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
            entries.add(HEMP_FIBER);
            entries.add(CORN_SEEDS);
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(entries -> {
            entries.add(STRAWBERRY);
            entries.add(CORN);
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(entries -> {
            entries.add(ModBlocks.HEMP);
            entries.add(ModBlocks.STRAWBERRY_BUSH);
            entries.add(ModBlocks.CORN);
        });
    }

    private ModItems() {
    }
}
