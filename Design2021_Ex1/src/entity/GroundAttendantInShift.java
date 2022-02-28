package entity;

import util.ShiftRole;

public class GroundAttendantInShift {
	private Shift shift;
	private GroundAttendant ga;
	private ShiftRole role;
	public GroundAttendantInShift(Shift shift, GroundAttendant ga, ShiftRole role) {
		super();
		this.shift = shift;
		this.ga = ga;
		this.role = role;
	}
	public GroundAttendant getGa() {
		return ga;
	}
	public void setGa(GroundAttendant ga) {
		this.ga = ga;
	}
	public Shift getShift() {
		return shift;
	}
	public void setShift(Shift shift) {
		this.shift = shift;
	}
	public ShiftRole getRole() {
		return role;
	}
	public void setRole(ShiftRole role) {
		this.role = role;
	}
	
	
	
}
