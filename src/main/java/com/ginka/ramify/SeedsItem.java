package com.ginka.ramify;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/** 种子：右键耕地种下作物喵 */
public class SeedsItem extends Item {
    private final Block crop;

    public SeedsItem(Settings settings, Block crop) {
        super(settings);
        this.crop = crop;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        if (!world.getBlockState(pos).isOf(net.minecraft.block.Blocks.FARMLAND)
                || !world.getBlockState(pos.up()).isAir()) {
            return ActionResult.PASS;
        }
        ItemStack stack = context.getStack();
        if (!world.isClient) {
            world.setBlockState(pos.up(), this.crop.getDefaultState(), Block.NOTIFY_ALL);
            if (context.getPlayer() != null && !context.getPlayer().getAbilities().creativeMode) {
                stack.decrement(1);
            }
        }
        return ActionResult.success(world.isClient);
    }
}
