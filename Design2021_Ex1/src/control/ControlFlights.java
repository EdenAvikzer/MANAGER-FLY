package control;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import entity.Airplane;
import entity.Airport;
import entity.Flight;
import entity.SeatInAirplane;
import util.Consts;

public class ControlFlights {
	
	public static ControlFlights instance;

	public static ControlFlights getInstance() {
		if (instance == null)
			instance = new ControlFlights();
		return instance;
		
	}	
	public List<Flight> getFlights() {
		List<Flight> flightList = new ArrayList<Flight>();

		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(util.Consts.SQL_SEL_OBJECT_FLIGHT);
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int i = 1;
					flightList.add(new Flight(rs.getString(i++),rs.getTimestamp(i++), rs.getTimestamp(i++), rs.getString(i++),
							rs.getString(i++),rs.getString(i++), rs.getString(i++)));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return flightList;	
	}
	
	
	public ArrayList<Airport> getairports() {
		ArrayList<Airport> airportList = new ArrayList<Airport>();

		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(util.Consts.SQL_SEL_AIRPORTS);
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int i = 1;
					airportList.add(new Airport(rs.getString(i++),rs.getString(i++), rs.getString(i++), rs.getInt(i++)));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return airportList;	
	}
	
	public List<Airplane> getairplanes() {
		List<Airplane> airplanesList = new ArrayList<Airplane>();

		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(util.Consts.SQL_SEL_AIRPLANES);
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int i = 1;
					airplanesList.add(new Airplane(rs.getString(i++),rs.getInt(i++)));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return airplanesList;	
	}
	
