package Viewer;

import Model.Slot;
import Model.SpecialVendingMachine;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Controls user input in the vending features menu.
 * @see MenuViewer
 */
public class VendingMenuViewer extends MenuViewer
{
	private Parent root;
	private final VBox vBox;
	private GridPane buttonGrid;
	private final AnchorPane rootAnchorPane;
	private final AnchorPane titleBar;
	private final Button minimizeButton;
	private final Button closeButton;
	private final UIManager uiManager;
	private final List<Integer> paymentDenominations;
	private int payment;

	/**
	 * A vending menu initializes some GUI elements when instantiated.
	 */
	public VendingMenuViewer()
	{
		// initialize GUI
		uiManager = new UIManager();

		rootAnchorPane = new AnchorPane();
		rootAnchorPane.getStyleClass().add("rounded-pane");

		minimizeButton = new Button("—");
		closeButton = new Button("✕");

		uiManager.initializeTopBarButtons();

		titleBar = new AnchorPane();
		HBox titleBarButtons = new HBox(minimizeButton, closeButton);
		UIManager.initializeTitleBar(titleBar, titleBarButtons, rootAnchorPane);

		vBox = new VBox();

		// initialize list of coins/bills used for payment
		paymentDenominations = new LinkedList<>();

		UIManager.setVboxAnchors(vBox);
	}

	/**
	 * Get the scene's root node
	 * @return root node
	 */
	public Parent getRoot()
	{
		return root;
	}

	/**
	 * Menu for inserting coins/bills to access the machine.
	 */
	public void openCoinInsertionMenu()
	{
		payment = 0;

		// initialize GUI

		Tooltip backButtonTooltip = new Tooltip("Cancel transaction and return money.");

		Button backButton = new Button("Back");
		Button continueButton = new Button("Continue");

		HBox navButtons = new HBox();

		Label paymentLabel = uiManager.addPaymentLabel();
		Label title = uiManager.addPaymentPrompt();

		Image coinSlotImage = new Image("/images/coin-slot.png", 0, 150, true, true);
		ImageView coinSlotView = new ImageView(coinSlotImage);

		buttonGrid = new GridPane();
		buttonGrid.setPadding(new Insets(10, 0, 0, 0));

		UIManager.setButtonGridGaps(buttonGrid, 5);
		uiManager.setupPaymentButtons("insert-coin-button", buttonGrid, paymentLabel);
		uiManager.setupNavButtons(backButton, continueButton, backButtonTooltip, navButtons);

		vBox.getChildren().addAll(coinSlotView, title, paymentLabel, buttonGrid, navButtons);
		vBox.setStyle("-fx-alignment: Center");

		rootAnchorPane.getChildren().add(vBox);

		root = rootAnchorPane;
		root.getStyleClass().add("root");

		moveApp(titleBar, stage);
	}

	/**
	 * Menu for selecting a slot.
	 */
	public void openVendingMenu()
	{
		// initialize GUI
		Button backButton = new Button();

		Label label = new Label("Select a slot:");
		label.getStyleClass().add("title-label");

		buttonGrid = new GridPane();

		UIManager.setButtonGridGaps(buttonGrid, 5);
		uiManager.setupSlotGrid(backButton);


		vBox.getChildren().addAll(label, buttonGrid);
		vBox.setStyle("-fx-alignment: Center");
		vBox.setMinSize(720, 720);

		if (vendingMachineController.getVendingMachines().getLast() instanceof SpecialVendingMachine)
		{
			Button customItemButton = new Button("Ramen");
			ButtonAnimator.resizeWhenHovered(customItemButton);

			customItemButton.getStyleClass().add("nav-continue-button");
			customItemButton.setStyle("-fx-font-size: 23");
			customItemButton.setOnAction(event ->
			{
				try
				{
					goToRamenMenu(event);
				} catch (IOException e)
				{
					throw new RuntimeException(e);
				}
			});

			vBox.getChildren().add(customItemButton);
			vBox.setSpacing(25);
		}

		root = vBox;
	}

