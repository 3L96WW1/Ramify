package com.ginka.ramify.client;

import com.ginka.ramify.ModBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import com.ginka.ramify.ModEntities;
import net.minecraft.client.render.RenderLayer;

public class RamifyClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // 十字模型必须走 cutout，否则透明区域会渲染成黑色喵
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.HEMP, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.COLD_HEMP, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ALPINE_FLAX, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.JUTE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SISAL, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.STRAWBERRY_BUSH, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CORN, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WILD_CORN, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.STRAW_MUSHROOM, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.MATSUTAKE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.RAPESEED, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WOODEN_SPIKES, RenderLayer.getCutout());
        EntityModelLayerRegistry.registerModelLayer(CrabModel.LAYER, CrabModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.CRAB, CrabRenderer::new);
        EntityRendererRegistry.register(ModEntities.LOBSTER, LobsterRenderer::new);
        GrillScreen.register();
    }
}
