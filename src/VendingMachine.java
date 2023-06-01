import java.util.ArrayList;
import java.util.List;

public class VendingMachine
{
	private static int itemLimit = 69;
	private int slotCount;
	private List<Slot> slots;

	public VendingMachine(int slotCount)
	{
		this.slotCount = slotCount;
		slots = new ArrayList<>(slotCount);
	}
}

