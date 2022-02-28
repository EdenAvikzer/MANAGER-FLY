package entity;

import java.sql.Timestamp;

public class Shift {
	
	private Timestamp startShiftTime;
	private Timestamp endShiftTime;
	public Shift(Timestamp startShiftTime, Timestamp endShiftTime) {
		super();
		this.startShiftTime = startShiftTime;
		this.endShiftTime = endShiftTime;
	}
	public Timestamp getStartShiftTime() {
		return startShiftTime;
	}
	public void setStartShiftTime(Timestamp startShiftTime) {
		this.startShiftTime = startShiftTime;
	}
	public Timestamp getEndShiftTime() {
		return endShiftTime;
	}
	public void setEndShiftTime(Timestamp endShiftTime) {
		this.endShiftTime = endShiftTime;
	}
	
	
}
