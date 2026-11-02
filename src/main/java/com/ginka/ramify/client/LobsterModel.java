package com.ginka.ramify.client;

import com.ginka.ramify.LobsterEntity;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class LobsterModel extends EntityModel<LobsterEntity> {
    public static final EntityModelLayer LAYER = new EntityModelLayer(Identifier.of("ramify", "lobster"), "main");
    private final ModelPart body;
    private final ModelPart tail1, tail2, fan;
    private final ModelPart clawLeft, clawRight;
    private final ModelPart antennaLeft, antennaRight;
    private final ModelPart eyeLeft, eyeRight;
    private final ModelPart[] legs;

    public LobsterModel(ModelPart root) {
        this.body = root.getChild("body");
        this.tail1 = root.getChild("tail1"); this.tail2 = root.getChild("tail2"); this.fan = root.getChild("fan");
        this.clawLeft = root.getChild("claw_left"); this.clawRight = root.getChild("claw_right");
        this.antennaLeft = root.getChild("antenna_left"); this.antennaRight = root.getChild("antenna_right");
        this.eyeLeft = root.getChild("eye_left"); this.eyeRight = root.getChild("eye_right");
        this.legs = new ModelPart[]{root.getChild("leg_l1"), root.getChild("leg_l2"), root.getChild("leg_l3"),
                root.getChild("leg_r1"), root.getChild("leg_r2"), root.getChild("leg_r3")};
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();
        root.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-4, -2, -5, 8, 4, 10),
                ModelTransform.pivot(0, 17, 0));
        root.addChild("tail1", ModelPartBuilder.create().uv(0, 16).cuboid(-3, -1, 0, 6, 2, 3),
                ModelTransform.pivot(0, 17, 5));
        root.addChild("tail2", ModelPartBuilder.create().uv(0, 23).cuboid(-2.5f, -0.8f, 0, 5, 2, 3),
                ModelTransform.pivot(0, 17, 8));
        root.addChild("fan", ModelPartBuilder.create().uv(0, 30).cuboid(-3, -0.5f, 0, 6, 1, 3),
                ModelTransform.pivot(0, 17, 11));
        root.addChild("claw_left", ModelPartBuilder.create().uv(0, 36).cuboid(-2, -1.5f, -1, 4, 3, 5),
                ModelTransform.of(-5, 17, -4, 0, 0.35f, 0));
        root.addChild("claw_right", ModelPartBuilder.create().uv(0, 36).cuboid(-2, -1.5f, -1, 4, 3, 5),
                ModelTransform.of(5, 17, -4, 0, -0.35f, 0));
        root.addChild("antenna_left", ModelPartBuilder.create().uv(48, 0).cuboid(-0.5f, -0.5f, -8, 1, 1, 8),
                ModelTransform.of(-2, 15, -5, 0.25f, 0, 0));
        root.addChild("antenna_right", ModelPartBuilder.create().uv(48, 0).cuboid(-0.5f, -0.5f, -8, 1, 1, 8),
                ModelTransform.of(2, 15, -5, 0.25f, 0, 0));
        root.addChild("eye_left", ModelPartBuilder.create().uv(56, 0).cuboid(-0.5f, -0.5f, -0.5f, 1, 1, 1),
                ModelTransform.pivot(-2, 15.5f, -5));
        root.addChild("eye_right", ModelPartBuilder.create().uv(56, 0).cuboid(-0.5f, -0.5f, -0.5f, 1, 1, 1),
                ModelTransform.pivot(2, 15.5f, -5));
        for (int i = 0; i < 3; i++) {
            float z = -2 + i * 3;
            root.addChild("leg_l" + (i + 1), ModelPartBuilder.create().uv(0, 46).cuboid(-4, 0, -0.5f, 4, 1, 1),
                    ModelTransform.of(-4, 18, z, 0, 0.3f, 0));
            root.addChild("leg_r" + (i + 1), ModelPartBuilder.create().uv(0, 46).cuboid(0, 0, -0.5f, 4, 1, 1),
                    ModelTransform.of(4, 18, z, 0, -0.3f, 0));
        }
        return TexturedModelData.of(data, 64, 64);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
        body.render(matrices, vertices, light, overlay, color);
        tail1.render(matrices, vertices, light, overlay, color);
        tail2.render(matrices, vertices, light, overlay, color);
        fan.render(matrices, vertices, light, overlay, color);
        clawLeft.render(matrices, vertices, light, overlay, color);
        clawRight.render(matrices, vertices, light, overlay, color);
        antennaLeft.render(matrices, vertices, light, overlay, color);
        antennaRight.render(matrices, vertices, light, overlay, color);
        eyeLeft.render(matrices, vertices, light, overlay, color);
        eyeRight.render(matrices, vertices, light, overlay, color);
        for (ModelPart leg : legs) leg.render(matrices, vertices, light, overlay, color);
    }

    @Override
    public void setAngles(LobsterEntity entity, float limbAngle, float limbDistance, float animationProgress,
                          float headYaw, float headPitch) {
        float swing = (float) Math.sin(limbAngle) * 0.35f * limbDistance;
        for (int i = 0; i < legs.length; i++) {
            legs[i].yaw = (i % 2 == 0 ? 0.3f : -0.3f) + (i % 2 == 0 ? swing : -swing);
        }
        tail1.pitch = 0.15f + (float) Math.sin(animationProgress * 0.08f) * 0.08f;
        tail2.pitch = 0.12f;
        fan.pitch = 0.1f;
        antennaLeft.roll = (float) Math.sin(animationProgress * 0.1f) * 0.06f;
        antennaRight.roll = -antennaLeft.roll;
    }
}
