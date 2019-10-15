package info.u_team.u_mod.block;

import info.u_team.u_mod.init.UModItemGroups;
import info.u_team.u_mod.init.UModTileEntityTypes;
import info.u_team.u_team_core.block.UTileEntityBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;

public class EnergyFurnceBlock extends UTileEntityBlock{

	public EnergyFurnceBlock(String name) {
		super(name, UModItemGroups.GROUP, Properties.create(Material.IRON), () -> UModTileEntityTypes.ENERGYFURNANCE);
	}
	
// TEST ONLY
	@Override
	public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn,
			BlockRayTraceResult hit) {
		TileEntity entity = worldIn.getTileEntity(pos);
		if(entity != null) {
			entity.getCapability(CapabilityEnergy.ENERGY).ifPresent(store -> {
				player.sendMessage(new StringTextComponent(String.valueOf(store.getEnergyStored())));
			});
		}
		return true;
	}
// I wish I had preprocessors

}