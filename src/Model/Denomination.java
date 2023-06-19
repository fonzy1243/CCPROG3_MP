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
		denominationStock.put(1, 501);
		denominationStock.put(5, 500);
		denominationStock.put(10, 500);
		denominationStock.put(25, 500);

		// Peso bills
		denominationStock.put(100, 250);
		denominationStock.put(500, 250);
		denominationStock.put(1000, 250);

		// Peso bills
		denominationStock.put(2000, 100);
		denominationStock.put(5000, 100);
		denominationStock.put(10000, 100);
		denominationStock.put(50000, 100);
		denominationStock.put(100000, 100);

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

	public long calculateTotal()
	{
		long total = 0;

		for (Map.Entry<Integer, Integer> entry : denominationStock.entrySet())
		{
			int denomination = entry.getKey();
			int stock = entry.getValue();

			total += (long) denomination * stock;
		}

		return total;
	}
}
