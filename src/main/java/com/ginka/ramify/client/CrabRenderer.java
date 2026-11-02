package com.ginka.ramify.client;

import com.ginka.ramify.CrabEntity;
import com.ginka.ramify.Ramify;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;

public class CrabRenderer extends MobEntityRenderer<CrabEntity, CrabModel> {
    public CrabRenderer(EntityRendererFactory.Context context) {
        super(context, new CrabModel(context.getPart(CrabModel.LAYER)), 0.4f);
    }

    @Override
    public Identifier getTexture(CrabEntity entity) {
        return Identifier.of(Ramify.MOD_ID, "textures/entity/crab.png");
    }

    @Override
    public void render(CrabEntity entity, float yaw, float tickDelta, MatrixStack matrices,
                       VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();
        // 螃蟹横着走：模型整体转 90 度
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90.0f));
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
        matrices.pop();
    }
}
