package com.ginka.ramify;

import net.minecraft.block.BlockState;
import net.minecraft.block.TallPlantBlock;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

/** 麻：像玫瑰丛一样始终两格高，上下同步破坏喵 */
public class HempBlock extends TallPlantBlock {
    public static final IntProperty AGE = IntProperty.of("age", 0, 3);

    public HempBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(AGE, 0));
    }

    @Override
    protected void appendProperties(StateManager.Builder<net.minecraft.block.Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(AGE);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (state.get(HALF) != DoubleBlockHalf.LOWER) {
            return;
        }
        if (world.getLightLevel(pos.up()) < 9 || random.nextInt(6) != 0) {
            return;
        }
        int age = state.get(AGE);
        BlockState above = world.getBlockState(pos.up());
        if (age < 3) {
            BlockState grown = state.with(AGE, age + 1);
            world.setBlockState(pos, grown, net.minecraft.block.Block.NOTIFY_LISTENERS);
            if (above.isOf(this) && above.get(HALF) == DoubleBlockHalf.UPPER) {
                world.setBlockState(pos.up(), above.with(AGE, age + 1), net.minecraft.block.Block.NOTIFY_LISTENERS);
            }
        } else if (above.isAir()) {
            world.setBlockState(pos.up(), state.with(HALF, DoubleBlockHalf.UPPER), net.minecraft.block.Block.NOTIFY_LISTENERS);
        }
    }
}
