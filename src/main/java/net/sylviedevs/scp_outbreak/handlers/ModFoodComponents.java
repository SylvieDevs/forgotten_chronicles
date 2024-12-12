package net.sylviedevs.scp_outbreak.handlers;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.FoodComponent;

public class ModFoodComponents {
    public static final FoodComponent PIZZA = new FoodComponent.Builder()
            .hunger(5)
            .saturationModifier(0.75f)
            .build();

    public static final FoodComponent PANACEA = new FoodComponent.Builder()
            .hunger(999)
            .statusEffect(new StatusEffectInstance(ModStatusEffects.retrieveStatusFromIdentifier("panacea"), 2400), 100)
            .alwaysEdible()
            .build();
}