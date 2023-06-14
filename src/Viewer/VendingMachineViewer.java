package Viewer;

import Controller.*;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class VendingMachineViewer extends Application
{
	private static VendingMachineController controller;

	public VendingMachineViewer()
	{
		controller = new VendingMachineController();
	}

	@Override
	public void start(Stage stage)
	{
		stage.initStyle(StageStyle.TRANSPARENT);

		MainMenuController mainMenuController = new MainMenuController();

		mainMenuController.setVendingMachineController(controller);
		mainMenuController.openMainMenu(stage);
	}
}
