package net.sylviedevs.scp_outbreak.item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroups;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.sylviedevs.scp_outbreak.SCPOutbreak;

public class ModItems {
    public static final Item SCP_109 = registerItem("scp_109", new Item(new FabricItemSettings()));

    private static void addItemsToIngredientItemGroup(FabricItemGroupEntries entries) {
        entries.add(SCP_109);
    }

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(SCPOutbreak.MOD_ID, name), item);
    }

    public static void registerModItems() {
        SCPOutbreak.LOGGER.info("Registering Items...");

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SEARCH).register(ModItems::addItemsToIngredientItemGroup);
    }
}
