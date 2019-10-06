package com.spatialkey;

/**
 * Represents an insurance policy that applies to one or more locations for a given peril and has a limited payout
 */
public class Policy {
    /**
     * The locations included in this insurance policy
     */
    private Location[] locations;
    /**
     * The maximum amount this insurance policy will ever pay out
     */
    private Double limit;
    /**
     * The peril this insurance policy applies to
     */
    private String peril;

    public Policy(String peril, Double limit, Location... locations) {
        this.limit = limit;
        this.peril = peril;
        this.locations = locations;
    }

    public Location[] getLocations() {
        return locations;
    }

    public void setLocations(Location[] locations) {
        this.locations = locations;
    }

    public Double getLimit() {
        return limit;
    }

    public void setLimit(Double limit) {
        this.limit = limit;
    }

    public String getPeril() {
        return peril;
    }

    public void setPeril(String peril) {
        this.peril = peril;
    }
}
