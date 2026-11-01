package com.ginka.ramify;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public final class ModSounds {
    public static final SoundEvent BANDAGE_USE = SoundEvent.of(Identifier.of(Ramify.MOD_ID, "bandage_use"));

    public static void register() {
        Registry.register(Registries.SOUND_EVENT, Identifier.of(Ramify.MOD_ID, "bandage_use"), BANDAGE_USE);
    }

    private ModSounds() {
    }
}
