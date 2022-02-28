package entity;

import java.sql.Date;

public class GroundAttendant extends Employee {

	public GroundAttendant(Integer employeeID, String firstName, String lastName, Date startDate, Date endDate) {
		super(employeeID, firstName, lastName, startDate, endDate);
	}
	
	public GroundAttendant(Integer employeeID, String firstName, String lastName, Date startDate) {
		super(employeeID, firstName, lastName, startDate);
	}
	public GroundAttendant(Integer employeeID, String firstName, String lastName) {
		super(employeeID, firstName, lastName);
	}
	

}
