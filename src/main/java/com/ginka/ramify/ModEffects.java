package com.ginka.ramify;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

/** 自定义状态效果喵 */
public final class ModEffects {
    public static final StatusEffect ANTI_INFECTION = new AntiInfectionEffect();
    public static final StatusEffect WARMTH = new WarmthEffect();
    public static final StatusEffect HEARTY = new HeartyEffect();

    public static RegistryEntry<StatusEffect> antiInfection() {
        return Registries.STATUS_EFFECT.getEntry(ANTI_INFECTION);
    }

    public static RegistryEntry<StatusEffect> warmth() {
        return Registries.STATUS_EFFECT.getEntry(WARMTH);
    }

    public static RegistryEntry<StatusEffect> hearty() {
        return Registries.STATUS_EFFECT.getEntry(HEARTY);
    }

    public static void register() {
        Registry.register(Registries.STATUS_EFFECT, Identifier.of(Ramify.MOD_ID, "anti_infection"), ANTI_INFECTION);
        Registry.register(Registries.STATUS_EFFECT, Identifier.of(Ramify.MOD_ID, "warmth"), WARMTH);
        Registry.register(Registries.STATUS_EFFECT, Identifier.of(Ramify.MOD_ID, "hearty"), HEARTY);
    }

    private ModEffects() {
    }
}
