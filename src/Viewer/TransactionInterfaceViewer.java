package Viewer;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class TransactionInterfaceViewer extends MenuViewer
{
	@FXML
	public TableView transactionTable;
	@FXML
	public TableColumn<Object, Object> transactionItemColumn;
	@FXML
	public TableColumn<Object, Object> transactionInitialStockColumn;
	@FXML
	public TableColumn<Object, Object> transactionCurrentStockColumn;
	@FXML
	public TableColumn<Object, Object> transactionSoldColumn;
	@FXML
	public Label totalPriceLabel;
	@FXML
	private VBox transactionInterfaceVbox;
	@FXML
	private HBox navButtons;
	@FXML
	private AnchorPane topBar;
	@FXML
	private Button minimizeButton;

    public TransactionInterfaceViewer()
    {

    }

    /**
     * Opens the interface where a user can view the transaction history of the vending machine.
     */
    public void openTransactionInterface()
    {
		vendingMachineController.getVendingMachines().getLast().getTransactionTracker().setCurrentSlots(vendingMachineController.getVendingMachines().getLast().getSlots());
		vendingMachineController.getVendingMachines().getLast().getTransactionTracker().calculateTotalAmountCollected();
		vendingMachineController.getVendingMachines().getLast().getTransactionTracker().createTransactionFile();

		transactionInterfaceVbox.setSpacing(15);

		transactionTable.setEditable(false);

		transactionItemColumn.setCellValueFactory(new PropertyValueFactory<>("item"));
		transactionInitialStockColumn.setCellValueFactory(new PropertyValueFactory<>("initialStock"));
		transactionCurrentStockColumn.setCellValueFactory(new PropertyValueFactory<>("currentStock"));
		transactionSoldColumn.setCellValueFactory(new PropertyValueFactory<>("sold"));

		ObservableList transactionList = vendingMachineController.getVendingMachines().getLast().getTransactionTracker().getTransactionList();

		transactionTable.setItems(transactionList);

		totalPriceLabel.setText("Total amount collected: ₱ " + (float) vendingMachineController.getVendingMachines().getLast().getTransactionTracker().getTotalAmountCollected() / 100);

		moveApp(topBar, stage);
		minimizeButton.setOnAction(actionEvent -> minimizeApp(stage));

		for (Node button : navButtons.getChildren())
		{
			ButtonAnimator.resizeWhenHovered((Button) button);
		}

		navButtons.setSpacing(7);
    }

	/**
	 * Returns to the maintenance menu.
	 * @param event button click
	 * @throws IOException if error occurred while opening maintenance menu FXML
	 */
	@FXML
	private void back(ActionEvent event) throws IOException
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MaintenanceMenu.fxml"));
		openMenuScene(event, loader, "maintenance", vendingMachineController);
	}

	/**
     * Transaction is a class that represents a row in the transaction table.
     */
	public static class Transaction
	{
		private final SimpleStringProperty item;
		private final SimpleIntegerProperty initialStock;
		private final SimpleIntegerProperty currentStock;
		private final SimpleIntegerProperty sold;

		public Transaction(String item, int initialStock, int currentStock)
		{
			this.item = new SimpleStringProperty(item);
			this.initialStock = new SimpleIntegerProperty(initialStock);
			this.currentStock = new SimpleIntegerProperty(currentStock);
			this.sold = new SimpleIntegerProperty(initialStock - currentStock);
		}

		/**
		 * Returns the item name.
		 * @return item name
		 */
		public String getItem()
		{
			return item.get();
		}

		/**
		 * Sets the item name.
		 * @param item item name
		 */
		public void setItem(String item)
		{
			this.item.set(item);
		}

		/**
		 * Returns the initial stock of the item.
		 * @return initial stock of the item
		 */
		public int getInitialStock() {
			return initialStock.get();
		}

		/**
		 * Sets the initial stock of the item.
		 * @param initialStock initial stock of the item
		 */
		public void setInitialStock(int initialStock) {
			this.initialStock.set(initialStock);
		}

		/**
		 * Returns the current stock of the item.
		 * @return current stock of the item
		 */
		public int getCurrentStock() {
			return currentStock.get();
		}

		/**
		 * Sets the current stock of the item.
		 * @param currentStock current stock of the item
		 */
		public void setCurrentStock(int currentStock) {
			this.currentStock.set(currentStock);
		}

		/**
		 * Returns the number of items sold.
		 * @return number of items sold
		 */
		public int getSold()
		{
			return sold.get();
		}

		/**
		 * Sets the number of items sold.
		 * @param sold number of items sold
		 */
		public void setSold(int sold)
		{
			this.sold.set(sold);
		}
	}
}
