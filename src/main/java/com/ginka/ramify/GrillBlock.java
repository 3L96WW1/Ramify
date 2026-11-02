package com.ginka.ramify;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

/** 3×2 户外烧烤架的多方块外壳；控制器在底部中间喵 */
public class GrillBlock extends HorizontalFacingBlock implements BlockEntityProvider {
    public enum Part implements StringIdentifiable {
        BOTTOM_LEFT, BOTTOM_MIDDLE, BOTTOM_RIGHT, TOP_LEFT, TOP_MIDDLE, TOP_RIGHT;
        @Override public String asString() { return name().toLowerCase(); }
    }

    public static final EnumProperty<Part> PART = EnumProperty.of("part", Part.class);
    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
    private static final VoxelShape FULL = VoxelShapes.fullCube();

    public GrillBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState()
                .with(PART, Part.BOTTOM_MIDDLE).with(FACING, Direction.NORTH));
    }

    @Override
    protected MapCodec<? extends HorizontalFacingBlock> getCodec() {
        return MapCodec.unit(this);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(PART, FACING);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return FULL;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return FULL;
    }

    @Override
    public <T extends BlockEntity> net.minecraft.block.entity.BlockEntityTicker<T> getTicker(
            World world, BlockState state, net.minecraft.block.entity.BlockEntityType<T> type) {
        if (state.get(PART) != Part.BOTTOM_MIDDLE) return null;
        return world.isClient ? null : (w, pos, s, be) -> GrillBlockEntity.tick(w, pos, s, (GrillBlockEntity) be);
    }

    @Override
    public net.minecraft.block.BlockRenderType getRenderType(BlockState state) {
        return state.get(PART) == Part.BOTTOM_MIDDLE
                ? net.minecraft.block.BlockRenderType.MODEL
                : net.minecraft.block.BlockRenderType.INVISIBLE;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return state.get(PART) == Part.BOTTOM_MIDDLE ? new GrillBlockEntity(pos, state) : null;
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!world.isClient) {
            BlockPos controller = findController(world, pos, state);
            if (controller != null && world.getBlockEntity(controller) instanceof GrillBlockEntity grill) {
                player.openHandledScreen(grill);
            }
        }
        return ActionResult.success(world.isClient);
    }

    @Override
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!world.isClient) {
            breakStructure(world, pos, state, player);
        }
        return super.onBreak(world, pos, state, player);
    }

    public static @Nullable BlockPos findController(World world, BlockPos pos, BlockState state) {
        if (state.get(PART) == Part.BOTTOM_MIDDLE) return pos;
        Direction facing = state.get(FACING);
        Direction right = facing.rotateYClockwise();
        for (int dx = -2; dx <= 2; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                for (int dz = -2; dz <= 2; dz++) {
                    BlockPos p = pos.add(dx, dy, dz);
                    BlockState s = world.getBlockState(p);
                    if (s.isOf(ModBlocks.GRILL) && s.get(PART) == Part.BOTTOM_MIDDLE
                            && s.get(FACING) == facing) return p;
                }
            }
        }
        return null;
    }

    public static void breakStructure(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        BlockPos controller = findController(world, pos, state);
        if (controller == null) return;
        BlockState c = world.getBlockState(controller);
        Direction facing = c.get(FACING);
        Direction right = facing.rotateYClockwise();
        BlockPos[] positions = new BlockPos[]{
                controller, controller.offset(right), controller.offset(right.getOpposite())
        };
        for (BlockPos p : positions) {
            if (world.getBlockState(p).isOf(ModBlocks.GRILL)) {
                world.setBlockState(p, net.minecraft.block.Blocks.AIR.getDefaultState(), Block.NOTIFY_ALL);
            }
        }
        if (player == null || !player.isCreative()) {
            Block.dropStack(world, controller, new ItemStack(ModBlocks.GRILL_ITEM));
        }
    }
}
