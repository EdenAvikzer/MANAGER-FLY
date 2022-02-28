package entity;

import java.sql.Date;

public abstract class Employee {
	private Integer employeeID;
	private String firstName;
	private String LastName;
	private Date startDate;
	private Date endDate;
	
	public Employee(Integer employeeID, String firstName, String lastName, Date startDate, Date endDate) {
		super();
		this.employeeID = employeeID;
		this.firstName = firstName;
		LastName = lastName;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	

	public Employee(Integer employeeID, String firstName, String lastName, Date startDate) {
		super();
		this.employeeID = employeeID;
		this.firstName = firstName;
		LastName = lastName;
		this.startDate = startDate;
	}
	
	

	public Employee(Integer employeeID, String firstName, String lastName) {
		super();
		this.employeeID = employeeID;
		this.firstName = firstName;
		LastName = lastName;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((employeeID == null) ? 0 : employeeID.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		if (employeeID == null) {
			if (other.employeeID != null)
				return false;
		} else if (!employeeID.equals(other.employeeID))
			return false;
		return true;
	}


	public Integer getEmployeeID() {
		return employeeID;
	}
	public void setEmployeeID(Integer employeeID) {
		this.employeeID = employeeID;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return LastName;
	}
	public void setLastName(String lastName) {
		LastName = lastName;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}


	@Override
	public String toString() {
		return firstName + " " + LastName;
	}
	
	
	

}
