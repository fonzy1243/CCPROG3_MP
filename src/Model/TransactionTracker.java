package Model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TransactionTracker
{
    private int totalAmountCollected;
    private final Slot[] initialSlots;
    private final Slot[] currentSlots;

    public TransactionTracker(Slot[] initialSlots, Slot[] currentSlots)
    {
        this.initialSlots = initialSlots;
        this.currentSlots = currentSlots;
    }

    public void createTransactionFile()
    {
        File transactionFile = new File("transactions.txt");

        if (transactionFile.exists())
		{
			if (!transactionFile.delete())
			{
				System.out.println("Failed to delete existing file.");
			}
		}

        try
		{
			if (transactionFile.createNewFile())
			{
				System.out.println(transactionFile.getName());
			}
			else
			{
				System.out.println("Failed to create file.");
			}
		} catch (IOException exception)
		{
			System.out.println("Error occurred while creating file.");
			exception.printStackTrace();
		}

        for (int i = 0; i < initialSlots.length; i++)
        {
            try
            {
                FileWriter writer = new FileWriter(transactionFile, true);
                writer.write((initialSlots[i].getItemsCount() - currentSlots[i].getItemsCount()) + "\n");
                writer.close();

                totalAmountCollected += (initialSlots[i].getItemsCount() - currentSlots[i].getItemsCount()) * initialSlots[i].getItemList().get(0).getPrice();
            } catch (IOException exception)
            {
                System.out.println("Error occurred while writing to file.");
                exception.printStackTrace();
            }
        }

        try
        {
            FileWriter writer = new FileWriter(transactionFile, true);
            writer.write((float) totalAmountCollected / 100 + "\n");
            writer.close();
        } catch (IOException exception)
        {
            System.out.println("Error occurred while writing to file.");
            exception.printStackTrace();
        }
    }

    public void displayTransactionDetails()
    {
        System.out.println("Transaction Details");

        for (int i = 0; i < initialSlots.length; i++)
        {
            System.out.println(initialSlots[i].getItemList().get(0).getName() + ": " + (initialSlots[i].getItemsCount() - currentSlots[i].getItemsCount()));
        }

        System.out.println("Total amount collected: PhP" + (float) totalAmountCollected / 100);
    }

    public Slot[] readTransactionFile()
    {
        File transactionFile = new File("transactions.txt");
        Slot[] slots = new Slot[10];

        for (int i = 0; i < slots.length; i++)
        {
            slots[i] = new Slot();
        }

        if (transactionFile.exists())
        {
            try
            {
                java.util.Scanner scanner = new java.util.Scanner(transactionFile);

                for (int i = 0; i < slots.length; i++)
                {
                    if (scanner.hasNextInt())
                    {
                        int count = scanner.nextInt();

                        if (count > 0)
                        {
                            slots[i] = initialSlots[i];

                            while (slots[i].getItemsCount() != count) {
                                slots[i].getItemList().remove(0);
                            }
                        }
                    }
                }

                scanner.close();
            } catch (IOException exception)
            {
                System.out.println("Error occurred while reading file.");
                exception.printStackTrace();
            }
        }
        else
        {
            System.out.println("File does not exist.");
        }

        return slots;
    }
}
