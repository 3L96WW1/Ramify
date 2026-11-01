package com.ginka.ramify;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

/** 绷带：像吃东西一样用几秒，回血 + 5 秒抗性 + 移除一个负面效果喵 */
public class BandageItem extends Item {
    private static final int USE_TICKS = 32;

    public BandageItem(Settings settings) {
        super(settings);
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return USE_TICKS;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.EAT;   // 借用进食的"缓慢 + 晃动"表现，但没有碎屑粒子 喵
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        user.setCurrentHand(hand);
        if (!world.isClient) {
            world.playSound(null, user.getBlockPos(), ModSounds.BANDAGE_USE, SoundCategory.PLAYERS, 1.0f, 1.0f);
        }
        return TypedActionResult.consume(user.getStackInHand(hand));
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (!world.isClient && user instanceof PlayerEntity player) {
            player.heal(4.0f);
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 100, 0));
            removeOneNegative(player);
            player.getItemCooldownManager().set(this, 40);
            if (!player.getAbilities().creativeMode) {
                stack.decrement(1);
            }
        }
        return stack;
    }

    private static void removeOneNegative(PlayerEntity player) {
        for (StatusEffectInstance effect : player.getStatusEffects()) {
            if (effect.getEffectType().value().getCategory() == StatusEffectCategory.HARMFUL) {
                player.removeStatusEffect(effect.getEffectType());
                return;
            }
        }
    }
}
