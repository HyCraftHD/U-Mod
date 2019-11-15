package info.u_team.u_mod.tileentity.basic;

import info.u_team.u_team_core.energy.BasicEnergyStorage;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.common.util.LazyOptional;

public abstract class BasicEnergyTileEntity extends BasicTickableTileEntity {
	
	protected final LazyOptional<BasicEnergyStorage> internalEnergyStorage;
	
	public BasicEnergyTileEntity(TileEntityType<?> type, int capacity, int maxReceive, int maxExtract) {
		this(type, capacity, maxReceive, maxExtract, 0);
	}
	
	public BasicEnergyTileEntity(TileEntityType<?> type, int capacity, int maxReceive, int maxExtract, int currentEnergy) {
		super(type);
		internalEnergyStorage = LazyOptional.of(() -> new BasicEnergyStorage(capacity, maxReceive, maxExtract, currentEnergy));
	}
	
	@Override
	public void writeNBT(CompoundNBT compound) {
		internalEnergyStorage.ifPresent(handler -> compound.put("energy", handler.serializeNBT()));
	}
	
	@Override
	public void readNBT(CompoundNBT compound) {
		internalEnergyStorage.ifPresent(handler -> handler.deserializeNBT(compound.getCompound("energy")));
	}
	
	// Invalidate lazy optional
	@Override
	public void remove() {
		super.remove();
		internalEnergyStorage.invalidate();
	}
	
	// Getter
	public LazyOptional<BasicEnergyStorage> getInternalEnergyStorage() {
		return internalEnergyStorage;
	}
	
}