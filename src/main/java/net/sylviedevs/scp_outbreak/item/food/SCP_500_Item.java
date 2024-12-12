package net.sylviedevs.scp_outbreak.item.food;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Rarity;
import net.minecraft.world.World;
import net.sylviedevs.scp_outbreak.handlers.ModFoodComponents;
import net.sylviedevs.scp_outbreak.handlers.classes.SCPItem;
import net.sylviedevs.scp_outbreak.utils.ModMethodUtils;

@SuppressWarnings("unused")
public class SCP_500_Item extends SCPItem {
    public SCP_500_Item(Settings settings) {
        super(settings
                .rarity(Rarity.EPIC)
                .food(ModFoodComponents.PANACEA)
        );
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (world.isClient) {
            MinecraftClient client = MinecraftClient.getInstance();

            client.particleManager.addEmitter(user, ParticleTypes.TOTEM_OF_UNDYING, 100);
        } else {
            ModMethodUtils.removeHarmfulEffects(user);

            world.playSound(null,
                    user.getBlockPos(), SoundEvents.ITEM_TOTEM_USE,
                    SoundCategory.BLOCKS, 1f, 1f);
        }

        return super.finishUsing(stack, world, user);
    }
}
