package net.sylviedevs.forgotten_chronicles.handlers;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.sylviedevs.forgotten_chronicles.ForgottenChronicles;
import net.sylviedevs.forgotten_chronicles.entities.Hero_Entity;

public class ModEntities {
    public static final EntityType<Hero_Entity> Hero = Registry.register(
            Registries.ENTITY_TYPE, new Identifier(ForgottenChronicles.MOD_ID, "hero"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, Hero_Entity::new)
                    .dimensions(EntityDimensions.fixed(0.6f, 1.8f)).build());
}