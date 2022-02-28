package boundary;


import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import control.ControlFlights;
import entity.Airplane;
import entity.Airport;
import entity.Flight;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import util.FlightStatus;

public class AddFlight {
	
	@FXML
    private AnchorPane mainScreen;
	
	@FXML
    private Button returnButton;

    @FXML
    private TextField flightNumberField;

    @FXML
    private DatePicker depDate;

    @FXML
    private DatePicker landDate;

    @FXML
    private Button addFlightButton;
    
    @FXML
    private ImageView checker;

    @FXML
    private ChoiceBox<Airport> origin;

    @FXML
    private ChoiceBox<Airport> destination;

    @FXML
    private Label errorLabel;
    
    @FXML
    private ChoiceBox<Integer> depHour;
    
    @FXML
    private ChoiceBox<Integer> depMinutes;
    
    @FXML
    private ChoiceBox<Integer> landHour;
    
    @FXML
    private ChoiceBox<Integer> landMinutes;
    @FXML
    private ChoiceBox<Airplane> choosePlaneChoise;
    
    @FXML
	private TableView<Flight> allFlightsTable;
	@FXML
	private TableColumn<Flight, String> flightIDCol;
	@FXML
	private TableColumn<Flight, LocalDateTime> departureCol;
	@FXML
	private TableColumn<Flight, LocalDateTime> landingCol;
	@FXML
	private TableColumn<Flight, String> fromCol;
	@FXML
	private TableColumn<Flight, String> toCol;
	@FXML
	private TableColumn<Flight, FlightStatus> statusCol;
	@FXML
	private TableColumn<Flight, String> airplaneIDCol;

	List<Flight> allFlights = new ArrayList<Flight>();



	@FXML
	public void initialize() 
	{
    	//set the time field
    	ArrayList<Integer> hoursList  = new ArrayList<>();
		ArrayList<Integer> minuteList  = new ArrayList<>();
		for(int i=0;i<24;i++) {
			hoursList.add(i);
		}
		depHour.setItems(FXCollections.observableArrayList(hoursList));	
		landHour.setItems(FXCollections.observableArrayList(hoursList));	
		for(int i=0;i<60;i++) {
			minuteList.add(i);
		}
		depMinutes.setItems(FXCollections.observableArrayList(minuteList));
		landMinutes.setItems(FXCollections.observableArrayList(minuteList));
		
		//You can book a flight for at least another two months
    	depDate.setDayCellFactory(picker -> new DateCell() {
			public void updateItem(LocalDate date, boolean empty) {
				super.updateItem(date, empty);
				LocalDate today = LocalDate.now();
				LocalDate twoMoreMonth = today.plus(2,ChronoUnit.MONTHS);
	            LocalDate maxDate = LocalDate.of(2030, 1, 1);
	            setDisable(date.compareTo(maxDate)>0 || date.compareTo(twoMoreMonth) < 0 );
			}
		});
    	depDate.setEditable(false);
    	
    	//You can book a flight for at least another two months
    	landDate.setDayCellFactory(picker -> new DateCell() {
			public void updateItem(LocalDate date, boolean empty) {
				super.updateItem(date, empty);
				LocalDate today = LocalDate.now();
				LocalDate twoMoreMonth = today.plus(2,ChronoUnit.MONTHS);
	            LocalDate maxDate = LocalDate.of(2030, 1, 1);
	            setDisable(date.compareTo(maxDate)>0 || date.compareTo(twoMoreMonth) < 0 );
			}
		});
    	landDate.setEditable(false);
    	//set planes list
    	choosePlaneChoise.setItems(FXCollections.observableArrayList(ControlFlights.getInstance().getairplanes()));
    	
    	//set airports list
    	origin.setItems(FXCollections.observableArrayList(ControlFlights.getInstance().getairports()));
    	destination.setItems(FXCollections.observableArrayList(ControlFlights.getInstance().getairports()));
    	
    	//The user can not select the same origin and destination airport
    	origin.setOnAction(event -> {
    		if (origin.getValue() != null) {
					ObservableList<Airport> airportList = FXCollections.observableArrayList(ControlFlights.getInstance().getairports());
			    	airportList.remove(origin.getValue());
			    	destination.setItems(airportList);
				}
    	});
    	
    	
    	//fill table
		allFlights = ControlFlights.getInstance().getFlights();
		
		allFlightsTable.getItems().addAll(allFlights);
		
		flightIDCol.setCellValueFactory(flight -> new ReadOnlyObjectWrapper<String>(flight.getValue().getFlightID()));
		departureCol.setCellValueFactory(flight -> new ReadOnlyObjectWrapper<LocalDateTime>(flight.getValue().getDepartureTime().toLocalDateTime()));
		landingCol.setCellValueFactory(flight -> new ReadOnlyObjectWrapper<LocalDateTime>(flight.getValue().getLandingTime().toLocalDateTime()));	
		fromCol.setCellValueFactory(flight -> {
			Airport origin = ControlFlights.getInstance().getAirportByID(flight.getValue().getOriginAirportID().getAirportID());
			String originAirport = origin.getCity() + ", " + origin.getCountry();
			return new ReadOnlyStringWrapper(originAirport);
		});
		
		toCol.setCellValueFactory(flight -> {
			Airport destination = ControlFlights.getInstance().getAirportByID(flight.getValue().getDestinationAirportID().getAirportID());
			String destinationAirport = destination.getCity() + ", " + destination.getCountry();
			return new ReadOnlyStringWrapper(destinationAirport);
		});
		
		statusCol.setCellValueFactory(flight -> new ReadOnlyObjectWrapper<FlightStatus>(flight.getValue().getStatus()));
		
		airplaneIDCol.setCellValueFactory(flight -> new ReadOnlyObjectWrapper<String>(flight.getValue().getPlaneID()));
    	

	}

    
    
