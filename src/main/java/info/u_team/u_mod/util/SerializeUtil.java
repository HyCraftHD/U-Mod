package info.u_team.u_mod.util;

import com.google.gson.*;

import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.ForgeRegistries;

public class SerializeUtil {
	
	public static JsonElement serializeIngredient(Ingredient ingredient) {
		return ingredient.serialize();
	}
	
	public static Ingredient deserializeIngredient(JsonElement json) {
		return Ingredient.deserialize(json);
	}
	
	public static JsonElement serializeItemStack(ItemStack stack) {
		final String itemName = ForgeRegistries.ITEMS.getKey(stack.getItem()).toString();
		final int count = stack.getCount();
		if (stack.hasTag() || count != 1) {
			final JsonObject object = new JsonObject();
			object.addProperty("item", itemName);
			if (count != 1) {
				object.addProperty("count", stack.getCount());
			}
			if (stack.hasTag()) {
				object.addProperty("nbt", stack.getTag().toString());
			}
			return object;
		} else {
			return new JsonPrimitive(itemName);
		}
	}
	
	public static ItemStack deserializeItemStack(JsonElement json) {
		if (json != null && !json.isJsonNull()) {
			if (json.isJsonObject()) {
				return CraftingHelper.getItemStack(json.getAsJsonObject(), true);
			} else if (json.isJsonPrimitive()) {
				final Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(json.getAsString()));
				if (item == null) {
					throw new IllegalStateException("Item: " + json.getAsString() + " does not exist");
				}
				return new ItemStack(item);
			} else {
				throw new JsonSyntaxException("Expected item to be object or array of objects");
			}
		} else {
			throw new JsonSyntaxException("Item cannot be null");
		}
	}
	
}
