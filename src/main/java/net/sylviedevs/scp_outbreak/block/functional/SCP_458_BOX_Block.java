package net.sylviedevs.scp_outbreak.block.functional;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.sylviedevs.scp_outbreak.handlers.ModItems;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SCP_458_BOX_Block extends Block {
    protected static final VoxelShape CLOSED_SHAPE = Block.createCuboidShape(0.0F, 0.0F, 0.0F, 16.0F, 1.0F, 16.0F);
    protected static final VoxelShape OPEN_SHAPE = Block.createCuboidShape(0.0F, 0.0F, 0.0F, 16.0F, 13.5F, 16.0F);

    public static final BooleanProperty OPEN = BooleanProperty.of("open");
    public static final DirectionProperty FACING = DirectionProperty.of("facing");

    public SCP_458_BOX_Block(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState()
                .with(FACING, Direction.NORTH)
                .with(OPEN, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING).add(OPEN);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
        tooltip.add( Text.translatable("tooltip.scp-outbreak.scp_458.block").formatted(Formatting.GRAY) );

        super.appendTooltip(stack, world, tooltip, options);
    }

    @Override
    @SuppressWarnings("deprecation")
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            boolean isOpen = state.get(OPEN);

            if ( !isOpen && !player.isSneaking() ) {
                world.setBlockState(pos, state.with(OPEN, true));

                world.playSound(null,
                        pos, SoundEvents.BLOCK_BARREL_OPEN,
                        SoundCategory.BLOCKS, 1f, 1f);
            }

            if ( isOpen && player.isSneaking() ) {
                world.setBlockState(pos, state.with(OPEN, false));

                world.playSound(null,
                        pos, SoundEvents.BLOCK_BARREL_CLOSE,
                        SoundCategory.BLOCKS, 1f, 1f);
            } else if ( isOpen && !player.isSneaking() ) {
                player.getInventory().insertStack( new ItemStack(ModItems.retrieveItemFromIdentifier("scp_458"), 1) );
            }
        }

        return ActionResult.SUCCESS;
    }

    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return state.get(OPEN) ? OPEN_SHAPE : CLOSED_SHAPE;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        return this.getDefaultState().with(FACING, context.getHorizontalPlayerFacing().getOpposite());
    }
}
