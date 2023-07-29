package Viewer;

import Model.Item;
import Model.SpecialVendingMachine;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class RamenMenuViewer extends MenuViewer
{
	@FXML
	private GridPane buttonGrid;
	@FXML
	private Button minimizeButton;
	@FXML
	private AnchorPane topBar;
	@FXML
	private Label paymentLabel;
	private List<Integer> paymentDenominations;

	private int payment;

	public RamenMenuViewer()
	{

	}

	public void openRamenMenu()
	{
		moveApp(topBar, stage);

		minimizeButton.setOnAction(actionEvent -> minimizeApp(stage));

		setupButtonGrid(buttonGrid);
		VendingMenuViewer.UIManager.setButtonGridGaps(buttonGrid, 10);
		System.out.println(this.payment);
		paymentLabel.setText("₱" + (float) payment / 100);
	}

	public void setPayment(int payment)
	{
		this.payment = payment;
		paymentLabel.setText("₱" + (float) payment / 100);
	}

	public void setPaymentDenominations(List<Integer> paymentDenominations)
	{
		this.paymentDenominations = paymentDenominations;
	}

	@FXML
	private void buyTonkotsuRamen()
	{
		boolean hasBroth = ((SpecialVendingMachine) vendingMachineController.getVendingMachines().getLast())
				.getSpecialItemStock().get("tonkotsu broth").size() >= 1;

		boolean hasNoodles = ((SpecialVendingMachine) vendingMachineController.getVendingMachines().getLast())
				                   .getSpecialItemStock().get("noodles").size() >= 1;

		boolean hasEnoughEggs = ((SpecialVendingMachine) vendingMachineController.getVendingMachines().getLast())
				                                           .getSpecialItemStock().get("egg").size() >= 2;

		boolean hasEnoughChashu = ((SpecialVendingMachine) vendingMachineController.getVendingMachines().getLast())
				                   .getSpecialItemStock().get("chashu").size() >= 4;

		boolean hasSpringOnions = ((SpecialVendingMachine) vendingMachineController.getVendingMachines().getLast())
				                   .getSpecialItemStock().get("spring onion").size() >= 1;

		boolean hasEnoughFishCake = ((SpecialVendingMachine) vendingMachineController.getVendingMachines().getLast())
				                   .getSpecialItemStock().get("fish cake").size() >= 2;

		boolean hasEnoughIngredients = hasBroth && hasNoodles && hasEnoughEggs && hasEnoughChashu && hasSpringOnions &&
		                               hasEnoughFishCake;

		if (!hasEnoughIngredients)
		{
			openPopup("Not enough items.");
			return;
		}

		for (String item : ((SpecialVendingMachine) vendingMachineController.getVendingMachines().getLast()).getSpecialItems())
		{
			if (item.equals("miso broth") || item.equals("shio broth") || item.equals("water"))
			{
				continue;
			}

			int quantity = switch (item)
			{
				case "noodles", "tonkotsu broth", "spring onions" -> 1;
				case "egg", "fish cake" -> 2;
				case "chashu" -> 4;
				default -> throw new IllegalStateException("Unexpected value: " + item);
			};

			((SpecialVendingMachine) vendingMachineController.getVendingMachines().getLast()).removeSpecialItem(item, quantity);
		}
	}

	@FXML
	private void buyMisoRamen()
	{

	}

	@FXML
	private void buyShioRamen()
	{

	}

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
				paymentLabel.setText("₱" + (float) (payment + denomination) / 100);
				payment += denomination;
				paymentDenominations.add(denomination);
			});
		}
	}

	@FXML
	private void back(ActionEvent event) throws IOException
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/TestMenu.fxml"));
		openPopup("Your " + payment + " has been returned.");
		openMenuScene(event, loader, "test", null, null);
	}
}
