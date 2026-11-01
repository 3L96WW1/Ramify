package com.ginka.ramify;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;

import java.util.List;

/** 抗感染：持续期间不断清除身上的负面效果喵 */
public class AntiInfectionEffect extends StatusEffect {
    public AntiInfectionEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0x4FBF8B);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (!entity.getWorld().isClient) {
            for (StatusEffectInstance effect : List.copyOf(entity.getStatusEffects())) {
                if (effect.getEffectType().value().getCategory() == StatusEffectCategory.HARMFUL) {
                    entity.removeStatusEffect(effect.getEffectType());
                }
            }
        }
        return true;
    }
}
