package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * Controls user input in the main menu.
 * Allows the user to navigate to submenus.
 * @see Controller.MenuController
 */
public class MainMenuController extends MenuController
{
	@FXML
	private AnchorPane topBar;
	@FXML
	private Button minimizeButton;
	@FXML
	private Button testVendingMachineButton;
	@FXML
	private Button createMenuButton;
	@FXML
	private Button exitButton;
	@FXML
	private VBox mainMenuVBox;

	public MainMenuController()
	{

	}

	/**
	 * Opens the main menu scene through its FXML file.
	 * @param stage application window.
	 */
	public void openMainMenu(Stage stage)
	{
		try
		{
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainMenu.fxml"));
			Parent root = loader.load();

			Scene scene = new Scene(root, 730, 730);
			scene.setFill(Color.TRANSPARENT);

			if (mainMenuVBox == null)
			{
				mainMenuVBox = (VBox) root.lookup("#mainMenuVBox");
			}

			// Get the CSS file from the styles folder.
			String css = Objects
					.requireNonNull(this.getClass()
							.getResource("/styles/application.css"))
					.toExternalForm();

			// Apply the CSS styles to the scene.
			scene.getStylesheets().add(css);

			mainMenuVBox.setSpacing(12);
			mainMenuVBox.setAlignment(Pos.CENTER);

			// Instantiate buttons from the FXML file and animate them.

			if (createMenuButton == null)
			{
				createMenuButton = (Button) root.lookup("#createMenuButton");
			}

			ButtonAnimator.resizeWhenHovered(createMenuButton);

			if (testVendingMachineButton == null)
			{
				testVendingMachineButton = (Button) root.lookup("#testVendingMachineButton");
			}

			ButtonAnimator.resizeWhenHovered(testVendingMachineButton);

			if (exitButton == null)
			{
				exitButton = (Button) root.lookup("#exitButton");
			}

			ButtonAnimator.resizeWhenHovered(exitButton);

			if (minimizeButton == null)
			{
				minimizeButton = (Button) root.lookup("#minimizeButton");
			}

			minimizeButton.setOnAction(event -> minimizeApp(stage));

			if (topBar == null)
			{
				topBar = (AnchorPane) root.lookup("#topBar");
			}

			// Allow the user to click and drag the app through the top bar.
			moveApp(topBar, stage);

			stage.setScene(scene);
			stage.setResizable(false);
			stage.setTitle("Vending Machine Factory Simulator");
			stage.show();
		}
		catch (Exception exception)
		{
			exception.printStackTrace();
		}
	}

	/**
	 * Opens the create menu scene through FXML and sets the controller to createMenuController.
	 * @param event button click
	 * @throws IOException if error occurs loading the FXML file
	 */
	@FXML
	private void goToCreateMenu(ActionEvent event) throws IOException
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CreateMenu.fxml"));
		openMenuScene(event, loader, "create", vendingMachineController);
	}

	/**
	 * Opens the create menu scene through FXML and sets the controller to testMenuController.
	 * @param event button click
	 * @throws IOException if error occurs loading the FXML file
	 */
	@FXML
	private void goToTestMenu(ActionEvent event) throws IOException
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/TestMenu.fxml"));
		openMenuScene(event, loader, "test", vendingMachineController);
	}
}
