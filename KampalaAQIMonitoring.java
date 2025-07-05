import java.util.Random;
import java.util.Arrays;


public class KampalaAQIMonitoring {
    
    // AQI Categories for reference
    private static final String[] AQI_CATEGORIES = {
        "Good (0-50)", "Moderate (51-100)", "Unhealthy for Sensitive Groups (101-150)",
        "Unhealthy (151-200)", "Very Unhealthy (201-300)", "Hazardous (301+)"
    };
    
    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("KCCA AirQO - Kampala Air Quality Monitor");
        System.out.println("Smart City Initiative - 30 Day Analysis");
        System.out.println("========================================\n");
        
        try {
            // i) Generate 30 random AQI readings between 1 and 300
            int[] aqiReadings = generateAQIReadings(30);
            
            // Display daily readings
            displayDailyReadings(aqiReadings);
            
            // ii) Compute and display median AQI value
            double median = calculateMedian(aqiReadings);
            System.out.printf("📊 MEDIAN AQI VALUE: %.1f\n", median);
            System.out.println("   Category: " + getAQICategory((int)median));
            
            // iii) Identify and count hazardous days (AQI > 200)
            int hazardousDays = countHazardousDays(aqiReadings);
            System.out.printf("⚠️  HAZARDOUS DAYS (AQI > 200): %d days\n", hazardousDays);
            System.out.printf("   Percentage: %.1f%%\n", (hazardousDays / 30.0) * 100);
            
            // Additional analysis
            performAdditionalAnalysis(aqiReadings);
            
        } catch (Exception e) {
            System.err.println("❌ Error in AQI monitoring system: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Generates random AQI readings between 1 and 300
     * @param days Number of days to generate readings for
     * @return Array of AQI readings
     */
    private static int[] generateAQIReadings(int days) {
        Random random = new Random();
        int[] readings = new int[days];
        
        System.out.println("🌡️  GENERATING " + days + " DAYS OF AQI DATA FOR KAMPALA");
        System.out.println("   Range: 1-300 AQI units\n");
        
        for (int i = 0; i < days; i++) {
            // Generate random AQI between 1 and 300 (inclusive)
            readings[i] = random.nextInt(300) + 1;
        }
        
        return readings;
    }
    
    /**
     * Displays daily AQI readings in a formatted table
     * @param readings Array of AQI readings
     */
    private static void displayDailyReadings(int[] readings) {
        System.out.println("📅 DAILY AQI READINGS FOR KAMPALA:");
        System.out.println("Day  | AQI  | Category");
        System.out.println("-----|------|------------------");
        
        for (int i = 0; i < readings.length; i++) {
            String category = getAQICategory(readings[i]);
            System.out.printf("%2d   | %3d  | %s\n", (i + 1), readings[i], category);
        }
        System.out.println();
    }
    
    /**
     * Calculates median AQI value from the readings array
     * @param readings Array of AQI readings
     * @return Median value
     */
    private static double calculateMedian(int[] readings) {
        // Create a copy to avoid modifying original array
        int[] sortedReadings = Arrays.copyOf(readings, readings.length);
        Arrays.sort(sortedReadings);
        
        int length = sortedReadings.length;
        
        if (length % 2 == 0) {
            // Even number of elements - average of two middle elements
            int midIndex1 = length / 2 - 1;
            int midIndex2 = length / 2;
            return (sortedReadings[midIndex1] + sortedReadings[midIndex2]) / 2.0;
        } else {
            // Odd number of elements - middle element
            return sortedReadings[length / 2];
        }
    }
    
    /**
     * Counts the number of hazardous days (AQI > 200)
     * @param readings Array of AQI readings
     * @return Number of hazardous days
     */
    private static int countHazardousDays(int[] readings) {
        int hazardousCount = 0;
        
        System.out.println("🚨 HAZARDOUS DAYS ANALYSIS (AQI > 200):");
        
        for (int i = 0; i < readings.length; i++) {
            if (readings[i] > 200) {
                hazardousCount++;
                System.out.printf("   Day %d: AQI %d (%s)\n", 
                    (i + 1), readings[i], getAQICategory(readings[i]));
            }
        }
        
        if (hazardousCount == 0) {
            System.out.println("   ✅ No hazardous days recorded!");
        }
        
        System.out.println();
        return hazardousCount;
    }
    
    /**
     * Determines AQI category based on reading value
     * @param aqi AQI reading value
     * @return String describing AQI category
     */
    private static String getAQICategory(int aqi) {
        if (aqi <= 50) return "Good";
        else if (aqi <= 100) return "Moderate";
        else if (aqi <= 150) return "Unhealthy for Sensitive";
        else if (aqi <= 200) return "Unhealthy";
        else if (aqi <= 300) return "Very Unhealthy";
        else return "Hazardous";
    }
    
    /**
     * Performs additional statistical analysis on AQI data
     * @param readings Array of AQI readings
     */
    private static void performAdditionalAnalysis(int[] readings) {
        System.out.println("\n📈 ADDITIONAL KAMPALA AIR QUALITY ANALYSIS:");
        
        // Calculate average
        double average = Arrays.stream(readings).average().orElse(0.0);
        System.out.printf("   Average AQI: %.1f\n", average);
        
        // Find min and max
        int min = Arrays.stream(readings).min().orElse(0);
        int max = Arrays.stream(readings).max().orElse(0);
        System.out.printf("   Best Day: AQI %d (%s)\n", min, getAQICategory(min));
        System.out.printf("   Worst Day: AQI %d (%s)\n", max, getAQICategory(max));
        
        // Category distribution
        System.out.println("\n🏷️  AQI CATEGORY DISTRIBUTION:");
        int[] categoryCount = new int[6];
        
        for (int reading : readings) {
            if (reading <= 50) categoryCount[0]++;
            else if (reading <= 100) categoryCount[1]++;
            else if (reading <= 150) categoryCount[2]++;
            else if (reading <= 200) categoryCount[3]++;
            else if (reading <= 300) categoryCount[4]++;
            else categoryCount[5]++;
        }
        
        for (int i = 0; i < AQI_CATEGORIES.length; i++) {
            System.out.printf("   %s: %d days\n", AQI_CATEGORIES[i], categoryCount[i]);
        }
        
        // Health recommendations
        System.out.println("\n💡 HEALTH RECOMMENDATIONS FOR KAMPALA:");
        if (average <= 50) {
            System.out.println("   ✅ Air quality is generally good for outdoor activities");
        } else if (average <= 100) {
            System.out.println("   ⚠️  Moderate air quality - sensitive individuals should be cautious");
        } else if (average <= 150) {
            System.out.println("   ⚠️  Unhealthy for sensitive groups - limit outdoor exposure");
        } else {
            System.out.println("   🚨 Poor air quality - consider indoor activities and air purifiers");
        }
    }
}