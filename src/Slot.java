import java.util.ArrayList;
import java.util.List;

public class Slot
{
	private static final int initItemCount = 10;
	private List<Item> itemList;

	public Slot()
	{
		itemList = new ArrayList<>(initItemCount);
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
