package info.u_team.u_mod.init;

import info.u_team.u_mod.UMod;
import info.u_team.u_mod.tileentity.CrateTileEntity;
import info.u_team.u_team_core.tileentitytype.UTileEntityType.UBuilder;
import info.u_team.u_team_core.util.registry.BaseRegistryUtil;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(bus = Bus.MOD, modid = UMod.MODID)
public class UModTileEntityTypes {
	
	public static final TileEntityType<CrateTileEntity> CRATE = UBuilder.create("crate", CrateTileEntity::new, UModBlocks.CRATE).build();
	
	@SubscribeEvent
	public static void register(Register<TileEntityType<?>> event) {
		BaseRegistryUtil.getAllGenericRegistryEntriesAndApplyNames(UMod.MODID, TileEntityType.class).forEach(event.getRegistry()::register);
	}
	
}