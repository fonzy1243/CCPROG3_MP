package Viewer;

import Model.Item;
import Model.SpecialVendingMachine;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

import java.io.IOException;
import java.util.*;

public class RamenMenuViewer extends MenuViewer
{
	@FXML
	private HBox customItemButtons;
	@FXML
	private Label processingText;
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
		HashMap<String, LinkedList<Item>> specialItemStock = ((SpecialVendingMachine) vendingMachineController.getVendingMachines().getLast()).getSpecialItemStock();

		boolean hasBroth = specialItemStock.containsKey(ramenBroth) && specialItemStock.get(ramenBroth).size() >= 1;
		boolean hasNoodles = specialItemStock.containsKey("noodles") && specialItemStock.get("noodles").size() >= 1;
		boolean hasEnoughEggs = specialItemStock.containsKey("egg") && specialItemStock.get("egg").size() >= 2;
		boolean hasEnoughChashu = specialItemStock.containsKey("chashu") && specialItemStock.get("chashu").size() >= 4;
		boolean hasSpringOnions = specialItemStock.containsKey("spring onions") && specialItemStock.get("spring onions").size() >= 1;
		boolean hasEnoughFishCake = specialItemStock.containsKey("fish cake") && specialItemStock.get("fish cake").size() >= 2;

		System.out.println(hasBroth);
		System.out.println(hasNoodles);
		System.out.println(hasEnoughEggs);
		System.out.println(hasEnoughChashu);
		System.out.println(hasSpringOnions);
		System.out.println(hasEnoughFishCake);


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

		int price = 0;

		for (String item : ((SpecialVendingMachine) vendingMachineController.getVendingMachines().getLast()).getSpecialItems())
		{
			if ((item.contains("broth") && !item.trim().split("\\s+")[0].equals(ramenBroth.trim().split("\\s+")[0]))
			    || item.equals("water") || item.equals("salt") || item.contains("bones"))
			{
				continue;
			}

			int quantity;

			if (item.equals(ramenBroth) || item.equals("noodles") || item.equals("spring onions"))
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

			System.out.println(item);
			price += ((SpecialVendingMachine) vendingMachineController.getVendingMachines().getLast())
					         .getSpecialItemStock().get(item).get(0).getPrice() * quantity;

			((SpecialVendingMachine) vendingMachineController.getVendingMachines().getLast()).removeSpecialItem(item, quantity);
		}

		if (payment < price)
		{
			openPopup("Payment insufficient.");;
			return;
		}

		List<Integer> change = vendingMachineController.getVendingMachines().getLast().getRamenChange(payment - price);

		if (change.isEmpty())
		{
			openPopup("Could not produce change. Your ₱" + (float) payment / 100 + " has been returned. " +
			          "Try again at a later date.");
		}
		else if (payment == price)
		{
			processingText.setVisible(true);
			processingText.setManaged(true);

			Timeline timeline = processRamen(processingText, customItemButtons);
			timeline.playFromStart();

			timeline.setOnFinished(actionEvent ->
			{
				for (Integer denomination : paymentDenominations)
				{
					vendingMachineController.getVendingMachines().getLast().getDenominations().addDenomination(denomination, 1);
				}

				openPopup("You paid the exact amount and have received " + ramenBroth.trim().split("\\s+")[0] + " ramen.");
				payment = 0;
				openRamenMenu();
			});
		}
		else
		{
			processingText.setVisible(true);
			processingText.setManaged(true);

			Timeline timeline = processRamen(processingText, customItemButtons);
			timeline.playFromStart();

			StringBuilder stringBuilder = new StringBuilder();
			change.forEach((changeDenom) -> stringBuilder.append("₱").append((float) changeDenom / 100).append(" "));

			int finalPrice = price;
			timeline.setOnFinished(actionEvent ->
			{
				for (Integer denomination : paymentDenominations)
				{
					vendingMachineController.getVendingMachines().getLast().getDenominations()
							.addDenomination(denomination, 1);
				}

				if (change.size() == 1)
				{
					openPopup("You have received " + ramenBroth.trim().split("\\s+")[0] + ". Your change is" + stringBuilder);
				}
				else
				{
					openPopup("You have received " + ramenBroth.trim().split("\\s+")[0] + ". Your change is ₱" +
					          (float) (payment - finalPrice) / 100 + ": " + stringBuilder);
				}

				payment = 0;
				openRamenMenu();
			});
		}
	}


	private Timeline processRamen(Label processingText, HBox ramenButtons)
	{
		for (Node button : ramenButtons.getChildren())
		{
			button.setDisable(true);
		}

		return new Timeline(
				new KeyFrame(Duration.ZERO, event -> processingText.setText("Boiling noodles...")),
				new KeyFrame(Duration.millis(600), event -> processingText.setText("Simmering broth...")),
				new KeyFrame(Duration.millis(1200), event -> processingText.setText("Pouring noodles...")),
				new KeyFrame(Duration.millis(1800), event -> processingText.setText("Pouring broth...")),
				new KeyFrame(Duration.millis(2400), event -> processingText.setText("Adding toppings...")),
				new KeyFrame(Duration.millis(3000), event -> processingText.setText("DONE!"))
		);
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
		openMenuScene(event, loader, "test", null, null);
		openPopup("Your ₱" + (float) payment / 100 + " has been returned.");
	}
}
