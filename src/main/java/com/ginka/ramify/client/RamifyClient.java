package com.ginka.ramify.client;

import com.ginka.ramify.ModBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;

public class RamifyClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // 十字形模型必须走 cutout，否则透明像素会被画成黑色喵
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.HEMP, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CORN, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.STRAWBERRY_BUSH, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WOODEN_SPIKES, RenderLayer.getCutout());
    }
}
