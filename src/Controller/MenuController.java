package Controller;

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
import java.util.Objects;

/**
 * <h2>Menu Controller</h2>
 * <br/>
 * MenuController is the abstract base class for all menu controlling functions
 * which manage the interaction between the user and the application. A MenuController
 * object encapsulates the application's UI elements and the controller for the application's
 * vending machines.
 * <hr/>
 * All menu controller subclasses share the same stage, as a new window is not required for most of
 * the application's functions (barring the open popup method and its uses).
 * <hr/>
 * All menu controller subclasses using the name interface handle input using text-fields, the difference
 * in naming is simply just that.
 */
public abstract class MenuController
{
	/**
	 * Application window
	 */
	protected Stage stage;

	/**
	 * Sets the stage (window) used by the menu controller.
	 * @param stage application window
	 */
	public void setStage(Stage stage)
	{
		this.stage = stage;
	}

	// for the VendingMachineController, we use composition instead of inheritance in case
	// we need to create a menu that does not handle the vending machines (i.e. an application settings menu)

	/**
	 * Goes from the menu controller's current scene to the main menu scene and returns control to the
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

		// Create a controller to handle user input.
		MainMenuController mainMenuController = loader.getController();
		mainMenuController.openMainMenu(stage);

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
	 * Opens a menu scene and hands control to its respective controller. It maintains the same stage used
	 * in the previous scene.
	 * @param event button click
	 * @param loader FXML loader (if used)
	 * @param menu menu to be loaded, for example: <ul><li>vending</li><li>create</li><li>test</li></ul>
	 * @param vendingMachineController controller for application's vending machines
	 * @throws IOException if error occurred while loading FXML
	 */
	void openMenuScene(ActionEvent event, FXMLLoader loader, String menu,
	                   VendingMachineController vendingMachineController) throws IOException
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
			VendingMenuController vendingMenuController = new VendingMenuController();
			vendingMenuController.setVendingMachineController(vendingMachineController);
			vendingMenuController.setStage(stage);
			vendingMenuController.openCoinInsertionMenu();
			root = vendingMenuController.getRoot();
		}

		// Create a new scene.
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Scene scene = new Scene(root, 730, 730);

		// Change the new scene to its corresponding menu scene and creates a controller to handle user input.
		switch (menu)
		{
			// Create controller for create menu
			case "create" ->
			{
				CreateMenuController createMenuController = loader.getController();
				createMenuController.setVendingMachineController(vendingMachineController);
				createMenuController.setStage(stage);
				createMenuController.openCreateMenu();
			}
			// Create controller for test menu
			case "test" ->
			{
				TestMenuController testMenuController = loader.getController();
				testMenuController.setVendingMachineController(vendingMachineController);
				testMenuController.setStage(stage);
				testMenuController.openTestMenu();
			}
			// Create controller for maintenance menu
			case "maintenance" ->
			{
				MaintenanceMenuController maintenanceMenuController = loader.getController();
				maintenanceMenuController.setVendingMachineController(vendingMachineController);
				maintenanceMenuController.setStage(stage);
				maintenanceMenuController.openMaintenanceMenu();
			}
			// Create controller for stock interface
			case "stock" ->
			{
				StockInterfaceController stockInterfaceController = loader.getController();
				stockInterfaceController.setVendingMachineController(vendingMachineController);
				stockInterfaceController.setStage(stage);
				stockInterfaceController.openStockInterface();
			}
			// Create controller for price interface
			case "price" ->
			{
				PriceInterfaceController priceInterfaceController = loader.getController();
				priceInterfaceController.setVendingMachineController(vendingMachineController);
				priceInterfaceController.setStage(stage);
				priceInterfaceController.openPriceInterface();
			}
			// Create controller for money menu
			case "money" ->
			{
				MoneyMenuController moneyMenuController = loader.getController();
				moneyMenuController.setVendingMachineController(vendingMachineController);
				moneyMenuController.setStage(stage);
				moneyMenuController.openMoneyMenu();
			}
			// Create controller for withdraw interface
			case "withdraw" ->
			{
				WithdrawInterfaceController withdrawInterfaceController = loader.getController();
				withdrawInterfaceController.setVendingMachineController(vendingMachineController);
				withdrawInterfaceController.setStage(stage);
				withdrawInterfaceController.openWithdrawInterface();
			}
			// Create controller for dispense interface
			case "dispense" ->
			{
				DispenseInterfaceController dispenseInterfaceController = loader.getController();
				dispenseInterfaceController.setVendingMachineController(vendingMachineController);
				dispenseInterfaceController.setStage(stage);
				dispenseInterfaceController.openDispenseInterface();
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
