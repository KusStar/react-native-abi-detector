package org.reactnative.abidetector;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class RNABIDetectorModule extends ReactContextBaseJavaModule {
    private static final String MODULE_NAME = "RNABIDetector";
    private static final int FLAGS_GET_APP_INFO = Build.VERSION.SDK_INT >= Build.VERSION_CODES.N ?
            PackageManager.MATCH_UNINSTALLED_PACKAGES
            : PackageManager.GET_UNINSTALLED_PACKAGES;

    private Context context;

    public RNABIDetectorModule(ReactApplicationContext reactContext) {
        super(reactContext);
        context = reactContext;
    }

    @Override
    public String getName() {
        return MODULE_NAME;
    }

    @Override
    public Map<String, Object> getConstants() {
        return ABIs.getAll();
    }

    @ReactMethod
    public void get(final Promise promise) {
        String packageName = context.getApplicationInfo().packageName;
        ApplicationInfo info = getAppInfo(packageName);
        if (info != null) {
            String abi = getABI(info.sourceDir, info.nativeLibraryDir);
            promise.resolve(abi);
        } else {
            promise.resolve(ABIs.UNKNOWN);
        }
    }

    private ApplicationInfo getAppInfo(final String packageName) {
        try {
            return context
                    .getPackageManager()
                    .getApplicationInfo(packageName, FLAGS_GET_APP_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getABI(String path, String nativePath) {
        String abi = ABIs.UNKNOWN;

        try {
            File file = new File(path);
            ZipFile zipFile = new ZipFile(file);
            String name;

            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                name = entries.nextElement().getName();

                if (name.contains("lib/")) {
                    if (name.contains("arm64-v8a")) {
                        abi = ABIs.ARMV8;
                    } else if (name.contains("armeabi-v7a")) {
                        if (!abi.equals(ABIs.ARMV8)) {
                            abi = ABIs.ARMV7;
                        }
                    } else if (name.contains("armeabi")) {
                        if (!abi.equals(ABIs.ARMV8) && !abi.equals(ABIs.ARMV7)) {
                            abi = ABIs.ARMV5;
                        }
                    } else if (name.contains("x86_64")) {
                        abi = ABIs.X86_64;
                    } else if (name.contains("x86")) {
                        if (!abi.equals(ABIs.X86_64)) {
                            abi = ABIs.X86;
                        }
                    }
                }
            }
            zipFile.close();

            if (abi.equals(ABIs.UNKNOWN)) {
                return getABIByNativeDir(nativePath);
            } else {
                return abi;
            }
        } catch (Exception e) {
            return ABIs.UNKNOWN;
        }
    }

    private String getABIByNativeDir(String nativePath) {
        File file = new File(nativePath.substring(0, nativePath.lastIndexOf("/")));
        List<String> abiList = new ArrayList<>();

        File[] fileList = file.listFiles();
        if (fileList == null) return ABIs.UNKNOWN;
        for (File it : fileList) {
            abiList.add(it.getName());
        }

        if (abiList.contains("arm64")) {
            return ABIs.ARMV8;
        } else if (abiList.contains("arm")) {
            return ABIs.ARMV7;
        } else if (abiList.contains("x86_64")) {
            return ABIs.X86_64;
        } else if (abiList.contains("x86")) {
            return ABIs.X86;
        } else {
            return ABIs.UNKNOWN;
        }
    }

}