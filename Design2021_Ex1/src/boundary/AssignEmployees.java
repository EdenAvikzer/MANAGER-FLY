package boundary;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import control.ControlShibutz;
import entity.Flight;
import entity.FlightAttendant;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import entity.Pilot;

public class AssignEmployees {
	@FXML
	private AnchorPane mainScreen;
	@FXML
    private ComboBox<Flight> flightsCombo;
	@FXML
    private TableView<Pilot> pilotTable;

    @FXML
    private TableColumn<Pilot, Integer> pilotID;

    @FXML
    private TableColumn<Pilot, String> pilotFName;

    @FXML
    private TableColumn<Pilot, String> pilotLName;

    @FXML
    private TableView<FlightAttendant> flightAttendantTable;

    @FXML
    private TableColumn<FlightAttendant, Integer> attendandID;

    @FXML
    private TableColumn<FlightAttendant, String> attendandFName;

    @FXML
    private TableColumn<FlightAttendant, String> attendandLName;
    @FXML
    private ComboBox<Pilot> mainPilotCombo;

    @FXML
    private ComboBox<Pilot> secondaryPilotCombo;
    
    @FXML
    private Label attendantLbl;
    
    @FXML
    private TextField searchfaField;

    @FXML
    private TextField searchPilotField;
    
    private HashMap<String, List<FlightAttendant>> faForFlight;

	
	@FXML
	void initialize() {
		flightsCombo.setItems(FXCollections.observableArrayList(ControlShibutz.getInstance().getFutureFlights()));
		faForFlight = ControlShibutz.getInstance().getFAForFlight();
		flightsCombo.setOnAction(event -> {
			Flight flight = flightsCombo.getValue();
			mainPilotCombo.getSelectionModel().clearSelection();
			secondaryPilotCombo.getSelectionModel().clearSelection();
			pilotTable.getItems().clear();
			flightAttendantTable.getItems().clear();
			if(flight != null) {
				List<Pilot> avilablePilots = ControlShibutz.getInstance().getAvilablePilots(Date.valueOf(flight.getDepartureTime().toLocalDateTime().toLocalDate()),
						Date.valueOf(flight.getLandingTime().toLocalDateTime().toLocalDate()), flight.getFlightID());
				setPilots(flight, avilablePilots);
				List<FlightAttendant> avilableFA = ControlShibutz.getInstance().getAvilableFA(Date.valueOf(flight.getDepartureTime().toLocalDateTime().toLocalDate()),
						Date.valueOf(flight.getLandingTime().toLocalDateTime().toLocalDate()), flight.getFlightID());
				setFA(flight, avilableFA);
			}
		});
	}
	

	private void setFA(Flight flight, List<FlightAttendant> avilableFA) {
		flightAttendantTable.getItems().addAll(avilableFA);
		attendandID.setCellValueFactory(fa -> new ReadOnlyObjectWrapper<Integer>(fa.getValue().getEmployeeID()));
		attendandFName.setCellValueFactory(fa -> new ReadOnlyObjectWrapper<String>(fa.getValue().getFirstName()));
		attendandLName.setCellValueFactory(fa -> new ReadOnlyObjectWrapper<String>(fa.getValue().getLastName()));
		
		flightAttendantTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		int numOfFA = ControlShibutz.getInstance().getNumOfFAByPlane(flight.getPlaneID());
		attendantLbl.setText("choose " + numOfFA + " flight attendants");
		
		for(FlightAttendant fa: faForFlight.get(flight.getFlightID())) {
			flightAttendantTable.getSelectionModel().select(fa);
		}
	}

	private void setPilots(Flight flight, List<Pilot> avilablePilots) {
		pilotTable.getItems().addAll(avilablePilots);
		pilotID.setCellValueFactory(pilot -> new ReadOnlyObjectWrapper<Integer>(pilot.getValue().getEmployeeID()));
		pilotFName.setCellValueFactory(pilot -> new ReadOnlyObjectWrapper<String>(pilot.getValue().getFirstName()));
		pilotLName.setCellValueFactory(pilot -> new ReadOnlyObjectWrapper<String>(pilot.getValue().getLastName()));
		
		mainPilotCombo.setItems(FXCollections.observableArrayList(avilablePilots));
		Pilot main = ControlShibutz.getInstance().getRealPilotByID(flight.getMainPilotID());
		if(main != null)
			mainPilotCombo.getSelectionModel().select(main);
		secondaryPilotCombo.setItems(FXCollections.observableArrayList(avilablePilots));
		Pilot secondary = ControlShibutz.getInstance().getRealPilotByID(flight.getSeconsaryPilotID());
		if(secondary != null)
			secondaryPilotCombo.getSelectionModel().select(secondary);
	}
	
	@FXML
	void saveBtn(ActionEvent event) {
		Pilot main = mainPilotCombo.getValue();
		Pilot secondary = secondaryPilotCombo.getValue();
		if(main != null) 
			ControlShibutz.getInstance().addMainPilot(flightsCombo.getValue(), main);
		if(secondary != null)
			ControlShibutz.getInstance().addSecondaryPilot(flightsCombo.getValue(), secondary);
		ArrayList<FlightAttendant> changedFAs = new ArrayList<FlightAttendant>();
		changedFAs.addAll(flightAttendantTable.getSelectionModel().getSelectedItems());
		List<FlightAttendant> recent = faForFlight.get(flightsCombo.getValue().getFlightID());
		for(FlightAttendant fa: recent) {
			if(!changedFAs.contains(fa))
				ControlShibutz.getInstance().removeFAFromFlight(flightsCombo.getValue(), fa);
		}
		for(FlightAttendant fa: changedFAs) {
			if(!recent.contains(fa))
				ControlShibutz.getInstance().addFAToFlight(flightsCombo.getValue(), fa);
		}
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Employees added successfully to flight " + flightsCombo.getValue().getFlightID());
		alert.setHeaderText(main + " " + secondary + " " + changedFAs + " was successfuly added");
		alert.showAndWait();
		clearFields();
	}
	
	 private void clearFields() {
		flightsCombo.getSelectionModel().clearSelection();		
	}


	@FXML
	void returnToMenu(ActionEvent event) throws IOException {
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("/boundary/MenuScreen.fxml"));
		AnchorPane pane = loader.load();
		mainScreen.getChildren().removeAll(mainScreen.getChildren());
		mainScreen.getChildren().add(pane);
	}
	
}
