package boundary;

import java.io.IOException;

import control.ExportControl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class ExportJson {
	@FXML
	private AnchorPane mainScreen;
	
	@FXML
	private Button exportBtn;
	
	
	@FXML
	void exportToJson(ActionEvent event) {
		ExportControl.getInstance().exportToJSON();
	}
	
	@FXML
    void returnToMenu(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/boundary/MenuScreen.fxml"));
		AnchorPane pane = loader.load();
		mainScreen.getChildren().removeAll(mainScreen.getChildren());
		mainScreen.getChildren().add(pane);
    }
}
