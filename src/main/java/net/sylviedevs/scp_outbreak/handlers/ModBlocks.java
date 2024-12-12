package net.sylviedevs.scp_outbreak.handlers;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.sylviedevs.scp_outbreak.SCPOutbreak;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class ModBlocks {
    public static List<Block> registeredBlocks = new ArrayList<>();
    public static Object[][] blockIdentifiers = {
            {"document_crafter", "functional", ItemGroups.FUNCTIONAL, Blocks.IRON_BLOCK},
            {"scp_458_box", "functional", ItemGroups.FUNCTIONAL, Blocks.WHITE_WOOL},

            {"white_tile", "building", ItemGroups.BUILDING_BLOCKS, Blocks.QUARTZ_BRICKS},
            {"checkered_tile", "building", ItemGroups.BUILDING_BLOCKS, Blocks.QUARTZ_BRICKS},
    };

    public static Block retrieveBlockFromIdentifier(String BlockIdentifier) {
        for (int thisCounter = 0; thisCounter < blockIdentifiers.length; thisCounter++) {
            if (BlockIdentifier.equals( blockIdentifiers[thisCounter][0] )) { return registeredBlocks.get(thisCounter); }
        }

        return Blocks.BEDROCK;
    }

    private static void addBlocksToIngredientItemGroup(FabricItemGroupEntries entries, RegistryKey<ItemGroup> itemGroup) {
        for (Object[] thisIdentifierBlock : blockIdentifiers) {
            if ( !itemGroup.equals( thisIdentifierBlock[2] ) ) { continue; }

            entries.add( retrieveBlockFromIdentifier((String) thisIdentifierBlock[0]) );
        }
    }

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(SCPOutbreak.MOD_ID, name), block);
    }

    private static void registerBlockItem(String name, Block block) {
        Registry.register(Registries.ITEM, new Identifier(SCPOutbreak.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings())).getBlock();
    }

    @SuppressWarnings("unchecked")
    public static void registerModBlocks() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        SCPOutbreak.LOGGER.info("Registering Blocks...");

        for (Object[] thisBlock : blockIdentifiers) {
            try {
                Class<?> retrieveClass = Class.forName("net.sylviedevs.scp_outbreak.block." + thisBlock[1].toString() + "." + thisBlock[0].toString().toUpperCase() + "_Block");

                if (!Block.class.isAssignableFrom(retrieveClass)) {
                    throw new IllegalArgumentException("Class " + thisBlock[0] + " is not a subtype of Block");
                }

                Class<? extends Block> typedClass = retrieveClass.asSubclass(Block.class);

                Block thisBlockClass = typedClass.getDeclaredConstructor(Block.Settings.class).newInstance(FabricBlockSettings.create());
                Block thisRegisteredBlock = registerBlock((String) thisBlock[0], thisBlockClass);

                if (thisBlock[2] instanceof RegistryKey<?>) {
                    RegistryKey<ItemGroup> thisItemGroup = (RegistryKey<ItemGroup>) thisBlock[2];

                    ItemGroupEvents.modifyEntriesEvent(thisItemGroup)
                            .register(entries ->
                                    addBlocksToIngredientItemGroup(entries, thisItemGroup)
                            );
                }

                registeredBlocks.add( thisRegisteredBlock );
            } catch (ClassNotFoundException e) {
                Block thisRegisteredBlock = registerBlock((String) thisBlock[0], new Block(FabricBlockSettings.copyOf((AbstractBlock) thisBlock[3])));

                if (thisBlock[2] instanceof RegistryKey<?>) {
                    RegistryKey<ItemGroup> thisItemGroup = (RegistryKey<ItemGroup>) thisBlock[2];

                    ItemGroupEvents.modifyEntriesEvent(thisItemGroup)
                            .register(entries ->
                                    addBlocksToIngredientItemGroup(entries, thisItemGroup)
                            );
                }

                registeredBlocks.add( thisRegisteredBlock );
            } catch (Exception e) {
                throw new RuntimeException("Failed to create Block: " + thisBlock[0], e);
            }
        }
    }
}
