package com.ginka.ramify;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

/** 采集暴击：采麻时额外掉落喵 */
public class HarvestCritEffect extends StatusEffect {
    public HarvestCritEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0x7BC043);
    }
}
