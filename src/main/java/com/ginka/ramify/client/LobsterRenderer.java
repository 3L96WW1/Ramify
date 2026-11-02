package com.ginka.ramify.client;

import com.ginka.ramify.LobsterEntity;
import com.ginka.ramify.Ramify;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class LobsterRenderer extends MobEntityRenderer<LobsterEntity, LobsterModel> {
    public LobsterRenderer(EntityRendererFactory.Context context) {
        super(context, new LobsterModel(context.getPart(LobsterModel.LAYER)), 0.4f);
    }

    @Override
    public Identifier getTexture(LobsterEntity entity) {
        return Identifier.of(Ramify.MOD_ID, "textures/entity/lobster.png");
    }

}
