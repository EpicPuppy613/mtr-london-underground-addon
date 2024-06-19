package net.londonunderground.mod.render;

import net.londonunderground.mod.Init;
import net.londonunderground.mod.blocks.TunnelDarknessBlock;
import org.mtr.mapping.holder.*;
import org.mtr.mapping.mapper.BlockEntityRenderer;
import org.mtr.mapping.mapper.GraphicsHolder;
import org.mtr.mapping.mapper.PlayerHelper;
import org.mtr.mod.render.MainRenderer;
import org.mtr.mod.render.QueuedRenderLayer;
import org.mtr.mod.render.StoredMatrixTransformations;

import javax.annotation.Nonnull;

public class RenderDarkTile<T extends TunnelDarknessBlock.TileEntityTunnelDarkness> extends BlockEntityRenderer<T> {

	private static final TunnelEntityModel TUNNEL_ENTITY_MODEL = new TunnelEntityModel();
	private static final int VIEW_DISTANCE = 64; // Distance to start rendering the tunnel block

	public RenderDarkTile(Argument dispatcher) {
		super(dispatcher);
	}

	@Override
	public void render(@Nonnull T entity, float tickDelta, @Nonnull GraphicsHolder graphicsHolder, int light, int overlay) {
		final ClientPlayerEntity player = MinecraftClient.getInstance().getPlayerMapped();
		if (player == null) {
			return;
		}

		final BlockPos pos = entity.getPos2();
		if (!PlayerHelper.isHolding(PlayerEntity.cast(player), item -> Block.getBlockFromItem(item).data instanceof TunnelDarknessBlock)) {
			if (pos.getSquaredDistance(new Vector3i(player.getBlockPos().data)) < VIEW_DISTANCE * VIEW_DISTANCE) {
				return;
			}
		}

		final StoredMatrixTransformations storedMatrixTransformations = new StoredMatrixTransformations(pos.getX(), pos.getY() + 1.5, pos.getZ());
		MainRenderer.scheduleRender(new Identifier(Init.MOD_ID, "textures/block/tunnel_darkness.png"), false, QueuedRenderLayer.EXTERIOR, (graphicsHolderNew, offset) -> {
			storedMatrixTransformations.transform(graphicsHolderNew, offset);
			TUNNEL_ENTITY_MODEL.render(graphicsHolderNew, light, overlay, 1, 1, 1, 50);
			graphicsHolderNew.pop();
		});
	}

	@Override
	public boolean rendersOutsideBoundingBox2(@Nonnull T blockEntity) {
		return true;
	}

	@Override
	public int getRenderDistance2() {
		return 512;
	}

	@Override
	public boolean isInRenderDistance(@Nonnull T blockEntity, @Nonnull Vector3d position) {
		return true;
	}
}
