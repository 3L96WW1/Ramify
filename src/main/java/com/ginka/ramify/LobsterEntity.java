package com.ginka.ramify;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

/** 龙虾：钓鱼获得，也可在沙滩/河边少量生成喵 */
public class LobsterEntity extends CrabEntity {
    public LobsterEntity(EntityType<? extends LobsterEntity> type, World world) {
        super(type, world);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return CrabEntity.createAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 4.0);
    }

    @Override
    public RegistryKey<net.minecraft.loot.LootTable> getLootTableId() {
        return RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.of(Ramify.MOD_ID, "entities/lobster"));
    }
}
