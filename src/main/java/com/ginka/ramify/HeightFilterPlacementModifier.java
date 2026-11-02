package com.ginka.ramify;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.feature.FeaturePlacementContext;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;
import net.minecraft.world.gen.placementmodifier.PlacementModifierType;

import java.util.stream.Stream;

/** 按绝对 Y 过滤已放置的位置，用于普通麻类 Y<120、高山亚麻 Y120~220 的硬限制喵 */
public class HeightFilterPlacementModifier extends PlacementModifier {
    public static final MapCodec<HeightFilterPlacementModifier> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    Codec.INT.fieldOf("min").forGetter(m -> m.min),
                    Codec.INT.fieldOf("max").forGetter(m -> m.max)
            ).apply(instance, HeightFilterPlacementModifier::new));

    public static final PlacementModifierType<HeightFilterPlacementModifier> TYPE =
            Registry.register(Registries.PLACEMENT_MODIFIER_TYPE,
                    Identifier.of(Ramify.MOD_ID, "height_filter"), () -> CODEC);

    private final int min;
    private final int max;

    public HeightFilterPlacementModifier(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public static void init() {
        // 触发类初始化，确保 placement modifier 类型在数据包加载前注册
    }

    @Override
    public Stream<BlockPos> getPositions(FeaturePlacementContext context, Random random, BlockPos pos) {
        return pos.getY() >= this.min && pos.getY() <= this.max ? Stream.of(pos) : Stream.empty();
    }

    @Override
    public PlacementModifierType<?> getType() {
        return TYPE;
    }
}
