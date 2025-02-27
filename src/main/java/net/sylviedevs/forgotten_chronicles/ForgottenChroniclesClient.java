package net.sylviedevs.forgotten_chronicles;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.sylviedevs.forgotten_chronicles.client.entity.Hero_Renderer;
import net.sylviedevs.forgotten_chronicles.handlers.ModEntities;

public class ForgottenChroniclesClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(ModEntities.Hero, Hero_Renderer::new);
    }
}
