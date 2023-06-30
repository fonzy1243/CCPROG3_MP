package Viewer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class TransactionInterfaceViewer extends MenuViewer
{
	// TODO: Cleanup here
//	@FXML
//	public TableView transactionTable;
//	@FXML
//	public TableColumn transactionItemColumn;
//	@FXML
//	public TableColumn transactionSoldColumn;
	@FXML
	public Label totalPriceLabel;
	@FXML
	private VBox transactionInterfaceVbox;
	@FXML
	private Button backButton;
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
		vendingMachineController.getVendingMachines().getLast().getTransactionTracker().setTotalAmountCollected();

		vendingMachineController.getVendingMachines().getLast().getTransactionTracker().createTransactionFile();

		transactionInterfaceVbox.setSpacing(15);

		// TODO: Set tableview here

		totalPriceLabel.setText("Total amount collected: ₱ " + vendingMachineController.getVendingMachines().getLast().getTransactionTracker().getTotalAmountCollected());

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
}
