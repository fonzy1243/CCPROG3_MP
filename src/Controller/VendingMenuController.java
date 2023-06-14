package Controller;

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

import java.io.IOException;

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
		HBox titleBarButtons = new HBox(minimizeButton, closeButton);
		UIManager.initializeTitleBar(titleBar, titleBarButtons, rootAnchorPane);

		vBox = new VBox();

		uiManager.setAnchors();

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

		payment = 0;

		UIManager.setButtonGridGaps(buttonGrid);
		uiManager.setupPaymentButtons(paymentLabel, buttonGrid);
		uiManager.setupNavButtons(backButton, continueButton, backButtonTooltip, navButtons);

		System.out.println((float) payment / 100);

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

		UIManager.setButtonGridGaps(buttonGrid);
		uiManager.setupSlotGrid(backButton);

		vBox.getChildren().addAll(label, buttonGrid);
		vBox.setStyle("-fx-alignment: Center");
		vBox.setMinSize(720, 720);

		root = vBox;
	}

	private class UIManager
	{
		private Label addPaymentLabel()
		{
			Label paymentLabel = new Label();
			paymentLabel.getStyleClass().add("title-label");
			paymentLabel.setText(String.valueOf(payment));
			return paymentLabel;
		}

		private void setAnchors()
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

		public static void setButtonGridGaps(GridPane buttonGrid)
		{
			buttonGrid.setAlignment(Pos.CENTER);
			buttonGrid.setHgap(5);
			buttonGrid.setVgap(5);
		}

		public void setupPaymentButtons(Label paymentLabel, GridPane buttonGrid)
		{
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
				});
			}
		}

		public static void initializeTitleBar(AnchorPane titleBar, HBox titleBarButtons, AnchorPane rootAnchorPane)
		{
			titleBar.getStyleClass().add("top-bar");

			titleBarButtons.setPrefWidth(709);
			titleBarButtons.setStyle("-fx-hgap: 25");
			titleBarButtons.setAlignment(Pos.BASELINE_RIGHT);

			titleBar.getChildren().add(titleBarButtons);

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

				openPopup("Your " + (float) payment / 100 + " has been returned.");
			});

			continueButton.getStyleClass().add("nav-continue-button");
			continueButton.setOnAction(event ->
			{
				vBox.getChildren().clear();
				openVendingMenu();
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

