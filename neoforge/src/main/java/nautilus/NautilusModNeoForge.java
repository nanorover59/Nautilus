package nautilus;


import nautilus.client.NautilusModClient;
import nautilus.client.render.entity.model.NautilusEntityModel;
import nautilus.entity.NautilusEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod(NautilusMod.MOD_ID)
public class NautilusModNeoForge {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, NautilusMod.MOD_ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.createItems(NautilusMod.MOD_ID);

    public NautilusModNeoForge(IEventBus eventBus) {
        ENTITY_TYPES.register("nautilus", () -> NautilusMod.NAUTILUS);
        ENTITY_TYPES.register(eventBus);

        ITEMS.register("nautilus_spawn_egg", () -> NautilusMod.NAUTILUS_SPAWN_EGG);
        ITEMS.register(eventBus);

        eventBus.addListener(NautilusModNeoForge::onRegisterAttributes);
        eventBus.addListener(NautilusModNeoForge::onBuildContents);
    }

    public static void onRegisterAttributes(EntityAttributeCreationEvent event) {
        event.put(NautilusMod.NAUTILUS, NautilusEntity.createNautilusAttributes().build());
    }

    public static void onBuildContents(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.SPAWN_EGGS)
            event.accept(NautilusMod.NAUTILUS_SPAWN_EGG);
    }
}