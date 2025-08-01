package nautilus.client.render.entity;

import nautilus.NautilusMod;
import nautilus.NautilusModClient;
import nautilus.client.render.entity.model.NautilusEntityModel;
import nautilus.entity.NautilusEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;

@Environment(value = EnvType.CLIENT)
public class NautilusEntityRenderer extends MobEntityRenderer<NautilusEntity, NautilusEntityModel>
{
	private static final Identifier TEXTURE = Identifier.of(NautilusMod.MOD_ID, "textures/entity/nautilus.png");

	public NautilusEntityRenderer(EntityRendererFactory.Context context)
	{
		super(context, new NautilusEntityModel(context.getPart(NautilusModClient.MODEL_NAUTILUS_LAYER)), 0.5f);
	}
	
	@Override
	protected void setupTransforms(NautilusEntity nautilusEntity, MatrixStack matrixStack, float animationProgress, float bodyYaw, float tickDelta, float scale)
	{
		super.setupTransforms(nautilusEntity, matrixStack, animationProgress, bodyYaw, tickDelta, scale);
		
		if(!nautilusEntity.isTouchingWater() && nautilusEntity.isOnGround())
		{
			matrixStack.translate(0.0f, 0.2f, 0.0f);
			matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(this.getLyingAngle(nautilusEntity)));
			matrixStack.translate(0.0f, -0.4f, 0.0f);
		}
	}

	@Override
	public Identifier getTexture(NautilusEntity NautilusEntity)
	{
		return TEXTURE;
	}
}