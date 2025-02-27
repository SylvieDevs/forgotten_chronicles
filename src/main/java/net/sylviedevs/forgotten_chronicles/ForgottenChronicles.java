package net.sylviedevs.forgotten_chronicles;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.sylviedevs.forgotten_chronicles.entities.Hero_Entity;
import net.sylviedevs.forgotten_chronicles.events.ServerJoinEvent;
import net.sylviedevs.forgotten_chronicles.handlers.ModEntities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ForgottenChronicles implements ModInitializer {
	public static final String MOD_ID = "forgotten_chronicles";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
        FabricDefaultAttributeRegistry.register(ModEntities.Hero, Hero_Entity.setAttributes());

        ServerPlayConnectionEvents.JOIN.register(new ServerJoinEvent());
    }
}