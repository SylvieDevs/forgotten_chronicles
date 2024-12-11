package net.sylviedevs.scp_outbreak.handlers;

import net.minecraft.item.FoodComponent;

public class ModFoodComponents {
    public static final FoodComponent PIZZA = new FoodComponent.Builder()
            .hunger(5)
            .saturationModifier(0.65f)
            .build();
}