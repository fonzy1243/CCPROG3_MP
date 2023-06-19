package Controller;

import Model.RegularVendingMachine;
import Model.SpecialVendingMachine;
import Model.VendingMachine;

import java.util.LinkedList;

/**
 * Controls the vending machine functions through user input.
 * <p>
 * Each menu controller subclass shares the same static instance of a vending machine controller
 * such that it can be used to manage the relationship between user input through the different
 * menus and the application's vending machines.
 */
public class VendingMachineController
{
	private final LinkedList<VendingMachine> vendingMachines;

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

	public LinkedList<VendingMachine> getVendingMachines()
	{
		return vendingMachines;
	}
}
