package com.ginka.ramify;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

/** 草莓丛：没有幼株阶段，成熟后采摘，过一段时间重新结果；不可再生（不掉落丛）喵 */
public class StrawberryBushBlock extends Block {
    public static final BooleanProperty RIPE = BooleanProperty.of("ripe");
    private static final VoxelShape SHAPE = Block.createCuboidShape(2, 0, 2, 14, 24, 14);

    public StrawberryBushBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(RIPE, true));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(RIPE);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockState floor = world.getBlockState(pos.down());
        return floor.isOf(net.minecraft.block.Blocks.GRASS_BLOCK) || floor.isOf(net.minecraft.block.Blocks.DIRT)
                || floor.isOf(net.minecraft.block.Blocks.PODZOL) || floor.isOf(net.minecraft.block.Blocks.COARSE_DIRT);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!state.get(RIPE) && world.getLightLevel(pos.up()) >= 9 && random.nextInt(10) == 0) {
            world.setBlockState(pos, state.with(RIPE, true), Block.NOTIFY_LISTENERS);
        }
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!state.get(RIPE)) {
            return ActionResult.PASS;
        }
        if (!world.isClient) {
            int count = 1 + world.random.nextInt(2);
            Block.dropStack(world, pos, new ItemStack(ModItems.STRAWBERRY, count));
            world.setBlockState(pos, state.with(RIPE, false), Block.NOTIFY_LISTENERS);
        }
        return ActionResult.success(world.isClient);
    }
}
