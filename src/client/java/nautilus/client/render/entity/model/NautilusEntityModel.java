package nautilus.client.render.entity.model;

import nautilus.entity.NautilusEntity;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

public class NautilusEntityModel extends EntityModel<NautilusEntity>
{
	private final ModelPart shell;
	private final ModelPart body;
	private final ModelPart lid;
	private final ModelPart armsLeft;
	private final ModelPart armsRight;
	private final ModelPart armsTop;
	private final ModelPart armsBottom;
	
	public NautilusEntityModel(ModelPart root)
	{
		this.shell = root.getChild("shell");
		this.body = root.getChild("body");
		this.lid = root.getChild("lid");
		this.armsLeft = root.getChild("armsLeft");
		this.armsRight = root.getChild("armsRight");
		this.armsTop = root.getChild("armsTop");
		this.armsBottom = root.getChild("armsBottom");
	}
	
	public static TexturedModelData getTexturedModelData()
	{
		float yOffset = 15.0f;
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData shell = modelPartData.addChild("shell", ModelPartBuilder.create().uv(24, 16).cuboid(-3.0F, -4.75F, 1.0F, 6.0F, 7.0F, 4.0F, new Dilation(0.0F)).uv(0, 0).cuboid(-3.0F, -4.75F, -7.0F, 6.0F, 12.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, yOffset, 0.0F, 0.4363F, 0.0F, 0.0F));
		ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create().uv(20, 27).cuboid(-2.0F, -4.0F, 6.0F, 4.0F, 4.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, yOffset + 6.0f, -5.0F));
		ModelPartData lid = modelPartData.addChild("lid", ModelPartBuilder.create().uv(0, 20).cuboid(-2.0F, -1.0F, 9.0F, 4.0F, 1.0F, 8.0F, new Dilation(0.0F)).uv(24, 2).cuboid(-2.0F, -2.0F, 11.0F, 4.0F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, yOffset + 2.0f, -8.0F));
		ModelPartData armsLeft =  modelPartData.addChild("armsLeft", ModelPartBuilder.create().uv(0, 27).cuboid(0.0F, -2.0F, 0.0F, 0.0F, 4.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(-1.0F, yOffset + 4.0f, 5.0F));
		ModelPartData armsRight = modelPartData.addChild("armsRight", ModelPartBuilder.create().uv(0, 23).cuboid(0.0F, -2.0F, 0.0F, 0.0F, 4.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(1.0F, yOffset + 4.0f, 5.0F));
		ModelPartData armsTop = modelPartData.addChild("armsTop", ModelPartBuilder.create().uv(14, 0).cuboid(-2.0F, 0.0F, 0.0F, 4.0F, 0.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, yOffset + 3.0f, 5.0F));
		ModelPartData armsBottom = modelPartData.addChild("armsBottom", ModelPartBuilder.create().uv(10, 20).cuboid(-2.0F, 0.0F, 0.0F, 4.0F, 0.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, yOffset + 5.0f, 5.0F));
		return TexturedModelData.of(modelData, 64, 64);
	}
	
	@Override
	public void setAngles(NautilusEntity nautilusEntity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
	{
		float armsRotation = ((float) nautilusEntity.getArmsTickOffset() + ageInTicks) * 7.448451f * ((float) Math.PI / 180.0f);
        this.armsLeft.yaw = MathHelper.cos(armsRotation * 64.0f * ((float) Math.PI / 180.0f)) * -0.1f;
        this.armsRight.yaw = -this.armsLeft.yaw;
        this.armsTop.pitch = -this.armsLeft.yaw;
        this.armsBottom.pitch = this.armsLeft.yaw;
	}
	
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color)
	{
		shell.render(matrices, vertexConsumer, light, overlay, color);
		body.render(matrices, vertexConsumer, light, overlay, color);
		lid.render(matrices, vertexConsumer, light, overlay, color);
		armsLeft.render(matrices, vertexConsumer, light, overlay, color);
		armsRight.render(matrices, vertexConsumer, light, overlay, color);
		armsTop.render(matrices, vertexConsumer, light, overlay, color);
		armsBottom.render(matrices, vertexConsumer, light, overlay, color);
	}
}