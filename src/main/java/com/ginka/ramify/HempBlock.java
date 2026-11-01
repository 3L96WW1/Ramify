package com.ginka.ramify;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

/** 麻：成熟后像高草丛一样占两格，最多两格喵 */
public class HempBlock extends Block {
    public static final IntProperty AGE = IntProperty.of("age", 0, 3);
    public static final EnumProperty<BlockHalf> HALF = EnumProperty.of("half", BlockHalf.class);
    private static final VoxelShape SHAPE = Block.createCuboidShape(2, 0, 2, 14, 16, 14);

    public HempBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState()
                .with(AGE, 0).with(HALF, BlockHalf.BOTTOM));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE, HALF);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        if (state.get(HALF) == BlockHalf.TOP) {
            BlockState below = world.getBlockState(pos.down());
            return below.isOf(this) && below.get(HALF) == BlockHalf.BOTTOM;
        }
        BlockState floor = world.getBlockState(pos.down());
        return floor.isOf(net.minecraft.block.Blocks.GRASS_BLOCK)
                || floor.isOf(net.minecraft.block.Blocks.DIRT)
                || floor.isOf(net.minecraft.block.Blocks.COARSE_DIRT)
                || floor.isOf(net.minecraft.block.Blocks.PODZOL);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        if (!world.isClient && state.get(HALF) == BlockHalf.BOTTOM && state.get(AGE) == 3
                && world.getBlockState(pos.up()).isAir()) {
            world.setBlockState(pos.up(), state.with(HALF, BlockHalf.TOP), Block.NOTIFY_ALL);
        }
    }

    @Override
    protected BlockState getStateForNeighborUpdate(BlockState state, net.minecraft.util.math.Direction direction,
                                                   BlockState neighborState, net.minecraft.world.WorldAccess world,
                                                   BlockPos pos, BlockPos neighborPos) {
        if (!this.canPlaceAt(state, world, pos)) {
            return net.minecraft.block.Blocks.AIR.getDefaultState();
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (state.get(HALF) != BlockHalf.BOTTOM) {
            return;
        }
        if (world.getLightLevel(pos.up()) < 9 || random.nextInt(6) != 0) {
            return;
        }
        int age = state.get(AGE);
        if (age < 3) {
            BlockState grown = state.with(AGE, age + 1);
            world.setBlockState(pos, grown, Block.NOTIFY_LISTENERS);
            if (age + 1 == 3 && world.getBlockState(pos.up()).isAir()) {
                world.setBlockState(pos.up(), grown.with(HALF, BlockHalf.TOP), Block.NOTIFY_LISTENERS);
            }
        } else if (world.getBlockState(pos.up()).isAir()) {
            // 顶端被破坏后补回来
            world.setBlockState(pos.up(), state.with(HALF, BlockHalf.TOP), Block.NOTIFY_LISTENERS);
        }
    }
}
