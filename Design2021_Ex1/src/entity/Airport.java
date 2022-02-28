package entity;

public class Airport {
	
	private String airportID;
	private String country;
	private String city;
	private int timeZone;
	
	public Airport(String airportID, String country, String city, int timeZone) {
		super();
		this.airportID = airportID;
		this.country = country;
		this.city = city;
		this.timeZone = timeZone;
	}
	
	
	public Airport(String airportID, String country, String city) {
		super();
		this.airportID = airportID;
		this.country = country;
		this.city = city;
	}


	public Airport(String airportID) {
		super();
		this.airportID = airportID;
	}
	
	@Override
	public String toString() {
		return   country + ", " + city;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Airport other = (Airport) obj;
		if (airportID != other.airportID)
			return false;
		return true;
	}
	public String getAirportID() {
		return airportID;
	}
	public void setAirportID(String airportID) {
		this.airportID = airportID;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public int getTimeZone() {
		return timeZone;
	}
	public void setTimeZone(int timeZone) {
		this.timeZone = timeZone;
	}
	
	
}