package com.ginka.ramify;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Fertilizable;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.item.ItemPlacementContext;

/** 麻：始终两格高，上下同步破坏喵 */
public class HempBlock extends Block implements Fertilizable {
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
        return isInvisibleTop(state) ? VoxelShapes.empty() : SHAPE;
    }


    private static boolean isInvisibleTop(BlockState state) {
        return state.get(HALF) == BlockHalf.TOP && state.get(AGE) < 3;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState state = super.getPlacementState(ctx);
        return state == null ? null : state.with(AGE, 3);
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
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        super.onBlockAdded(state, world, pos, oldState, notify);
        if (!world.isClient && state.get(HALF) == BlockHalf.BOTTOM && world.getBlockState(pos.up()).isAir()) {
            BlockState top = state.with(HALF, BlockHalf.TOP);
            if (top.canPlaceAt(world, pos.up())) {
                world.setBlockState(pos.up(), top, Block.NOTIFY_ALL);
            }
        }
    }

    @Override
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!world.isClient) {
            boolean top = state.get(HALF) == BlockHalf.TOP;
            BlockPos otherPos = top ? pos.down() : pos.up();
            BlockState other = world.getBlockState(otherPos);
            if (other.isOf(this) && other.get(HALF) != state.get(HALF)) {
                if (top) {
                    world.breakBlock(otherPos, player == null || !player.isCreative(), player);
                } else {
                    world.setBlockState(otherPos, net.minecraft.block.Blocks.AIR.getDefaultState(), Block.NOTIFY_ALL);
                }
            }
        }
        return super.onBreak(world, pos, state, player);
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
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state) {
        return state.get(HALF) == BlockHalf.BOTTOM && state.get(AGE) < 3;
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        if (state.get(HALF) != BlockHalf.BOTTOM || state.get(AGE) >= 3) {
            return;
        }
        int age = state.get(AGE) + 1;
        BlockState grown = state.with(AGE, age);
        world.setBlockState(pos, grown, Block.NOTIFY_LISTENERS);
        BlockState above = world.getBlockState(pos.up());
        if (above.isOf(this) && above.get(HALF) == BlockHalf.TOP) {
            world.setBlockState(pos.up(), above.with(AGE, age), Block.NOTIFY_LISTENERS);
        }
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
        BlockState above = world.getBlockState(pos.up());
        if (age < 3) {
            BlockState grown = state.with(AGE, age + 1);
            world.setBlockState(pos, grown, Block.NOTIFY_LISTENERS);
            if (above.isOf(this) && above.get(HALF) == BlockHalf.TOP) {
                world.setBlockState(pos.up(), above.with(AGE, age + 1), Block.NOTIFY_LISTENERS);
            }
        } else if (above.isAir()) {
            world.setBlockState(pos.up(), state.with(HALF, BlockHalf.TOP), Block.NOTIFY_LISTENERS);
        }
    }

    @Override
    public void onPlaced(net.minecraft.world.World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        if (!world.isClient && state.get(HALF) == BlockHalf.BOTTOM && world.getBlockState(pos.up()).isAir()) {
            BlockState top = state.with(HALF, BlockHalf.TOP);
            if (top.canPlaceAt(world, pos.up())) {
                world.setBlockState(pos.up(), top, Block.NOTIFY_ALL);
            }
        }
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!world.isClient && state.get(HALF) == BlockHalf.TOP && !newState.isOf(this)) {
            BlockPos below = pos.down();
            BlockState bottom = world.getBlockState(below);
            if (bottom.isOf(this) && bottom.get(HALF) == BlockHalf.BOTTOM) {
                world.setBlockState(below, net.minecraft.block.Blocks.AIR.getDefaultState(), Block.NOTIFY_ALL);
            }
        }
        super.onStateReplaced(state, world, pos, newState, moved);
    }
}
