package com.ginka.ramify;

import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

/** 彩蛋物品：草莓草莓，紫色名字 + 作弊提示喵 */
public class MurmurBotItem extends Item {
    public MurmurBotItem(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.literal("你是不是作弊了喵").formatted(Formatting.GRAY));
    }
}
