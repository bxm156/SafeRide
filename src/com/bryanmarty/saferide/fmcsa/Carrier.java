package com.bryanmarty.saferide.fmcsa;

import com.bryanmarty.saferide.fmcsa.PassengerCarrier.VehicleType;

public class Carrier {

	public String name = "";
	public String DOT = "";
	public VehicleType vehicleType = VehicleType.Motorcoach;
	public String principalLocation = "";
	public boolean allowedToOperate = false;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDOT() {
		return DOT;
	}
	public void setDOT(String dOT) {
		DOT = dOT;
	}
	public VehicleType getVehicleType() {
		return vehicleType;
	}
	public void setVehicleType(VehicleType vehicleType) {
		this.vehicleType = vehicleType;
	}
	public String getPrincipalLocation() {
		return principalLocation;
	}
	public void setPrincipalLocation(String principalLocation) {
		this.principalLocation = principalLocation;
	}
	public boolean isAllowedToOperate() {
		return allowedToOperate;
	}
	public void setAllowedToOperate(boolean allowedToOperate) {
		this.allowedToOperate = allowedToOperate;
	}
	
	@Override
	public String toString() {
		return name + ", " + principalLocation +", " + vehicleType.toString() + ", " + allowedToOperate + ", " + DOT;
	}
	
	public String getCleanedName() {
		String clean = name;
		clean = clean.replace('.',' ');
		clean = clean.replace(',',' ');
		return clean;
	}
	
}
