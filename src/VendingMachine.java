import java.util.ArrayList;
import java.util.List;

public class VendingMachine
{
	private static final int itemLimit = 69;

	private final List<Slot> slots;

	public VendingMachine(int slotCount)
	{
		slots = new ArrayList<>(slotCount);

		for (int i = 0; i < slotCount; i++)
		{
			slots.add(new Slot());
		}
	}

	public List<Slot> getSlots()
	{
		return slots;
	}

	public int getAvailability(Item item)
	{
		int count = 0;

		for (Slot slot : slots)
		{
			if (slot.getItemList().get(0).getName().equals(item.getName()))
			{
				count += slot.getItemList().size();
			}
		}

		return count;
	}

	public void addItemToSlot(Item item, int slotNumber, int quantity)
	{
		int totalItemCount = 0;

		for (Slot slot : slots)
		{
			totalItemCount += slot.getItemList().size();
		}

		if (totalItemCount >= itemLimit)
		{
			return;
		}

		slots.get(slotNumber).addItem(item, quantity);
	}

	// for testing
	public void printSlotItems(int slotNumber)
	{
		int count = 0;
		for (Item item : slots.get(slotNumber).getItemList())
		{
			count++;
			System.out.println("Item " + count + ":");
			System.out.println("Item name: "+ item.getName());
			System.out.println("Item price: " + (float) item.getPrice() / 100);
			System.out.println("Item calories: " + item.getCalories());
		}
	}

	public void dispenseItem(int slotNumber)
	{

	}
}

