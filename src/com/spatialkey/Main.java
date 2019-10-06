package com.spatialkey;

import java.util.Map;

/**
 * A filler class to simulate the containing logic that would lead to an analysis kickoff.  Do not review this besides for context
 */
public class Main {

    public static void main(String[] args) {
        //Some commandline handling logic

        Policy[] hardcodedData = new Policy[] {
                new Policy("Fire", 200000.0, new Location("123 main street, Anytown, USA", 100000.0), new Location("1600 Pennsylvania Avenue, Washington DC, USA", 100000000.0)),
                new Policy("War", 2000000.0, new Location("1600 Pennsylvania Avenue, Washington DC, USA", 100000000.0)),
        };


        DataAnalysisService dataAnalysisService = new DataAnalysisService();
        Map results = dataAnalysisService.analyzeData(new String[]{"Fire", "War"}, hardcodedData);

        results.forEach((o, o2) -> System.out.println(o + ": " + o2));
    }
}