	/**
	 * Menu for dispensing items
	 * @param slot slot being purchased from
	 * @param slotIndex index of slot
	 */
	public void openDispenseMenu(Slot slot, int slotIndex)
	{
		// Return if there are no items in the slot
		if (slot.getItemList().size() == 0)
		{
			openPopup("Slot has no items.");
			openVendingMenu();
			return;
		}

		// Return if the item is unbuyable
		if (vendingMachineController.getVendingMachines().getLast() instanceof SpecialVendingMachine)
		{
			if (((SpecialVendingMachine) vendingMachineController.getVendingMachines().getLast()).getUnbuyableItems().
					contains(slot.getItemList().get(0).getName()))
			{
				openPopup("This item is unbuyable.");
				openVendingMenu();
				return;
			}
		}

		// Initialize GUI

		String itemName = slot.getItemList().get(0).getName();

		AnchorPane itemDisplayBackground = new AnchorPane();

		final Label sceneTitle = new Label("Purchasing Item");
		Label itemNameLabel = new Label(itemName + " - " + vendingMachineController.getVendingMachines().getLast().
				getAvailability(itemName) + " left");
		Label itemPriceLabel = new Label("₱" + (float) slot.getItemList().get(0).getPrice() / 100);
		Label itemCalorieLabel = new Label(slot.getItemList().get(0).getCalories() + " kcal");

		Label paymentTitle = new Label("Payment:");

		Label paymentLabel = new Label("₱" + (float) payment / 100);

		uiManager.setupGUIElements(sceneTitle, itemDisplayBackground, itemNameLabel, itemPriceLabel, itemCalorieLabel,
				paymentTitle, paymentLabel);

		GridPane addPaymentButtons = new GridPane();
		UIManager.setButtonGridGaps(addPaymentButtons, 18);

		HBox transactionButtons = new HBox();

		uiManager.setAnchors(itemNameLabel, itemPriceLabel, itemCalorieLabel,
				paymentTitle, paymentLabel, addPaymentButtons, transactionButtons);

		// Sets the cancel button to open the vending menu when clicked.
		Button cancelButton = new Button("✕");
		cancelButton.getStyleClass().add("item-back-button");
		cancelButton.setOnAction(event ->
		{
			vBox.getChildren().clear();
			openPopup("Your ₱" + (float) payment / 100 + " has been returned.");
			openVendingMenu();
		});

		Button buyButton = new Button("Buy");
		buyButton.getStyleClass().add("add-button");
		// Sets the buy button to produce change and dispense an item when clicked.
		buyButton.setOnAction(event -> produceChange(slot, slotIndex, itemName, sceneTitle, buyButton));

		// Finish setting up GUI
		uiManager.setupPaymentButtons("add-button", addPaymentButtons, paymentLabel);

		transactionButtons.getChildren().addAll(cancelButton, buyButton);
		transactionButtons.setSpacing(12);

		itemDisplayBackground.getChildren().addAll(itemNameLabel, itemPriceLabel, itemCalorieLabel,
				paymentTitle, paymentLabel, addPaymentButtons, transactionButtons);

		vBox.getChildren().addAll(sceneTitle, itemDisplayBackground);
		vBox.setPadding(new Insets(0, 0, 35, 0));
		root = vBox;
	}

	/**
	 * Outputs change for the transaction through a popup box.
	 * @param slot slot being purchased from
	 * @param slotIndex index of slot
	 * @param itemName name of item being purchased
	 * @param sceneTitle label for scene prompt
	 * @param button button used to purchase item
	 */
	private void produceChange(Slot slot, int slotIndex, String itemName, Label sceneTitle, Button button)
	{
		// Get the item's price
		int itemPrice = slot.getItemList().get(0).getPrice();

		// Get the list of coins/bills returned as change
		List<Integer> changeList = vendingMachineController.getVendingMachines().getLast().
				dispenseItem(slotIndex, payment);

		// Error handling
		if (changeList == null)
		{
			openPopup("Please insert more money.");
		}
		else if (changeList.size() == 0 && payment > itemPrice)
		{
			openPopup("Could not produce change. Your ₱" + (float) payment / 100 + " has been returned. " +
			          "Try again at a later date.");
			// to extract method
			changeScene();
		}
		// If payment is exact
		else if (payment == itemPrice)
		{
			Timeline timeline = showProcessingText(sceneTitle, button);
			timeline.playFromStart();

			timeline.setOnFinished(actionEvent ->
			{
				for (Integer denomination : paymentDenominations)
				{
					vendingMachineController.getVendingMachines().getLast().getDenominations()
							.addDenomination(denomination, 1);
				}
				openPopup("You paid the exact amount and have received " + itemName);
				// to extract method
				changeScene();
			});
		}
		// If there is change
		else
		{
			Timeline timeline = showProcessingText(sceneTitle, button);

			// Append the list of coins onto string builder with currency symbol.
			StringBuilder stringBuilder = new StringBuilder();
			changeList.forEach((change) -> stringBuilder.append("₱").append((float) change / 100).append(" "));

			timeline.playFromStart();

			// Display popup when animation is finished
			timeline.setOnFinished(actionEvent ->
			{
				for (Integer denomination : paymentDenominations)
				{
					vendingMachineController.getVendingMachines().getLast().getDenominations()
							.addDenomination(denomination, 1);
				}

				// If there is only 1 coin/bill for change
				if (changeList.size() == 1)
				{
					openPopup("You have received " + itemName + ". Your change is " + stringBuilder);
				}
				else
				{
					openPopup("You have received " + itemName + ". Your change is ₱" +
					          (float) (payment - itemPrice) / 100 + ": " + stringBuilder);
				}
				// return to coin insertion menu
				changeScene();
			});
		}
	}

