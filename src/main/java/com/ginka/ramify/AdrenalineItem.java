package com.ginka.ramify;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

/** 肾上腺素：力量、抗性提升、速度 II 各 4 分钟喵 */
public class AdrenalineItem extends Item {
    private static final int DURATION = 20 * 60 * 4;

    public AdrenalineItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if (!world.isClient) {
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, DURATION, 0));
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, DURATION, 0));
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, DURATION, 1));
            world.playSound(null, user.getBlockPos(), SoundEvents.ITEM_HONEY_BOTTLE_DRINK, SoundCategory.PLAYERS, 0.6f, 1.2f);
            if (user instanceof ServerPlayerEntity serverPlayer) {
                Criteria.CONSUME_ITEM.trigger(serverPlayer, stack);
            }
            user.getItemCooldownManager().set(this, 200);
            if (!user.getAbilities().creativeMode) {
                stack.decrement(1);
            }
        }
        return TypedActionResult.success(stack, world.isClient);
    }
}
