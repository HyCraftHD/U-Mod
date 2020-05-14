package info.u_team.u_mod.api.fluid;

import net.minecraftforge.fluids.FluidStack;

public class FluidHandlerHelper {
	
	public static boolean canFluidStacksStack(FluidStack a, FluidStack b) {
		if (a.isEmpty() || !(a.getFluid() == b.getFluid()) || a.hasTag() != b.hasTag())
			return false;
		
		return (!a.hasTag() || a.getTag().equals(b.getTag()));
	}
	
	public static FluidStack copyStackWithSize(FluidStack stack, int size) {
		if (size == 0)
			return FluidStack.EMPTY;
		final FluidStack copy = stack.copy();
		copy.setAmount(size);
		return copy;
	}
	
}
