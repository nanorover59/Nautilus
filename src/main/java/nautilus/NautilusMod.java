package nautilus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nautilus.entity.NautilusEntity;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EntityType.Builder;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnLocation;
import net.minecraft.entity.SpawnLocationTypes;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.SpawnRestriction.SpawnPredicate;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;

public class NautilusMod implements ModInitializer
{
	public static final String MOD_ID = "nautilus";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final EntityType<NautilusEntity> NAUTILUS = Builder.create(NautilusEntity::new, SpawnGroup.WATER_AMBIENT).dimensions(0.85f, 0.85f).eyeHeight(0.25f).maxTrackingRange(8).build();
	public static final Item NAUTILUS_SPAWN_EGG = new SpawnEggItem(NAUTILUS, 0xD4CCC3, 0xAE4635, new Item.Settings());
	public static final TagKey<Biome> SPAWNS_NAUTILUS = TagKey.of(RegistryKeys.BIOME, Identifier.of(MOD_ID, "spawns_nautilus"));
	
	@Override
	public void onInitialize()
	{
		registerMobEntity(NAUTILUS, "nautilus", NautilusEntity.createNautilusAttributes(), SpawnLocationTypes.IN_WATER, NautilusEntity::canSpawn);
		registerItem(NAUTILUS_SPAWN_EGG, "nautilus_spawn_egg");
		BiomeModifications.addSpawn(BiomeSelectors.tag(SPAWNS_NAUTILUS), SpawnGroup.WATER_AMBIENT, NAUTILUS, 1, 1, 1);
	}
	
	private static <T extends MobEntity> void registerMobEntity(EntityType<T> entityType, String name, DefaultAttributeContainer.Builder attributes, SpawnLocation location, SpawnPredicate<T> predicate)
	{
		Registry.register(Registries.ENTITY_TYPE, Identifier.of(MOD_ID, name), entityType);
		FabricDefaultAttributeRegistry.register(entityType, attributes);
		SpawnRestriction.register(entityType, location, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, predicate);
	}
	
	private static void registerItem(Item item, String name)
	{
		Registry.register(Registries.ITEM, Identifier.of(MOD_ID, name), item);
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(content -> content.add(item));
	}
}