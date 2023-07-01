package Model;

import Viewer.TransactionInterfaceViewer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * TransactionTracker keeps track of the transactions that occur in the vending machine every time a restocking occurs.
 */
public class TransactionTracker
{
    private int totalAmountCollected;
    private Slot[] initialSlots;
    private Slot[] currentSlots;

    public TransactionTracker(Slot[] initialSlots)
    {
        this.initialSlots = new Slot[initialSlots.length];

        for (int i = 0; i < initialSlots.length; i++)
        {
            this.initialSlots[i] = new Slot();

            for (int j = 0; j < initialSlots[i].getItemList().size(); j++)
            {
                this.initialSlots[i].getItemList().add(initialSlots[i].getItemList().get(j));
            }
        }
    }

    /**
     * Sets the initialSlots array.
     * @param initialSlots array of slots
     */
    public void setInitialSlots(Slot[] initialSlots)
    {
        this.initialSlots = new Slot[initialSlots.length];

        for (int i = 0; i < initialSlots.length; i++)
        {
            this.initialSlots[i] = new Slot();

            for (int j = 0; j < initialSlots[i].getItemList().size(); j++)
            {
                this.initialSlots[i].getItemList().add(initialSlots[i].getItemList().get(j));
            }
        }
    }

    /**
     * Sets the currentSlots array.
     * @param currentSlots array of slots
     */
    public void setCurrentSlots(Slot[] currentSlots)
    {
        this.currentSlots = new Slot[currentSlots.length];

        for (int i = 0; i < currentSlots.length; i++)
        {
            this.currentSlots[i] = new Slot();

            for (int j = 0; j < currentSlots[i].getItemList().size(); j++)
            {
                this.currentSlots[i].getItemList().add(currentSlots[i].getItemList().get(j));
            }
        }
    }

    /**
     * Gets the total amount collected.
     * @return total amount collected
     */
    public int getTotalAmountCollected()
    {
        return totalAmountCollected;
    }

    /**
     * Sets the total amount collected.
     */
    public void calculateTotalAmountCollected()
    {
        totalAmountCollected = 0;

        for (int i = 0; i < initialSlots.length; i++)
        {
            if (initialSlots[i].getItemList().size() > 0)
            {
                totalAmountCollected += (initialSlots[i].getItemsCount() - currentSlots[i].getItemsCount()) * initialSlots[i].getItemList().get(0).getPrice();
            }
        }
    }

    /**
     * Creates a text file that contains the transactions that occurred during the restocking.
     * @throws IOException if an I/O error occurs
     */
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
                if (initialSlots[i].getItemsCount() > 0)
                {
                    writer.write("Slot " + (i + 1) + ". " + initialSlots[i].getItemList().get(0).getName() + ": " + (initialSlots[i].getItemsCount() - currentSlots[i].getItemsCount()) + "\n");
                    writer.close();
                }
                else
                {
                    writer.write("Slot " + (i + 1) + ". N/A: -1\n");
                    writer.close();
                }
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

    /**
     * Gets the transaction list.
     * @return transaction list
     */
    public ObservableList getTransactionList()
    {
        ObservableList<TransactionInterfaceViewer.Transaction> transactionList = FXCollections.observableArrayList();

        for (int i = 0; i < initialSlots.length; i++)
        {
            if (initialSlots[i].getItemsCount() > 0)
            {
                TransactionInterfaceViewer.Transaction transaction = new TransactionInterfaceViewer.Transaction(initialSlots[i].getItemList().get(0).getName(), initialSlots[i].getItemsCount(), currentSlots[i].getItemsCount());

                transactionList.add(transaction);
            }
        }

        return transactionList;
    }
}
