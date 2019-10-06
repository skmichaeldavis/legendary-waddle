package com.spatialkey;

/**
 * A class representing a black box that loads data from a third party that indicates the depth/impact that an event
 * of a given peril has on a given location.  For instance if given a peril of "flood" it could tell you an address
 * had 2 meters of flooding. It also has a mechanism to classify that impact by severity.
 */
public class BlackBoxEventData {
    /**
     * A placeholder factory/loading method for loading data for a given event of a specified peril.  In the real world
     * this would require information to specify the exact event being loaded and not just a peril
     * @param perilName The peril to load data for
     * @throws InterruptedException Representative error for some error in the loading process
     */
    public static BlackBoxEventData getData(String perilName) throws InterruptedException {
        return new BlackBoxEventData(perilName);
    }

    /**
     * ...
     * @param perilName The peril to load data for
     * @throws InterruptedException Representative error for some error in the loading process
     */
    private BlackBoxEventData(String perilName) throws InterruptedException {
        //Simulate data loading from a third party
        Thread.sleep(1000);
    }

    /**
     * Return the depth of the impact of this event
     * @param address The address to lookup
     * @return The depth of the impact at the given location in arbitrary event specific units
     */
    public long lookupEventDepthImpact(String address) {
        try {
            //Simulate lookup process
            Thread.sleep(100);
        } catch (InterruptedException e) {
            //Insert error handling here
        }
        return (long) (Math.random() * 150000);
    }

    /**
     * Classify the impact in categories of low/medium/high
     * @param depthImpact The depth of the impact from this event
     * @return A string of either low, medium, or high
     */
    public String classifyData(long depthImpact) {
        if(depthImpact == 0) {
            return "NONE";
        }

        if(depthImpact < 1000) {
            return "LOW";
        }

        if(depthImpact < 100000) {
            return "MEDIUM";
        }

        return "HIHG";
    }
}
