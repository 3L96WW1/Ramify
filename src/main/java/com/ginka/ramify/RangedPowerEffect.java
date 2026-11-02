package com.ginka.ramify;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

/** 远程伤害提升标记效果（具体伤害由弓/弩逻辑后续接入）喵 */
public class RangedPowerEffect extends StatusEffect {
    public RangedPowerEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0x8FB8FF);
    }
}
