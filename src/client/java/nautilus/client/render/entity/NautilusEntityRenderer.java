package nautilus.client.render.entity;

import nautilus.NautilusMod;
import nautilus.NautilusModClient;
import nautilus.client.render.entity.model.NautilusEntityModel;
import nautilus.entity.NautilusEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;

@Environment(value = EnvType.CLIENT)
public class NautilusEntityRenderer extends MobEntityRenderer<NautilusEntity, LivingEntityRenderState, NautilusEntityModel>
{
	private static final Identifier TEXTURE = Identifier.of(NautilusMod.MOD_ID, "textures/entity/nautilus.png");

	public NautilusEntityRenderer(EntityRendererFactory.Context context)
	{
		super(context, new NautilusEntityModel(context.getPart(NautilusModClient.MODEL_NAUTILUS_LAYER)), 0.5f);
	}
	
	public LivingEntityRenderState createRenderState()
	{
		return new LivingEntityRenderState();
	}

	@Override
	public Identifier getTexture(LivingEntityRenderState state)
	{
		return TEXTURE;
	}
	
	@Override
	protected void setupTransforms(LivingEntityRenderState state, MatrixStack matrixStack, float bodyYaw, float baseHeight)
	{
		super.setupTransforms(state, matrixStack, bodyYaw, baseHeight);
		
		if(!state.touchingWater)
		{
			matrixStack.translate(0.0f, 0.2f, 0.0f);
			matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(90.0f));
			matrixStack.translate(0.0f, -0.4f, 0.0f);
		}
	}
}