	/**
	 * Creates an animation to show the machine's processing of the transaction.
	 * @param sceneTitle label to display processing text
	 * @param button button used in purchasing the item
	 * @return animation of text displaying "Dispensing Item" with ellipsis gradually being completed
	 */
	private Timeline showProcessingText(Label sceneTitle, Button button)
	{
		button.setDisable(true);
		return new Timeline(
				new KeyFrame(Duration.ZERO, event -> sceneTitle.setText("Dispensing item")),
				new KeyFrame(Duration.millis(600), event -> sceneTitle.setText("Dispensing item.")),
				new KeyFrame(Duration.millis(1200), event -> sceneTitle.setText("Dispensing item..")),
				new KeyFrame(Duration.millis(1700), event -> sceneTitle.setText("Dispensing item...")),
				new KeyFrame(Duration.millis(2200))
		);
	}

	/**
	 * Changes the scene to the coin insertion menu.
	 */
	private void changeScene()
	{
		vBox.getChildren().clear();
		rootAnchorPane.getChildren().remove(vBox);
		openCoinInsertionMenu();
	}

	private void goToRamenMenu(ActionEvent event) throws IOException
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/RamenMenu.fxml"));
		openMenuScene(event, loader, "ramen", payment, paymentDenominations);
	}

	/**
	 * Nested class for additional abstraction as this menu has a lot of UI elements.
	 * Its static methods can be reused for other menu controllers.
	 */
	protected class UIManager
	{
		/**
		 * Sets up the vending menu's GUI.
		 * @param sceneTitle text title that serves as user prompt
		 * @param itemDisplayBackground anchor pane managing alignment
		 * @param itemNameLabel text displaying item name
		 * @param itemPriceLabel text displaying item price
		 * @param itemCalorieLabel text displaying item calories
		 * @param paymentTitle text prompt for payment
		 * @param paymentLabel text displaying payment
		 */
		private void setupGUIElements(Label sceneTitle, AnchorPane itemDisplayBackground, Label itemNameLabel,
		                              Label itemPriceLabel, Label itemCalorieLabel, Label paymentTitle, Label paymentLabel)
		{
			sceneTitle.getStyleClass().add("title-label");
			sceneTitle.setPadding(new Insets(0, 0, 25,0));
			itemDisplayBackground.setPrefSize(540, 360);
			itemDisplayBackground.setMaxSize(540, 360);
			itemDisplayBackground.getStyleClass().add("item-display-background");
			itemNameLabel.getStyleClass().add("item-name");
			itemPriceLabel.getStyleClass().add("item-other");
			itemCalorieLabel.getStyleClass().add("item-other");
			paymentTitle.getStyleClass().add("item-other");
			paymentLabel.getStyleClass().add("item-other");
		}

		/**
		 * Sets the alignment of elements in their parent anchor pane.
		 * @param itemNameLabel text displaying item name
		 * @param itemPriceLabel text displaying item price
		 * @param itemCalorieLabel text displaying item calories
		 * @param paymentTitle text prompt for payment
		 * @param paymentLabel text displaying payment
		 * @param addPaymentButtons button for adding coin/bill to payment
		 * @param transactionButtons button for cancelling or purchasing the displayed item
		 */
		private void setAnchors(Label itemNameLabel, Label itemPriceLabel, Label itemCalorieLabel, Label paymentTitle,
		                        Label paymentLabel, GridPane addPaymentButtons, HBox transactionButtons)
		{
			AnchorPane.setTopAnchor(itemNameLabel, 42.0);
			AnchorPane.setLeftAnchor(itemNameLabel, 40.0);
			AnchorPane.setTopAnchor(itemPriceLabel,85.0);
			AnchorPane.setLeftAnchor(itemPriceLabel, 40.0);
			AnchorPane.setTopAnchor(itemCalorieLabel,85.0);
			AnchorPane.setLeftAnchor(itemCalorieLabel, 147.0);
			AnchorPane.setTopAnchor(paymentTitle, 121.0);
			AnchorPane.setLeftAnchor(paymentTitle, 345.0);
			AnchorPane.setTopAnchor(paymentLabel, 155.0);
			AnchorPane.setLeftAnchor(paymentLabel, 345.0);
			AnchorPane.setTopAnchor(addPaymentButtons, 138.0);
			AnchorPane.setLeftAnchor(addPaymentButtons, 40.0);
			AnchorPane.setTopAnchor(transactionButtons, 210.0);
			AnchorPane.setLeftAnchor(transactionButtons, 345.0);
		}

		/**
		 * Adds a label to display current payment amount.
		 * @return label with current payment amount
		 */
		private Label addPaymentLabel()
		{
			Label paymentLabel = new Label();
			paymentLabel.getStyleClass().add("title-label");
			paymentLabel.setText("₱" + payment);
			return paymentLabel;
		}

		/**
		 * Sets a VBox's alignment.
		 * @param vBox VBox to be formatted
		 */
		protected static void setVboxAnchors(VBox vBox)
		{
			AnchorPane.setTopAnchor(vBox, 20.0);
			AnchorPane.setRightAnchor(vBox, 0.0);
			AnchorPane.setBottomAnchor(vBox, 0.0);
			AnchorPane.setLeftAnchor(vBox, 0.0);
		}

		/**
		 * Adds a text prompt for coin/bill insertion
		 * @return text prompt for insertion
		 */
		private Label addPaymentPrompt()
		{
			Label title = new Label("INSERT HERE");
			title.getStyleClass().add("title-label");
			return title;
		}

		/**
		 * Adds top bar button functionality and updates their styles.
		 */
		public void initializeTopBarButtons()
		{
			minimizeButton.getStyleClass().add("minimize-button");
			minimizeButton.setOnAction(event -> minimizeApp(stage));

			closeButton.getStyleClass().add("close-button");
			closeButton.setOnAction(event -> closeApp());
		}

		/**
		 * Dynamically generates a grid of buttons for the vending machine slots.
		 * @param backButton button to return from menu
		 */
		public void setupSlotGrid(Button backButton)
		{
			int slotsLength = vendingMachineController.getVendingMachines().get(0).getSlots().length;

			int backButtonRow = slotsLength / 3;  // Add the back button in the next row
			int backButtonColumn = slotsLength % 3;

			// Start from slotsLength - 1 such that the button order is increasing from bottom to top.
			for (int i = slotsLength - 1; i >= 0; i--)
			{
				Button button = new Button();
				button.getStyleClass().add("slot-button");
				button.setText(String.valueOf(i + 1));

				int row = (slotsLength - i - 1) / 3;
				int column = (slotsLength - i - 1) % 3;
				buttonGrid.add(button, column, row);

				ButtonAnimator.resizeWhenHovered(button);

				// Set each button to open the dispense menu for their respective slots when clicked.
				int slotIndex = i;
				button.setOnAction(event ->
				{
					vBox.getChildren().clear();

					openDispenseMenu(vendingMachineController.getVendingMachines().getLast().
								getSlots()[slotIndex], slotIndex);
				});
			}

			backButton.getStyleClass().add("slot-back-button");
			backButton.setText("⌫");

			backButton.setOnAction(event ->
			{
				try
				{
					returnToMainMenu(event);
					openPopup("Your ₱" + (float) payment / 100 + " has been returned.");
				} catch (IOException e)
				{
					throw new RuntimeException(e);
				}
			});

			buttonGrid.add(backButton, backButtonColumn, backButtonRow);
			ButtonAnimator.resizeWhenHovered(backButton);
		}

		/**
		 * Set the gap between buttons in a grid pane.
		 * @param buttonGrid grid pane to be formatted
		 * @param gap size of gap
		 */
		protected static void setButtonGridGaps(GridPane buttonGrid, double gap)
		{
			buttonGrid.setAlignment(Pos.CENTER);
			buttonGrid.setHgap(gap);
			buttonGrid.setVgap(gap);
		}

		/**
		 * Dynamically creates a grid of buttons to add coins/bills to the payment.
		 * @param style CSS style of the grid buttons
		 * @param addPaymentButtons grid pane to be added to
		 * @param paymentLabel text displaying current payment amount
		 */
		private void setupPaymentButtons(String style, GridPane addPaymentButtons, Label paymentLabel)
		{
			int column = 0;
			int row = 0;
			int maxColumns = 3;

			// Create a button for each denomination used.
			for (int denomination : vendingMachineController.getVendingMachines().getLast().
					getDenominations().getDenominationList())
			{
				Button button = new Button();
				button.getStyleClass().add(style);

				// Resize the font depending on the value of the coin/bill
				if (denomination >= 100000 && !style.equals("add-button"))
				{
					button.setStyle("-fx-font-size: 19");
				}
				else if (denomination >= 100000)
				{
					button.setStyle("-fx-font-size: 14.5");
				}

				if (denomination < 100)
				{
					button.setText(denomination + "¢");
				}
				else
				{
					button.setText("₱" + denomination / 100);
				}

				ButtonAnimator.resizeWhenHovered(button);

				addPaymentButtons.add(button, column, row);

				column++;

				if (column >= maxColumns)
				{
					column = 0;
					row++;
				}

				// Set each button to add its corresponding denomination to the payment when clicked.
				button.setOnAction(event ->
				{
					paymentLabel.setText("₱" + (float) (payment + denomination) / 100);
					payment += denomination;
					paymentDenominations.add(denomination);
				});
			}
		}

		/**
		 * Initialize app title bar and its buttons.
		 * @param titleBar title bar pane
		 * @param titleBarButtons minimize and close button hbox
		 * @param rootAnchorPane parent pane
		 */
		protected static void initializeTitleBar(AnchorPane titleBar, HBox titleBarButtons, AnchorPane rootAnchorPane)
		{
			titleBar.getStyleClass().add("top-bar");
			titleBar.getChildren().add(titleBarButtons);

			titleBarButtons.setPrefWidth(709);
			titleBarButtons.setStyle("-fx-hgap: 25");
			titleBarButtons.setAlignment(Pos.BASELINE_RIGHT);

			rootAnchorPane.getChildren().add(titleBar);
		}

		/**
		 * Initialize the navigation buttons.
		 * @param backButton back button
		 * @param continueButton continue button
		 * @param backButtonTooltip tooltip for back button
		 * @param navButtons hbox containing the buttons
		 */
		protected void setupNavButtons(Button backButton, Button continueButton, Tooltip backButtonTooltip, HBox navButtons)
		{
			backButton.getStyleClass().add("nav-back-button");
			backButton.setTooltip(backButtonTooltip);
			backButton.setOnAction(event ->
			{
				try
				{
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/TestMenu.fxml"));
					openMenuScene(event, loader, "test", null, null);
				} catch (IOException exception)
				{
					throw new RuntimeException(exception);
				}

				if (payment > 0)
				{
					openPopup("Your ₱" + (float) payment / 100 + " has been returned.");
				}
			});

			continueButton.getStyleClass().add("nav-continue-button");
			continueButton.setOnAction(event ->
			{
				if (payment == 0)
				{
					openPopup("Please insert payment.");
					event.consume();
				}
				else
				{
					vBox.getChildren().clear();
					openVendingMenu();
				}
			});

			ButtonAnimator.resizeWhenHovered(backButton);
			ButtonAnimator.resizeWhenHovered(continueButton);

			navButtons.getChildren().addAll(backButton, continueButton);

			navButtons.setAlignment(Pos.CENTER);
			navButtons.setPadding(new Insets(30, 0, 0, 0));
			navButtons.setSpacing(15);
		}
	}
}

