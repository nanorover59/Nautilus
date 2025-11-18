package nautilus.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import nautilus.NautilusMod;
import nautilus.client.NautilusModClient;
import nautilus.client.render.entity.model.NautilusEntityModel;
import nautilus.entity.NautilusEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class NautilusEntityRenderer extends MobRenderer<NautilusEntity, NautilusEntityModel>
{
	private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(NautilusMod.MOD_ID, "textures/entity/nautilus.png");

	public NautilusEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new NautilusEntityModel(context.bakeLayer(NautilusModClient.MODEL_NAUTILUS_LAYER)), 0.5f);
    }

    @Override
	protected void setupRotations(NautilusEntity nautilusEntity, PoseStack matrixStack, float animationProgress, float bodyYaw, float tickDelta, float scale)
	{
		super.setupRotations(nautilusEntity, matrixStack, animationProgress, bodyYaw, tickDelta, scale);
		
		if(!nautilusEntity.isInWater() && nautilusEntity.onGround())
		{
			matrixStack.translate(0.0f, 0.2f, 0.0f);
			matrixStack.mulPose(Axis.ZP.rotationDegrees(this.getFlipDegrees(nautilusEntity)));
			matrixStack.translate(0.0f, -0.4f, 0.0f);
		}
	}

    @Override
    public ResourceLocation getTextureLocation(NautilusEntity entity) {
        return TEXTURE;
    }
}