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

	public void addItem(Item newItem, int quantity)
	{
		for (int i = 0; i < quantity; i++)
		{
			itemList.add(newItem);
		}
	}

	// for testing
	// actually we should probably move this back to slot
	public void printSlotItems()
	{
		int count = 0;
		for (Item item : itemList)
		{
			count++;
			System.out.println("Item " + count + ":");
			System.out.println("Item name: "+ item.getName());
			System.out.println("Item price: " + (float) item.getPrice() / 100);
			System.out.println("Item calories: " + item.getCalories());
		}
	}
}
