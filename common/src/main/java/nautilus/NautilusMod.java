package nautilus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nautilus.entity.NautilusEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityType.Builder;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.biome.Biome;

public class NautilusMod
{
	public static final String MOD_ID = "nautilus";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final EntityType<NautilusEntity> NAUTILUS = Builder.of(NautilusEntity::new, MobCategory.WATER_AMBIENT).sized(0.85f, 0.85f).eyeHeight(0.25f).clientTrackingRange(8).build("nautilus");
	public static final Item NAUTILUS_SPAWN_EGG = new SpawnEggItem(NAUTILUS, 0xD4CCC3, 0xAE4635, new Item.Properties());
	public static final TagKey<Biome> SPAWNS_NAUTILUS = TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(MOD_ID, "spawns_nautilus"));
}