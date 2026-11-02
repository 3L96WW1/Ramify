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
import net.minecraft.world.WorldView;

/** 玉米：像甘蔗一样两格高，上下同步破坏；玩家种的只能放耕地，野生的能长草地喵 */
public class CornBlock extends TallPlantBlock {
    public static final IntProperty AGE = IntProperty.of("age", 0, 7);

    private final boolean wild;

    public CornBlock(Settings settings, boolean wild) {
        super(settings);
        this.wild = wild;
        this.setDefaultState(this.stateManager.getDefaultState().with(AGE, 0));
    }

    @Override
    protected void appendProperties(StateManager.Builder<net.minecraft.block.Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(AGE);
    }

    @Override
    protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        if (state.get(HALF) == DoubleBlockHalf.UPPER) {
            BlockState below = world.getBlockState(pos.down());
            return below.isOf(this) && below.get(HALF) == DoubleBlockHalf.LOWER;
        }
        BlockState floor = world.getBlockState(pos.down());
        if (wild) {
            return floor.isOf(net.minecraft.block.Blocks.GRASS_BLOCK)
                    || floor.isOf(net.minecraft.block.Blocks.DIRT)
                    || floor.isOf(net.minecraft.block.Blocks.COARSE_DIRT)
                    || floor.isOf(net.minecraft.block.Blocks.PODZOL);
        }
        return floor.isOf(net.minecraft.block.Blocks.FARMLAND);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (state.get(HALF) != DoubleBlockHalf.LOWER) {
            return;
        }
        if (world.getLightLevel(pos.up()) < 9 || random.nextInt(5) != 0) {
            return;
        }
        int age = state.get(AGE);
        BlockState above = world.getBlockState(pos.up());
        if (age < 7) {
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
