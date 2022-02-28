package control;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JsonArray;
import org.json.simple.JsonObject;
import org.json.simple.Jsoner;
import entity.Airport;
import entity.Flight;
import entity.SeatInAirplane;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import util.Consts;
import util.SeatType;

public class ExportControl {
	
	private static ExportControl instance;
	
	public static ExportControl getInstance() {
		if (instance == null)
			instance = new ExportControl();
		return instance;
	}
	
	public void exportToJSON() {
		List<Flight> flightsUpdatedToday = getFlightsUpdatedToday();
		JsonArray updatedFlights = new JsonArray();
		for (int i = 0; i < flightsUpdatedToday.size(); i++) {
			JsonObject updatedFlight = new JsonObject();
			updatedFlight.put("FlightNum", flightsUpdatedToday.get(i).getFlightID().toString()); 
			updatedFlight.put("DepartureTime", flightsUpdatedToday.get(i).getDepartureTime().toString());    
			updatedFlight.put("LandingTime", flightsUpdatedToday.get(i).getLandingTime().toString()); 
			updatedFlight.put("Status", flightsUpdatedToday.get(i).getStatus().toString());  
			updatedFlight.put("TailNumber", flightsUpdatedToday.get(i).getPlaneID().toString());  
			updatedFlight.put("DepartureAirportCode", flightsUpdatedToday.get(i).getOriginAirportID().getAirportID().toString());  
			updatedFlight.put("LandingAirportCode", flightsUpdatedToday.get(i).getDestinationAirportID().getAirportID().toString());
			updatedFlight.put("DepartureCity", flightsUpdatedToday.get(i).getOriginAirportID().getCity().toString());
			updatedFlight.put("DepartureCountry", flightsUpdatedToday.get(i).getOriginAirportID().getCountry().toString());
			updatedFlight.put("DestinationCity", flightsUpdatedToday.get(i).getDestinationAirportID().getCity().toString());
			updatedFlight.put("DestinationCountry", flightsUpdatedToday.get(i).getDestinationAirportID().getCountry().toString());
			
			JsonArray updatedSeats = new JsonArray();
			List<SeatInAirplane> seatsByFlight = getSeatsByFlight(flightsUpdatedToday.get(i).getFlightID());
			for(int j = 0; j < seatsByFlight.size(); j++) {
				JsonObject updatedSeat = new JsonObject();
				updatedSeat.put("Row", seatsByFlight.get(j).getRowNum());
				updatedSeat.put("Seat", seatsByFlight.get(j).getColNum());
				updatedSeat.put("Class", seatsByFlight.get(j).getSeatType());
				
				updatedSeats.add(updatedSeat);
			}
			updatedFlight.put("SeatsInFlight", updatedSeats);
			updatedFlights.add(updatedFlight);
		}
		JsonObject doc = new JsonObject();
		doc.put("flights", updatedFlights);

		File file = new File("json/flights.json");
		file.getParentFile().mkdir();

		try (FileWriter writer = new FileWriter(file)) {
			writer.write(Jsoner.prettyPrint(doc.toJson()));
			writer.flush();
			 Alert alert = new Alert(AlertType.INFORMATION, "flights data exported successfully!");
			 alert.setHeaderText("Success");
			 alert.setTitle("Success Data Export");
			 alert.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
		
		
	private List<SeatInAirplane> getSeatsByFlight(String flightID) {	
		List<SeatInAirplane> seatsList = new ArrayList<SeatInAirplane>();

		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
					CallableStatement stmt =  conn.prepareCall(util.Consts.SQL_SEATS_DETAILS)){
					stmt.setString(1, flightID);
					ResultSet rs = stmt.executeQuery(); {
						while (rs.next()) {
							int i = 1;
							seatsList.add(new SeatInAirplane(rs.getInt(i++), rs.getString(i++), SeatType.valueOf(rs.getString(i++))));
						}
					}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return seatsList;	
	}

	private List<Flight> getFlightsUpdatedToday() {
		List<Flight> flightsList = new ArrayList<Flight>();

		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(util.Consts.SQL_EXPORT_DATA);
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int i = 1;
					flightsList.add(new Flight(rs.getString(i++),rs.getTimestamp(i++), rs.getTimestamp(i++), rs.getString(i++),
							rs.getString(i++), new Airport(rs.getString(i++),rs.getString(i++),rs.getString(i++)), new Airport(rs.getString(i++),rs.getString(i++),rs.getString(i++))));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return flightsList;	
	}
}
