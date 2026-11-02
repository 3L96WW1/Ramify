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
    public static final Block ALPINE_FLAX = new AlpineFlaxBlock(AbstractBlock.Settings.create()
            .strength(0.3333f).sounds(BlockSoundGroup.GRASS).nonOpaque().ticksRandomly().noCollision());
    public static final Block COLD_HEMP = new HempBlock(AbstractBlock.Settings.create()
            .strength(0.3333f).sounds(BlockSoundGroup.GRASS).nonOpaque().ticksRandomly().noCollision());
    public static final Block STRAWBERRY_BUSH = new StrawberryBushBlock(AbstractBlock.Settings.create()
            .strength(0.2f).sounds(BlockSoundGroup.GRASS).nonOpaque().ticksRandomly().noCollision());
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
    public static final Item CORN_BLOCK = new BlockItem(CORN, new Item.Settings());
    public static final Block GRILL = new GrillBlock(AbstractBlock.Settings.create().strength(2.0f).sounds(BlockSoundGroup.METAL).nonOpaque());
    public static final Item GRILL_ITEM = new GrillItem(GRILL, new Item.Settings().maxCount(1));
    public static final net.minecraft.block.entity.BlockEntityType<GrillBlockEntity> GRILL_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(Ramify.MOD_ID, "grill"),
                    net.minecraft.block.entity.BlockEntityType.Builder.create(GrillBlockEntity::new, GRILL).build());

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
        reg("cold_hemp", COLD_HEMP);
        reg("alpine_flax", ALPINE_FLAX);
        reg("strawberry_bush", STRAWBERRY_BUSH);
        regBlockOnly("corn", CORN);
        regBlockOnly("wild_corn", WILD_CORN);
        reg("straw_mushroom", STRAW_MUSHROOM);
        reg("matsutake", MATSUTAKE);
        regBlockOnly("rapeseed", RAPESEED);
        Registry.register(Registries.ITEM, Identifier.of(Ramify.MOD_ID, "corn_block"), CORN_BLOCK);
        regBlockOnly("grill", GRILL);
        Registry.register(Registries.ITEM, Identifier.of(Ramify.MOD_ID, "grill"), GRILL_ITEM);
        reg("wooden_spikes", WOODEN_SPIKES);
    }

    private ModBlocks() {
    }
}
