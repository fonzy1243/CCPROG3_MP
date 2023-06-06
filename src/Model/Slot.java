package Model;

import java.util.LinkedList;
import java.util.List;

public class Slot
{
	private final List<Item> itemList;

	public Slot()
	{
		itemList = new LinkedList<>();
	}

	public List<Item> getItemList()
	{
		return this.itemList;
	}

	// for testing
	public void printSlotItems()
	{
		int count = 0;
		for (Item item : itemList)
		{
			count++;
			System.out.println("Model.Item " + count + ":");
			System.out.println("Model.Item name: "+ item.getName());
			System.out.println("Model.Item price: " + (float) item.getPrice() / 100);
			System.out.println("Model.Item calories: " + item.getCalories());
		}
	}
}
