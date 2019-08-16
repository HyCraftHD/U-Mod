package info.u_team.u_mod.tileentity;

import info.u_team.u_mod.init.UModTileEntities;
import info.u_team.u_team_core.tileentity.UTileEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;

public class CrateTileEntity extends UTileEntity {
	
	public CrateTileEntity() {
		super(UModTileEntities.CRATE);
	}
	
	@Override
	public void writeNBT(CompoundNBT compound) {
		super.writeNBT(compound);
	}
	
	@Override
	public void readNBT(CompoundNBT compound) {
		super.readNBT(compound);
	}
}