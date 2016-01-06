package utils;

import java.io.File;

public class Toolbox {

    private static Toolbox singleton;
    private static final String HEXES = "0123456789ABCDEF";

    // Singleton pattern
    public static Toolbox getToolbox() {
        if (singleton == null) {
            singleton = new Toolbox();
            return singleton;
        }
        return singleton;
    }

    // Open file from resources
    public static File getResource(String resourcename) {
        ClassLoader classLoader = getToolbox().getClass().getClassLoader();
        return new File(classLoader.getResource(resourcename).getFile());
    }

    // Fast byte-array to hex String conversion
    public static String getHex(byte[] raw) {
        if (raw == null) {
            return null;
        }
        final StringBuilder hex = new StringBuilder(2 * raw.length);
        for (final byte b : raw) {
            hex.append(HEXES.charAt((b & 0xF0) >> 4)).append(
                    HEXES.charAt((b & 0x0F)));
        }
        return hex.toString();
    }

}
