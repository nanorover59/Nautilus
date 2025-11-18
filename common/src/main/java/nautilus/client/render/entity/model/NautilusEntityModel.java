package nautilus.client.render.entity.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import nautilus.entity.NautilusEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

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
	
	public static LayerDefinition getTexturedModelData()
	{
		float yOffset = 15.0f;
		MeshDefinition modelData = new MeshDefinition();
		PartDefinition modelPartData = modelData.getRoot();
		PartDefinition shell = modelPartData.addOrReplaceChild("shell", CubeListBuilder.create().texOffs(24, 16).addBox(-3.0F, -4.75F, 1.0F, 6.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(0, 0).addBox(-3.0F, -4.75F, -7.0F, 6.0F, 12.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, yOffset, 0.0F, 0.4363F, 0.0F, 0.0F));
		PartDefinition body = modelPartData.addOrReplaceChild("body", CubeListBuilder.create().texOffs(20, 27).addBox(-2.0F, -4.0F, 6.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, yOffset + 6.0f, -5.0F));
		PartDefinition lid = modelPartData.addOrReplaceChild("lid", CubeListBuilder.create().texOffs(0, 20).addBox(-2.0F, -1.0F, 9.0F, 4.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(24, 2).addBox(-2.0F, -2.0F, 11.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, yOffset + 2.0f, -8.0F));
		PartDefinition armsLeft =  modelPartData.addOrReplaceChild("armsLeft", CubeListBuilder.create().texOffs(0, 27).addBox(0.0F, -2.0F, 0.0F, 0.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, yOffset + 4.0f, 5.0F));
		PartDefinition armsRight = modelPartData.addOrReplaceChild("armsRight", CubeListBuilder.create().texOffs(0, 23).addBox(0.0F, -2.0F, 0.0F, 0.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, yOffset + 4.0f, 5.0F));
		PartDefinition armsTop = modelPartData.addOrReplaceChild("armsTop", CubeListBuilder.create().texOffs(14, 0).addBox(-2.0F, 0.0F, 0.0F, 4.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, yOffset + 3.0f, 5.0F));
		PartDefinition armsBottom = modelPartData.addOrReplaceChild("armsBottom", CubeListBuilder.create().texOffs(10, 20).addBox(-2.0F, 0.0F, 0.0F, 4.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, yOffset + 5.0f, 5.0F));
		return LayerDefinition.create(modelData, 64, 64);
	}
	
	@Override
	public void setupAnim(NautilusEntity nautilusEntity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
	{
		float armsRotation = ((float) nautilusEntity.getArmsTickOffset() + ageInTicks) * 7.448451f * ((float) Math.PI / 180.0f);
        this.armsLeft.yRot = Mth.cos(armsRotation * 64.0f * ((float) Math.PI / 180.0f)) * -0.1f;
        this.armsRight.yRot = -this.armsLeft.yRot;
        this.armsTop.xRot = -this.armsLeft.yRot;
        this.armsBottom.xRot = this.armsLeft.yRot;
	}
	
	@Override
	public void renderToBuffer(PoseStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color)
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