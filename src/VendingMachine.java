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

	public void dispenseItem(int slotNumber)
	{

	}
}

