package info.u_team.u_mod.tilentity.energy;

import java.util.List;

import info.u_team.u_mod.api.*;
import info.u_team.u_mod.api.IIOMode.IOModeHandler;
import info.u_team.u_mod.energy.EnergyConsumer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fml.relauncher.*;
import net.minecraftforge.items.*;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

public abstract class TileEntityMachine extends TileEntityEnergyGui implements IClientProgress {	
	
	protected final IOModeHandler iomode_handler;
	
	protected int max_progress = 100;
	protected int progress = max_progress;
	
	protected int recipeid = -1;
	
	@SideOnly(Side.CLIENT)
	public int progress_client;
	
	public TileEntityMachine(int size, String name) {
		super(size, name, new EnergyConsumer(40000, 1000));
		this.iomode_handler = new IOModeHandler(this);
	}
	
	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		return this.iomode_handler.getSlots(side);
	}
	
	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
		return this.iomode_handler.canExtractItem(direction, index, stack);
	}
	
	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
		return this.iomode_handler.canInsertItem(direction, index, itemStackIn);
	}
	
	public void checkRecipe() {
		for (int i = 0; i < getRecipes().size(); i++) {
			IMachineRecipe recipe = getRecipes().get(i);
			if (recipe.areIngredientsMatching(this)) {
				recipeid = i;
				if (max_progress != recipe.getTime()) {
					progress = max_progress = recipe.getTime();
				} else {
					max_progress = recipe.getTime();
				}
				return;
			}
		}
		progress = max_progress = 100;
		recipeid = -1;
	}
	
	@Override
	public void update() {
		if (world.isRemote) {
			return;
		}
		if (recipeid >= 0) {
			IMachineRecipe recipe = getRecipes().get(recipeid);
			if (!recipe.areIngredientsMatching(this)) {
				recipeid = -1;
				return;
			}
			if (!recipe.isEnergyMatching(this) || !recipe.areOutputsMatching(this)) {
				return;
			}
			progress--;
			if (progress <= 0) {
				recipe.execute(this);
				progress = max_progress;
				super.markDirty();
			}
		}
	}
	
	@Override
	public void markDirty() {
		if (!world.isRemote) {
			checkRecipe();
		}
		super.markDirty();
	}
	
	public abstract List<IMachineRecipe> getRecipes();
	
	@Override
	public void readNBT(NBTTagCompound compound) {
		super.readNBT(compound);
		progress = compound.getInteger("progress");
		recipeid = compound.getInteger("recipe");
	}
	
	@Override
	public void writeNBT(NBTTagCompound compound) {
		super.writeNBT(compound);
		compound.setInteger("progress", progress);
		compound.setInteger("recipe", recipeid);
	}
	
	@Override
	public int getField(int id) {
		if (id == 2) {
			return 100 - (int) (((float) progress / (float) max_progress) * 100);
		}
		return super.getField(id);
	}
	
	@Override
	public void setField(int id, int value) {
		if (id == 2) {
			progress_client = value;
		} else {
			super.setField(id, value);
		}
	}
	
	@Override
	public int getFieldCount() {
		return 3;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public int getImplProgress() {
		return progress_client;
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || capability == CapabilityEnergy.ENERGY
				|| capability == CapabilityIOMode.IOMODE_CAPABILITY) {
			return true;
		}
		return super.hasCapability(capability, facing);
	}
		
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && facing != null) {
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(this.iomode_handler.get(facing));
		} else if (capability == CapabilityEnergy.ENERGY) {
			return CapabilityEnergy.ENERGY.cast(ienergy);
		} else if (capability == CapabilityIOMode.IOMODE_CAPABILITY) {
			return CapabilityIOMode.IOMODE_CAPABILITY.cast(this.iomode_handler);
		}
		return super.getCapability(capability, facing);
	}
}
