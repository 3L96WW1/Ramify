package com.ginka.ramify;

import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradedItem;
import net.minecraft.village.VillagerProfession;

/** 村民交易：农民收购麻叶/麻纤维/玉米，出售玉米种子喵 */
public final class ModTrades {
    public static void register() {
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.FARMER, 1, factories -> {
            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(ModItems.HEMP_LEAF, 16),
                    new ItemStack(Items.EMERALD, 1), 16, 2, 0.05f));
            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(ModItems.HEMP_FIBER, 4),
                    new ItemStack(Items.EMERALD, 1), 12, 5, 0.05f));
            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(ModItems.CORN, 8),
                    new ItemStack(Items.EMERALD, 1), 16, 2, 0.05f));
        });
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.FARMER, 2, factories ->
                factories.add((entity, random) -> new TradeOffer(
                        new TradedItem(Items.EMERALD, 1),
                        new ItemStack(ModItems.CORN_SEEDS, 4), 12, 5, 0.05f)));
    }

    private ModTrades() {
    }
}
