package Viewer;

import Controller.VendingMachineController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * <h2>Menu Viewer</h2>
 * <br/>
 * MenuViewer is the abstract base class for all menu controlling functions
 * which manage the interaction between the user and the application. A MenuViewer
 * object encapsulates the application's UI elements and the controller for the application's
 * vending machines.
 * <hr/>
 * All menu viewer subclasses share the same stage, as a new window is not required for most of
 * the application's functions (barring the open popup method and its uses).
 * <hr/>
 * All menu viewer subclasses using the name interface handle input using text-fields, the difference
 * in naming is simply just that.
 */
public abstract class MenuViewer
{
	/**
	 * Application window
	 */
	protected Stage stage;
	protected static VendingMachineController vendingMachineController;

	/**
	 * Passes the program's vending machine controller (static) to the main menu scene controller.
	 * @param controller manages vending machine functions such as dispensing item.
	 */
	public void setVendingMachineController(VendingMachineController controller)
	{
		vendingMachineController = controller;
	}

	/**
	 * Sets the stage (window) used by the menu controller.
	 * @param stage application window
	 */
	protected void setStage(Stage stage)
	{
		this.stage = stage;
	}

	/**
	 * Goes from the menu viewer's current scene to the main menu scene and returns control to the
	 * mainMenuController.
	 * @param event button click
	 * @throws IOException if error occurred when loading FXML file
	 */
	@FXML
	void returnToMainMenu(ActionEvent event) throws IOException
	{
		// Load the main menu's FXML
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainMenu.fxml"));
		Parent root = loader.load();

		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Scene scene = new Scene(root, 730, 730);

		// Create a viewer to handle user input and UI.
		MainMenuViewer mainMenuViewer = loader.getController();
		mainMenuViewer.openMainMenu(stage);

		// Apply the application's CSS style.
		String css = Objects
				.requireNonNull(this.getClass()
						.getResource("/styles/application.css"))
				.toExternalForm();

		scene.getStylesheets().add(css);
		scene.setFill(Color.TRANSPARENT);
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * Opens a menu scene and hands UI control to its respective viewer. It maintains the same stage used
	 * in the previous scene.
	 *
	 * @param event                button click
	 * @param loader               FXML loader (if used)
	 * @param menu                 menu to be loaded, for example: <ul><li>vending</li><li>create</li><li>test</li></ul>
	 * @param payment
	 * @param paymentDenominations
	 * @throws IOException if error occurred while loading FXML
	 */
	void openMenuScene(ActionEvent event, FXMLLoader loader, String menu, Integer payment, List<Integer> paymentDenominations) throws IOException
	{
		// Scene's root node
		Parent root;

		// Vending menu does not use an FXML file.
		if (!menu.equals("vending"))
		{
			root = loader.load();
		}
		else
		{
			VendingMenuViewer vendingMenuViewer = new VendingMenuViewer();
			vendingMenuViewer.setStage(stage);
			vendingMenuViewer.openCoinInsertionMenu();
			root = vendingMenuViewer.getRoot();
		}

		// Create a new scene.
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Scene scene = new Scene(root, 730, 730);

		// Change the new scene to its corresponding menu scene and creates a viewer to handle UI.
		switch (menu)
		{
			// Create viewer for create menu
			case "create" ->
			{
				CreateMenuViewer createMenuViewer = loader.getController();
				createMenuViewer.setStage(stage);
				createMenuViewer.openCreateMenu();
			}
			// Create viewer for test menu
			case "test" ->
			{
				TestMenuViewer testMenuController = loader.getController();
				testMenuController.setStage(stage);
				testMenuController.openTestMenu();
			}
			// Create viewer for maintenance menu
			case "maintenance" ->
			{
				MaintenanceMenuViewer maintenanceMenuController = loader.getController();
				maintenanceMenuController.setStage(stage);
				maintenanceMenuController.openMaintenanceMenu();
			}
			// Create viewer for stock interface
			case "stock" ->
			{
				StockInterfaceViewer stockInterfaceViewer = loader.getController();
				stockInterfaceViewer.setStage(stage);
				stockInterfaceViewer.openStockInterface();
			}
			// Create viewer for price interface
			case "price" ->
			{
				PriceInterfaceViewer priceInterfaceViewer = loader.getController();

				priceInterfaceViewer.setStage(stage);
				priceInterfaceViewer.openPriceInterface();
			}
			// Create viewer for money menu
			case "money" ->
			{
				MoneyMenuViewer moneyMenuViewer = loader.getController();
				moneyMenuViewer.setStage(stage);
				moneyMenuViewer.openMoneyMenu();
			}
			// Create viewer for transaction interface
			case "transaction" ->
			{
				TransactionInterfaceViewer transactionInterfaceViewer = loader.getController();
				transactionInterfaceViewer.setStage(stage);
				transactionInterfaceViewer.openTransactionInterface();
			}
			// Create viewer for withdraw interface
			case "withdraw" ->
			{
				WithdrawInterfaceViewer withdrawInterfaceViewer = loader.getController();
				withdrawInterfaceViewer.setStage(stage);
				withdrawInterfaceViewer.openWithdrawInterface();
			}
			// Create viewer for dispense interface
			case "dispense" ->
			{
				DispenseInterfaceViewer dispenseInterfaceViewer = loader.getController();
				dispenseInterfaceViewer.setStage(stage);
				dispenseInterfaceViewer.openDispenseInterface();
			}
			case "ramen" ->
			{
				RamenMenuViewer ramenMenuViewer = loader.getController();
				ramenMenuViewer.setStage(stage);
				ramenMenuViewer.setPayment(payment);
				ramenMenuViewer.setPaymentDenominations(paymentDenominations);
				ramenMenuViewer.openRamenMenu();
			}
			default ->
			{
				// Do nothing.
			}
		}

		// Get CSS filepath.
		String css = Objects
				.requireNonNull(this.getClass()
						.getResource("/styles/application.css"))
				.toExternalForm();

		scene.getStylesheets().add(css);
		scene.setFill(Color.TRANSPARENT);
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * Closes the application and all dependent windows.
	 */
	@FXML
	void closeApp()
	{
		Platform.exit();
		System.exit(0);
	}

	/**
	 * Minimizes a window.
	 * @param stage window to be minimized
	 */
	void minimizeApp(Stage stage)
	{
		stage.setIconified(true);
	}

	/**
	 * Allows the user to click and drag the top bar to move the app around the screen.
	 * @param topBar draggable anchor pane
	 * @param stage application window
	 */
	void moveApp(AnchorPane topBar, Stage stage)
	{
		double[] x = {0};
		double[] y = {0};

		topBar.setOnMousePressed(mouseEvent ->
		{
			x[0] = mouseEvent.getSceneX();
			y[0] = mouseEvent.getSceneY();
		});

		topBar.setOnMouseDragged(mouseEvent ->
		{
			stage.setX(mouseEvent.getScreenX() - x[0]);
			stage.setY(mouseEvent.getScreenY() - y[0]);
		});
	}

	/**
	 * Opens a popup dialog box to inform the user of something.
	 * @param text string to be displayed in the popup
	 */
	void openPopup(String text)
	{
		try
		{
			// Load the popup box's FXML.
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PopupBox.fxml"));
			Parent root = loader.load();

			// Create a new window for the popup.
			Stage popupStage = new Stage(StageStyle.TRANSPARENT);
			Scene scene = new Scene(root);
			scene.setFill(Color.TRANSPARENT);

			// Change the text in the popup.
			Label popupLabel = (Label) root.lookup("#popupLabel");
			popupLabel.setText(text);

			// If somehow the text is longer than this such that it no longer fits, the user is trolling.
			if (text.length() >= 115)
			{
				popupLabel.setStyle("-fx-font-size: 16");
			}
			else if (text.length() >= 85)
			{
				popupLabel.setStyle("-fx-font-size: 18");
			}
			else if (text.length() >= 65)
			{
				popupLabel.setStyle("-fx-font-size: 20");
			}
			else if (text.length() >= 45)
			{
				popupLabel.setStyle("-fx-font-size:  22");
			}

			Button closeButton = (Button) root.lookup("#closeButton");
			closeButton.setOnAction(event -> popupStage.close());

			AnchorPane topBar = (AnchorPane) root.lookup("#topBar");

			moveApp(topBar, popupStage);

			// Apply the application's CSS theme.
			String css = Objects
					.requireNonNull(this.getClass()
							.getResource("/styles/application.css"))
					.toExternalForm();

			scene.getStylesheets().add(css);

			popupStage.setScene(scene);
			popupStage.show();
		} catch (Exception exception)
		{
			exception.printStackTrace();
		}
	}
}
