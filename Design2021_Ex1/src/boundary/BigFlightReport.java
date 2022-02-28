package boundary;

import java.io.IOException;
import java.sql.Date;

import javax.swing.JFrame;

import control.ControlReport;
import control.LoginControl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;

public class BigFlightReport {
	@FXML
	private AnchorPane targerReport;
	
	@FXML
	private AnchorPane mainScreen;
	
    @FXML
    private Button returnButton;
    
	@FXML
	private TextField touristSeats;
	@FXML
	private DatePicker fromDate;

	@FXML
	private DatePicker toDate;
	@FXML
	private ComboBox<String> countriesCombo;

	@FXML
	void initialize() {
		String type = LoginControl.getInstance().getLoginUser().getType();
		if(type.equals("flight_manager")) {
			targerReport.setVisible(false);
		}
		countriesCombo.getItems().addAll(ControlReport.getInstance().getAllDestCountries());
	}
	
	@FXML
	void genarateReport(ActionEvent event) {
		Integer seatNum = Integer.parseInt(touristSeats.getText());
		Date from =  Date.valueOf(fromDate.getValue());
		Date to =  Date.valueOf(toDate.getValue());
		if(seatNum != null && from != null && to != null) {
			JFrame reportFrame = ControlReport.getInstance().produceBigFlightReport(seatNum, from, to);
			reportFrame.setVisible(true);
			touristSeats.clear();
			fromDate.setValue(null);
			toDate.setValue(null);
		}
		else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("You must fill all the fields");
			alert.setTitle("failed to genarate report");
			alert.showAndWait();
		}
		
		
	}
	
	@FXML
	void genarateReport2(ActionEvent event) {
		String destCountry = countriesCombo.getValue();
		if(!destCountry.isEmpty()) {
			JFrame reportFrame = ControlReport.getInstance().produceDestCountryReport(destCountry);
			reportFrame.setVisible(true);
			countriesCombo.getSelectionModel().clearSelection();
		}
		else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("You must fill all the fields");
			alert.setTitle("failed to genarate report");
			alert.showAndWait();
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
