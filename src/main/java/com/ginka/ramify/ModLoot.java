package com.ginka.ramify;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;

/** 钓鱼时小概率钓到生蟹/生龙虾喵 */
public final class ModLoot {
    public static void register() {
        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
            if (key == LootTables.FISHING_GAMEPLAY) {
                tableBuilder.pool(LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1.0f))
                        .conditionally(RandomChanceLootCondition.builder(0.10f))
                        .with(ItemEntry.builder(ModItems.RAW_CRAB).weight(3))
                        .with(ItemEntry.builder(ModItems.RAW_LOBSTER).weight(2)));
            }
        });
    }

    private ModLoot() {
    }
}
