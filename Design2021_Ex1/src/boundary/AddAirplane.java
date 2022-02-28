package boundary;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import control.ControlFlights;
import entity.Airplane;
import entity.SeatInAirplane;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;


public class AddAirplane {
	
	@FXML
    private AnchorPane mainScreen ;

	
	@FXML
    private Button returnButton;

    @FXML
    private TextField tailNumberField;

    @FXML
    private TextField flightAttendNumberField;

    @FXML
    private TableView<Airplane> allAiprlaneView;

    @FXML
    private TableColumn<Airplane, String> tailNumCol;

    @FXML
    private TableColumn<Airplane, Integer> NumOfFlightAttenCol;
    
    @FXML
    private TextField seatsInRowField;
    
    @FXML
    private TextField firstClassField;
    
    @FXML
    private TextField buisnessClassField;
    
    @FXML
    private TextField touristCladdField;
    
    @FXML 
    private TableView<SeatInAirplane> seatsByAirplane;
    
    @FXML
    private TableColumn<SeatInAirplane, Integer> seatIDCol;
    
    @FXML
    private TableColumn<SeatInAirplane, Integer> rowCol;
    
    @FXML
    private TableColumn<SeatInAirplane, String> seatCol;
    
    @FXML
    private TableColumn<SeatInAirplane, String> classCol;
    @FXML
    private Text seatDetailsText;
    
    private AddFlight flightManagment;
    
    List<Airplane> allAirplanes = new ArrayList<Airplane>();
    
    @FXML
	public void initialize() {
    	
    	allAirplanes = ControlFlights.getInstance().getairplanes();
    	allAiprlaneView.getItems().addAll(allAirplanes);
    	
    	tailNumCol.setCellValueFactory(airplane -> new ReadOnlyObjectWrapper<String>(airplane.getValue().getTailNumber()));
    	NumOfFlightAttenCol.setCellValueFactory(airplane -> new ReadOnlyObjectWrapper<Integer>(airplane.getValue().getNumberOfFlightAttendants()));
    	
    	allAiprlaneView.setOnMouseClicked(event -> {
    		seatsByAirplane.getItems().clear();
    		Airplane airplane = allAiprlaneView.getSelectionModel().getSelectedItem();
    		if(airplane != null) {
    			List<SeatInAirplane> seats = ControlFlights.getInstance().getseatsInAirplane(airplane.getTailNumber());
    			seatsByAirplane.getItems().addAll(seats);
    			seatIDCol.setCellValueFactory(seat -> new ReadOnlyObjectWrapper<Integer>(seat.getValue().getId()));
    			rowCol.setCellValueFactory(seat -> new ReadOnlyObjectWrapper<Integer>(seat.getValue().getRowNum()));
    			seatCol.setCellValueFactory(seat -> new ReadOnlyObjectWrapper<String>(seat.getValue().getColNum()));
    			classCol.setCellValueFactory(seat -> new ReadOnlyObjectWrapper<String>(seat.getValue().getSeatType().toString()));
    			seatDetailsText.setText("Seats details for plane: " + airplane.getTailNumber());
    		}
    	});
    	
    }
    public void setFlightController(AddFlight flightManagment) {
        this.flightManagment = flightManagment;
    }
    
    @FXML
    void addAirplane(ActionEvent event) {
    	
		try {
			String tailNum = tailNumberField.getText();
			Integer amountOfFllightAttend = Integer.parseInt(flightAttendNumberField.getText());
			int seatsInRow = Integer.parseInt(seatsInRowField.getText());
			int firstClassRows = Integer.parseInt(firstClassField.getText());
			int buisnessClassRows = Integer.parseInt(buisnessClassField.getText());
			int touristClassRows = Integer.parseInt(touristCladdField.getText());  
    	
			if(!tailNum.isEmpty()) {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setHeaderText("You are about to add plane " + tailNum + ".\n press ok to continue");
				alert.setTitle("CONFIRMATION");
				Optional<ButtonType> choose = alert.showAndWait();
			
				if(choose.get() == ButtonType.OK) {
	    	
					Airplane airplane = new Airplane(tailNum, amountOfFllightAttend);
					System.out.println("Airplane created successfully");
					ControlFlights.getInstance().addAirplane(airplane);
	    	
					allAiprlaneView.getItems().clear();
					allAiprlaneView.getItems().addAll(FXCollections.observableArrayList(ControlFlights.getInstance().getairplanes()));
					tailNumberField.setText("");
					flightAttendNumberField.setText("");
	 	
					char seatNum = 'A';
					for(int i = 1; i <= seatsInRow; i++) {
						for(int j = 1; j <= firstClassRows; j++) {
							SeatInAirplane seat = new SeatInAirplane(j, String.valueOf(seatNum), "firstClass", tailNum);
							ControlFlights.getInstance().addSeat(seat);
						}
						for(int k = firstClassRows + 1; k <= buisnessClassRows + firstClassRows; k++) {
							SeatInAirplane seat = new SeatInAirplane(k, String.valueOf(seatNum), "buisnessClass", tailNum);
							ControlFlights.getInstance().addSeat(seat);
						}
						for(int r = buisnessClassRows + firstClassRows + 1; r <= buisnessClassRows + firstClassRows + touristClassRows; r++) {
							SeatInAirplane seat = new SeatInAirplane(r, String.valueOf(seatNum), "touristClass", tailNum);
							ControlFlights.getInstance().addSeat(seat);
						}
						seatNum++;
					}
					seatsInRowField.clear();
					firstClassField.clear();
					buisnessClassField.clear();
					touristCladdField.clear();
					
					if(flightManagment != null)
						flightManagment.inflateUI(airplane);
				}
			}
			else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setHeaderText("You must fill all the fields");
				alert.setTitle("failed to add Airplane");
				alert.showAndWait();
			}
		
			} catch(NumberFormatException e) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setHeaderText("You must fill all the fields");
					alert.setTitle("failed to add Airplane");
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
