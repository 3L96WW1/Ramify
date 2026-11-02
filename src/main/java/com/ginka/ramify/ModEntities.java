package com.ginka.ramify;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnLocationTypes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.Heightmap;

public final class ModEntities {
    public static final EntityType<CrabEntity> CRAB = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(Ramify.MOD_ID, "crab"),
            FabricEntityTypeBuilder.<CrabEntity>createMob()
                    .spawnGroup(SpawnGroup.CREATURE)
                    .entityFactory(CrabEntity::new)
                    .dimensions(EntityDimensions.fixed(0.9f, 0.6f))
                    .spawnRestriction(SpawnLocationTypes.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                            CrabEntity::canSpawn)
                    .defaultAttributes(CrabEntity::createAttributes)
                    .build());

    public static void register() {
        var beaches = BiomeSelectors.includeByKey(BiomeKeys.BEACH, BiomeKeys.SNOWY_BEACH, BiomeKeys.RIVER);
        BiomeModifications.addSpawn(beaches, SpawnGroup.CREATURE, CRAB, 20, 1, 4);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.RIVER), SpawnGroup.CREATURE,
                CRAB, 4, 1, 2);
    }

    private ModEntities() {
    }
}
