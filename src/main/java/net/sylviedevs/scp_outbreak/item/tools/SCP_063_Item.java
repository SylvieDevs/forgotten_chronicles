package net.sylviedevs.scp_outbreak.item.tools;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Rarity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.sylviedevs.scp_outbreak.handlers.classes.SCPItem;
import net.sylviedevs.scp_outbreak.utils.ModParticleUtils;

@SuppressWarnings("unused")
public class SCP_063_Item extends SCPItem {
    public SCP_063_Item(Settings settings) {
        super(settings
                .rarity(Rarity.EPIC)
                .maxCount(1)
        );
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        World thisWorld = user.getWorld();
        BlockPos entityBlockPos = entity.getBlockPos();

        if (thisWorld.isClient) {
            ModParticleUtils.summonPoofParticles(thisWorld, entity.getX(), entity.getY() + (entity.getDimensions(entity.getPose()).height/2), entity.getZ());
        } else {
            thisWorld.playSound(null,
                    entityBlockPos, SoundEvents.ITEM_FLINTANDSTEEL_USE,
                    SoundCategory.BLOCKS, 1f, 1f);

            entity.discard();
        }

        return ActionResult.SUCCESS;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World thisWorld = context.getWorld();
        BlockPos blockPos = context.getBlockPos();
        BlockState blockState = context.getWorld().getBlockState(blockPos);

        if (thisWorld.isClient) {
            thisWorld.addBlockBreakParticles(blockPos, blockState);
        } else {
            if ( thisWorld.canSetBlock(blockPos) ) {
                thisWorld.playSound(null,
                        blockPos, SoundEvents.ITEM_FLINTANDSTEEL_USE,
                        SoundCategory.BLOCKS, 1f, 1f);

                thisWorld.setBlockState(blockPos, Blocks.AIR.getDefaultState());
            }
        }

        return ActionResult.SUCCESS;
    }
}
