package com.spatialkey;

import java.util.HashMap;
import java.util.Map;

/**
 * A service for taking your insurance policies and analyzing them against events!
 */
public class DataAnalysisService {
    /**
     * Analyzes a portfolio against given events
     * @param eventPerils The perils of events to analyze against (we're assuming each peril only has one event in the system)
     * @param policies Your portfolio of insurance policies
     * @return A map containing your total exposure for each event peril
     */
    public HashMap analyzeData(String[] eventPerils, Policy[] policies) {
        Map analysisResults = new HashMap();

        Location[] locations = extractLocations(policies);
        for(String eventPeril : eventPerils) {
            locations = enhanceLocationsWithEventData(eventPeril, locations);
        }

        EnhancedLocation[] enhancedLocations = (EnhancedLocation[]) locations;

        for(String eventPeril : eventPerils) {
            long totalExposure = 0;
            for (Policy policy : policies) {
                if(policy.getPeril().equals(eventPeril)) {
                    double policyUnlimitedExposure = sumWeighedTiv(policy, eventPeril, enhancedLocations);

                    if(policyUnlimitedExposure > policy.getLimit()) {
                        totalExposure += policy.getLimit();
                    } else {
                        totalExposure += policyUnlimitedExposure;
                    }
                }
            }

            analysisResults.put(eventPeril, totalExposure);
        }

        analysisResults.put("total", analysisResults.values().stream().reduce((o, o2) -> ((Number) o).doubleValue() + ((Number) o2).doubleValue()).get());

        return (HashMap) analysisResults;
    }

    /**
     * Find the total weighed insured value for all locations in the policy
     * @param policy
     * @param eventPeril
     * @param enhancedLocations
     * @return
     */
    private double sumWeighedTiv(Policy policy, String eventPeril, EnhancedLocation[] enhancedLocations) {
        double totalWeighedTiv = 0;
        for(Location loc : policy.getLocations()) {
            for(EnhancedLocation loc_ : enhancedLocations) {
                if(loc.getAddress() == loc_.getAddress()) {
                    try {
                        long impact = loc_.getEventImpact(eventPeril);

                        BlackBoxEventData eventData = BlackBoxEventData.getData(eventPeril);
                        String severity = eventData.classifyData(impact);

                        //Apply weight to TIV based on severity of impact
                        if(severity.equals("HIGH")) {
                            totalWeighedTiv += loc_.getTotalInsuredValue() * .9;
                        } else if(severity.equals("MEDIUM")) {
                            totalWeighedTiv += loc_.getTotalInsuredValue() * .5;
                        } else if(severity.equals("LOW")) {
                            totalWeighedTiv += loc_.getTotalInsuredValue() * .3;
                        }
                    } catch (InterruptedException e) {
                    }
                }
            }
        }

        return totalWeighedTiv;
    }

    /**
     * Get a set of each unique location in your set of policies
     * @param policies The policies to etract locations from
     * @return A unique array of locations from the policies in question
     */
    private Location[] extractLocations(Policy[] policies) {
        int numberLocations = 0;

        for(int i = 0; i < policies.length; i++) {
            for(int x = 0; x < policies[i].getLocations().length; x++) {
                numberLocations++;
            }
        }

        Location[] locations = new Location[numberLocations];

        for(int i = 0; i < policies.length; i++) {
            for(int x = 0; x < policies[i].getLocations().length; x++) {
                locations[--numberLocations]= policies[i].getLocations()[x];
            }
        }

        return locations;
    }

    /**
     * Takes a list of locations and enhances them with data for a given event
     * @param eventPeril The event to enhance data with
     * @param locations The locations to enhance
     * @return A list of locations of the same length as locations with the enhancements applied
     */
    private EnhancedLocation[] enhanceLocationsWithEventData(String eventPeril, Location[] locations) {
        BlackBoxEventData data = null;
        try {
            data = BlackBoxEventData.getData(eventPeril);
        } catch (InterruptedException e) {        }

        EnhancedLocation[] arr = new EnhancedLocation[locations.length];
        int i = -1;
        for(Location loc : locations) {
            Long depth = data.lookupEventDepthImpact(loc.getAddress());

            if(loc instanceof EnhancedLocation) {
                i++;
                arr[i] = (EnhancedLocation) loc;
                arr[i].addEnhancement(eventPeril,depth);
            } else if(loc instanceof Location && !(loc instanceof EnhancedLocation)) {
                i++;
                arr[i] = new EnhancedLocation(loc.getAddress(), loc.getTotalInsuredValue());
                arr[i].addEnhancement(eventPeril, depth);
            }
        }

        return arr;
    }
}
