package info.u_team.u_mod.tilentity.machine;

import static info.u_team.u_mod.recipe.RecipeManager.getAlloyFurnaceRecipes;

import info.u_team.u_mod.container.machine.ContainerPulverizer;
import info.u_team.u_mod.recipe.machine.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

public class TileEntityAlloyFurnace extends TileEntityMachine {
	
	public static final int[] IN = { 0, 1, 2 }, OUT = { 3 };
	
	public TileEntityAlloyFurnace() {
		super(4, "alloyfurnace");
	}
	
	@Override
	public void checkRecipe() {
		for (int i = 0; i < getAlloyFurnaceRecipes().size(); i++) {
			RecipeAlloyFurnace recipe = getAlloyFurnaceRecipes().get(i);
			if (recipe.areIngredientsMatching(this)) {
				System.out.println("TEST");
				recipeid = i;
				progress = max_progress = recipe.getTime();
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
			RecipeAlloyFurnace recipe = getAlloyFurnaceRecipes().get(recipeid);
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
	public void readNBT(NBTTagCompound compound) {
		super.readNBT(compound);
		recipeid = compound.getInteger("recipe");
	}
	
	@Override
	public void writeNBT(NBTTagCompound compound) {
		super.writeNBT(compound);
		compound.setInteger("recipe", recipeid);
	}
	
	@Override
	public int rate() {
		return 5;
	}
	
	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		if (side == EnumFacing.DOWN) {
			return OUT;
		} else if (side == EnumFacing.UP) {
			return IN;
		}
		return null;
	}
	
	@Override
	public boolean canInsertItem(int index, ItemStack stack, EnumFacing direction) {
		if (index == 0) {
			return true;
		}
		return false;
	}
	
	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
		if (index > 0) {
			return true;
		}
		return false;
	}
	
	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		if (index == 0) {
			return true;
		}
		return false;
	}
	
	@Override
	public Container createContainer(InventoryPlayer inventory, EntityPlayer player) {
		return new ContainerPulverizer(player, world, pos);
	}
	
}
