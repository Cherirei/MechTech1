package com.example.mechtech;

public class ServiceStations {
    String code, name, county, address, workingHours,phoneNo;
    public ServiceStations() {
    }

    public ServiceStations(String code, String name, String county, String address, String workingHours, String phoneNo) {
        this.code = code;
        this.name = name;
        this.county = county;
        this.address = address;
        this.workingHours = workingHours;
        this.phoneNo = phoneNo;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(String workingHours) {
        this.workingHours = workingHours;
    }
}
