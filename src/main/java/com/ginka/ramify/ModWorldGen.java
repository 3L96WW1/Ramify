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
    public static final RegistryKey<PlacedFeature> STRAWBERRY_COMMON =
            RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Ramify.MOD_ID, "strawberry_bush_common"));
    public static final RegistryKey<PlacedFeature> STRAWBERRY_RIVER =
            RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Ramify.MOD_ID, "strawberry_bush_river"));
    public static final RegistryKey<PlacedFeature> STRAWBERRY_RARE =
            RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Ramify.MOD_ID, "strawberry_bush_rare"));
    public static final RegistryKey<PlacedFeature> RAPESEED_JUNGLE =
            RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Ramify.MOD_ID, "rapeseed_jungle_patch"));
    public static final RegistryKey<PlacedFeature> COLD_HEMP_PLACED =
            RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Ramify.MOD_ID, "cold_hemp_patch"));
    public static final RegistryKey<PlacedFeature> ALPINE_FLAX_PLACED =
            RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Ramify.MOD_ID, "alpine_flax_patch"));
    public static final RegistryKey<PlacedFeature> ALPINE_FLAX_CHERRY =
            RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Ramify.MOD_ID, "alpine_flax_cherry"));

    public static void register() {
        // 麻：草原/草甸/普通森林，白桦林与热带草原不长
        var plains = BiomeSelectors.includeByKey(BiomeKeys.PLAINS, BiomeKeys.SUNFLOWER_PLAINS, BiomeKeys.MEADOW);
        BiomeModifications.addFeature(plains, GenerationStep.Feature.VEGETAL_DECORATION, HEMP_PLACED);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.FOREST, BiomeKeys.FLOWER_FOREST,
                BiomeKeys.TAIGA), GenerationStep.Feature.VEGETAL_DECORATION, HEMP_PLACED);
        BiomeModifications.addFeature(plains, GenerationStep.Feature.VEGETAL_DECORATION, CORN_PLACED);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.FOREST, BiomeKeys.FLOWER_FOREST),
                GenerationStep.Feature.VEGETAL_DECORATION, CORN_PLACED);

        // 黄麻：白桦林，沼泽同等概率
        BiomeModifications.addFeature(
                BiomeSelectors.includeByKey(BiomeKeys.BIRCH_FOREST, BiomeKeys.OLD_GROWTH_BIRCH_FOREST),
                GenerationStep.Feature.VEGETAL_DECORATION, JUTE_PLACED);
        BiomeModifications.addFeature(
                BiomeSelectors.includeByKey(BiomeKeys.SWAMP, BiomeKeys.MANGROVE_SWAMP),
                GenerationStep.Feature.VEGETAL_DECORATION, JUTE_PLACED);

        // 剑麻：热带草原
        BiomeModifications.addFeature(BiomeSelectors.tag(BiomeTags.IS_SAVANNA),
                GenerationStep.Feature.VEGETAL_DECORATION, SISAL_PLACED);

        // 草菇：森林
        BiomeModifications.addFeature(BiomeSelectors.tag(BiomeTags.IS_FOREST),
                GenerationStep.Feature.VEGETAL_DECORATION, STRAW_MUSHROOM_PLACED);

        // 松茸：雪原/雪林
        var snowy = BiomeSelectors.includeByKey(BiomeKeys.SNOWY_PLAINS, BiomeKeys.SNOWY_TAIGA, BiomeKeys.SNOWY_BEACH,
                BiomeKeys.ICE_SPIKES, BiomeKeys.GROVE, BiomeKeys.SNOWY_SLOPES,
                BiomeKeys.FROZEN_PEAKS, BiomeKeys.JAGGED_PEAKS, BiomeKeys.FROZEN_RIVER);
        BiomeModifications.addFeature(snowy, GenerationStep.Feature.VEGETAL_DECORATION, MATSUTAKE_PLACED);
        BiomeModifications.addFeature(snowy, GenerationStep.Feature.VEGETAL_DECORATION, COLD_HEMP_PLACED);

        // 高山亚麻：Y 96~140，所有非雪地群系
        var alpine = BiomeSelectors.foundInOverworld().and(ctx -> {
            String path = ctx.getBiomeKey().getValue().getPath();
            return !path.contains("snow") && !path.contains("frozen") && !path.contains("ice")
                    && !path.equals("grove") && !path.equals("jagged_peaks");
        });
        BiomeModifications.addFeature(alpine, GenerationStep.Feature.VEGETAL_DECORATION, ALPINE_FLAX_PLACED);

        // 油菜花：森林与草原
        BiomeModifications.addFeature(BiomeSelectors.tag(BiomeTags.IS_FOREST),
                GenerationStep.Feature.VEGETAL_DECORATION, RAPESEED_PLACED);
        BiomeModifications.addFeature(plains, GenerationStep.Feature.VEGETAL_DECORATION, RAPESEED_PLACED);
        BiomeModifications.addFeature(
                BiomeSelectors.includeByKey(BiomeKeys.JUNGLE, BiomeKeys.SPARSE_JUNGLE, BiomeKeys.BAMBOO_JUNGLE),
                GenerationStep.Feature.VEGETAL_DECORATION, RAPESEED_JUNGLE);

        // 草莓丛：白桦林/草甸/森林/向日葵平原常见，河岸与少数林地为低权重
        BiomeModifications.addFeature(
                BiomeSelectors.includeByKey(BiomeKeys.BIRCH_FOREST, BiomeKeys.OLD_GROWTH_BIRCH_FOREST,
                        BiomeKeys.FOREST, BiomeKeys.FLOWER_FOREST, BiomeKeys.MEADOW, BiomeKeys.SUNFLOWER_PLAINS),
                GenerationStep.Feature.VEGETAL_DECORATION, STRAWBERRY_COMMON);
        BiomeModifications.addFeature(
                BiomeSelectors.includeByKey(BiomeKeys.RIVER, BiomeKeys.BEACH),
                GenerationStep.Feature.VEGETAL_DECORATION, STRAWBERRY_RIVER);
        BiomeModifications.addFeature(
                BiomeSelectors.includeByKey(BiomeKeys.CHERRY_GROVE, BiomeKeys.DARK_FOREST, BiomeKeys.SWAMP,
                        BiomeKeys.MANGROVE_SWAMP, BiomeKeys.TAIGA, BiomeKeys.OLD_GROWTH_PINE_TAIGA,
                        BiomeKeys.OLD_GROWTH_SPRUCE_TAIGA),
                GenerationStep.Feature.VEGETAL_DECORATION, STRAWBERRY_RARE);
    }

    private ModWorldGen() {
    }
}
