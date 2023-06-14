package Model;

import java.util.*;

public class Denomination
{
	private final Map<Integer, Integer> denominationStock;
	private final List<Integer> denominationList;

	public Denomination()
	{
		denominationStock = new HashMap<>();

		// Centavo coins
		denominationStock.put(1, 1000);
		denominationStock.put(5, 1000);
		denominationStock.put(10, 1000);
		denominationStock.put(25, 1000);

		// Peso bills
		denominationStock.put(100, 1000);
		denominationStock.put(500, 1000);
		denominationStock.put(1000, 1000);

		// Peso bills
		denominationStock.put(2000, 500);
		denominationStock.put(5000, 500);
		denominationStock.put(10000, 500);
		denominationStock.put(50000, 500);
		denominationStock.put(100000, 500);

		denominationList = new ArrayList<>(denominationStock.keySet());
		denominationList.sort(Collections.reverseOrder());
	}

	public Map<Integer, Integer> getDenominationStock()
	{
		return denominationStock;
	}

	public void addDenomination(int value, int quantity)
	{
		if (!denominationStock.containsKey(value))
		{
			// Error handling for no value found
			return;
		}

		if (quantity <= 0)
		{
			// Error handling for invalid quantity
			return;
		}

		int newDenominationQuantity = denominationStock.get(value) + quantity;

		denominationStock.remove(value);
		denominationStock.put(value, newDenominationQuantity);
	}

	public void removeDenomination(int value, int quantity)
	{
		if (!denominationStock.containsKey(value))
		{
			// Error handling for no value found
			return;
		}

		if (quantity <= 0)
		{
			// Error handling for invalid quantity
			return;
		}

		if (denominationStock.get(value) - quantity < 0)
		{
			// Error handling for insufficient stock
			return;
		}

		int newDenominationQuantity = denominationStock.get(value) - quantity;


		denominationStock.remove(value);
		denominationStock.put(value, newDenominationQuantity);
	}

	public List<Integer> getDenominationList()
	{
		return denominationList;
	}
}
