package Controller;

import javafx.animation.ScaleTransition;
import javafx.scene.control.Button;
import javafx.util.Duration;

public class ButtonAnimator
{
	// test comment
	public void resizeWhenHovered(Button button)
	{
		ScaleTransition newScale = new ScaleTransition(Duration.millis(25));
		newScale.setToX(1.01);
		newScale.setToY(1.01);
		newScale.setNode(button);

		ScaleTransition oldScale = new ScaleTransition(Duration.millis(25));
		oldScale.setToX(1);
		oldScale.setToY(1);
		oldScale.setNode(button);

		button.setOnMouseEntered(mouseEvent -> newScale.playFromStart());

		button.setOnMouseExited(mouseEvent -> oldScale.playFromStart());
	}
}
