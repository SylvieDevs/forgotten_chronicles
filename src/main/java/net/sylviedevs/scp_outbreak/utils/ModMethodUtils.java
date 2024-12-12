package net.sylviedevs.scp_outbreak.utils;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;

import java.util.Random;

public class ModMethodUtils {
    public static int getRandomInt(int min, int max) {
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }

    public static int getHarmfulEffectsAmount(LivingEntity entity) {
        int counter = 0;

        for (StatusEffectInstance effect : entity.getStatusEffects()) {
            if (effect.getEffectType().getCategory() != StatusEffectCategory.HARMFUL) { continue; }

            counter++;
        }

        return counter;
    }

    public static void removeHarmfulEffects(LivingEntity entity) {
        for (StatusEffectInstance effect : entity.getStatusEffects()) {
            if (effect.getEffectType().getCategory() != StatusEffectCategory.HARMFUL) { continue; }

            entity.removeStatusEffect(effect.getEffectType());
        }
    }
}