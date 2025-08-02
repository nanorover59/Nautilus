package nautilus;

import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nautilus.entity.NautilusEntity;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.EntityType;
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
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;

public class NautilusMod implements ModInitializer
{
	public static final String MOD_ID = "nautilus";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final EntityType<NautilusEntity> NAUTILUS = registerMobEntity("nautilus", EntityType.Builder.create(NautilusEntity::new, SpawnGroup.WATER_AMBIENT).dimensions(0.75f, 0.75f).eyeHeight(0.5F).maxTrackingRange(8), NautilusEntity.createNautilusAttributes(), SpawnLocationTypes.IN_WATER, NautilusEntity::canSpawn);
	public static final Item NAUTILUS_SPAWN_EGG = registerItem("nautilus_spawn_egg", settings -> new SpawnEggItem(NAUTILUS, settings));
	public static final TagKey<Biome> SPAWNS_NAUTILUS = TagKey.of(RegistryKeys.BIOME, Identifier.of(MOD_ID, "spawns_nautilus"));
	
	@Override
	public void onInitialize()
	{
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(content -> content.add(NAUTILUS_SPAWN_EGG));
		BiomeModifications.addSpawn(BiomeSelectors.tag(SPAWNS_NAUTILUS), SpawnGroup.WATER_AMBIENT, NAUTILUS, 1, 1, 1);
	}
	
	private static <T extends MobEntity> EntityType<T> registerMobEntity(String id, EntityType.Builder<T> type, DefaultAttributeContainer.Builder attributes, SpawnLocation location, SpawnPredicate<T> predicate)
	{
		RegistryKey<EntityType<?>> key = RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(MOD_ID, id));
		EntityType<T> entityType = Registry.register(Registries.ENTITY_TYPE, key, type.build(key));
		FabricDefaultAttributeRegistry.register(entityType, attributes);
		SpawnRestriction.register(entityType, location, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, predicate);
		return entityType;
	}
	
	public static Item registerItem(String id, Function<Item.Settings, Item> factory)
	{
		RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(MOD_ID, id));
		Item item = (Item) factory.apply(new Item.Settings().registryKey(key));
		return Registry.register(Registries.ITEM, key, item);
	}
}