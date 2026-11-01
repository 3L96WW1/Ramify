package com.ginka.ramify;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

/** 木刺：地面生物踩上去受伤并被推开喵 */
public class WoodenSpikesBlock extends Block {
    private static final VoxelShape SHAPE = Block.createCuboidShape(0, 0, 0, 16, 3, 16);

    public WoodenSpikesBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (world.isClient || !(entity instanceof MobEntity mob) || entity instanceof PlayerEntity) {
            return;
        }
        if (world.getTime() % 20 == 0) {
            mob.damage(world.getDamageSources().generic(), 2.0f);
            Vec3d push = entity.getPos().subtract(Vec3d.ofCenter(pos)).normalize().multiply(0.6);
            entity.setVelocity(entity.getVelocity().x + push.x, 0.35, entity.getVelocity().z + push.z);
            entity.velocityModified = true;
        }
    }
}
