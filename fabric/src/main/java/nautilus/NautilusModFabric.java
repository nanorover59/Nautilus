package nautilus;

import nautilus.entity.NautilusEntity;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.levelgen.Heightmap;

public class NautilusModFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        registerMobEntity(NautilusMod.NAUTILUS, "nautilus", NautilusEntity.createNautilusAttributes(), SpawnPlacementTypes.IN_WATER, NautilusEntity::checkSurfaceWaterAnimalSpawnRules);
        registerItem(NautilusMod.NAUTILUS_SPAWN_EGG, "nautilus_spawn_egg");
        BiomeModifications.addSpawn(BiomeSelectors.tag(NautilusMod.SPAWNS_NAUTILUS), MobCategory.WATER_AMBIENT, NautilusMod.NAUTILUS, 5, 1, 1);
    }

    private static <T extends Mob> void registerMobEntity(EntityType<T> entityType, String name, AttributeSupplier.Builder attributes, SpawnPlacementType location, SpawnPlacements.SpawnPredicate<T> predicate) {
        Registry.register(BuiltInRegistries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(NautilusMod.MOD_ID, name), entityType);
        FabricDefaultAttributeRegistry.register(entityType, attributes);
        SpawnPlacements.register(entityType, location, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, predicate);
    }

    private static void registerItem(Item item, String name) {
        Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(NautilusMod.MOD_ID, name), item);
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.SPAWN_EGGS).register(content -> content.accept(item));
    }
}