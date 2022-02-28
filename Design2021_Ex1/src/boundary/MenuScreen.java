package boundary;

import java.io.IOException;

import control.LoginControl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;


public class MenuScreen {
	
    @FXML
    private AnchorPane mainScreen;
    
    @FXML
    private Button flightManagmentBtn;

    @FXML
    private Button reportsBtn;

    @FXML
    private Button airplaneManagmentBtn;

    @FXML
    private Button airportManagmentBtn;

    @FXML
    private Button employeeManagmentBtn;

    @FXML
    private Button assignEmployeesBtn;

    @FXML
    private Button shiftManagmentBtn;

    @FXML
    private Button importStatusBtn;

    @FXML
    private Button exportFlightsBtn;
	
    @FXML
    void initialize() {
    	String type = LoginControl.getInstance().getLoginUser().getType();
		if(type.equals("flight_manager")) {
			employeeManagmentBtn.setVisible(false);
			assignEmployeesBtn.setVisible(false);
			shiftManagmentBtn.setVisible(false);
			importStatusBtn.setVisible(false);
			exportFlightsBtn.setVisible(false);
			airportManagmentBtn.setVisible(false);
		}
		else if(type.equals("chief_flight_manager")) {
			employeeManagmentBtn.setVisible(false);
			assignEmployeesBtn.setVisible(false);
			shiftManagmentBtn.setVisible(false);
			importStatusBtn.setVisible(false);
			exportFlightsBtn.setVisible(false);
		}
		else if(type.equals("employee_manager")) {
			flightManagmentBtn.setVisible(false);
			reportsBtn.setVisible(false);
			airplaneManagmentBtn.setVisible(false);
			airportManagmentBtn.setVisible(false);
			importStatusBtn.setVisible(false);
			exportFlightsBtn.setVisible(false);
		}
    }
    @FXML
    void flightManagment(ActionEvent event) throws IOException {
    	moveToPage("AddFlight");
    }	
    
    @FXML
    void airplaneManagment(ActionEvent event) throws IOException {
    	moveToPage("AddAirplane");
    }
    
    @FXML
    void airportManagment(ActionEvent event) throws IOException{
    	moveToPage("AddAirport");
    }
    
    @FXML
    void genrateBigFlightRepo(ActionEvent event) throws IOException{
    	moveToPage("BigFlightReport");
    }
    
    @FXML
    void employeeManagment(ActionEvent event) throws IOException{
    	moveToPage("AddEmployee");
    }
    
    @FXML
    void assignEmployees(ActionEvent event) throws IOException{
    	moveToPage("AssignEmployees");
    }
    
    @FXML
    void shiftManagment(ActionEvent event) throws IOException{
    	moveToPage("AddShift");
    }
    
    @FXML
    void importStatus(ActionEvent event) throws IOException{
    	moveToPage("ImportXml");
    }
    
    @FXML
    void exportFlights(ActionEvent event) throws IOException {
    	moveToPage("ExportJson");
    }
    
    @FXML
    void logout(ActionEvent event) throws IOException {
    	moveToPage("Login");
    }
    
    void moveToPage(String toMove) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/boundary/" + toMove + ".fxml"));
		AnchorPane pane = loader.load();
		mainScreen.getChildren().removeAll(mainScreen.getChildren());
		mainScreen.getChildren().add(pane);
    }

}