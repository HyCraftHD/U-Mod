package info.u_team.u_mod.api;

import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;

public enum InteractionType {
	
	EXECUTE(false),
	SIMULATE(true);
	
	private final boolean simulate;
	
	private InteractionType(boolean simulate) {
		this.simulate = simulate;
	}
	
	public boolean isSimulate() {
		return simulate;
	}
	
	public static InteractionType fromBoolean(boolean simulate) {
		return simulate ? SIMULATE : EXECUTE;
	}
	
	public static InteractionType fromFluidAction(FluidAction action) {
		return action.simulate() ? SIMULATE : EXECUTE;
	}
}
