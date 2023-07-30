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
	private Button tonkotsuButton;
	@FXML
	private Button misoButton;
	@FXML
	private Button shioButton;
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

		tonkotsuButton.setOnAction(event -> buyRamen("tonkotsu broth"));
		misoButton.setOnAction(event -> buyRamen("miso broth"));
		shioButton.setOnAction(event -> buyRamen("shio broth"));
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

	private boolean hasEnoughIngredients(String ramenBroth)
	{
		boolean hasBroth = ((SpecialVendingMachine) vendingMachineController.getVendingMachines().getLast())
				                   .getSpecialItemStock().get(ramenBroth).size() >= 1;

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

		return hasBroth && hasNoodles && hasEnoughEggs && hasEnoughChashu && hasSpringOnions && hasEnoughFishCake;
	}

	@FXML
	private void buyRamen(String ramenBroth)
	{
		if (!hasEnoughIngredients(ramenBroth))
		{
			openPopup("Not enough items.");
			return;
		}

		for (String item : ((SpecialVendingMachine) vendingMachineController.getVendingMachines().getLast()).getSpecialItems())
		{
			if ((item.contains("broth") && !item.trim().split("\\s+")[0].equals(ramenBroth.trim().split("\\s+")[0]))
			    || item.equals("water"))
			{
				continue;
			}

			int quantity;

			if (item.equals(ramenBroth) || item.equals("noodles") || item.equals("spring onion"))
			{
				quantity = 1;
			}
			else if (item.equals("egg") || item.equals("fish cake"))
			{
				quantity = 2;
			}
			else
			{
				quantity = 4;
			}

			((SpecialVendingMachine) vendingMachineController.getVendingMachines().getLast()).removeSpecialItem(item, quantity);
		}
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
