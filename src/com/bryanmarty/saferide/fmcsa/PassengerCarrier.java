package com.bryanmarty.saferide.fmcsa;

import java.net.URL;

import com.bryanmarty.saferide.fmcsa.FMCSAScrapper.VehicleType;
import com.bryanmarty.saferide.interfaces.CarrierListItem;

public class PassengerCarrier implements CarrierListItem {

	/* Search Table */
	public String name = "";
	public String principalLocation = "";
	public VehicleType vehicleType = VehicleType.Motorcoach;
	public URL detailPage;
	public boolean allowedToOperate = false;
	
	/* Details */
	public String companyName_;
	public String dba_;
	public String dot_;
	public String mcNumber_;
	public String address_;
	public String mailingAddress_;
	public String telephone_;
	public String fax_;
	public int motorcoaches_;
	public int schoolBuses_;
	public int miniBusesVans_;
	public int limousines_;
	public boolean outOfService_ = false;
	public String outOfServiceDate_;


	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public URL getDetailPage() {
		return detailPage;
	}
	public void setDetailPage(URL detailPage) {
		this.detailPage = detailPage;
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
	
	/* Details */
	public String getCompanyName_() {
		return companyName_;
	}
	public void setCompanyName_(String companyName) {
		this.companyName_ = companyName;
	}
	public String getDBA() {
		return dba_;
	}
	public void setDBA(String DBA) {
		this.dba_ = DBA;
	}
	public String getDOT() {
		return dot_;
	}
	public void setDOT(String DOT) {
		this.dot_ = DOT;
	}
	public String getMCNumber() {
		return mcNumber_;
	}
	public void setMCNumber(String mcNumber) {
		this.mcNumber_ = mcNumber;
	}
	public String getAddress_() {
		return address_;
	}
	public void setAddress(String address) {
		this.address_ = address;
	}
	public String getMailingAddress() {
		return mailingAddress_;
	}
	public void setMailingAddress_(String mailingAddress) {
		this.mailingAddress_ = mailingAddress;
	}
	public String getTelephone() {
		return telephone_;
	}
	public void setTelephone(String telephone) {
		this.telephone_ = telephone;
	}
	public String getFax() {
		return fax_;
	}
	public void setFax(String fax) {
		this.fax_ = fax;
	}
	public int getMotorcoaches() {
		return motorcoaches_;
	}
	public void setMotorcoaches(int motorcoaches) {
		this.motorcoaches_ = motorcoaches;
	}
	public int getSchoolBuses() {
		return schoolBuses_;
	}
	public void setSchoolBuses(int schoolBuses) {
		this.schoolBuses_ = schoolBuses;
	}
	public int getMiniBusesVans() {
		return miniBusesVans_;
	}
	public void setMiniBusesVans(int miniBusesVans) {
		this.miniBusesVans_ = miniBusesVans;
	}
	public int getLimousines() {
		return limousines_;
	}
	public void setLimousines(int limousines) {
		this.limousines_ = limousines;
	}
	public boolean isOutOfService() {
		return outOfService_;
	}
	public void setOutOfService(boolean outOfService) {
		this.outOfService_ = outOfService;
	}
	public String getOutOfServiceDate() {
		return outOfServiceDate_;
	}
	public void setOutOfServiceDate(String outOfServiceDate) {
		this.outOfServiceDate_ = outOfServiceDate;
	}
	
	@Override
	public String toString() {
		return name + ", " + principalLocation +", " + vehicleType.toString() + ", " + allowedToOperate;
	}
}
