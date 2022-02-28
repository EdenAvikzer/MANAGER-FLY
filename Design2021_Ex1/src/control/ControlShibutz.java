package control;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import entity.Employee;
import entity.Flight;
import entity.FlightAttendant;
import entity.GroundAttendant;
import entity.GroundAttendantInShift;
import entity.Pilot;
import entity.Shift;
import util.Consts;
import util.ShiftRole;

public class ControlShibutz {

	public static ControlShibutz instance;
	
	public static ControlShibutz getInstance() {
		if (instance == null)
			instance = new ControlShibutz();
		return instance;
	}

	public List<Flight> getFutureFlights() {
		List<Flight> flightList = new ArrayList<Flight>();

		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
					CallableStatement stmt =  conn.prepareCall(util.Consts.SQL_SEL_FUTURE_FLIGHTS)){
				stmt.setDate(1, Date.valueOf(LocalDate.now()));
				ResultSet rs = stmt.executeQuery(); {		
					while (rs.next()) {
						int i = 1;
						flightList.add(new Flight(rs.getString(i++),rs.getTimestamp(i++), rs.getTimestamp(i++),
								rs.getString(i++),rs.getString(i++),rs.getString(i++),rs.getInt(i++), rs.getInt(i++)));		
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return flightList;	
	}

	public List<Pilot> getAvilablePilots(Date departure, Date landing, String flightID) {
		List<Pilot> pilotsWithSameDate = new ArrayList<Pilot>();
		List<Pilot> toReturn = new ArrayList<Pilot>();

		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
					CallableStatement stmt =  conn.prepareCall(util.Consts.SQL_SEL_AVILABLE_PILOTS)){
				stmt.setDate(1, departure);
				stmt.setDate(2, landing);
				stmt.setString(3, flightID);
				ResultSet rs = stmt.executeQuery(); {		
					while (rs.next()) {
						int i = 1;
						pilotsWithSameDate.add(new Pilot(rs.getInt(i++), rs.getString(i++), rs.getString(i++), rs.getDate(i++), rs.getDate(i++)));				
					}
				}
				List<Pilot> allPilots = getAllPilots();
				toReturn.addAll(allPilots);
				for(Pilot pilot: allPilots) {
					for(Pilot pilot2: pilotsWithSameDate) {
						if(pilot.equals(pilot2)) {
							toReturn.remove(pilot);
						}
						
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return toReturn;		
	}

	public List<Pilot> getAllPilots() {
		List<Pilot> pilotList = new ArrayList<Pilot>();

		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(util.Consts.SQL_SEL_ALL_PILOTS);
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int i = 1;
					pilotList.add(new Pilot(rs.getInt(i++), rs.getString(i++), rs.getString(i++), rs.getDate(i++), rs.getDate(i++), rs.getInt(i++), rs.getDate(i++)));	
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return pilotList;	
	}

	public List<FlightAttendant> getAvilableFA(Date departure, Date landing, String flightID) {
		List<FlightAttendant> faWithSameDate = new ArrayList<FlightAttendant>();
		List<FlightAttendant> toReturn = new ArrayList<FlightAttendant>();

		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
					CallableStatement stmt =  conn.prepareCall(util.Consts.SQL_SEL_AVILABLE_FA)){
				stmt.setDate(1, departure);
				stmt.setDate(2, landing);
				stmt.setString(3, flightID);
				ResultSet rs = stmt.executeQuery(); {		
					while (rs.next()) {
						int i = 1;
						faWithSameDate.add(new FlightAttendant(rs.getInt(i++), rs.getString(i++), rs.getString(i++), rs.getDate(i++), rs.getDate(i++)));				
					}
				}
				List<FlightAttendant> allFA = getAllFA();
				toReturn.addAll(allFA);
				for(FlightAttendant fa: allFA) {
					for(FlightAttendant fa2: faWithSameDate) {
						if(fa.equals(fa2))
							toReturn.remove(fa);
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return toReturn;		
	}

	public List<FlightAttendant> getAllFA() {
		List<FlightAttendant> faList = new ArrayList<FlightAttendant>();

		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(util.Consts.SQL_SEL_ALL_FA);
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int i = 1;
					faList.add(new FlightAttendant(rs.getInt(i++), rs.getString(i++), rs.getString(i++), rs.getDate(i++), rs.getDate(i++)));	
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return faList;	
	}
	
	public List<GroundAttendantInShift> getAllGAInShift() {
		List<GroundAttendantInShift> gaInShiftList = new ArrayList<GroundAttendantInShift>();

		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(util.Consts.SQL_GA_IN_SHIFT);
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int i = 1;
					gaInShiftList.add(new GroundAttendantInShift(new Shift(rs.getTimestamp(i++), rs.getTimestamp(i++)),
							new GroundAttendant(rs.getInt(i++), rs.getString(i++), rs.getString(i++)), ShiftRole.valueOf(rs.getString(i++))));	
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return gaInShiftList;	
	}

	public int getNumOfFAByPlane(String planeID) {
		int numOfFA = 0;
		
		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
					CallableStatement callst = conn.prepareCall(Consts.SQL_SEL_FA_BY_PLANE))
					{
					int k=1;
					callst.setString(k++, planeID);
					
					ResultSet rs = callst.executeQuery();
					while (rs.next()) 
					{
						int i =1;
						numOfFA = (rs.getInt(i));
					}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return numOfFA;	
	}

	public HashMap<String, List<FlightAttendant>> getFAForFlight() {
		HashMap<String, List<FlightAttendant>> toReturn = new HashMap<>();
		List<Flight> flights = getFutureFlights();
		for(Flight flight: flights) {
			ArrayList<FlightAttendant> allFAPerFlight = getAllFAByIDS(flight.getFlightID());
			toReturn.put(flight.getFlightID(), allFAPerFlight);
		}
		return toReturn;
	}

	private ArrayList<FlightAttendant> getAllFAByIDS(String flightID) {
		ArrayList<FlightAttendant> fa = new ArrayList<FlightAttendant>();
		int id;
		String fname;
		String lname;
		Date startDate;
		Date endDate;
		
		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
					CallableStatement callst = conn.prepareCall(Consts.SQL_SEL_FA_BY_FLIGHT))
					{
					int k=1;
					callst.setString(k++, flightID);
					
					
					ResultSet rs = callst.executeQuery();
					while (rs.next()) 
					{
						int i =1;
						id = (rs.getInt(i++));
						fname = (rs.getString(i++));
						lname = (rs.getString(i++));
						startDate = (rs.getDate(i++));
						endDate = (rs.getDate(i++));
						
						fa.add(new FlightAttendant(id, fname, lname, startDate, endDate));
					}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return fa;	
	}

	public FlightAttendant getRealFAByID(int id) {
		ArrayList<FlightAttendant> fas = new ArrayList<FlightAttendant>();
		fas.addAll(getAllFA());
		for(FlightAttendant fa : fas) {
			if(fa.getEmployeeID().equals(id))
				return fa;
		}
		return null;
	}
	
	public Pilot getRealPilotByID(int id) {
		ArrayList<Pilot> pilots = new ArrayList<Pilot>();
		pilots.addAll(getAllPilots());
		for(Pilot pilot : pilots) {
			if(pilot.getEmployeeID().equals(id))
				return pilot;
		}
		return null;
	}

	public boolean addMainPilot(Flight flight, Pilot main) {
		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					CallableStatement stmt = conn.prepareCall(util.Consts.SQL_UPD_MAIN_PILOT)) {
				int i = 1;
				stmt.setString(i++, String.valueOf(main.getEmployeeID()));
				stmt.setString(i++, flight.getFlightID());
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

	public boolean addSecondaryPilot(Flight flight, Pilot secondary) {
		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					CallableStatement stmt = conn.prepareCall(util.Consts.SQL_UPD_SECONDARY_PILOT)) {
				int i = 1;
				stmt.setString(i++, String.valueOf(secondary.getEmployeeID()));
				stmt.setString(i++, flight.getFlightID());
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

	public boolean removeFAFromFlight(Flight flight, FlightAttendant fa) {
		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					CallableStatement stmt = conn.prepareCall(Consts.SQL_DEL_FA_FROM_FLIGHT)) {
				stmt.setString(1, String.valueOf(fa.getEmployeeID()));
				stmt.setString(2, flight.getFlightID());
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

	public boolean addFAToFlight(Flight flight, FlightAttendant fa) {
		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					CallableStatement stmt = conn.prepareCall(Consts.SQL_INS_FA_TO_FLIGHT)) {
				stmt.setString(1, String.valueOf(fa.getEmployeeID()));
				stmt.setString(2, flight.getFlightID());
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

	public boolean addPilot(Pilot pilot) {
		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					CallableStatement stmt = conn.prepareCall(Consts.SQL_INS_PILOT)){			
				int i = 1;
				stmt.setInt(i++, pilot.getEmployeeID());
				stmt.setString(i++, pilot.getFirstName());
				stmt.setString(i++, pilot.getLastName());
				stmt.setDate(i++, pilot.getStartDate());
				stmt.setNull(i++, java.sql.Types.DATE);
				stmt.setInt(i++, pilot.getLicenceID());
				stmt.setDate(i++, pilot.getLicenceStartDate());
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

	public boolean addFlightAttendant(FlightAttendant fa) {
		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					CallableStatement stmt = conn.prepareCall(Consts.SQL_INS_FLIGHTATTENDANT)){			
				int i = 1;
				stmt.setInt(i++, fa.getEmployeeID());
				stmt.setString(i++, fa.getFirstName());
				stmt.setString(i++, fa.getLastName());
				stmt.setDate(i++, fa.getStartDate());
				stmt.setNull(i++, java.sql.Types.DATE);
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

	public boolean addGroundAttendant(GroundAttendant ga) {
		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					CallableStatement stmt = conn.prepareCall(Consts.SQL_INS_GROUNDATTENDANT)){			
				int i = 1;
				stmt.setInt(i++, ga.getEmployeeID());
				stmt.setString(i++, ga.getFirstName());
				stmt.setString(i++, ga.getLastName());
				stmt.setDate(i++, ga.getStartDate());
				stmt.setNull(i++, java.sql.Types.DATE);
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

	public List<GroundAttendant> getAllGA() {
		List<GroundAttendant> gaList = new ArrayList<GroundAttendant>();

		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(util.Consts.SQL_SEL_ALL_GA);
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int i = 1;
					gaList.add(new GroundAttendant(rs.getInt(i++), rs.getString(i++), rs.getString(i++), rs.getDate(i++), rs.getDate(i++)));	
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return gaList;	
	}

	private boolean addShift(Shift shift) {
		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					CallableStatement stmt = conn.prepareCall(util.Consts.SQL_INS_SHIFT)) {
				int i = 1;
				
				stmt.setTimestamp(i++, shift.getStartShiftTime());
				stmt.setTimestamp(i++, shift.getEndShiftTime());
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

	public boolean addGAInShift(Shift shift, Integer employeeID, String role) {
		if(!isExistShift(shift))
			addShift(shift);
		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					CallableStatement stmt = conn.prepareCall(util.Consts.SQL_INS_GA_IN_SHIFT)) {
				int i = 1;		
				stmt.setString(i++, String.valueOf(employeeID));
				stmt.setTimestamp(i++, shift.getStartShiftTime());
				stmt.setTimestamp(i++, shift.getEndShiftTime());
				stmt.setString(i++, role);
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
	
	private boolean isExistShift(Shift shift)
	{
		Timestamp start = null;
		Timestamp end = null;
		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
					CallableStatement callst = conn.prepareCall(Consts.SQL_SHIFT_EXIST))
					{
					int k=1;
					callst.setTimestamp(k++, shift.getStartShiftTime());
					callst.setTimestamp(k++, shift.getEndShiftTime());
					
					ResultSet rs = callst.executeQuery();
					while (rs.next()) {
						start = rs.getTimestamp(1);
						end = rs.getTimestamp(2);
					}
					
					
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		if(start == null || end == null)
			return false;
		return true;
	}

	public boolean updatePilotEndDate(Employee employee) {
		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					CallableStatement stmt = conn.prepareCall(util.Consts.SQL_UPD_PILOT_END_DATE)) {
				int i = 1;
				stmt.setDate(i++, Date.valueOf(LocalDate.now()));
				stmt.setString(i++, String.valueOf(employee.getEmployeeID()));
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

	public boolean updateFAEndDate(Employee employee) {
		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					CallableStatement stmt = conn.prepareCall(util.Consts.SQL_UPD_FA_END_DATE)) {
				int i = 1;
				stmt.setDate(i++, Date.valueOf(LocalDate.now()));
				stmt.setString(i++, String.valueOf(employee.getEmployeeID()));
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
	
	public boolean updateGAEndDate(Employee employee) {
		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					CallableStatement stmt = conn.prepareCall(util.Consts.SQL_UPD_GA_END_DATE)) {
				int i = 1;
				stmt.setDate(i++, Date.valueOf(LocalDate.now()));
				stmt.setString(i++, String.valueOf(employee.getEmployeeID()));
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
	 
		
}
