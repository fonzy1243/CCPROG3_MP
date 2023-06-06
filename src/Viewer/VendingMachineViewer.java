package Viewer;

import Controller.*;
import javafx.application.Application;
import javafx.stage.Stage;

public class VendingMachineViewer extends Application
{
	private final VendingMachineController controller;

	public VendingMachineViewer()
	{
		this.controller = new VendingMachineController();
	}

	@Override
	public void start(Stage stage)
	{
		MainMenuController mainMenuController = new MainMenuController();

		mainMenuController.setVendingMachineController(this.controller);
		mainMenuController.openMainMenu(stage);
	}
}
