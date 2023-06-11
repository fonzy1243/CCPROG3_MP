package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

abstract class MenuController
{
	@FXML
	void returnToMainMenu(ActionEvent event) throws IOException
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainMenu.fxml"));
		Parent root = loader.load();

		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Scene scene = new Scene(root);

		MainMenuController mainMenuController = loader.getController();
		mainMenuController.openMainMenu(stage);

		String css = Objects
				.requireNonNull(this.getClass()
						.getResource("/styles/application.css"))
				.toExternalForm();

		scene.getStylesheets().add(css);
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
			vendingMenuController.openCoinInsertionMenu();
			root = vendingMenuController.getRoot();
		}

		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Scene scene = new Scene(root);

		switch (menu)
		{
			case "create" ->
			{
				CreateMenuController createMenuController = loader.getController();
				createMenuController.setVendingMachineController(vendingMachineController);
				createMenuController.openCreateMenu();
			}
			case "test" ->
			{
				TestMenuController testMenuController = loader.getController();
				testMenuController.setVendingMachineController(vendingMachineController);
				testMenuController.openTestMenu();
			}
			default ->
			{

			}
		}

		String css = Objects
				.requireNonNull(this.getClass()
						.getResource("/styles/application.css"))
				.toExternalForm();

		scene.getStylesheets().add(css);
		stage.setScene(scene);
		stage.show();
	}
}
