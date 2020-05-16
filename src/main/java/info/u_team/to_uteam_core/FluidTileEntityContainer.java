package info.u_team.to_uteam_core;

import java.util.List;

import com.google.common.collect.Lists;

import info.u_team.u_team_core.container.UTileEntityContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fluids.FluidStack;

public class FluidTileEntityContainer<T extends TileEntity> extends UTileEntityContainer<T> {
	
	private final NonNullList<FluidStack> fluidStacks = NonNullList.create();
	private final List<FluidSlot> fluidSlots = Lists.newArrayList();
	
	// Client
	public FluidTileEntityContainer(ContainerType<?> type, int id, PlayerInventory playerInventory, PacketBuffer buffer) {
		super(type, id, playerInventory, buffer);
	}
	
	// Server
	public FluidTileEntityContainer(ContainerType<?> type, int id, PlayerInventory playerInventory, T tileEntity) {
		super(type, id, playerInventory, tileEntity);
	}
	
	@Override
	protected void init(boolean server) {
	}
	
	protected FluidSlot addFluidSlot(FluidSlot slot) {
		fluidSlots.add(slot);
		fluidStacks.add(FluidStack.EMPTY);
		return slot;
	}
	
	public NonNullList<FluidStack> getFluids() {
		final NonNullList<FluidStack> list = NonNullList.create();
		
		for (int index = 0; index < fluidSlots.size(); index++) {
			list.add(fluidSlots.get(index).getStack());
		}
		return list;
	}
	
}