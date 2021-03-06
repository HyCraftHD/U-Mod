package info.u_team.u_mod.api.inventory;

import net.minecraft.inventory.IInventory;
import net.minecraftforge.fluids.capability.IFluidHandler;

public interface IGeneralInventory extends IInventory, IFluidHandler {
	
	boolean hasItems();
	
	boolean hasFluids();
	
	boolean isItemsEmpty();
	
	boolean isFluidsEmpty();
	
	@Override
	default boolean isEmpty() {
		return isItemsEmpty();
	}
	
	default boolean isAllEmpty() {
		return isItemsEmpty() && isFluidsEmpty();
	}
	
}
