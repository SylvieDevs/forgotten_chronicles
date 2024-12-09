package net.sylviedevs.scp_outbreak.handlers;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.sylviedevs.scp_outbreak.SCPOutbreak;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    public static final ItemGroup OBJECTS_GROUP = Registry.register(Registries.ITEM_GROUP,
            new Identifier(SCPOutbreak.MOD_ID, "objects"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup." + SCPOutbreak.MOD_ID + ".objects"))
                    .icon(() -> new ItemStack( ModItems.retrieveItemFromIdentifier("scp_109") )).entries((displayContext, entries) -> {
                        entries.add( ModItems.retrieveItemFromIdentifier("scp_109") );
                    }).build());


    public static void registerItemGroups() {
        SCPOutbreak.LOGGER.info("Registering Item Groups...");
    }
}