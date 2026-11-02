package com.ginka.ramify;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public final class ModBlocks {
    public static final Block HEMP = new HempBlock(AbstractBlock.Settings.create()
            .strength(0.3333f).sounds(BlockSoundGroup.GRASS).nonOpaque().ticksRandomly().noCollision());
    public static final Block JUTE = new HempBlock(AbstractBlock.Settings.create()
            .strength(0.3333f).sounds(BlockSoundGroup.GRASS).nonOpaque().ticksRandomly().noCollision());
    public static final Block SISAL = new WildPlantBlock(AbstractBlock.Settings.create()
            .breakInstantly().sounds(BlockSoundGroup.GRASS).nonOpaque().noCollision());
    public static final Block STRAWBERRY_BUSH = new StrawberryBushBlock(AbstractBlock.Settings.create()
            .breakInstantly().sounds(BlockSoundGroup.GRASS).nonOpaque().ticksRandomly().noCollision());
    public static final Block CORN = new CornBlock(AbstractBlock.Settings.create()
            .strength(0.1333f).sounds(BlockSoundGroup.GRASS).nonOpaque().ticksRandomly().noCollision(), false);
    public static final Block WILD_CORN = new CornBlock(AbstractBlock.Settings.create()
            .strength(0.1333f).sounds(BlockSoundGroup.GRASS).nonOpaque().ticksRandomly().noCollision(), true);
    public static final Block STRAW_MUSHROOM = new WildPlantBlock(AbstractBlock.Settings.create()
            .breakInstantly().sounds(BlockSoundGroup.GRASS).nonOpaque().noCollision());
    public static final Block MATSUTAKE = new WildPlantBlock(AbstractBlock.Settings.create()
            .breakInstantly().sounds(BlockSoundGroup.GRASS).nonOpaque().noCollision());
    public static final Block RAPESEED = new WildPlantBlock(AbstractBlock.Settings.create()
            .breakInstantly().sounds(BlockSoundGroup.GRASS).nonOpaque().noCollision());
    public static final Block WOODEN_SPIKES = new WoodenSpikesBlock(AbstractBlock.Settings.create().strength(1.0f).sounds(BlockSoundGroup.WOOD).nonOpaque());

    private static void reg(String name, Block block) {
        Registry.register(Registries.BLOCK, Identifier.of(Ramify.MOD_ID, name), block);
        Registry.register(Registries.ITEM, Identifier.of(Ramify.MOD_ID, name), new BlockItem(block, new Item.Settings()));
    }

    private static void regBlockOnly(String name, Block block) {
        Registry.register(Registries.BLOCK, Identifier.of(Ramify.MOD_ID, name), block);
    }

    public static void register() {
        reg("hemp", HEMP);
        reg("jute", JUTE);
        reg("sisal", SISAL);
        reg("strawberry_bush", STRAWBERRY_BUSH);
        regBlockOnly("corn", CORN);
        regBlockOnly("wild_corn", WILD_CORN);
        reg("straw_mushroom", STRAW_MUSHROOM);
        reg("matsutake", MATSUTAKE);
        reg("rapeseed", RAPESEED);
        reg("wooden_spikes", WOODEN_SPIKES);
    }

    private ModBlocks() {
    }
}
