package boundary;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import control.ControlShibutz;
import entity.Employee;
import entity.FlightAttendant;
import entity.GroundAttendant;
import entity.Pilot;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class AddEmployee {
	@FXML
    private AnchorPane mainScreen;
	@FXML
	private ComboBox<String> roleCombo;
	@FXML
    private AnchorPane fieldsPane;
	@FXML
    private TextField employeeIDField;
    @FXML
    private TextField employeeFNameField;
    @FXML
    private TextField employeeLNameField;
    @FXML
    private VBox pilotVBox;
    @FXML
    private TextField pilotLicenseNumField;
    @FXML
    private DatePicker pilotLicenseStartDate;
    @FXML
    private TableView<Employee> allRoleView;
    @FXML
    private TableColumn<Employee, Integer> IDCol;

    @FXML
    private TableColumn<Employee, String> fNameCol;

    @FXML
    private TableColumn<Employee, String> lNameCol;

    @FXML
    private TableColumn<Pilot, Integer> licenseNumCol;

    @FXML
    private TableColumn<Pilot, LocalDate> licenseDateCol;

	
	@FXML
	void initialize() {
		fieldsPane.setVisible(false);
		roleCombo.getItems().add("Pilot");
		roleCombo.getItems().add("Flight Attendant");
		roleCombo.getItems().add("Ground Attendant");
		
		roleCombo.setOnAction(event -> {
			allRoleView.getItems().clear();
			addToTable();
			fieldsPane.setVisible(true);
			if(roleCombo.getValue().equals("Pilot"))
				pilotVBox.setVisible(true);
			else {
				pilotVBox.setVisible(false);
			}
		});
	}
	
	 @FXML
	 void addEmployee(ActionEvent event) {
		 try {
			 String role = roleCombo.getValue();
			 Integer employeeID = Integer.parseInt(employeeIDField.getText());
			 String fName = employeeFNameField.getText();
			 String lName = employeeLNameField.getText();
			 if(employeeID != null && !fName.isEmpty() && !lName.isEmpty()) {
				 if(role.equals("Pilot")) {
					 Integer licenseNum = Integer.parseInt(pilotLicenseNumField.getText());
					 Date licenseStart = Date.valueOf(pilotLicenseStartDate.getValue());
					 if(licenseNum != null && licenseStart != null) {
						 Pilot pilot = new Pilot(employeeID, fName, lName, Date.valueOf(LocalDate.now()), licenseNum, licenseStart);
						 ControlShibutz.getInstance().addPilot(pilot);
					 }
					 else {
						 Alert alert = new Alert(AlertType.ERROR);
							alert.setHeaderText("You must fill all the fields");
							alert.setTitle("failed to add Employee");
							alert.showAndWait();
					 }
				 }
				else if(role.equals("Flight Attendant")) {
					FlightAttendant fa = new FlightAttendant(employeeID, fName, lName, Date.valueOf(LocalDate.now()));	
					ControlShibutz.getInstance().addFlightAttendant(fa);
				}
				else {
					GroundAttendant ga = new GroundAttendant(employeeID, fName, lName, Date.valueOf(LocalDate.now()));	
					ControlShibutz.getInstance().addGroundAttendant(ga);	
				}
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setHeaderText(roleCombo.getValue() + " " + fName + " " + lName + " added successfully!");
				alert.setTitle("New employee added");
				alert.showAndWait();
				clearFields();
				allRoleView.getItems().clear();
				addToTable();
			 }
			 else {
				 Alert alert = new Alert(AlertType.ERROR);
					alert.setHeaderText("You must fill all the fields");
					alert.setTitle("failed to add Employee");
					alert.showAndWait();
			 }			
		 } catch(NumberFormatException e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setHeaderText("You must fill fill numbers in the id field");
				alert.setTitle("failed to add Airplane");
				alert.showAndWait();
		}
		
	 }

	private void addToTable() {
		if(roleCombo.getValue().equals("Flight Attendant")) {
			allRoleView.getItems().addAll(FXCollections.observableArrayList(ControlShibutz.getInstance().getAllFA()));
			licenseNumCol.setVisible(false);
			licenseDateCol.setVisible(false);
		}
		else if (roleCombo.getValue().equals("Ground Attendant")) {
			allRoleView.getItems().addAll(FXCollections.observableArrayList(ControlShibutz.getInstance().getAllGA()));
			licenseNumCol.setVisible(false);
			licenseDateCol.setVisible(false);
		}
		else {
			licenseNumCol.setVisible(true);
			licenseDateCol.setVisible(true);
			allRoleView.getItems().addAll(FXCollections.observableArrayList(ControlShibutz.getInstance().getAllPilots()));
			licenseNumCol.setCellValueFactory(p -> new ReadOnlyObjectWrapper<Integer>(p.getValue().getLicenceID()));
			licenseDateCol.setCellValueFactory(p -> new ReadOnlyObjectWrapper<LocalDate>(p.getValue().getLicenceStartDate().toLocalDate()));
		}
		IDCol.setCellValueFactory(a -> new ReadOnlyObjectWrapper<Integer>(a.getValue().getEmployeeID()));
		fNameCol.setCellValueFactory(a -> new ReadOnlyObjectWrapper<String>(a.getValue().getFirstName()));
		lNameCol.setCellValueFactory(a -> new ReadOnlyObjectWrapper<String>(a.getValue().getLastName()));
		
	}
	
	@FXML
    void endContract(ActionEvent event) {
		Employee employee = allRoleView.getSelectionModel().getSelectedItem();
		if(employee != null) {
			if(roleCombo.getValue().equals("Pilot")) 
				ControlShibutz.getInstance().updatePilotEndDate(employee);
			else if(roleCombo.getValue().equals("Flight Attendant"))
				ControlShibutz.getInstance().updateFAEndDate(employee);
			else
				ControlShibutz.getInstance().updateGAEndDate(employee);
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setHeaderText(roleCombo.getValue() + " " + employee.getFirstName() + " " + employee.getLastName() + " contract end date updated to: " + LocalDate.now());
			alert.setTitle("Contract end");
			alert.showAndWait();
			allRoleView.getSelectionModel().clearSelection();
		}
    }

	private void clearFields() {
		employeeIDField.clear();
		employeeFNameField.clear();
		employeeLNameField.clear();
		pilotLicenseNumField.clear();
		pilotLicenseStartDate.setValue(null);
	}
	
	
	@FXML
    void returnToMenu(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/boundary/MenuScreen.fxml"));
		AnchorPane pane = loader.load();
		mainScreen.getChildren().removeAll(mainScreen.getChildren());
		mainScreen.getChildren().add(pane);
    }

}
