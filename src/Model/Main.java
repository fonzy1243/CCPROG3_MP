package Model;

import Viewer.VendingMachineViewer;
import javafx.application.Application;

public class Main
{
	public static void main(String[] args)
	{
		System.setProperty("prism.lcdtext", "false");
		Application.launch(VendingMachineViewer.class, args);
	}
}