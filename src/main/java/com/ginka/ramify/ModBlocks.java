package com.ginka.ramify;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public final class ModBlocks {
    public static final Block HEMP = new HempBlock(AbstractBlock.Settings.create()
            .strength(0.2f).nonOpaque().ticksRandomly().noCollision());
    public static final Block STRAWBERRY_BUSH = new StrawberryBushBlock(AbstractBlock.Settings.create()
            .strength(0.2f).nonOpaque().ticksRandomly().noCollision());
    public static final Block CORN = new CornBlock(AbstractBlock.Settings.create()
            .strength(0.2f).nonOpaque().ticksRandomly().noCollision());
    public static final Block WOODEN_SPIKES = new WoodenSpikesBlock(AbstractBlock.Settings.create().strength(1.0f).nonOpaque());

    private static void reg(String name, Block block) {
        Registry.register(Registries.BLOCK, Identifier.of(Ramify.MOD_ID, name), block);
        Registry.register(Registries.ITEM, Identifier.of(Ramify.MOD_ID, name), new BlockItem(block, new Item.Settings()));
    }

    public static void register() {
        reg("hemp", HEMP);
        reg("strawberry_bush", STRAWBERRY_BUSH);
        reg("corn", CORN);
        reg("wooden_spikes", WOODEN_SPIKES);
    }

    private ModBlocks() {
    }
}
