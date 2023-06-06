package Controller;

import Model.RegularVendingMachine;
import Model.SpecialVendingMachine;
import Model.VendingMachine;

import java.util.LinkedList;

public class VendingMachineController
{
	private LinkedList<VendingMachine> vendingMachines;

	public VendingMachineController()
	{
		vendingMachines = new LinkedList<>();
	}

	public void createRegularMachine(int slotCount)
	{
		vendingMachines.addFirst(new RegularVendingMachine(slotCount));
	}

	public void createSpecialMachine(int slotCount)
	{
		vendingMachines.addFirst(new SpecialVendingMachine(slotCount));
	}

	public void purchaseItem()
	{
//		vendingMachines.get(0).dispenseItem();
	}
}
