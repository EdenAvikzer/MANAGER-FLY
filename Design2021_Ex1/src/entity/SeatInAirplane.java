package entity;

import util.SeatType;

public class SeatInAirplane {
	private int id;
	private int rowNum;
	private String colNum;
	private SeatType seatType;
	private String tailNumber;
	
	public SeatInAirplane(int rowNum, String colNum, String seatType, String tailNumber) {
		super();
		this.rowNum = rowNum;
		this.colNum = colNum;
		this.seatType = SeatType.valueOf(seatType);
		this.tailNumber = tailNumber;
	}
	

	public SeatInAirplane(int id, int rowNum, String colNum, String seatType, String tailNumber) {
		super();
		this.id = id;
		this.rowNum = rowNum;
		this.colNum = colNum;
		this.seatType = SeatType.valueOf(seatType);
		this.tailNumber = tailNumber;
	}
	
	public SeatInAirplane(int rowNum, String colNum, SeatType seatType) {
		super();
		this.rowNum = rowNum;
		this.colNum = colNum;
		this.seatType = seatType;
	}


	public int getRowNum() {
		return rowNum;
	}

	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}

	public String getColNum() {
		return colNum;
	}

	public void setColNum(String colNum) {
		this.colNum = colNum;
	}

	public SeatType getSeatType() {
		return seatType;
	}

	public void setSeatType(SeatType seatType) {
		this.seatType = seatType;
	}

	public String getTailNumber() {
		return tailNumber;
	}

	public void setTailNumber(String tailNumber) {
		this.tailNumber = tailNumber;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}
	
	
	
	
	
	
}