    @FXML
    void addNewFlight(ActionEvent event) {
    	
    	try {
        	String flightNum = flightNumberField.getText();
        	Date dp = Date.valueOf(depDate.getValue());
        	Date ld = Date.valueOf(landDate.getValue());
        	
        	LocalDateTime departureTime = LocalDateTime.of(depDate.getValue().getYear(), depDate.getValue().getMonth(), depDate.getValue().getDayOfMonth(), depHour.getValue(), depMinutes.getValue());		
    		LocalDateTime landingTime = LocalDateTime.of(landDate.getValue().getYear(), landDate.getValue().getMonth(), landDate.getValue().getDayOfMonth(), landHour.getValue(), landMinutes.getValue());
    		
    		Timestamp departure = Timestamp.valueOf(departureTime);
    		Timestamp landing = Timestamp.valueOf(landingTime);
        	
        	
//        	Timestamp ts = new Timestamp(dp.getTime());
//        	Timestamp ts1 = new Timestamp(ld.getTime());
//
//        	ts.setHours(depHour.getValue());
//        	ts.setMinutes(depMinutes.getValue());
//        	ts1.setHours(landHour.getValue());
//        	ts1.setMinutes(landMinutes.getValue());
        	
        	Airport originAirport = origin.getValue();
        	Airport destinationAirport = destination.getValue();
        	
        	Airplane plane = choosePlaneChoise.getValue();
        	
        	if(!flightNum.isEmpty() && dp != null && ld != null && originAirport != null && destinationAirport != null && plane != null) {
            	Flight flight = new Flight(flightNum,departure,landing,FlightStatus.onTime.toString(), origin.getValue().getAirportID(), destination.getValue().getAirportID(),
            			null, null, choosePlaneChoise.getValue().getTailNumber());
            	
            	Alert confirm = new Alert(AlertType.CONFIRMATION);
            	confirm.setHeaderText("You are about to add flight " + flightNum + ".\n press ok to continue");
            	confirm.setTitle("CONFIRMATION");
    			Optional<ButtonType> choose = confirm.showAndWait();
    			
       			if(choose.get() == ButtonType.OK) { 
          	      	
       				String result = ControlFlights.getInstance().addFlight(flight);
            	
       				if(result.equals("plane")) { //if the chosen plane is not avilable
       					Alert alert = new Alert(AlertType.ERROR);
       					alert.setHeaderText("The plane " + choosePlaneChoise.getValue().getTailNumber() + " is not avilable in this day");
       					alert.setTitle("Failed Creating New Flight");
       					alert.showAndWait();
       				}
       				else if(result.equals("origin")) { //if the origin airport is not ok
       					Alert alert = new Alert(AlertType.ERROR);
       					alert.setHeaderText("There is another flight that depart from  airport " + originAirport.getCity() + ", " + originAirport.getCountry() + 
       							" within less than 30 minutes different");
       					alert.setTitle("Failed Creating New Flight");
       					alert.showAndWait();
       				}       	
       				else if(result.equals("dest")) { //if the destination airport is not ok
       					Alert alert = new Alert(AlertType.ERROR);
       					alert.setHeaderText("There is another flight that land in  airport " + destinationAirport.getCity() + ", " + destinationAirport.getCountry() + 
       							" within less than 30 minutes different");
       					alert.setTitle("Failed Creating New Flight");
       					alert.showAndWait();
       				}
       				else if(result.equals("error")) {
       					Alert alert = new Alert(AlertType.ERROR);
       					alert.setHeaderText("an error occured, please try again");
       					alert.setTitle("Failed Creating New Flight");
       					alert.showAndWait();
       				}
       				else { //flight added successfully		   	
       					Alert alert = new Alert(AlertType.INFORMATION);
       					alert.setHeaderText("Flight added successfully");
       					alert.setTitle("New Flight added");
       					alert.showAndWait();
       					flightNumberField.clear();
       					depDate.setValue(null);
       					landDate.setValue(null);
       					depHour.getSelectionModel().clearSelection();
       					depMinutes.getSelectionModel().clearSelection();
       					landHour.getSelectionModel().clearSelection();
       					landMinutes.getSelectionModel().clearSelection();
       					origin.getSelectionModel().clearSelection();
       					destination.getSelectionModel().clearSelection();
       					choosePlaneChoise.getSelectionModel().clearSelection();
       					checker.setVisible(false);
       				}
       			}
        	}
        	
        	else {
        		Alert alert = new Alert(AlertType.ERROR);
        		alert.setHeaderText("an error occured, please try again");
        		alert.setTitle("Failed Creating New Flight");
        		alert.showAndWait();
        	}
            	
            allFlightsTable.getItems().clear();
            allFlightsTable.getItems().addAll(FXCollections.observableArrayList(ControlFlights.getInstance().getFlights()));
    	} catch(NullPointerException e) {
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setHeaderText("an error occured, please try again");
    		alert.setTitle("Failed Creating New Flight");
    		alert.showAndWait();
    	}

    }
    

