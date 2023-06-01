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


}
