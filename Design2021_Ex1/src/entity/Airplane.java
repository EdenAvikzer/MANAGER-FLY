package entity;


public class Airplane {

	private String tailNumber;
	private int numberOfFlightAttendants;
	private int check;
	
	
	public Airplane(String tailNumber, int numberOfFlightAttendants) {
		super();
		this.tailNumber = tailNumber;
		this.numberOfFlightAttendants = numberOfFlightAttendants;
	}
	public Airplane(String tailNumber) {
		super();
		this.tailNumber = tailNumber;
	}
	
	@Override
	public String toString() {
		return "Airplane " + tailNumber;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Airplane other = (Airplane) obj;
		if (tailNumber == null) {
			if (other.tailNumber != null)
				return false;
		} else if (!tailNumber.equals(other.tailNumber))
			return false;
		return true;
	}
	public String getTailNumber() {
		return tailNumber;
	}
	public void setTailNumber(String tailNumber) {
		this.tailNumber = tailNumber;
	}
	public int getNumberOfFlightAttendants() {
		return numberOfFlightAttendants;
	}
	public void setNumberOfFlightAttendants(int numberOfFlightAttendants) {
		this.numberOfFlightAttendants = numberOfFlightAttendants;
	}
	
	
}
