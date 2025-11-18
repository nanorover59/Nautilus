package nautilus.client;

import nautilus.NautilusMod;
import nautilus.client.render.entity.NautilusEntityRenderer;
import nautilus.client.render.entity.model.NautilusEntityModel;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class NautilusModClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(NautilusMod.NAUTILUS, NautilusEntityRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(NautilusModClient.MODEL_NAUTILUS_LAYER, NautilusEntityModel::getTexturedModelData);
    }
}