package util;

import java.net.URLDecoder;

public class Consts {
	protected static final String DB_FILEPATH = getDBPath();
	public static final String CONN_STR =
			"jdbc:ucanaccess://" + DB_FILEPATH;
	public static final String JDBC_STR = "net.ucanaccess.jdbc.UcanaccessDriver";
	
	
	public static final String SQL_SEL_FLIGHTS = "SELECT Flight.flightID, Flight.originAirportID, Flight.destinationAirportID, Flight.status, Flight.airplaneID  FROM Flight";
	public static final String SQL_SEL_OBJECT_FLIGHT = "SELECT Flight.flightID, Flight.departureTime, Flight.landingTime, Flight.status, Flight.originAirportID, Flight.destinationAirportID, Flight.airplaneID FROM Flight";
	public static final String SQL_SEL_AIRPORTS = "SELECT Airport.airportID, Airport.country, Airport.city, Airport.timeZone\r\n"
			+ "FROM Airport;\r\n";
	public static final String SQL_SEL_AIRPLANES = "SELECT Airplane.* FROM Airplane";
	public static final String SQL_SEL_PILOTS = "SELECT Pilot.* FROM Pilot";
	public static final String SQL_INS_FLIGHT ="{ call quer_insert_flight(?,?,?,?,?,?,?,?,?,?)";
	public static final String SQL_INS_AIRPLANE ="{ call quer_insert_airplane(?,?)";
	public static final String SQL_INS_AIRPORT ="{ call quer_insert_airport(?,?,?,?)";
	public static final String SQL_INS_SEATS = "{ call quer_insert_seat(?,?,?,?)";
	public static final String SQL_UPD_FLIGHT_STATUS ="{ call quer_upd_flight_status(?,?) }";
	public static final String SQL_UPD_FLIGHT_PLANE = "{ call quer_upd_flight_plane(?,?) }";
	public static final String SQL_FLIGHTS_BY_STATUS = "{ call quer_flight_by_status(?) }";
	public static final String SQL_SEL_PLANES_BY_FLIGHT = "{ call quer_planes(?,?,?) }";
	public static final String SQL_DEL_AIRPLANE = "{ call quer_del_airplanes(?) }";
	public static final String SQL_VALIDATE_FLIGHT_BY_ORIGIN = "{ call quer_validate_flight_by_origin(?,?) }";
	public static final String SQL_VALIDATE_FLIGHT_BY_DESTINATION = "{ call quer_validate_flight_by_destination(?,?) }";
	public static final String SQL_SEL_SEATS_BY_AIRPLANE = "{ call quer_seats_by_airplane(?) }";
	
	public static final String SQL_SEL_FUTURE_FLIGHTS = "{ call quer_future_flights(?) }";
	public static final String SQL_SEL_AVILABLE_PILOTS = "{ call quer_avilable_pilots(?,?,?) }";
	public static final String SQL_SEL_AVILABLE_FA = "{ call quer_avilable_fa(?,?,?) }";
	public static final String SQL_SEL_ALL_PILOTS = "SELECT * FROM Pilot";
	public static final String SQL_SEL_ALL_FA = "SELECT flightAttendant.ID, flightAttendant.firstName, flightAttendant.lastName, flightAttendant.startDate, flightAttendant.endDate FROM flightAttendant";
	public static final String SQL_SEL_ALL_GA = "SELECT * FROM groundAttendant";
	public static final String SQL_SEL_FA_BY_PLANE = "{ call quer_num_by_plane(?) }";
	public static final String SQL_SEL_FA_BY_FLIGHT = "{ call quer_fa_by_flight(?) }";
	public static final String SQL_UPD_MAIN_PILOT = "{ call quer_update_main_pilot(?,?) }";
	public static final String SQL_UPD_SECONDARY_PILOT = "{ call quer_update_secondary_pilot(?,?) }";
	public static final String SQL_DEL_FA_FROM_FLIGHT = "{ call quer_delete_fa_from_flight(?,?) }";
	public static final String SQL_INS_FA_TO_FLIGHT = "{ call quer_insert_fa_to_flight(?,?) }";
	public static final String SQL_INS_PILOT = "{ call quer_insert_pilot(?,?,?,?,?,?,?) }";
	public static final String SQL_INS_GROUNDATTENDANT = "{ call quer_insert_groundAttendant(?,?,?,?,?) }";
	public static final String SQL_INS_FLIGHTATTENDANT = "{ call quer_insert_flightAttendant(?,?,?,?,?) }";
	public static final String SQL_INS_SHIFT = "{ call quer_insert_shift(?,?) }";
	public static final String SQL_INS_GA_IN_SHIFT = "{ call quer_insert_ga_in_shift(?,?,?,?) }";
	public static final String SQL_SHIFT_EXIST = "{ call quer_is_shift_exist(?,?) }";
	public static final String SQL_FLIGHT_EXIST = "{ call quer_is_flight_exist(?) }";
	public static final String SQL_UPD_STATUS = "{ call quer_update_status(?,?) }";
	public static final String SQL_SEATS_DETAILS = "{ call quer_seats_by_flight(?) }";
	public static final String SQL_GA_IN_SHIFT = "SELECT GroundAttendantInShift.startTime, GroundAttendantInShift.endTime, GroundAttendant.ID, GroundAttendant.firstName, GroundAttendant.lastName, GroundAttendantInShift.shiftRole\r\n"
			+ "FROM GroundAttendant INNER JOIN GroundAttendantInShift ON GroundAttendant.ID = GroundAttendantInShift.attendantID\r\n"
			+ "ORDER BY GroundAttendantInShift.startTime DESC;\r\n";
	
	public static final String SQL_EXPORT_DATA = "SELECT Flight.flightID, Flight.departureTime, Flight.landingTime, Flight.status, Flight.airplaneID, Flight.originAirportID, Airport.country, Airport.city, Flight.destinationAirportID, Airport_1.country, Airport_1.city\r\n"
			+ "FROM (Airport INNER JOIN Flight ON Airport.airportID = Flight.originAirportID) INNER JOIN Airport AS Airport_1 ON Flight.destinationAirportID = Airport_1.airportID\r\n"
			+ "WHERE (((Flight.updateDate)=Date()));\r\n";
	
	public static final String SQL_DEST_COUNTRIES = "SELECT DISTINCT Airport.country\r\n"
			+ "FROM Flight INNER JOIN Airport ON Flight.destinationAirportID = Airport.airportID\r\n"
			+ "GROUP BY Airport.country;";
	
	public static final String SQL_SEL_ALL_USERS ="SELECT User.* FROM User;";
	
	public static final String SQL_UPD_PILOT_END_DATE ="{ call quer_update_pilot_end_date(?,?) }";
	public static final String SQL_UPD_FA_END_DATE ="{ call quer_update_fa_end_date(?,?) }";
	public static final String SQL_UPD_GA_END_DATE ="{ call quer_update_ga_end_date(?,?) }";
	
	private static String getDBPath() {
		try {
			String path = Consts.class.getProtectionDomain().getCodeSource().getLocation().getPath();
			String decoded = URLDecoder.decode(path, "UTF-8");
			if (decoded.contains(".jar")) {
				decoded = decoded.substring(0, decoded.lastIndexOf('/'));
				return decoded + "/src/entity/db.accdb";
			} else {
				decoded = decoded.substring(0, decoded.lastIndexOf("bin/"));
				System.out.println(decoded);
				return decoded + "/src/entity/db.accdb";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
