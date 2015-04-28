package core;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class ConfigurationContrainer {
    private HashMap<String, Boolean> configurationSet ;
    private HashMap<String, Boolean> limitCycleSet;
    private boolean limitCycleDetected;

    public ConfigurationContrainer() {
        configurationSet = new LinkedHashMap<>();
        limitCycleDetected = false;
        limitCycleSet = new LinkedHashMap<>();
    }

//    public boolean insertConfiguration(String configuration) {
//        configurationSet.putIfAbsent(configuration, false);
//    }
//
//    public boolean isPresent(String configuration) {
//
//    }
//
//    public long retrieveLimitCycle(String firstDuplicateConf) 
//    {
//
//    }

}
