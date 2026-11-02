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
    public static final EntityType<LobsterEntity> LOBSTER = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(Ramify.MOD_ID, "lobster"),
            FabricEntityTypeBuilder.<LobsterEntity>createMob()
                    .spawnGroup(SpawnGroup.CREATURE)
                    .entityFactory(LobsterEntity::new)
                    .dimensions(EntityDimensions.fixed(0.9f, 0.6f))
                    .spawnRestriction(SpawnLocationTypes.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                            CrabEntity::canSpawn)
                    .defaultAttributes(LobsterEntity::createAttributes)
                    .build());

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
        var beaches = BiomeSelectors.includeByKey(BiomeKeys.BEACH, BiomeKeys.SNOWY_BEACH);
        BiomeModifications.addSpawn(beaches, SpawnGroup.CREATURE, CRAB, 30, 1, 4);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.RIVER, BiomeKeys.FROZEN_RIVER),
                SpawnGroup.CREATURE, CRAB, 6, 1, 2);
        BiomeModifications.addSpawn(beaches, SpawnGroup.CREATURE, LOBSTER, 3, 1, 1);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.RIVER, BiomeKeys.FROZEN_RIVER),
                SpawnGroup.CREATURE, LOBSTER, 1, 1, 1);
    }

    private ModEntities() {
    }
}
