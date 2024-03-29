package Viewer;

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

/**
 * WithdrawInterfaceViewer manages the Withdraw Interface's GUI.
 */
public class WithdrawInterfaceViewer extends MenuViewer
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

	public WithdrawInterfaceViewer()
	{

	}

	/**
	 * Opens the interface where a user can withdraw money from the vending machine.
	 */
	public void openWithdrawInterface()
	{
		withdrawInterfaceVbox.setSpacing(35);

		moveApp(topBar, stage);
		minimizeButton.setOnAction(actionEvent -> minimizeApp(stage));

		// animate all nav buttons
		for (Node button : navButtons.getChildren())
		{
			ButtonAnimator.resizeWhenHovered((Button) button);
		}

		navButtons.setSpacing(7);

		// make total money effectively final for use in the lambda expression
		final double[] totalMoney = {vendingMachineController.getVendingMachines().getLast().getDenominations()
				                             .calculateTotal() / 100.0};

		moneyLabel.setText("₱" + totalMoney[0]);

		addButton.setOnAction(event ->
		{
			// Cannot withdraw negative value
			if ((int) (Float.parseFloat(moneyTextField.getText()) * 100) <= 0)
			{
				openPopup("Invalid withdrawal.");
				return;
			}

			List<Integer> change;

			if (hasMoreThanTwoDecimalPlaces(moneyTextField.getText()))
			{
				openPopup("Cannot have more than 2 decimal places.");
				return;
			}

			if (!moneyTextField.getText().contains("."))
			{
				change = vendingMachineController.getVendingMachines().getLast()
						.withdrawMoney(Integer.parseInt(moneyTextField.getText()) * 100);
			}
			else
			{
				String moneyText = moneyTextField.getText().replace(".", "");

				change = vendingMachineController.getVendingMachines().getLast().withdrawMoney(Integer.parseInt(moneyText));
			}

			// convert total in cents to real total

			float total = 0;

			for (Integer currency : change)
			{
				total += (float) currency / 100;
			}

			StringBuilder stringBuilder = new StringBuilder();
			change.forEach((changeValue) -> stringBuilder.append("₱").append((float) changeValue / 100).append(" "));

			totalMoney[0] = (float) vendingMachineController.getVendingMachines().getLast()
					.getDenominations().calculateTotal() / 100;

			String formattedTotal = String.format("%.2f", totalMoney[0]);
			moneyLabel.setText("₱" + formattedTotal);

			if (change.isEmpty())
			{
				openPopup("Cannot withdraw that amount.");
				return;
			}

			openPopup("You have withdrawn ₱" + total + ": " + stringBuilder);
		});
	}

	/**
	 * Returns to the money menu
	 * @param event button click
	 * @throws IOException if error occurred while opening money menu FXML
	 */
	@FXML
	private void back(ActionEvent event) throws IOException
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MoneyMenu.fxml"));
		openMenuScene(event, loader, "money", null, null);
	}

	/**
	 * Checks if the input string has more than two decimal places.
	 * @param numberString the input string
	 * @return true if the input string has more than two decimal places, false otherwise
	 */
	public static boolean hasMoreThanTwoDecimalPlaces(String numberString) {
		// Regular expression to match numbers with two or more decimal places
		String regex = "^\\d*\\.\\d{3,}$";

		// Check if the input string matches the regular expression
		return numberString.matches(regex);
	}
}
