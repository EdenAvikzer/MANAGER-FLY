package boundary;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

import control.ControlShibutz;
import entity.GroundAttendant;
import entity.GroundAttendantInShift;
import entity.Shift;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import util.ShiftRole;

public class AddShift {
	@FXML
	private AnchorPane mainScreen;
	
	@FXML
    private ChoiceBox<Integer> startHour;

    @FXML
    private ChoiceBox<Integer> startMinute;

    @FXML
    private ChoiceBox<Integer> endHour;

    @FXML
    private ChoiceBox<Integer> endMinute;
    
    @FXML
    private DatePicker startDate;
    
    @FXML
    private DatePicker endDate;
    
    @FXML
    private ChoiceBox<GroundAttendant> gaCombo;

    @FXML
    private ChoiceBox<ShiftRole> roleCombo;
    
    @FXML
    private TableView<GroundAttendantInShift> allGAInShiftView;

    @FXML
    private TableColumn<GroundAttendantInShift, LocalDateTime> startCol;

    @FXML
    private TableColumn<GroundAttendantInShift, LocalDateTime> endCol;

    @FXML
    private TableColumn<GroundAttendantInShift, GroundAttendant> gaCol;

    @FXML
    private TableColumn<GroundAttendantInShift, ShiftRole> roleCol;
	
	@FXML
	void initialize() {
		ArrayList<Integer> hoursList  = new ArrayList<>();
		ArrayList<Integer> minuteList  = new ArrayList<>();
		for(int i=0;i<24;i++) {
			hoursList.add(i);
		}
		startHour.setItems(FXCollections.observableArrayList(hoursList));	
		endHour.setItems(FXCollections.observableArrayList(hoursList));	
		for(int i=0;i<60;i++) {
			minuteList.add(i);
		}
		startMinute.setItems(FXCollections.observableArrayList(minuteList));
		endMinute.setItems(FXCollections.observableArrayList(minuteList));
		
		roleCombo.setItems(FXCollections.observableArrayList(ShiftRole.values()));
		gaCombo.setItems(FXCollections.observableArrayList(ControlShibutz.getInstance().getAllGA()));		
		
		allGAInShiftView.getItems().addAll(FXCollections.observableArrayList(ControlShibutz.getInstance().getAllGAInShift()));
		startCol.setCellValueFactory(gaShift -> new ReadOnlyObjectWrapper<LocalDateTime>(gaShift.getValue().getShift().getStartShiftTime().toLocalDateTime()));
		endCol.setCellValueFactory(gaShift -> new ReadOnlyObjectWrapper<LocalDateTime>(gaShift.getValue().getShift().getEndShiftTime().toLocalDateTime()));
		gaCol.setCellValueFactory(gaShift -> new ReadOnlyObjectWrapper<GroundAttendant>(gaShift.getValue().getGa()));
		roleCol.setCellValueFactory(gaShift -> new ReadOnlyObjectWrapper<ShiftRole>(gaShift.getValue().getRole()));
	}
	
	@FXML
	void addShift(ActionEvent event) {
		if(startDate != null && startHour.getValue() != null && startMinute.getValue() != null && endDate.getValue() != null && endHour.getValue() != null && endMinute.getValue() != null && gaCombo.getValue() != null) {
			LocalDateTime startTime = LocalDateTime.of(startDate.getValue().getYear(), startDate.getValue().getMonth(), startDate.getValue().getDayOfMonth(), startHour.getValue(), startMinute.getValue());		
			LocalDateTime endTime = LocalDateTime.of(endDate.getValue().getYear(), endDate.getValue().getMonth(), endDate.getValue().getDayOfMonth(), endHour.getValue(), endMinute.getValue());
			Timestamp start = Timestamp.valueOf(startTime);
			Timestamp end = Timestamp.valueOf(endTime);
			if(start.before(end)) {
				Shift shift = new Shift(start, end);
				GroundAttendant ga = gaCombo.getValue();
				ControlShibutz.getInstance().addGAInShift(shift, ga.getEmployeeID(), roleCombo.getValue().toString());
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setHeaderText("Ground Attendant added successfully to shift");
				alert.setTitle("New ga added");
				alert.showAndWait();
				clearFields();
				initialize();
			}	
			else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setHeaderText("start time after end time!!");
				alert.setTitle("failed to add GA to Shift");
				alert.showAndWait();
			}		
		}
		else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("You must fill all the fields");
			alert.setTitle("failed to add GA to Shift");
			alert.showAndWait();
		}		
	}

	private void clearFields() {
		startDate.setValue(null);
		startHour.getSelectionModel().clearSelection();
		startMinute.getSelectionModel().clearSelection();
		endDate.setValue(null);
		endHour.getSelectionModel().clearSelection();
		endMinute.getSelectionModel().clearSelection();
		gaCombo.getSelectionModel().clearSelection();
		roleCombo.getSelectionModel().clearSelection();		
	}
	
	@FXML
    void returnToMenu(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/boundary/MenuScreen.fxml"));
		AnchorPane pane = loader.load();
		mainScreen.getChildren().removeAll(mainScreen.getChildren());
		mainScreen.getChildren().add(pane);
    }

}
