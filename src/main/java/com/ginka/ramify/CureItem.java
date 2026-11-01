package com.ginka.ramify;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

/** 抗感染药：立刻清除所有负面效果，并在 4 分钟内持续压制负面效果喵 */
public class CureItem extends Item {
    private static final int IMMUNITY_TICKS = 20 * 60 * 4;

    public CureItem(Settings settings) {
        super(settings);
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return 24;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.DRINK;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        user.setCurrentHand(hand);
        return TypedActionResult.consume(user.getStackInHand(hand));
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (!world.isClient && user instanceof PlayerEntity player) {
            for (StatusEffectInstance effect : java.util.List.copyOf(player.getStatusEffects())) {
                if (effect.getEffectType().value().getCategory() == StatusEffectCategory.HARMFUL) {
                    player.removeStatusEffect(effect.getEffectType());
                }
            }
            player.addStatusEffect(new StatusEffectInstance(ModEffects.antiInfection(), IMMUNITY_TICKS, 0));
            world.playSound(null, player.getBlockPos(), SoundEvents.ENTITY_GENERIC_DRINK, SoundCategory.PLAYERS, 0.6f, 1.0f);
            if (!player.getAbilities().creativeMode) {
                stack.decrement(1);
            }
        }
        return stack;
    }
}