	public static boolean addAirplane(Airplane airplane) {	
		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
					CallableStatement stmt = conn.prepareCall(util.Consts.SQL_INS_AIRPLANE)) {
				int i = 1;
	
				stmt.setString(i++, airplane.getTailNumber());
				stmt.setInt(i++, airplane.getNumberOfFlightAttendants());

				stmt.executeUpdate();
				
				System.out.println("Airplane added successfully");
				return true;
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean addSeat(SeatInAirplane seat) {	
		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
					CallableStatement stmt = conn.prepareCall(util.Consts.SQL_INS_SEATS)) {
				int i = 1;
	
				stmt.setInt(i++, seat.getRowNum());
				stmt.setString(i++, seat.getColNum());
				stmt.setString(i++, seat.getSeatType().toString());
				stmt.setString(i++, seat.getTailNumber());

				stmt.executeUpdate();
				
				return true;
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public String addFlight(Flight flight) {	
		boolean isPlaneAvilable =  validateAirplane(Date.valueOf(flight.getDepartureTime().toLocalDateTime().toLocalDate()), Date.valueOf(flight.getLandingTime().toLocalDateTime().toLocalDate()),
				flight.getPlaneID());	
		if(isPlaneAvilable == false)
			return "plane";
		
		boolean isOriginOk = validateFlightByDepAirport(flight.getOriginAirportID().getAirportID(), flight.getDepartureTime());
		if(isOriginOk == false)
			return "origin";
		
    	boolean isDestOk = validateFlightBydestAirport(flight.getDestinationAirportID().getAirportID(), flight.getLandingTime());  
    	if(isDestOk == false)
    		return "dest";
		
		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
					CallableStatement stmt = conn.prepareCall(util.Consts.SQL_INS_FLIGHT)) {
				int i = 1;

				stmt.setString(i++, flight.getFlightID());
				stmt.setTimestamp(i++, flight.getDepartureTime());
				stmt.setTimestamp(i++, flight.getLandingTime());
				stmt.setString(i++, flight.getStatus().toString());
				stmt.setString(i++, flight.getOriginAirportID().getAirportID());
				stmt.setString(i++, flight.getDestinationAirportID().getAirportID());
				stmt.setNull(i++, java.sql.Types.VARCHAR);
				stmt.setNull(i++, java.sql.Types.VARCHAR);
				stmt.setString(i++, flight.getPlaneID());
				stmt.setDate(i++, Date.valueOf(LocalDate.now()));
				
				stmt.executeUpdate();
				
				System.out.println("Flight added successfully");
				return "Add";
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return "error";
	}
	
	public static boolean addAirport(Airport airport) {	
		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
					CallableStatement stmt = conn.prepareCall(util.Consts.SQL_INS_AIRPORT)) {
				int i = 1;
	
				stmt.setString(i++, airport.getAirportID());
				stmt.setString(i++, airport.getCountry());
				stmt.setString(i++, airport.getCity());
				stmt.setInt(i++, airport.getTimeZone());

				stmt.executeUpdate();
				
				System.out.println("Airport added successfully");
				return true;
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	public Flight getFlightByID(String id) {
		ArrayList<Flight> flights = new ArrayList<Flight>();
		flights.addAll(getFlights());
		for(Flight f : flights)
		{
			if(f.getFlightID().equals(id))
			{
				return f;
			}
		}
		return null;
	}
	
	public Airport getAirportByID(String id) {
		ArrayList<Airport> airports = new ArrayList<Airport>();
		airports.addAll(getairports());
		for(Airport ap : airports)
		{
			if(ap.getAirportID().equals(id))
			{
				return ap;
			}
		}
		return null;
	}
	
	public boolean validateAirplane(Date departure, Date landing, String tailNum) {
		List<Flight> flightWithSameAirplane = new ArrayList<Flight>();
		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
					CallableStatement stmt =  conn.prepareCall(util.Consts.SQL_SEL_PLANES_BY_FLIGHT)){
				stmt.setDate(1, departure);
				stmt.setDate(2, landing);
				stmt.setString(3, tailNum);
				ResultSet rs = stmt.executeQuery(); {		
					while (rs.next()) {
						int i = 1;
						flightWithSameAirplane.add(new Flight(rs.getString(i++)));				
					}
				}
				if(!flightWithSameAirplane.isEmpty())
					return false;
				return true;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return false;		
	}
			
	public static List<SeatInAirplane> getseatsInAirplane(String tailNumber) {
		List<SeatInAirplane> seatInAirplane = new ArrayList<SeatInAirplane>();
		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
					CallableStatement stmt =  conn.prepareCall(util.Consts.SQL_SEL_SEATS_BY_AIRPLANE)){
				stmt.setString(1, tailNumber);
				ResultSet rs = stmt.executeQuery(); {		
					while (rs.next()) {
						int i = 1;
						seatInAirplane.add(new SeatInAirplane(rs.getInt(i++), rs.getInt(i++), rs.getString(i++), rs.getString(i++), rs.getString(i++)));				
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return seatInAirplane;		
	}


	public boolean validateFlightByDepAirport(String airportCode, Timestamp time) {
		ArrayList<Flight> flights = new ArrayList<Flight>();
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String timeFormat = format.format(time);
		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					CallableStatement stmt =  conn.prepareCall(Consts.SQL_VALIDATE_FLIGHT_BY_ORIGIN)){
				stmt.setString(1, airportCode);
				stmt.setString(2, timeFormat);
				ResultSet rs = stmt.executeQuery(); {		
					while (rs.next()) {
						int i = 1;
						flights.add(new Flight(rs.getString(i++)));				
					}
				}
				if(!flights.isEmpty())
					return false;
				return true;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return false;		
	}
	
	public boolean validateFlightBydestAirport(String airportCode, Timestamp time) {
		ArrayList<Flight> flights = new ArrayList<Flight>();
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String timeFormat = format.format(time);
		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					CallableStatement stmt =  conn.prepareCall(Consts.SQL_VALIDATE_FLIGHT_BY_DESTINATION)){
				stmt.setString(1, airportCode);
				stmt.setString(2, timeFormat);
				ResultSet rs = stmt.executeQuery(); {		
					while (rs.next()) {
						int i = 1;
						flights.add(new Flight(rs.getString(i++)));				
					}
				}
				if(!flights.isEmpty())
					return false;
				return true;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return false;		
	}
		
}

