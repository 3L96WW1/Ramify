package com.ginka.ramify.client;

import com.ginka.ramify.LobsterEntity;
import com.ginka.ramify.Ramify;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;

public class LobsterRenderer extends MobEntityRenderer<LobsterEntity, CrabModel<LobsterEntity>> {
    public LobsterRenderer(EntityRendererFactory.Context context) {
        super(context, new CrabModel<LobsterEntity>(context.getPart(CrabModel.LAYER)), 0.4f);
    }

    @Override
    public Identifier getTexture(LobsterEntity entity) {
        return Identifier.of(Ramify.MOD_ID, "textures/entity/lobster.png");
    }

    @Override
    public void render(LobsterEntity entity, float yaw, float tickDelta, MatrixStack matrices,
                       VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90.0f));
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
        matrices.pop();
    }
}
