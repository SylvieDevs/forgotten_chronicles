package net.sylviedevs.scp_outbreak;

import net.fabricmc.api.ModInitializer;

import net.sylviedevs.scp_outbreak.item.ModItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SCPOutbreak implements ModInitializer {
	public static final String MOD_ID = "scp-outbreak";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.registerModItems();
	}
}