package info.u_team.u_mod.api.fluid;

import net.minecraft.inventory.IInventory;
import net.minecraftforge.fluids.capability.IFluidHandler;

public interface IGeneralInventory extends IInventory, IFluidHandler {
	
	boolean hasItems();
	
	boolean hasFluids();
	
}
