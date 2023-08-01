package Model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * A Special Vending Machine is a vending machine that supports customizable items (currently unimplemented for MCO1)
 */
public class SpecialVendingMachine extends VendingMachine
{
	private final HashMap<String, LinkedList<Item>> specialItemStock;

	private final HashSet<String> specialItems = new HashSet<>(Set.of("noodles", "egg", "chashu", "tonkotsu broth",
			"miso broth", "shio broth", "spring onions", "fish cake", "pork bones", "chicken bones", "miso paste", "salt", "water"));

	private final HashSet<String> unbuyableItems = new HashSet<>(Set.of("tonkotsu broth", "miso broth", "shio broth",
			"spring onions", "pork bones", "salt", "chicken bones", "miso paste"));

	public SpecialVendingMachine(int slotCount)
	{
		super(slotCount);

		specialItemStock = new HashMap<>();

		for (String itemName : specialItems)
		{
			specialItemStock.put(itemName, new LinkedList<>());
		}
	}

	@Override
	public boolean addItemToSlot(Item item, int slotIndex, int quantity)
	{
		if (!specialItems.contains(item.getName().toLowerCase()))
		{
			return false;
		}

		super.addItemToSlot(item, slotIndex, quantity);
		addSpecialItem(item, quantity);

		return true;
	}

	public void addSpecialItem(Item item, int quantity)
	{
		for (int i = 0; i < quantity; i++)
		{
			specialItemStock.get(item.getName().toLowerCase()).add(item);
		}
	}

	public void removeSpecialItem(String itemName, int quantity)
	{
		if (!specialItemStock.containsKey(itemName.toLowerCase()))
		{
			return;
		}

		if (quantity <= 0)
		{
			return;
		}

		if (specialItemStock.get(itemName.toLowerCase()).size() - quantity < 0)
		{
			return;
		}

		boolean machineHasItem = false;
		int slotIndex = 0;

		for (Slot slot : this.slots)
		{
			if (slot.getItemList().size() == 0)
			{
				slotIndex++;
				continue;
			}

			if (slot.getItemList().get(0).getName().equalsIgnoreCase(itemName))
			{
				machineHasItem = true;
				break;
			}

			slotIndex++;
		}

		if (!machineHasItem && !unbuyableItems.contains(itemName.toLowerCase()))
		{
			return;
		}

		for (int i = 0; i < quantity; i++)
		{
			System.out.println("Removed from special stock " + specialItemStock.get(itemName).remove());
		}

		for (int i = 0; i < quantity; i++)
		{
			System.out.println("Removed from machine " + this.slots[slotIndex].getItemList().remove(0));
		}
	}

	public HashMap<String, LinkedList<Item>> getSpecialItemStock()
	{
		return specialItemStock;
	}

	public HashSet<String> getSpecialItems()
	{
		return specialItems;
	}

	public HashSet<String> getUnbuyableItems()
	{
		return unbuyableItems;
	}

	public boolean enoughBrothIngredients(String brothName)
	{
		switch (brothName)
		{
			case "tonkotsu broth" -> {
				return specialItemStock.get("pork bones").size() >= 1 && specialItemStock.get("water").size() >= 5;
			}
			case "shio broth" -> {
				return specialItemStock.get("salt").size() >= 1 &&
						specialItemStock.get("chicken bones").size() >= 2 &&
						specialItemStock.get("water").size() >= 5;
			}
			case "miso broth" -> {
				return specialItemStock.get("salt").size() >= 1 &&
						specialItemStock.get("chicken bones").size() >= 2 &&
						specialItemStock.get("water").size() >= 5 &&
						specialItemStock.get("miso paste").size() >= 1;
			}
			default -> {
				return false;
			}
		}
	}

	public void removeBrothIngredients(String brothName)
	{
		switch (brothName)
		{
			case "tonkotsu broth" -> {
				this.removeSpecialItem("pork bones", 1);
				this.removeSpecialItem("water", 5);
			}
			case "shio broth" -> {
				this.removeSpecialItem("salt", 1);
				this.removeSpecialItem("chicken bones", 2);
				this.removeSpecialItem("water", 5);
			}
			case "miso broth" -> {
				this.removeSpecialItem("salt", 1);
				this.removeSpecialItem("chicken bones", 2);
				this.removeSpecialItem("water", 5);
				this.removeSpecialItem("miso paste", 1);
			}
		}
	}
}
