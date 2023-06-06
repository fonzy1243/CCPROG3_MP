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

	private Stage stage;
	private Parent root;
	private Scene scene;

	public CreateMenuController()
	{

	}

	@FXML
	public void openCreateMenu()
	{
		createMenuVBox.setSpacing(15);
		createSpecialButton.setDisable(true);
	}

	public void returnToMainMenu(ActionEvent event) throws IOException
	{
		Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/MainMenu.fxml")));
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Scene scene = new Scene(root);

		returnButton.setDisable(true);

		String css = Objects
				.requireNonNull(this.getClass()
						.getResource("/styles/application.css"))
				.toExternalForm();

		createRegularButton.setDisable(true);

		scene.getStylesheets().add(css);
		stage.setScene(scene);
		stage.show();
	}
}
