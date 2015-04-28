package core;

import java.util.LinkedHashMap;

public class ConfigurationContrainer {

    private LinkedHashMap<String, Long> configurationSet;
    private long nextConfigurationId;
    private boolean limitCycleDetected;
    private long limitCycleSize;
    private String lastConfig;

    public ConfigurationContrainer() {
        configurationSet = new LinkedHashMap<>();
        limitCycleDetected = false;
        nextConfigurationId = 1;
        limitCycleSize = -1;
        lastConfig = "";
    }

    public boolean insertConfiguration(String configuration) {
        Long duplicate = configurationSet.get(configuration);
        boolean isInserted;

        if (duplicate != null) {
            limitCycleDetected = true;
            limitCycleSize = nextConfigurationId - duplicate;
            isInserted = false;
        } else {
            configurationSet.put(configuration, nextConfigurationId);
            nextConfigurationId++;
            isInserted = true;
        }

        lastConfig = configuration;
        return isInserted;
    }

    public boolean isPresent(String configuration) {
        return configurationSet.containsKey(configuration);
    }

    public long retrieveLimitCycleSize() {
        return limitCycleSize;
    }

    public boolean cycleDetected() {
        return limitCycleDetected;

    }

    public String getLastConfig() {
       return lastConfig;
    }
}
