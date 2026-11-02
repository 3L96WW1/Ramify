package com.ginka.ramify;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

/** 草莓丛：不可再生，打掉获得草莓；不再右键采摘喵 */
public class StrawberryBushBlock extends Block {
    private static final VoxelShape SHAPE = Block.createCuboidShape(2, 0, 2, 14, 24, 14);

    public StrawberryBushBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockState floor = world.getBlockState(pos.down());
        return floor.isOf(net.minecraft.block.Blocks.GRASS_BLOCK)
                || floor.isOf(net.minecraft.block.Blocks.DIRT)
                || floor.isOf(net.minecraft.block.Blocks.PODZOL)
                || floor.isOf(net.minecraft.block.Blocks.COARSE_DIRT);
    }

    @Override
    protected BlockState getStateForNeighborUpdate(BlockState state, net.minecraft.util.math.Direction direction,
                                                   BlockState neighborState, WorldAccess world,
                                                   BlockPos pos, BlockPos neighborPos) {
        if (!canPlaceAt(state, world, pos)) {
            return net.minecraft.block.Blocks.AIR.getDefaultState();
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }
}
