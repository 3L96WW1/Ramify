package com.ginka.ramify;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

/** 温暖：持续清除冻伤进度喵 */
public class WarmthEffect extends StatusEffect {
    public WarmthEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0xFFB347);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (!entity.getWorld().isClient && entity.getFrozenTicks() > 0) {
            entity.setFrozenTicks(0);
        }
        return true;
    }
}
