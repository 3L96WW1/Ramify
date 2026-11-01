package com.ginka.ramify;

import net.fabricmc.api.ModInitializer;

/** 麻 / Ramify —— 绷带与野外植物（Minecraft 1.21.1 Fabric）喵 */
public class Ramify implements ModInitializer {
    public static final String MOD_ID = "ramify";

    @Override
    public void onInitialize() {
        ModSounds.register();
        ModEffects.register();
        ModBlocks.register();
        ModItems.register();
        ModWorldGen.register();
    }
}
