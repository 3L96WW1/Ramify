package com.ginka.ramify;

import net.minecraft.block.BlockState;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;

/** 高山亚麻：能长在草甸/丘陵/山地石面上喵 */
public class AlpineFlaxBlock extends HempBlock {
    public AlpineFlaxBlock(Settings settings) {
        super(settings);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        if (state.get(HALF) == BlockHalf.TOP) {
            return super.canPlaceAt(state, world, pos);
        }
        BlockState floor = world.getBlockState(pos.down());
        return floor.isOf(net.minecraft.block.Blocks.GRASS_BLOCK)
                || floor.isOf(net.minecraft.block.Blocks.DIRT)
                || floor.isOf(net.minecraft.block.Blocks.COARSE_DIRT)
                || floor.isOf(net.minecraft.block.Blocks.PODZOL)
                || floor.isOf(net.minecraft.block.Blocks.STONE)
                || floor.isOf(net.minecraft.block.Blocks.GRANITE)
                || floor.isOf(net.minecraft.block.Blocks.DIORITE)
                || floor.isOf(net.minecraft.block.Blocks.ANDESITE)
                || floor.isOf(net.minecraft.block.Blocks.GRAVEL)
                || floor.isOf(net.minecraft.block.Blocks.TUFF)
                || floor.isOf(net.minecraft.block.Blocks.CALCITE);
    }
}