    @FXML
    void checkDate(ActionEvent event) {
    	Timestamp ts = null;
    	Timestamp ts1 = null;
    	
    	if (depDate.getValue() != null && landDate.getValue() != null
    			 && depHour.getValue() != null   && depMinutes.getValue() != null && 
    			 landHour.getValue() != null && landMinutes.getValue() != null) {
    		
    		Date dp = Date.valueOf(depDate.getValue());
        	Date ld = Date.valueOf(landDate.getValue());
        	
        	ts = new Timestamp(dp.getTime());
        	ts1 = new Timestamp(ld.getTime());
        	
        	ts.setHours(depHour.getValue());
        	ts.setMinutes(depMinutes.getValue());
        	ts1.setHours(landHour.getValue());
        	ts1.setMinutes(landMinutes.getValue());
    		
    		//Check that the departure time is before the landing time
    		if (ts.after(ts1)) {
        		depDate.setValue(null);
        		landDate.setValue(null);
        		errorLabel.setText("Invalid dates, please try again");
        		errorLabel.setVisible(true);
        		checker.setVisible(false);
        		depHour.setValue(0);
        		depMinutes.setValue(0);
        		landHour.setValue(0);
        		landMinutes.setValue(0);
        	}
        	else {

        		checker.setVisible(true);
        		errorLabel.setText("");
        		origin.setDisable(false);
        		destination.setDisable(false);
        		choosePlaneChoise.setDisable(false);
        	}
    	}
    	else  {
    		depDate.setValue(null);
    		landDate.setValue(null);
    		depHour.setValue(0);
    		depMinutes.setValue(0);
    		landHour.setValue(0);
    		landMinutes.setValue(0);
    	}	
    	
    }
    
    @FXML
    void returnToMenu(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/boundary/MenuScreen.fxml"));
		AnchorPane pane = loader.load();
		mainScreen.getChildren().removeAll(mainScreen.getChildren());
		mainScreen.getChildren().add(pane);
    }
    
    public void moveToAddPlane(ActionEvent event) throws Exception {   
        FXMLLoader fXMLLoader = new FXMLLoader(getClass().getResource("/boundary/AddAirplane.fxml"));
        Parent parent = fXMLLoader.load();
        AddAirplane controller = fXMLLoader.getController();
        controller.setFlightController(this); // Pass this controller to NewCustomerController
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    	
    }
    
    @FXML
    public void moveToAddAirport(ActionEvent event) throws Exception {
    	FXMLLoader fXMLLoader = new FXMLLoader(getClass().getResource("/boundary/AddAirport.fxml"));
        Parent parent = fXMLLoader.load();
        AddAirport controller = fXMLLoader.getController();
        controller.setFlightController(this); // Pass this controller to NewCustomerController
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }
    
    public void inflateUI(Airplane airplane) {
        choosePlaneChoise.setItems(FXCollections.observableArrayList(ControlFlights.getInstance().getairplanes()));
    }
    public void inflateUI(Airport airport) {
        origin.setItems(FXCollections.observableArrayList(ControlFlights.getInstance().getairports()));
        destination.setItems(FXCollections.observableArrayList(ControlFlights.getInstance().getairports()));
    }

    
}
    
