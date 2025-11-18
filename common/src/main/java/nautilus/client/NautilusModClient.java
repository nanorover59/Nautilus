package nautilus.client;

import nautilus.NautilusMod;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class NautilusModClient
{
	public static final ModelLayerLocation MODEL_NAUTILUS_LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(NautilusMod.MOD_ID, "nautilus"), "main");
}