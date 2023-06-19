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
	private final ButtonAnimator buttonAnimator;
	private double totalMoney;

	public DispenseInterfaceController()
	{
		this.buttonAnimator = new ButtonAnimator();
	}

	public void setVendingMachineController(VendingMachineController vendingMachineController)
	{
		this.vendingMachineController = vendingMachineController;
	}

	public void openDispenseInterface()
	{
		moveApp(topBar, stage);
		minimizeButton.setOnAction(actionEvent -> minimizeApp(stage));

		for (Node button : navButtons.getChildren())
		{
			buttonAnimator.resizeWhenHovered((Button) button);
		}

		navButtons.setSpacing(7);

		VendingMenuController.UIManager.setButtonGridGaps(buttonGrid, 7);

		setupButtonGrid(buttonGrid);

		dispenseInterfaceVbox.setSpacing(25);

		totalMoney = vendingMachineController.getVendingMachines().getLast().getDenominations()
				                    .calculateTotal() / 100.0;

		totalMoneyLabel.setText("₱" + totalMoney);
	}

	private void setupButtonGrid(GridPane buttonGrid)
	{
		int column = 0;
		int row = 0;
		int maxColumns = 3;

		for (int denomination : vendingMachineController.getVendingMachines().getLast().
				getDenominations().getDenominationList())
		{
			Button button = new Button();
			button.getStyleClass().add("insert-coin-button");


			if (denomination >= 100000)
			{
				button.setStyle("-fx-font-size: 19");
			}

			button.setText(denomination < 100 ? denomination + "¢" : "₱" + denomination / 100);

			buttonAnimator.resizeWhenHovered(button);

			buttonGrid.add(button, column, row);

			column++;

			if (column >= maxColumns)
			{
				column = 0;
				row++;
			}

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

	@FXML
	private void back(ActionEvent event) throws IOException
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MoneyMenu.fxml"));
		openMenuScene(event, loader, "money", vendingMachineController);
	}
}
