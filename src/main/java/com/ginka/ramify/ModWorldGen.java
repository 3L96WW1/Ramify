package com.ginka.ramify;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.PlacedFeature;

/** 野外生成：不同群系长不同的纤维植物和蘑菇喵 */
public final class ModWorldGen {
    public static final RegistryKey<PlacedFeature> HEMP_PLACED =
            RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Ramify.MOD_ID, "hemp_patch"));
    public static final RegistryKey<PlacedFeature> CORN_PLACED =
            RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Ramify.MOD_ID, "corn_patch"));
    public static final RegistryKey<PlacedFeature> JUTE_PLACED =
            RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Ramify.MOD_ID, "jute_patch"));
    public static final RegistryKey<PlacedFeature> SISAL_PLACED =
            RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Ramify.MOD_ID, "sisal_patch"));
    public static final RegistryKey<PlacedFeature> STRAW_MUSHROOM_PLACED =
            RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Ramify.MOD_ID, "straw_mushroom_patch"));
    public static final RegistryKey<PlacedFeature> MATSUTAKE_PLACED =
            RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Ramify.MOD_ID, "matsutake_patch"));
    public static final RegistryKey<PlacedFeature> RAPESEED_PLACED =
            RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Ramify.MOD_ID, "rapeseed_patch"));

    public static void register() {
        // 麻叶：草原/草甸，白桦林与热带草原不长
        var plains = BiomeSelectors.includeByKey(BiomeKeys.PLAINS, BiomeKeys.SUNFLOWER_PLAINS, BiomeKeys.MEADOW);
        BiomeModifications.addFeature(plains, GenerationStep.Feature.VEGETAL_DECORATION, HEMP_PLACED);
        BiomeModifications.addFeature(plains, GenerationStep.Feature.VEGETAL_DECORATION, CORN_PLACED);

        // 黄麻：白桦林
        BiomeModifications.addFeature(
                BiomeSelectors.includeByKey(BiomeKeys.BIRCH_FOREST, BiomeKeys.OLD_GROWTH_BIRCH_FOREST),
                GenerationStep.Feature.VEGETAL_DECORATION, JUTE_PLACED);

        // 剑麻：热带草原
        BiomeModifications.addFeature(BiomeSelectors.tag(BiomeTags.IS_SAVANNA),
                GenerationStep.Feature.VEGETAL_DECORATION, SISAL_PLACED);

        // 草菇：森林
        BiomeModifications.addFeature(BiomeSelectors.tag(BiomeTags.IS_FOREST),
                GenerationStep.Feature.VEGETAL_DECORATION, STRAW_MUSHROOM_PLACED);

        // 松茸：雪原/雪林
        BiomeModifications.addFeature(
                BiomeSelectors.includeByKey(BiomeKeys.SNOWY_PLAINS, BiomeKeys.SNOWY_TAIGA, BiomeKeys.SNOWY_BEACH,
                        BiomeKeys.ICE_SPIKES, BiomeKeys.GROVE, BiomeKeys.SNOWY_SLOPES,
                        BiomeKeys.FROZEN_PEAKS, BiomeKeys.JAGGED_PEAKS, BiomeKeys.FROZEN_RIVER),
                GenerationStep.Feature.VEGETAL_DECORATION, MATSUTAKE_PLACED);

        // 油菜花：森林与草原
        BiomeModifications.addFeature(BiomeSelectors.tag(BiomeTags.IS_FOREST),
                GenerationStep.Feature.VEGETAL_DECORATION, RAPESEED_PLACED);
        BiomeModifications.addFeature(plains, GenerationStep.Feature.VEGETAL_DECORATION, RAPESEED_PLACED);
    }

    private ModWorldGen() {
    }
}
