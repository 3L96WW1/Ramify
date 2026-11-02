package com.ginka.ramify;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

/** 亡灵伤害提升标记效果喵 */
public class UndeadPowerEffect extends StatusEffect {
    public UndeadPowerEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0xC08FFF);
    }
}
