package net.sylviedevs.forgotten_chronicles.client.entity;

import net.minecraft.util.Identifier;
import net.sylviedevs.forgotten_chronicles.ForgottenChronicles;
import net.sylviedevs.forgotten_chronicles.entities.Hero_Entity;
import software.bernie.geckolib.model.GeoModel;

public class Hero_Model extends GeoModel<Hero_Entity> {
    @Override
    public Identifier getModelResource(Hero_Entity heroEntity) {
        return new Identifier(ForgottenChronicles.MOD_ID, "geo/hero.geo.json");
    }

    @Override
    public Identifier getTextureResource(Hero_Entity heroEntity) {
        return new Identifier(ForgottenChronicles.MOD_ID, "textures/entity/hero.png");
    }

    @Override
    public Identifier getAnimationResource(Hero_Entity heroEntity) {
        return null;
    }
}
