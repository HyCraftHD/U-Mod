package info.u_team.u_mod.init;

import info.u_team.u_mod.UConstants;
import info.u_team.u_team_core.item.UItem;
import info.u_team.u_team_core.registry.ItemRegistry;
import info.u_team.u_team_core.util.RegistryUtil;
import net.minecraft.item.Item;

public class UItems {
	
	// public static final UItem dust = new ItemDust("dust",
	// EnumResources.ITEM_LIST);
	// public static final UItem ingot = new ItemDust("ingot",
	// EnumResources.ITEM_LIST);
	
	public static final UItem test = new UItem("test", UCreativeTabs.MACHINE);
	
	public static void init() {
		ItemRegistry.register(UConstants.MODID, RegistryUtil.getRegistryEntries(Item.class, UItems.class));
	}
	
}
