package net.sylviedevs.scp_outbreak;

import net.fabricmc.api.ModInitializer;

import net.sylviedevs.scp_outbreak.handlers.ModItemGroups;
import net.sylviedevs.scp_outbreak.handlers.ModItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;

public class SCPOutbreak implements ModInitializer {
	public static final String MOD_ID = "scp-outbreak";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItemGroups.registerItemGroups();

        try {
            ModItems.registerModItems();
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException |
                 IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}