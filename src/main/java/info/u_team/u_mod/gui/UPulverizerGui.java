package info.u_team.u_mod.gui;

import info.u_team.u_mod.UConstants;
import info.u_team.u_mod.container.UPulverizerContainer;
import info.u_team.u_mod.tilentity.UPulverizerTile;
import info.u_team.u_team_core.container.UContainer;
import net.minecraft.util.ResourceLocation;

public class UPulverizerGui extends UGuiContainer {
	
	private UPulverizerTile tile;
	
	public UPulverizerGui(UContainer inventorySlotsIn) {
		super(inventorySlotsIn);
		UPulverizerContainer container = ((UPulverizerContainer) this.inventorySlots);
		tile = (UPulverizerTile) container.world.getTileEntity(container.pos);
		
		BACKGROUND = new ResourceLocation(UConstants.MODID, "textures/gui/pulverizer.png");
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2;
		
		this.drawTexturedModalRect(i + 47, j + 28, 0, 166, Math.round(64 * (100 - tile.getField(0)) / 100), 7);
	}
}
