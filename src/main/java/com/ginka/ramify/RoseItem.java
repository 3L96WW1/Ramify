package com.ginka.ramify;

import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

/** 蔷薇：右键其他玩家送出，直接进对方背包并触发成就喵 */
public class RoseItem extends Item {
    public RoseItem(Settings settings) {
        super(settings);
    }

    public static void register() {
        UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (!(entity instanceof LivingEntity living) || entity == player) return ActionResult.PASS;
            if (!(entity instanceof PlayerEntity || entity instanceof VillagerEntity)) return ActionResult.PASS;
            ItemStack stack = player.getStackInHand(hand);
            if (!stack.isOf(ModItems.ROSE)) return ActionResult.PASS;
            return gift(player, living, stack);
        });
    }

    public static ActionResult gift(PlayerEntity user, LivingEntity entity, ItemStack stack) {
        if (entity == user || !(entity instanceof PlayerEntity || entity instanceof VillagerEntity)) {
            return ActionResult.PASS;
        }
        if (!user.getWorld().isClient) {
            if (entity instanceof PlayerEntity target) {
                ItemStack gift = new ItemStack(ModItems.ROSE);
                if (!target.getInventory().insertStack(gift)) target.dropItem(gift, false);
            } else {
                entity.dropStack(new ItemStack(ModItems.ROSE));
            }
            ServerWorld world = (ServerWorld) user.getWorld();
            world.spawnParticles(ParticleTypes.HEART, entity.getX(), entity.getBodyY(0.8), entity.getZ(),
                    5, 0.4, 0.4, 0.4, 0.01);
            world.spawnParticles(ParticleTypes.HEART, user.getX(), user.getBodyY(0.8), user.getZ(),
                    3, 0.3, 0.3, 0.3, 0.01);
            if (!user.isCreative()) stack.decrement(1);
            if (user instanceof ServerPlayerEntity serverUser) {
                var entry = serverUser.server.getAdvancementLoader().get(net.minecraft.util.Identifier.of(Ramify.MOD_ID, "rose_gift"));
                if (entry != null) serverUser.getAdvancementTracker().grantCriterion(entry, "manual");
            }
        }
        return ActionResult.success(user.getWorld().isClient);
    }
}
