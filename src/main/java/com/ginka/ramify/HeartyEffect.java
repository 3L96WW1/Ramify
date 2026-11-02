package com.ginka.ramify;

import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.Identifier;

/** 生命上限 +3 颗心（+6 HP）喵 */
public class HeartyEffect extends StatusEffect {
    public HeartyEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0xE04A4A);
        this.addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH,
                Identifier.of(Ramify.MOD_ID, "effect.hearty"),
                6.0, EntityAttributeModifier.Operation.ADD_VALUE);
    }
}
