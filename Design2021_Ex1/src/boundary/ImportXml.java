package boundary;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

import control.ControlXml;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

public class ImportXml {
	@FXML
	private AnchorPane mainScreen;
	@FXML
	private Button importBtn;
	@FXML
	private ListView<String> updatedStatusList;
	
	@FXML
    void importXml(ActionEvent event) {
		HashMap<String, String> updatedStatus = ControlXml.getInstance().importStatusFromXML();
		for(Entry<String, String> entry: updatedStatus.entrySet()) {
			updatedStatusList.getItems().add(entry.getKey() + " " + entry.getValue());
		}
    }
	
	@FXML
    void returnToMenu(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/boundary/MenuScreen.fxml"));
		AnchorPane pane = loader.load();
		mainScreen.getChildren().removeAll(mainScreen.getChildren());
		mainScreen.getChildren().add(pane);
    }
	
}
