package info.u_team.u_mod.tileentity;

import info.u_team.u_mod.container.OreWasherContainer;
import info.u_team.u_mod.init.*;
import info.u_team.u_mod.recipe.OneIngredientMachineRecipe;
import info.u_team.u_mod.tileentity.basic.BasicMachineTileEntity;
import info.u_team.u_mod.util.recipe.RecipeData;
import info.u_team.u_team_core.inventory.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.text.*;
import net.minecraftforge.common.util.LazyOptional;

public class OreWasherTileEntity extends BasicMachineTileEntity<OneIngredientMachineRecipe> {
	
	protected final UFluidStackHandler fluidIngredientSlots;
	protected final UFluidStackHandler fluidOutputSlots;
	
	protected final LazyOptional<UFluidStackHandler> fluidIngredientSlotsOptional;
	protected final LazyOptional<UFluidStackHandler> fluidOutputSlotsOptional;
	
	public OreWasherTileEntity() {
		super(UModTileEntityTypes.ORE_WASHER.get(), 20000, 100, 0, UModRecipeTypes.CRUSHER, 1, 6, 3, RecipeData.getBasicMachine());
		
		fluidIngredientSlots = new TileEntityUFluidStackHandler(1, this);
		fluidOutputSlots = new TileEntityUFluidStackHandler(1, this);
		
		fluidIngredientSlotsOptional = LazyOptional.of(() -> fluidIngredientSlots);
		fluidOutputSlotsOptional = LazyOptional.of(() -> fluidOutputSlots);
	}
	
	// Container
	
	@Override
	public ITextComponent getDisplayName() {
		return new TranslationTextComponent("container.umod.ore_washer");
	}
	
	@Override
	public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity player) {
		return new OreWasherContainer(id, playerInventory, this);
	}
	
	public UFluidStackHandler getFluidIngredientSlots() {
		return fluidIngredientSlots;
	}
	
	public UFluidStackHandler getFluidOutputSlots() {
		return fluidOutputSlots;
	}
}
