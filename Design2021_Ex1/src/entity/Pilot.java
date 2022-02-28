package entity;

import java.sql.Date;

public class Pilot extends Employee {
	
	private Integer licenceID;
	private Date licenceStartDate;

	public Pilot(Integer employeeID, String firstName, String lastName, Date startDate, Date endDate
			, Integer licenceID, Date licenceStartDate ) {
		
		super(employeeID, firstName, lastName, startDate, endDate);
		this.licenceID = licenceID;
		this.licenceStartDate = licenceStartDate;
	}
	public Pilot(Integer employeeID, String firstName, String lastName, Date startDate
			, Integer licenceID, Date licenceStartDate ) {
		
		super(employeeID, firstName, lastName, startDate);
		this.licenceID = licenceID;
		this.licenceStartDate = licenceStartDate;
	}
	

	public Pilot(Integer employeeID, String firstName, String lastName, Date startDate, Date endDate) {
		super(employeeID, firstName, lastName, startDate, endDate);
	}



	public Integer getLicenceID() {
		return licenceID;
	}

	public void setLicenceID(Integer licenceID) {
		this.licenceID = licenceID;
	}

	public Date getLicenceStartDate() {
		return licenceStartDate;
	}

	public void setLicenceStartDate(Date licenceStartDate) {
		this.licenceStartDate = licenceStartDate;
	}
	
	  
	

}
