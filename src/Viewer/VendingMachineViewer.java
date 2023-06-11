package Viewer;

import Controller.*;
import javafx.application.Application;
import javafx.stage.Stage;

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
		MainMenuController mainMenuController = new MainMenuController();

		mainMenuController.setVendingMachineController(controller);
		mainMenuController.openMainMenu(stage);
	}
}
