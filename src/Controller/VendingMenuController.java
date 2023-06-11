package Controller;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class VendingMenuController extends MenuController
{
	private Parent root;
	private final VBox vBox;
	private VendingMachineController vendingMachineController;
	private final ButtonAnimator buttonAnimator;
	private GridPane buttonGrid;

	private int payment;

	public VendingMenuController()
	{
		this.vBox = new VBox();
		this.buttonAnimator = new ButtonAnimator();
	}

	public void setVendingMachineController(VendingMachineController controller)
	{
		this.vendingMachineController = controller;
	}

	public Parent getRoot()
	{
		return root;
	}

	public void openCoinInsertionMenu()
	{
		Label paymentLabel = new Label();

		buttonGrid = new GridPane();
		buttonGrid.setAlignment(Pos.CENTER);

		buttonGrid.setHgap(5);
		buttonGrid.setVgap(5);

		int column = 0;
		int row = 0;
		int maxColumns = 3;

		for (int denomination : vendingMachineController.getVendingMachines().getLast()
				.getDenominations().getDenominationList())
		{
			Button button = new Button();
			button.getStyleClass().add("insert-coin-button");

			if (denomination < 100)
			{
				button.setText(String.valueOf((float) denomination / 100));
			}
			else
			{
				button.setText(String.valueOf(denomination / 100));
			}

			buttonGrid.add(button, column, row);

			buttonAnimator.resizeWhenHovered(button);

			column++;

			if (column >= maxColumns)
			{
				column = 0;
				row++;
			}

			button.setOnAction(event ->
			{
				paymentLabel.setText(String.valueOf((float) (payment + denomination) / 100));
				payment += denomination;
				// For verification
				System.out.println(payment);
			});
		}

		payment = 0;

		paymentLabel.getStyleClass().add("title-label");
		paymentLabel.setText(String.valueOf(payment));

		vBox.getChildren().addAll(paymentLabel, buttonGrid);
		vBox.setStyle("-fx-alignment: Center");
		vBox.setMinSize(720, 720);

		root = vBox;
	}

	public void openVendingMenu()
	{
		buttonGrid = new GridPane();

		buttonGrid.setAlignment(Pos.CENTER);
		buttonGrid.setHgap(5);
		buttonGrid.setVgap(5);

		int slotsLength = vendingMachineController.getVendingMachines().get(0).getSlots().length;

		int backButtonRow = slotsLength / 3;  // Add the back button in the next row
		int backButtonColumn = slotsLength % 3;

		for (int i = slotsLength - 1; i >= 0; i--)
		{
			Button button = new Button();
			button.getStyleClass().add("slot-button");
			button.setText(String.valueOf(i + 1));

			int row = (slotsLength - i - 1) / 3;
			int column = (slotsLength - i - 1) % 3;
			buttonGrid.add(button, column, row);

			buttonAnimator.resizeWhenHovered(button);
		}

		Button backButton = new Button();
		backButton.getStyleClass().add("slot-back-button");
		backButton.setText("⌫");

		backButton.setOnAction(event ->
		{
			try
			{
				returnToMainMenu(event);
			} catch (IOException e)
			{
				throw new RuntimeException(e);
			}
		});

		buttonGrid.add(backButton, backButtonColumn, backButtonRow);
		buttonAnimator.resizeWhenHovered(backButton);

		Label label = new Label("Select a slot:");
		label.getStyleClass().add("title-label");

		vBox.getChildren().addAll(label, buttonGrid);

		vBox.setStyle("-fx-alignment: Center");
		vBox.setMinSize(720, 720);

		root = vBox;
	}
}
