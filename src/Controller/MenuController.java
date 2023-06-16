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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

public abstract class MenuController
{
	protected Stage stage;

	public void setStage(Stage stage)
	{
		this.stage = stage;
	}

	@FXML
	void returnToMainMenu(ActionEvent event) throws IOException
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainMenu.fxml"));
		Parent root = loader.load();

		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Scene scene = new Scene(root, 730, 730);


		MainMenuController mainMenuController = loader.getController();
		mainMenuController.openMainMenu(stage);

		String css = Objects
				.requireNonNull(this.getClass()
						.getResource("/styles/application.css"))
				.toExternalForm();

		scene.getStylesheets().add(css);
		scene.setFill(Color.TRANSPARENT);
		stage.setScene(scene);
		stage.show();
	}

	void openMenuScene(ActionEvent event, FXMLLoader loader, String menu,
	                   VendingMachineController vendingMachineController) throws IOException
	{
		Parent root;

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

		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Scene scene = new Scene(root, 730, 730);

		switch (menu)
		{
			case "create" ->
			{
				CreateMenuController createMenuController = loader.getController();
				createMenuController.setVendingMachineController(vendingMachineController);
				createMenuController.setStage(stage);
				createMenuController.openCreateMenu();
			}
			case "test" ->
			{
				TestMenuController testMenuController = loader.getController();
				testMenuController.setVendingMachineController(vendingMachineController);
				testMenuController.setStage(stage);
				testMenuController.openTestMenu();
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

	@FXML
	void closeApp()
	{
		Platform.exit();
		System.exit(0);
	}

	void minimizeApp(Stage stage)
	{
		stage.setIconified(true);
	}

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

	void openPopup(String text)
	{
		try
		{
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PopupBox.fxml"));
			Parent root = loader.load();

			Stage popupStage = new Stage(StageStyle.TRANSPARENT);
			Scene scene = new Scene(root);
			scene.setFill(Color.TRANSPARENT);

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
