package net.sylviedevs.scp_outbreak.status;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import net.sylviedevs.scp_outbreak.utils.ModMethodUtils;

@SuppressWarnings("unused")
public class PANACEA_Status extends StatusEffect {
    public PANACEA_Status() {
        super(StatusEffectCategory.BENEFICIAL, 0xF00000);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (ModMethodUtils.getHarmfulEffectsAmount(entity) > 0) {
            ModMethodUtils.removeHarmfulEffects(entity);
        }
    }

    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        World world = entity.getWorld();

        if (!world.isClient) {
            entity.heal(entity.getMaxHealth());
            entity.setAir(entity.getMaxAir());

            world.playSound(entity.getX(), entity.getY(), entity.getZ(),
                    SoundEvents.ITEM_TOTEM_USE, entity.getSoundCategory(),
                    1.0f, 1.0f, false);
        }

        super.onApplied(entity, attributes, amplifier);
    }
}