package net.sylviedevs.scp_outbreak.item.objects;

import net.minecraft.block.Blocks;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.*;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SCP_109_Item extends Item {
    public SCP_109_Item(Settings settings) {
        super(new Settings()
                .maxCount(1)
        );
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World thisWorld = context.getWorld();

        if (!thisWorld.isClient()) {
            BlockPos blockPos = context.getBlockPos();
            Direction direction = context.getSide();
            BlockPos blockPos2 = blockPos.offset(direction);

            if ( thisWorld.canSetBlock(blockPos2) ) {
                thisWorld.setBlockState(blockPos2, Blocks.WATER.getDefaultState());

                thisWorld.playSound(null,
                        blockPos2, SoundEvents.ITEM_BUCKET_EMPTY,
                        SoundCategory.BLOCKS, 1f, 1f);
            }
        }

        return ActionResult.SUCCESS;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add( Text.translatable("tooltip.scp-outbreak.scp_109.nick").formatted(Formatting.GRAY) );

        super.appendTooltip(stack, world, tooltip, context);
    }
}
