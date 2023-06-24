package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;

public class WithdrawInterfaceController extends MenuController
{
	@FXML
	private VBox withdrawInterfaceVbox;
	@FXML
	private Label moneyLabel;
	@FXML
	private TextField moneyTextField;
	@FXML
	private Button addButton;
	@FXML
	private HBox navButtons;
	@FXML
	private AnchorPane topBar;
	@FXML
	private Button minimizeButton;

	private VendingMachineController vendingMachineController;
	private final ButtonAnimator buttonAnimator;

	public WithdrawInterfaceController()
	{
		this.buttonAnimator = new ButtonAnimator();
	}

	public void setVendingMachineController(VendingMachineController vendingMachineController)
	{
		this.vendingMachineController = vendingMachineController;
	}

	public void openWithdrawInterface()
	{
		withdrawInterfaceVbox.setSpacing(35);

		moveApp(topBar, stage);
		minimizeButton.setOnAction(actionEvent -> minimizeApp(stage));

		for (Node button : navButtons.getChildren())
		{
			buttonAnimator.resizeWhenHovered((Button) button);
		}

		navButtons.setSpacing(7);

		final double[] totalMoney = {vendingMachineController.getVendingMachines().getLast().getDenominations()
				                             .calculateTotal() / 100.0};

		moneyLabel.setText("₱" + totalMoney[0]);

		addButton.setOnAction(event ->
		{
			if ((int) (Float.parseFloat(moneyTextField.getText()) * 100) <= 0)
			{
				openPopup("Invalid withdrawal.");
				return;
			}

			List<Integer> change = vendingMachineController.getVendingMachines().getLast()
					.withdrawMoney((int) (Float.parseFloat(moneyTextField.getText()) * 100));

			float total = 0;

			for (Integer currency : change)
			{
				total += (float) currency / 100;
			}

			StringBuilder stringBuilder = new StringBuilder();
			change.forEach((changeValue) -> stringBuilder.append("₱").append((float) changeValue / 100).append(" "));

			totalMoney[0] -= total;
			moneyLabel.setText("₱" + totalMoney[0]);

			openPopup("You have withdrawn ₱" + total + ": " + stringBuilder);
		});
	}

	@FXML
	private void back(ActionEvent event) throws IOException
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MoneyMenu.fxml"));
		openMenuScene(event, loader, "money", vendingMachineController);
	}
}
