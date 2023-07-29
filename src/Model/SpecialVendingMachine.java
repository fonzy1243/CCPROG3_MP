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
			"miso broth", "shio broth", "spring onions", "fish cake", "pork bones", "chicken bones", "salt", "water"));

	private final HashSet<String> unbuyableItems = new HashSet<>(Set.of("tonkotsu broth", "miso broth", "shio broth",
			"spring onions", "pork bones", "salt", "chicken bones"));

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
		boolean isAdded = super.addItemToSlot(item, slotIndex, quantity);

		if (specialItems.contains(item.getName().toLowerCase()) && isAdded)
		{
			addSpecialItem(item, quantity);
		}

		return isAdded;
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

		for(Slot slot : this.slots)
		{
			slotIndex++;

			if (slot.getItemList().get(0).getName().equalsIgnoreCase(itemName))
			{
				machineHasItem = true;
				break;
			}
		}

		if (!machineHasItem && !unbuyableItems.contains(itemName.toLowerCase()))
		{
			return;
		}

		for (int i = 0; i < quantity; i++)
		{
			specialItemStock.get(itemName).remove();
		}

		if (!unbuyableItems.contains(itemName.toLowerCase()))
		{
			for (int i = 0; i < quantity; i++)
			{
				this.slots[slotIndex].getItemList().remove(0);
			}
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
}
