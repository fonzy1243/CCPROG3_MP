package Controller;

import Model.Item;
import Model.Slot;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import java.util.List;

/**
 * Controls user input in the vending features menu.
 * @see Controller.MenuController
 */
public class VendingMenuController extends MenuController
{
	private Parent root;
	private final VBox vBox;
	private VendingMachineController vendingMachineController;
	private final ButtonAnimator buttonAnimator;
	private GridPane buttonGrid;
	private final AnchorPane rootAnchorPane;
	private final AnchorPane titleBar;
	private final Button minimizeButton;
	private final Button closeButton;
	private final HBox titleBarButtons;
	private final UIManager uiManager;
	private int payment;

	public VendingMenuController()
	{
		uiManager = new UIManager();

		rootAnchorPane = new AnchorPane();
		rootAnchorPane.getStyleClass().add("rounded-pane");

		minimizeButton = new Button("—");
		closeButton = new Button("✕");

		uiManager.initializeButtons();

		titleBar = new AnchorPane();
		titleBarButtons = new HBox(minimizeButton, closeButton);
		UIManager.initializeTitleBar(titleBar, titleBarButtons, rootAnchorPane);

		vBox = new VBox();

		uiManager.setVboxAnchors();

		buttonAnimator = new ButtonAnimator();
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
		payment = 0;
		
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

	public void openVendingMenu()
	{
		Button backButton = new Button();

		Label label = new Label("Select a slot:");
		label.getStyleClass().add("title-label");

		buttonGrid = new GridPane();

		UIManager.setButtonGridGaps(buttonGrid, 5);
		uiManager.setupSlotGrid(backButton);

		vBox.getChildren().addAll(label, buttonGrid);
		vBox.setStyle("-fx-alignment: Center");
		vBox.setMinSize(720, 720);

		root = vBox;
	}


	public void openDispenseMenu(Slot slot, int slotIndex)
	{
		System.out.println(slot.getItemList().size());

		String itemName = slot.getItemList().get(0).getName();

		AnchorPane itemDisplayBackground = new AnchorPane();

		final Label sceneTitle = new Label("Purchasing Item");
		Label itemNameLabel = new Label(itemName + " - " + vendingMachineController.getVendingMachines().getLast().
				getAvailability(itemName) + " left");
		Label itemPriceLabel = new Label("₱" + (float) slot.getItemList().get(0).getPrice() / 100);
		Label itemCalorieLabel = new Label(slot.getItemList().get(0).getCalories() + " kcal");

		Label paymentTitle = new Label("Payment:");

		Label paymentLabel = new Label("₱" + (float) payment / 100);

		uiManager.setupGUIElements(sceneTitle, itemDisplayBackground, itemNameLabel, itemPriceLabel, itemCalorieLabel, paymentTitle, paymentLabel);

		GridPane addPaymentButtons = new GridPane();
		UIManager.setButtonGridGaps(addPaymentButtons, 18);

		HBox transactionButtons = new HBox();

		uiManager.setAnchors(itemNameLabel, itemPriceLabel, itemCalorieLabel,
				paymentTitle, paymentLabel, addPaymentButtons, transactionButtons);

		Button cancelButton = new Button("✕");
		cancelButton.getStyleClass().add("item-back-button");
		cancelButton.setOnAction(event ->
		{
			vBox.getChildren().clear();
			openVendingMenu();
		});

		Button buyButton = new Button("Buy");
		buyButton.getStyleClass().add("add-button");

		buyButton.setOnAction(event ->
				produceChange(slot, slotIndex, itemName, sceneTitle));

		uiManager.setupPaymentButtons("add-button", addPaymentButtons, paymentLabel);

		transactionButtons.getChildren().addAll(cancelButton, buyButton);
		transactionButtons.setSpacing(12);

		itemDisplayBackground.getChildren().addAll(itemNameLabel, itemPriceLabel, itemCalorieLabel,
				paymentTitle, paymentLabel, addPaymentButtons, transactionButtons);

		vBox.getChildren().addAll(sceneTitle, itemDisplayBackground);
		vBox.setPadding(new Insets(0, 0, 35, 0));
		root = vBox;
	}

	private void produceChange(Slot slot, int slotIndex, String itemName, Label sceneTitle)
	{
		List<Integer> changeList = vendingMachineController.getVendingMachines().getLast().
				dispenseItem(slotIndex, payment);

		if (changeList == null)
		{
			openPopup("Please insert more money.");
		}
		else if (changeList.size() == 0 && payment > slot.getItemList().get(0).getPrice())
		{
			openPopup("Could not produce change. Try again at a later date.");
			// to extract method
			changeScene();
		}
		else if (payment == slot.getItemList().get(0).getPrice())
		{
			openPopup("You paid the exact amount and have received " + itemName);
			// to extract method
			changeScene();
		}
		else
		{
			StringBuilder stringBuilder = new StringBuilder();
			changeList.forEach((change) -> stringBuilder.append("₱").append((float) change / 100).append(" "));

			Timeline timeline = new Timeline(
					new KeyFrame(Duration.ZERO, event -> sceneTitle.setText("Dispensing item")),
					new KeyFrame(Duration.millis(600), event -> sceneTitle.setText("Dispensing item.")),
					new KeyFrame(Duration.millis(1200), event -> sceneTitle.setText("Dispensing item..")),
					new KeyFrame(Duration.millis(1700), event -> sceneTitle.setText("Dispensing item...")),
					new KeyFrame(Duration.millis(2200))
			);

			timeline.playFromStart();

			timeline.setOnFinished(actionEvent ->
			{
				if (changeList.size() == 1)
				{
					openPopup("You have received " + itemName + ". Your change is " + stringBuilder);
				}
				else
				{
					openPopup("You have received " + itemName + ". Your change is ₱" +
					          (float) (payment - slot.getItemList().get(0).getPrice()) / 100 + ": " + stringBuilder);
				}

				changeScene();
			});
		}
	}

	private void changeScene()
	{
		vBox.getChildren().clear();
		rootAnchorPane.getChildren().remove(vBox);
		openCoinInsertionMenu();
	}

	private class UIManager
	{

		private void setupGUIElements(Label sceneTitle, AnchorPane itemDisplayBackground, Label itemNameLabel, Label itemPriceLabel, Label itemCalorieLabel, Label paymentTitle, Label paymentLabel)
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

		private void setAnchors(Label itemNameLabel, Label itemPriceLabel, Label itemCalorieLabel, Label paymentTitle, Label paymentLabel, GridPane addPaymentButtons, HBox transactionButtons)
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

		private Label addPaymentLabel()
		{
			Label paymentLabel = new Label();
			paymentLabel.getStyleClass().add("title-label");
			paymentLabel.setText("₱" + payment);
			return paymentLabel;
		}

		private void setVboxAnchors()
		{
			AnchorPane.setTopAnchor(vBox, 20.0);
			AnchorPane.setRightAnchor(vBox, 0.0);
			AnchorPane.setBottomAnchor(vBox, 0.0);
			AnchorPane.setLeftAnchor(vBox, 0.0);
		}

		private Label addPaymentPrompt()
		{
			Label title = new Label("INSERT HERE");
			title.getStyleClass().add("title-label");
			return title;
		}

		public void initializeButtons()
		{
			minimizeButton.getStyleClass().add("minimize-button");
			minimizeButton.setOnAction(event -> minimizeApp(stage));

			closeButton.getStyleClass().add("close-button");
			closeButton.setOnAction(event -> closeApp());
		}

		public void setupSlotGrid(Button backButton)
		{
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

				int slotIndex = i;
				button.setOnAction(event ->
				{
					vBox.getChildren().clear();

					// temporary testing code
					Item testItem = new Item("California Maki", 1500, 47);
					if (!vendingMachineController.getVendingMachines().getLast().
							addItemToSlot(testItem, slotIndex, 3))
					{
						openPopup("Cannot add item.");
						openVendingMenu();
					}
					else
					{
						System.out.println("Item added to slot " + (slotIndex + 1));
						openDispenseMenu(vendingMachineController.getVendingMachines().getLast().
								getSlots()[slotIndex], slotIndex);
					}
				});
			}

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
		}

		public static void setButtonGridGaps(GridPane buttonGrid, double gap)
		{
			buttonGrid.setAlignment(Pos.CENTER);
			buttonGrid.setHgap(gap);
			buttonGrid.setVgap(gap);
		}

		private void setupPaymentButtons(String style, GridPane addPaymentButtons, Label paymentLabel)
		{
			int column = 0;
			int row = 0;
			int maxColumns = 3;

			for (int denomination : vendingMachineController.getVendingMachines().getLast().
					getDenominations().getDenominationList())
			{
				Button button = new Button();
				button.getStyleClass().add(style);

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

				buttonAnimator.resizeWhenHovered(button);

				addPaymentButtons.add(button, column, row);

				column++;

				if (column >= maxColumns)
				{
					column = 0;
					row++;
				}

				button.setOnAction(event ->
				{
					paymentLabel.setText("₱" + (float) (payment + denomination) / 100);
					payment += denomination;
				});
			}
		}

		public static void initializeTitleBar(AnchorPane titleBar, HBox titleBarButtons, AnchorPane rootAnchorPane)
		{
			titleBar.getStyleClass().add("top-bar");
			titleBar.getChildren().add(titleBarButtons);

			titleBarButtons.setPrefWidth(709);
			titleBarButtons.setStyle("-fx-hgap: 25");
			titleBarButtons.setAlignment(Pos.BASELINE_RIGHT);

			rootAnchorPane.getChildren().add(titleBar);
		}

		public void setupNavButtons(Button backButton, Button continueButton, Tooltip backButtonTooltip, HBox navButtons)
		{
			backButton.getStyleClass().add("nav-back-button");
			backButton.setTooltip(backButtonTooltip);
			backButton.setOnAction(event ->
			{
				try
				{
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/TestMenu.fxml"));
					openMenuScene(event, loader, "test", vendingMachineController);
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

			buttonAnimator.resizeWhenHovered(backButton);
			buttonAnimator.resizeWhenHovered(continueButton);

			navButtons.getChildren().addAll(backButton, continueButton);

			navButtons.setAlignment(Pos.CENTER);
			navButtons.setPadding(new Insets(30, 0, 0, 0));
			navButtons.setSpacing(15);
		}
	}
}

