package info.u_team.to_uteam_core;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;

import info.u_team.u_team_core.container.UTileEntityContainer;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.container.*;
import net.minecraft.network.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.network.*;

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
	
	public FluidSlot getFluidSlot(int slot) {
		return fluidSlots.get(slot);
	}
	
	public void setFluidStackInSlot(int slot, FluidStack stack) {
		getFluidSlot(slot).putStack(stack);
	}
	
	public void setAllFluidSlots(List<FluidStack> list) {
		for (int index = 0; index < list.size(); index++) {
			getFluidSlot(index).putStack(list.get(index));
		}
	}
	
	@Override
	public void addListener(IContainerListener listener) {
		super.addListener(listener);
		if (listener instanceof ServerPlayerEntity) {
			TestNetwork.NETWORK.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) listener), new FluidSetAllContainerMessage(windowId, getFluids()));
		}
	}
	
	@Override
	public void detectAndSendChanges() {
		for (int index = 0; index < fluidSlots.size(); index++) {
			final FluidStack stackSlot = fluidSlots.get(index).getStack();
			final FluidStack stackSynced = fluidStacks.get(index);
			if (!stackSynced.isFluidStackIdentical(stackSlot)) {
				final FluidStack stackNewSynced = stackSlot.copy();
				fluidStacks.set(index, stackNewSynced);
				
				final List<NetworkManager> networkManagers = listeners.stream() //
						.filter(listener -> listener instanceof ServerPlayerEntity) //
						.map(listener -> ((ServerPlayerEntity) listener).connection.getNetworkManager()) //
						.collect(Collectors.toList());
				
				TestNetwork.NETWORK.send(PacketDistributor.NMLIST.with(() -> networkManagers), new FluidSetSlotContainerMessage(windowId, index, stackNewSynced));
			}
		}
	}
	
	public List<FluidSlot> getFluidSlots() {
		return fluidSlots;
	}
}
