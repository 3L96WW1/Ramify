package com.ginka.ramify;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

/** 放置 3×2 烤架结构喵 */
public class GrillItem extends BlockItem {
    public GrillItem(Block block, Settings settings) {
        super(block, settings);
    }

    @Override
    public net.minecraft.util.ActionResult place(ItemPlacementContext context) {
        World world = context.getWorld();
        // getBlockPos() 已经是“将要放置的位置”（点击方块 + 面），不要再 offset 一次
        BlockPos controller = context.getBlockPos();
        Direction facing = context.getHorizontalPlayerFacing().getOpposite();
        Direction right = facing.rotateYClockwise();
        // 控制器放在底排中间，整体宽 3 高 2
        BlockPos[] positions = new BlockPos[]{
                controller, controller.offset(right), controller.offset(right.getOpposite())
        };
        for (BlockPos p : positions) {
            if (!world.getBlockState(p).isReplaceable()) return net.minecraft.util.ActionResult.FAIL;
        }
        for (BlockPos p : positions) {
            if (!world.getBlockState(p.down()).isSolidBlock(world, p.down())) return net.minecraft.util.ActionResult.FAIL;
        }
        if (!world.isClient) {
            world.setBlockState(controller, ModBlocks.GRILL.getDefaultState()
                    .with(GrillBlock.PART, GrillBlock.Part.BOTTOM_MIDDLE).with(GrillBlock.FACING, facing));
            world.setBlockState(controller.offset(right), ModBlocks.GRILL.getDefaultState()
                    .with(GrillBlock.PART, GrillBlock.Part.BOTTOM_RIGHT).with(GrillBlock.FACING, facing));
            world.setBlockState(controller.offset(right.getOpposite()), ModBlocks.GRILL.getDefaultState()
                    .with(GrillBlock.PART, GrillBlock.Part.BOTTOM_LEFT).with(GrillBlock.FACING, facing));
        }
        context.getStack().decrement(1);
        return net.minecraft.util.ActionResult.success(world.isClient);
    }
}
