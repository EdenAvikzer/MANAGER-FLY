package entity;

import java.sql.Date;
import java.sql.Timestamp;

import util.FlightStatus;

public class Flight {
	private String flightID;
	private Timestamp departureTime;
	private Timestamp landingTime;
	private Airport originAirportID;
	private Airport destinationAirportID;
	private FlightStatus status;
	private Integer mainPilotID;
	private Integer seconsaryPilotID;
	private String planeID;
	private Date updateDate;

	public Flight(String flightID, Timestamp departureTime, Timestamp landingTime, String status,
			String originAirportID, String destinationAirportID , Integer mainPilotID,
			Integer seconsaryPilotID, String planeID) {
		super();
		this.flightID = flightID;
		this.departureTime = departureTime;
		this.landingTime = landingTime;
		this.originAirportID = new Airport(originAirportID);
		this.destinationAirportID = new Airport(destinationAirportID);
		this.status = FlightStatus.valueOf(status);
		this.mainPilotID = mainPilotID;
		this.seconsaryPilotID = seconsaryPilotID;
		this.planeID = planeID;
	}
	public Flight(String flightID, Timestamp departureTime, Timestamp landingTime, String status,
			String originAirportID, String destinationAirportID, String planeID) {
		super();
		this.flightID = flightID;
		this.departureTime = departureTime;
		this.landingTime = landingTime;
		this.originAirportID = new Airport(originAirportID);
		this.destinationAirportID = new Airport(destinationAirportID);
		this.status = FlightStatus.valueOf(status);
		this.planeID = planeID;
	}
	
	public Flight(String flightID, Timestamp departureTime, Timestamp landingTime, String status, String planeID,
			Airport originAirportID, Airport destinationAirportID) {
		super();
		this.flightID = flightID;
		this.departureTime = departureTime;
		this.landingTime = landingTime;
		this.originAirportID = originAirportID;
		this.destinationAirportID = destinationAirportID;
		this.status = FlightStatus.valueOf(status);
		this.planeID = planeID;
	}
	
	public Flight(String flightID, Timestamp departureTime, Timestamp landingTime) {
		this.flightID = flightID;
		this.departureTime = departureTime;
		this.landingTime = landingTime;
	}


	public Flight(String flightID, Timestamp departureTime, Timestamp landingTime, String originAirportID, String destinationAirportID, String planeID) {
		super();
		this.flightID = flightID;
		this.departureTime = departureTime;
		this.landingTime = landingTime;
		this.originAirportID = new Airport(originAirportID);
		this.destinationAirportID = new Airport(destinationAirportID);
		this.planeID = planeID;
	}
	
	
	
	public Flight(String flightID, Timestamp departureTime, Timestamp landingTime, String originAirportID,
			String destinationAirportID, FlightStatus status, Integer mainPilotID, Integer seconsaryPilotID,
			String planeID, Date updateDate) {
		super();
		this.flightID = flightID;
		this.departureTime = departureTime;
		this.landingTime = landingTime;
		this.originAirportID = new Airport(originAirportID);
		this.destinationAirportID = new Airport(destinationAirportID);
		this.status = status;
		this.mainPilotID = mainPilotID;
		this.seconsaryPilotID = seconsaryPilotID;
		this.planeID = planeID;
		this.updateDate = updateDate;
	}
	public Flight(String flightID, Timestamp departureTime, Timestamp landingTime, String originAirportID,
			String destinationAirportID, String planeID, Integer mainPilotID, Integer seconsaryPilotID) {
		super();
		this.flightID = flightID;
		this.departureTime = departureTime;
		this.landingTime = landingTime;
		this.originAirportID = new Airport(originAirportID);
		this.destinationAirportID = new Airport(destinationAirportID);
		this.mainPilotID = mainPilotID;
		this.seconsaryPilotID = seconsaryPilotID;
		this.planeID = planeID;
	}
	public Flight(String flightID) {
		super();
		this.flightID = flightID;
	}
	
	

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Flight other = (Flight) obj;
		if (flightID == null) {
			if (other.flightID != null)
				return false;
		} else if (!flightID.equals(other.flightID))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return flightID;
	}

	public String getFlightID() {
		return flightID;
	}

	public void setFlightID(String flightID) {
		this.flightID = flightID;
	}

	public Timestamp getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(Timestamp departureTime) {
		this.departureTime = departureTime;
	}

	public Timestamp getLandingTime() {
		return landingTime;
	}

	public void setLandingTime(Timestamp landingTime) {
		this.landingTime = landingTime;
	}

	public FlightStatus getStatus() {
		return status;
	}

	public void setStatus(FlightStatus status) {
		this.status = status;
	}
	
	public Airport getOriginAirportID() {
		return originAirportID;
	}

	public void setOriginAirportID(Airport originAirportID) {
		this.originAirportID = originAirportID;
	}

	public Airport getDestinationAirportID() {
		return destinationAirportID;
	}

	public void setDestinationAirportID(Airport destinationAirportID) {
		this.destinationAirportID = destinationAirportID;
	}
	public Integer getMainPilotID() {
		return mainPilotID;
	}
	public void setMainPilotID(Integer mainPilotID) {
		this.mainPilotID = mainPilotID;
	}
	public Integer getSeconsaryPilotID() {
		return seconsaryPilotID;
	}
	public void setSeconsaryPilotID(Integer seconsaryPilotID) {
		this.seconsaryPilotID = seconsaryPilotID;
	}
	public String getPlaneID() {
		return planeID;
	}
	public void setPlaneID(String planeID) {
		this.planeID = planeID;
	}
	
	
	
}