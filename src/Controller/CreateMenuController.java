package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class CreateMenuController
{
	@FXML
	VBox createMenuVBox;
	@FXML
	Button createRegularButton;
	@FXML
	Button createSpecialButton;
	@FXML
	Button returnButton;

	private final ButtonAnimator buttonAnimator;
	private VendingMachineController vendingMachineController;

	public CreateMenuController()
	{
		this.buttonAnimator = new ButtonAnimator();
	}

	public void setVendingMachineController(VendingMachineController vendingMachineController)
	{
		this.vendingMachineController = vendingMachineController;
	}

	public void openCreateMenu()
	{

		createMenuVBox.setSpacing(12);
		createSpecialButton.setDisable(true);

		buttonAnimator.resizeWhenHovered(createRegularButton);
		buttonAnimator.resizeWhenHovered(returnButton);
	}
	@FXML
	private void createRegularMachine()
	{
		vendingMachineController.createRegularMachine(8);
	}
	@FXML
	private void returnToMainMenu(ActionEvent event) throws IOException
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
}
