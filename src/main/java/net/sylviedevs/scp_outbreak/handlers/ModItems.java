package net.sylviedevs.scp_outbreak.handlers;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.sylviedevs.scp_outbreak.SCPOutbreak;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class ModItems {
    public static List<Item> registeredItems = new ArrayList<>();
    public static Object[][] itemIdentifiers = {
            {"blank_document", "ingredients", ItemGroups.INGREDIENTS},
            {"screwdriver", "tools", ItemGroups.TOOLS},

            {"scp_063", "tools", ItemGroups.TOOLS},
            {"scp_109", "tools", ItemGroups.TOOLS},
            {"scp_458", "food", ItemGroups.FOOD_AND_DRINK},
            {"scp_500", "food", ItemGroups.FOOD_AND_DRINK},

            {"scp_500_can", "tools", ItemGroups.TOOLS}
    };

    public static Item retrieveItemFromIdentifier(String itemIdentifier) {
        for (int thisCounter = 0; thisCounter < itemIdentifiers.length; thisCounter++) {
            if (itemIdentifier.equals( itemIdentifiers[thisCounter][0] )) { return registeredItems.get(thisCounter); }
        }

        return Items.BARRIER;
    }

    private static void addItemsToIngredientItemGroup(FabricItemGroupEntries entries, RegistryKey<ItemGroup> itemGroup) {
        for (Object[] thisIdentifierBlock : itemIdentifiers) {
            if ( !itemGroup.equals( thisIdentifierBlock[2] ) ) { continue; }

            entries.add( retrieveItemFromIdentifier((String) thisIdentifierBlock[0]) );
        }
    }

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(SCPOutbreak.MOD_ID, name), item);
    }

    @SuppressWarnings("unchecked")
    public static void registerModItems() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        SCPOutbreak.LOGGER.info("Registering Items...");

        for (Object[] thisItem : itemIdentifiers) {
            try {
                Class<?> retrieveClass = Class.forName("net.sylviedevs.scp_outbreak.item." + thisItem[1].toString().toLowerCase() + "." + thisItem[0].toString().toUpperCase() + "_Item");

                if (!Item.class.isAssignableFrom(retrieveClass)) {
                    throw new IllegalArgumentException("Class " + thisItem[0] + " is not a subtype of Item");
                }

                Class<? extends Item> typedClass = retrieveClass.asSubclass(Item.class);

                Item thisItemClass = typedClass.getDeclaredConstructor(Item.Settings.class).newInstance(new Item.Settings());
                Item thisRegisteredItem = registerItem((String) thisItem[0], thisItemClass);

                if (thisItem[2] instanceof RegistryKey<?>) {
                    RegistryKey<ItemGroup> thisItemGroup = (RegistryKey<ItemGroup>) thisItem[2];

                    ItemGroupEvents.modifyEntriesEvent(thisItemGroup)
                            .register(entries ->
                                    addItemsToIngredientItemGroup(entries, thisItemGroup)
                            );
                }

                registeredItems.add( thisRegisteredItem );
            } catch (ClassNotFoundException e) {
                Item thisRegisteredItem = registerItem((String) thisItem[0], new Item(new Item.Settings()));

                if (thisItem[2] instanceof RegistryKey<?>) {
                    RegistryKey<ItemGroup> thisItemGroup = (RegistryKey<ItemGroup>) thisItem[2];

                    ItemGroupEvents.modifyEntriesEvent(thisItemGroup)
                            .register(entries ->
                                    addItemsToIngredientItemGroup(entries, thisItemGroup)
                            );
                }

                registeredItems.add( thisRegisteredItem );
            } catch (Exception e) {
                throw new RuntimeException("Failed to create item: " + thisItem[0], e);
            }
        }

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SEARCH)
                .register(entries ->
                        addItemsToIngredientItemGroup(entries, ItemGroups.SEARCH)
                );
    }
}
