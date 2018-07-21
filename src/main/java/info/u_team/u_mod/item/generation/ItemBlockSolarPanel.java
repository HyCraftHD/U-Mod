package info.u_team.u_mod.item.generation;

import info.u_team.u_mod.block.generation.BlockSolarPanel;
import info.u_team.u_mod.block.generation.BlockSolarPanel.EnumType;
import info.u_team.u_team_core.item.UItemBlock;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class ItemBlockSolarPanel extends UItemBlock {
	
	public ItemBlockSolarPanel(BlockSolarPanel block) {
		super(block);
		setHasSubtypes(true);
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		int metadata = stack.getMetadata();
		return getUnlocalizedName() + "." + EnumType.byMetadata(metadata).getName();
	}
	
	@Override
	public int getMetadata(int damage) {
		return damage;
	}
	
	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (isInCreativeTab(tab)) {
			for (EnumType enumtype : EnumType.values()) {
				items.add(new ItemStack(this, 1, enumtype.getMetadata()));
			}
		}
	}
}