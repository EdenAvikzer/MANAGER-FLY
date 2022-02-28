package boundary;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import control.ControlFlights;
import entity.Airport;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;

public class AddAirport {
	
	@FXML
    private AnchorPane mainScreen;
	
	@FXML
    private TextField airportCodeField;

    @FXML
    private TextField cityField;

    @FXML
    private TextField countryField;

    @FXML
    private ComboBox<Integer> GMTCombo;

    @FXML
    private TableView<Airport> allAiprortView;

    @FXML
    private TableColumn<Airport, String> airportCodeCol;

    @FXML
    private TableColumn<Airport, String> cityCol;

    @FXML
    private TableColumn<Airport, String> countryCol;

    @FXML
    private TableColumn<Airport, Integer> GMTCol;
    
    private AddFlight flightManagment;
    
    List<Airport> allAirports = new ArrayList<Airport>();
    
    @FXML
    public void initialize() {
    	ArrayList<Integer> GMTList  = new ArrayList<Integer>();
		for(int i=-12;i<13;i++) {
			GMTList.add(i);
		}
		GMTCombo.setItems(FXCollections.observableArrayList(GMTList));
    	
    	
    	//view all airports
    	allAirports.addAll(ControlFlights.getInstance().getairports());
    	allAiprortView.getItems().addAll(allAirports);
    	
    	airportCodeCol.setCellValueFactory(airport -> new ReadOnlyObjectWrapper<String>(airport.getValue().getAirportID()));
    	
    	cityCol.setCellValueFactory(airport -> new ReadOnlyObjectWrapper<String>(airport.getValue().getCity()));
    	
    	countryCol.setCellValueFactory(airport -> new ReadOnlyObjectWrapper<String>(airport.getValue().getCountry()));
    	
    	GMTCol.setCellValueFactory(airport -> new ReadOnlyObjectWrapper<Integer>(airport.getValue().getTimeZone()));
    }
    
    @FXML
    void addAirport(ActionEvent event) {
    	try {
    		String airportCode = airportCodeField.getText();
        	String city = cityField.getText();
        	String country = countryField.getText();
        	Integer GMT = GMTCombo.getValue();
        	
        	if(airportCode != null && !city.isEmpty() && !country.isEmpty() && GMT != null) {
        		Alert alert = new Alert(AlertType.CONFIRMATION);
        		alert.setHeaderText("You are about to add airport in " + city + ", " + country + ".\n press ok to continue");
        		alert.setTitle("CONFIRMATION");
        		Optional<ButtonType> choose = alert.showAndWait();
        		
        		if(choose.get() == ButtonType.OK) {   	
        			Airport airport = new Airport(airportCode, country, city, GMT);
        			System.out.println("Airport created successfully");
            	
        			ControlFlights.getInstance().addAirport(airport);
            	    	
        			allAiprortView.getItems().clear();
        			allAiprortView.getItems().addAll(FXCollections.observableArrayList(ControlFlights.getInstance().getairports()));
        			
        			airportCodeField.clear();
        			cityField.clear();;
        			countryField.clear();
        			GMTCombo.getSelectionModel().clearSelection();
        			
        			if(flightManagment != null)
        				flightManagment.inflateUI(airport);
        		}
        	}
        	else {
        		Alert alert = new Alert(AlertType.ERROR);
        		alert.setHeaderText("You must fill all the fields");
        		alert.setTitle("Faild to add Airport");
        		alert.showAndWait();
        	}
    } catch(NumberFormatException e) {
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setHeaderText("You must fill all the fields");
    		alert.setTitle("Faild to add Airport");
    		alert.showAndWait();
}
    	
    	
    }
    
    public void setFlightController(AddFlight flightManagment) {
        this.flightManagment = flightManagment;
    }
    
    @FXML
    void returnToMenu(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/boundary/MenuScreen.fxml"));
		AnchorPane pane = loader.load();
		mainScreen.getChildren().removeAll(mainScreen.getChildren());
		mainScreen.getChildren().add(pane);
    }

}
