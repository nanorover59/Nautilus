package nautilus;

import nautilus.client.render.entity.NautilusEntityRenderer;
import nautilus.client.render.entity.model.NautilusEntityModel;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

public class NautilusModClient implements ClientModInitializer
{
	public static final EntityModelLayer MODEL_NAUTILUS_LAYER = new EntityModelLayer(Identifier.of(NautilusMod.MOD_ID, "nautilus"), "main");
	
	@Override
	public void onInitializeClient()
	{
		EntityRendererRegistry.register(NautilusMod.NAUTILUS, (context) -> new NautilusEntityRenderer(context));
		EntityModelLayerRegistry.registerModelLayer(MODEL_NAUTILUS_LAYER, NautilusEntityModel::getTexturedModelData);
	}
}