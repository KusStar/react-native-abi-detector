package org.reactnative.abidetector;

import java.util.HashMap;
import java.util.Map;

public class ABIs {
    public static final String ARMV8 = "arm64-v8a";
    public static final String ARMV7 = "armeabi-v7a";
    public static final String ARMV5 = "armeabi";
    public static final String X86_64 = "x86_64";
    public static final String X86 = "x86";
    public static final String UNKNOWN = "unknown";

    static Map<String, Object> getAll() {
        Map<String, Object> res = new HashMap<>();

        res.put("ARMV8", ARMV8);
        res.put("ARMV7", ARMV7);
        res.put("ARMV5", ARMV5);
        res.put("X86_64", X86_64);
        res.put("X86", X86);
        res.put("UNKNOWN", UNKNOWN);

        return res;
    }
}
