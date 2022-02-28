package entity;

import java.sql.Date;

public class FlightAttendant extends Employee {
	
	public FlightAttendant(Integer employeeID, String firstName, String lastName, Date startDate, Date endDate) {
		super(employeeID, firstName, lastName, startDate, endDate);
	}
	
	public FlightAttendant(Integer employeeID, String firstName, String lastName, Date startDate) {
		super(employeeID, firstName, lastName, startDate);
	}

	
}
