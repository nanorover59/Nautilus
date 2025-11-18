package nautilus.client;

import nautilus.NautilusMod;
import nautilus.client.render.entity.NautilusEntityRenderer;
import nautilus.client.render.entity.model.NautilusEntityModel;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.ClientNeoForgeMod;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@Mod(value = NautilusMod.MOD_ID, dist = Dist.CLIENT)
public class NautilusModClientNeoForge {
    public NautilusModClientNeoForge(IEventBus eventBus) {
        eventBus.addListener(NautilusModClientNeoForge::onRegisterLayerDefinitions);
        eventBus.addListener(NautilusModClientNeoForge::onRegisterEntityRenderers);
    }

    public static void onRegisterLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(NautilusModClient.MODEL_NAUTILUS_LAYER, NautilusEntityModel::getTexturedModelData);
    }

    public static void onRegisterEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(NautilusMod.NAUTILUS, NautilusEntityRenderer::new);
    }
}