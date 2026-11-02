package com.ginka.ramify.client;

import com.ginka.ramify.CrabEntity;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.render.VertexConsumer;

public class CrabModel extends EntityModel<CrabEntity> {
    public static final EntityModelLayer LAYER = new EntityModelLayer(Identifier.of("ramify", "crab"), "main");

    private final ModelPart body;
    private final ModelPart clawLeft;
    private final ModelPart clawRight;
    private final ModelPart eyeLeft;
    private final ModelPart eyeRight;
    private final ModelPart[] legs;

    public CrabModel(ModelPart root) {
        this.body = root.getChild("body");
        this.clawLeft = root.getChild("claw_left");
        this.clawRight = root.getChild("claw_right");
        this.eyeLeft = root.getChild("eye_left");
        this.eyeRight = root.getChild("eye_right");
        this.legs = new ModelPart[]{
                root.getChild("leg_l1"), root.getChild("leg_l2"), root.getChild("leg_l3"),
                root.getChild("leg_r1"), root.getChild("leg_r2"), root.getChild("leg_r3")
        };
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();
        root.addChild("body", ModelPartBuilder.create().uv(0, 0)
                        .cuboid(-5.0f, -2.0f, -4.0f, 10, 5, 8),
                ModelTransform.pivot(0.0f, 20.0f, 0.0f));
        root.addChild("claw_left", ModelPartBuilder.create().uv(0, 16)
                        .cuboid(-2.0f, -1.0f, -1.0f, 3, 2, 5),
                ModelTransform.of(-5.0f, 20.0f, -2.0f, 0.0f, 0.5f, 0.0f));
        root.addChild("claw_right", ModelPartBuilder.create().uv(0, 16)
                        .cuboid(-1.0f, -1.0f, -1.0f, 3, 2, 5),
                ModelTransform.of(5.0f, 20.0f, -2.0f, 0.0f, -0.5f, 0.0f));
        root.addChild("eye_left", ModelPartBuilder.create().uv(40, 0)
                        .cuboid(-0.5f, -0.5f, -0.5f, 1, 1, 1),
                ModelTransform.pivot(-2.0f, 17.5f, -4.2f));
        root.addChild("eye_right", ModelPartBuilder.create().uv(40, 0)
                        .cuboid(-0.5f, -0.5f, -0.5f, 1, 1, 1),
                ModelTransform.pivot(2.0f, 17.5f, -4.2f));
        for (int i = 0; i < 3; i++) {
            float z = -2.0f + i * 2.5f;
            root.addChild("leg_l" + (i + 1), ModelPartBuilder.create().uv(0, 32)
                            .cuboid(-3.0f, 0.0f, -0.5f, 4, 1, 1),
                    ModelTransform.of(-5.0f, 21.0f, z, 0.0f, 0.3f, 0.0f));
            root.addChild("leg_r" + (i + 1), ModelPartBuilder.create().uv(0, 32)
                            .cuboid(-1.0f, 0.0f, -0.5f, 4, 1, 1),
                    ModelTransform.of(5.0f, 21.0f, z, 0.0f, -0.3f, 0.0f));
        }
        return TexturedModelData.of(data, 64, 64);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
        body.render(matrices, vertices, light, overlay, color);
        clawLeft.render(matrices, vertices, light, overlay, color);
        clawRight.render(matrices, vertices, light, overlay, color);
        eyeLeft.render(matrices, vertices, light, overlay, color);
        eyeRight.render(matrices, vertices, light, overlay, color);
        for (ModelPart leg : legs) {
            leg.render(matrices, vertices, light, overlay, color);
        }
    }

    @Override
    public void setAngles(CrabEntity entity, float limbAngle, float limbDistance, float animationProgress,
                          float headYaw, float headPitch) {
        float swing = (float) Math.sin(limbAngle) * 0.4f * limbDistance;
        for (int i = 0; i < legs.length; i++) {
            legs[i].yaw = (i % 2 == 0 ? 0.3f : -0.3f) + (i % 2 == 0 ? swing : -swing);
        }
        clawLeft.roll = (float) Math.sin(animationProgress * 0.1f) * 0.1f;
        clawRight.roll = -clawLeft.roll;
    }
}
