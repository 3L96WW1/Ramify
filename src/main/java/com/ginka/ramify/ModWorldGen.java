package com.ginka.ramify;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.PlacedFeature;

/** 野外生成：草地上少量麻和玉米（数据在 data/ramify/worldgen/）喵 */
public final class ModWorldGen {
    public static final RegistryKey<PlacedFeature> HEMP_PLACED =
            RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Ramify.MOD_ID, "hemp_patch"));
    public static final RegistryKey<PlacedFeature> CORN_PLACED =
            RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Ramify.MOD_ID, "corn_patch"));

    public static void register() {
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(),
                GenerationStep.Feature.VEGETAL_DECORATION, HEMP_PLACED);
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(),
                GenerationStep.Feature.VEGETAL_DECORATION, CORN_PLACED);
    }

    private ModWorldGen() {
    }
}
