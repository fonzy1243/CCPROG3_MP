import javafx.application.Application;
import javafx.stage.Stage;

public class VendingMachineViewer extends Application
{
	private VendingMachineController controller;

	public VendingMachineViewer()
	{}

	public VendingMachineViewer(VendingMachineController controller)
	{
		this.controller = controller;
	}


	@Override
	public void start(Stage stage) throws Exception
	{


		stage.show();
	}
}
