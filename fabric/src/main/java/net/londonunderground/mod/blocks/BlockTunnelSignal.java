package net.londonunderground.mod.blocks;

import net.londonunderground.mod.BlockEntityTypes;
import org.mtr.mapping.holder.*;
import org.mtr.mapping.mapper.BlockEntityExtension;
import org.mtr.mapping.mapper.TextHelper;
import org.mtr.mod.block.BlockSignalLightBase;
import org.mtr.mod.block.IBlock;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class BlockTunnelSignal extends BlockSignalLightBase {

	public BlockTunnelSignal(BlockSettings settings) {
		super(settings, 2, 14);
	}

	@Nonnull
	@Override
	public VoxelShape getOutlineShape2(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return IBlock.getVoxelShapeByDirection(0, 0, 0, 16, 16, 16, IBlock.getStatePropertySafe(state, FACING));
	}

	@Override
	public BlockState getPlacementState2(ItemPlacementContext ctx) {
		return getDefaultState2().with(new Property<>(FACING.data), ctx.getPlayerFacing().rotateYClockwise().data).with(new Property<>(IS_45.data), EnumBooleanInverted.FALSE).with(new Property<>(IS_22_5.data), EnumBooleanInverted.FALSE);
	}

	@Nonnull
	@Override
	public BlockEntityExtension createBlockEntity(BlockPos pos, BlockState state) {
		return new TileEntityTunnelSignalLight1(pos, state);
	}

	public static class TileEntityTunnelSignalLight1 extends BlockEntityBase {

		public TileEntityTunnelSignalLight1(BlockPos pos, BlockState state) {
			super(BlockEntityTypes.TUNNEL_BLOCK_2_SIGNAL.get(), false, pos, state);
		}
	}

	@Override
	public void addTooltips(ItemStack stack, @Nullable BlockView world, List<MutableText> tooltip, TooltipContext options) {
		tooltip.add(TextHelper.literal("DEPRECATED!!!").formatted(TextFormatting.RED));
	}

}
