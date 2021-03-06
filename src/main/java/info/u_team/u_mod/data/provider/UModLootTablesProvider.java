package info.u_team.u_mod.data.provider;

import static info.u_team.u_mod.init.UModBlocks.*;

import java.util.function.BiConsumer;

import info.u_team.u_team_core.data.*;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTable;

public class UModLootTablesProvider extends CommonLootTablesProvider {
	
	public UModLootTablesProvider(GenerationData data) {
		super(data);
	}
	
	@Override
	protected void registerLootTables(BiConsumer<ResourceLocation, LootTable> consumer) {
		registerBlock(ELECTRIC_FURNACE, addTileEntityBlockLootTable(ELECTRIC_FURNACE.get()), consumer);
		registerBlock(CRUSHER, addTileEntityBlockLootTable(CRUSHER.get()), consumer);
		registerBlock(ORE_WASHER, addTileEntityBlockLootTable(ORE_WASHER.get()), consumer);
	}
}
