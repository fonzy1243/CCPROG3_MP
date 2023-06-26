package Model;

import java.util.LinkedList;
import java.util.List;

/**
 * A Slot contains a list of items with the same item type.
 */
public class Slot
{
	private final List<Item> itemList;

	public Slot()
	{
		itemList = new LinkedList<>();
	}

	/**
	 * Get the list of items in the lost
	 * @return item list
	 */
	public List<Item> getItemList()
	{
		return this.itemList;
	}

	/**
	 * Get the size of the item list
	 * @return item count
	 */
	public int getItemsCount()
	{
		return this.itemList.size();
	}
}
