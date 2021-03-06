package info.u_team.u_mod.data.provider;

import static info.u_team.u_mod.init.UModBlocks.*;

import info.u_team.u_mod.block.basic.BasicMachineBlock;
import info.u_team.u_team_core.data.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ConfiguredModel;

public class UModBlockStatesProvider extends CommonBlockStatesProvider {
	
	public UModBlockStatesProvider(GenerationData data) {
		super(data);
	}
	
	@Override
	protected void registerStatesAndModels() {
		addMachine(ELECTRIC_FURNACE.get());
		addMachine(CRUSHER.get());
		addMachine(ORE_WASHER.get());
	}
	
	private void addMachine(BasicMachineBlock block) {
		final String path = block.getRegistryName().getPath();
		
		getVariantBuilder(block).forAllStatesExcept(state -> {
			final String workingState = state.get(BasicMachineBlock.WORKING) ? "_working" : "";
			return ConfiguredModel.builder() //
					.modelFile(cubeFacing(path + workingState, modBlockLoc("machine/" + path + "_front" + workingState), modBlockLoc("machine/side"))) //
					.rotationY(((int) state.get(BasicMachineBlock.FACING).getHorizontalAngle() + 180) % 360).build();
		}, BasicMachineBlock.POWERED);
	}
	
	private ResourceLocation modBlockLoc(String name) {
		return modLoc("block/" + name);
	}
	
}
