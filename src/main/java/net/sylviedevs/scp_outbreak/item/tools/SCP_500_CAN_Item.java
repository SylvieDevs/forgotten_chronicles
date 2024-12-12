package net.sylviedevs.scp_outbreak.item.tools;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.sylviedevs.scp_outbreak.handlers.ModItems;
import net.sylviedevs.scp_outbreak.utils.ModMethodUtils;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@SuppressWarnings("unused")
public class SCP_500_CAN_Item extends Item {
    public SCP_500_CAN_Item(Settings settings) {
        super(settings
                .rarity(Rarity.EPIC)
                .maxCount(1)
        );
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getMainHandStack();
        NbtCompound nbtCompound = itemStack.getOrCreateNbt();

        if (!world.isClient && nbtCompound != null) {
            if (!nbtCompound.contains("pills_remaining")) {
                nbtCompound.putInt("pills_remaining", ModMethodUtils.getRandomInt(47, 59));
            }

            if (nbtCompound.getInt("pills_remaining") > 0) {
                nbtCompound.putInt("pills_remaining", nbtCompound.getInt("pills_remaining")-1);
                user.getInventory().insertStack( new ItemStack(ModItems.retrieveItemFromIdentifier("scp_500"), 1) );

                world.playSound(null,
                        user.getBlockPos(), SoundEvents.BLOCK_BARREL_OPEN,
                        SoundCategory.BLOCKS, 1f, 1f);

                return TypedActionResult.success(itemStack);
            } else {
                return TypedActionResult.fail(itemStack);
            }
        }

        return TypedActionResult.success(itemStack);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        NbtCompound nbtCompound = stack.getNbt();

        tooltip.add( Text.translatable("tooltip.scp-outbreak.scp_500_can.nick").formatted(Formatting.GRAY) );

        if ( nbtCompound != null && nbtCompound.contains("pills_remaining") ) {
            int nbtPillsAmount = nbtCompound.getInt("pills_remaining");

            tooltip.add( Text.literal("") );
            tooltip.add( Text.literal(String.valueOf(nbtPillsAmount)).formatted(Formatting.LIGHT_PURPLE).append(" ").append(Text.translatable("words.scp-outbreak.left").formatted(Formatting.LIGHT_PURPLE)) );
        }

        super.appendTooltip(stack, world, tooltip, context);
    }
}
