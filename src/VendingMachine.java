import java.util.ArrayList;
import java.util.List;

public class VendingMachine
{
	private static int itemLimit = 69;
	private static final int initSlotCount = 8;
	private List<Slot> slots;

	public VendingMachine()
	{
		slots = new ArrayList<>(initSlotCount);
	}

	public void addSlot()
	{
		Slot newSlot = new Slot();
		slots.add(newSlot);
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

	public void buyItem(int slotNumber)
	{

	}

	public void performMaintenance()
	{}
}

