package com.spatialkey;

/**
 * Represents an insurable location
 */
public class Location {
    private String address;
    private Double totalInsuredValue;

    public Location(String address, Double totalInsuredValue) {
        this.address = address;
        this.totalInsuredValue = totalInsuredValue;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getTotalInsuredValue() {
        return totalInsuredValue;
    }

    public void setTotalInsuredValue(Double totalInsuredValue) {
        this.totalInsuredValue = totalInsuredValue;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Location) {
            if(this.address == ((Location) obj).address && this.totalInsuredValue == ((Location) obj).totalInsuredValue)
                return true;
        }

        return false;
    }
}
