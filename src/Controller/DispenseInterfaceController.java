package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;

/**
 * DispenseInterfaceController manages user input when adding dispensing coins/bills (restocking) into the machine.
 */
public class DispenseInterfaceController extends MenuController
{
	public Button addButton;
	public VBox dispenseInterfaceVbox;
	public GridPane buttonGrid;
	public Label totalMoneyLabel;
	@FXML
	private HBox navButtons;
	@FXML
	private AnchorPane topBar;
	@FXML
	private Button minimizeButton;

	private VendingMachineController vendingMachineController;
	private double totalMoney;


	public DispenseInterfaceController()
	{

	}

	/**
	 * Sets the menu's vending machine controller
	 * @param vendingMachineController menu's vending machine controller
	 * @see VendingMachineController
	 */
	public void setVendingMachineController(VendingMachineController vendingMachineController)
	{
		this.vendingMachineController = vendingMachineController;
	}

	/**
	 * Opens the interface for adding coins/bills into the machine.
	 */
	public void openDispenseInterface()
	{
		moveApp(topBar, stage);
		minimizeButton.setOnAction(actionEvent -> minimizeApp(stage));

		// Make all nav buttons resizable when hovered.
		for (Node button : navButtons.getChildren())
		{
			ButtonAnimator.resizeWhenHovered((Button) button);
		}

		navButtons.setSpacing(7);

		VendingMenuController.UIManager.setButtonGridGaps(buttonGrid, 7);

		setupButtonGrid(buttonGrid);

		dispenseInterfaceVbox.setSpacing(25);

		totalMoney = vendingMachineController.getVendingMachines().getLast().getDenominations()
				                    .calculateTotal() / 100.0;

		totalMoneyLabel.setText("₱" + totalMoney);
	}

	/**
	 * Dynamically generate a grid of buttons for the coins/bills.
	 * @param buttonGrid grid pane to be added to
	 */
	private void setupButtonGrid(GridPane buttonGrid)
	{
		int column = 0;
		int row = 0;
		int maxColumns = 3;

		// Create a button for each denomination.
		for (int denomination : vendingMachineController.getVendingMachines().getLast().
				getDenominations().getDenominationList())
		{
			Button button = new Button();
			button.getStyleClass().add("insert-coin-button");

			// Make the font size smaller if the value is high.
			if (denomination >= 100000)
			{
				button.setStyle("-fx-font-size: 19");
			}

			button.setText(denomination < 100 ? denomination + "¢" : "₱" + denomination / 100);

			ButtonAnimator.resizeWhenHovered(button);

			buttonGrid.add(button, column, row);

			column++;

			if (column >= maxColumns)
			{
				column = 0;
				row++;
			}

			// Set each button to add its corresponding denomination to the machine and update the display.
			button.setOnAction(event ->
			{
				vendingMachineController.getVendingMachines().getLast().getDenominations()
						.addDenomination(denomination, 1);

				totalMoney = vendingMachineController.getVendingMachines().getLast().getDenominations()
						             .calculateTotal() / 100.0;
				totalMoneyLabel.setText("₱" + totalMoney);

			});
		}
	}

	/**
	 * Returns to the money menu.
	 * @param event button click
	 * @throws IOException if error occurs while loading money menu FXML
	 */
	@FXML
	private void back(ActionEvent event) throws IOException
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MoneyMenu.fxml"));
		openMenuScene(event, loader, "money", vendingMachineController);
	}
}
