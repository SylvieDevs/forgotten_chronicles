package net.sylviedevs.scp_outbreak.handlers;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroups;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.sylviedevs.scp_outbreak.SCPOutbreak;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class ModItems {
    private static final Item.Settings defaultSettings = new Item.Settings();

    public static List<Item> registeredItems = new ArrayList<>();
    public static String[][] itemIdentifiers = {
            {"scp_109", "objects"}
    };

    public static final Item ERROR_ITEM = registerItem("error", new Item(new FabricItemSettings()));

    public static Item retrieveItemFromIdentifier(String itemIdentifier) {
        for (int thisCounter = 0; thisCounter < itemIdentifiers.length; thisCounter++) {
            if (itemIdentifier.equals( itemIdentifiers[thisCounter][0] )) { return registeredItems.get(thisCounter); }
        }

        return ERROR_ITEM;
    }

    private static void addItemsToIngredientItemGroup(FabricItemGroupEntries entries) {
        for (Item thisRegisteredItem : registeredItems) {
            entries.add(thisRegisteredItem);
        }
    }

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(SCPOutbreak.MOD_ID, name), item);
    }

    public static void registerModItems() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        SCPOutbreak.LOGGER.info("Registering Items...");

        for (String[] thisItem : itemIdentifiers) {
            Class<?> retrieveClass = Class.forName("net.sylviedevs.scp_outbreak.item." + thisItem[1] + "." + thisItem[0].toUpperCase() + "_Item");

            if (!Item.class.isAssignableFrom(retrieveClass)) {
                throw new IllegalArgumentException("Class " + thisItem[0] + " is not a subtype of Item");
            }

            Class<? extends Item> typedClass = retrieveClass.asSubclass(Item.class);

            Item thisItemClass = typedClass.getDeclaredConstructor(Item.Settings.class).newInstance(defaultSettings);
            Item thisRegisteredItem = registerItem(thisItem[0], thisItemClass);

            registeredItems.add( thisRegisteredItem );
        }

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SEARCH).register(ModItems::addItemsToIngredientItemGroup);
    }
}
