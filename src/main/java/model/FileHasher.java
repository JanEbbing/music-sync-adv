package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class FileHasher {

    // Default Hash version used
    private static final String HASH_VERSION = "MD5"; // Alternative: "SHA-1",
                                                      // "SHA-256"

    private static final String HEXES = "0123456789ABCDEF";

    // Helper function
    private static String getHex(byte[] raw) {
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

    // Overloaded function for comfort, uses default hash algorithm
    public static byte[] createChecksum(File f) throws IOException,
            NoSuchAlgorithmException {
        return createChecksum(f, FileHasher.HASH_VERSION);
    }

    // Actual implementation
    public static byte[] createChecksum(File f, String HASH_VERSION)
            throws IOException,
            NoSuchAlgorithmException {
        InputStream fis = new FileInputStream(f);

        byte[] buffer = new byte[1024];
        // Local variable, not class
        MessageDigest complete = MessageDigest.getInstance(HASH_VERSION);
        int numRead;
        do {
            numRead = fis.read(buffer);
            if (numRead > 0) {
                complete.update(buffer, 0, numRead);
            }
        } while (numRead != -1);
        fis.close();
        return complete.digest();
    }

    public static String getMD5Checksum(File f) throws IOException,
            NoSuchAlgorithmException {
        byte[] b = createChecksum(f);
        return getHex(b);
    }

    public static String getMD5Checksum(File f, String HASH_VERSION)
            throws IOException, NoSuchAlgorithmException {
        byte[] b = createChecksum(f);
        return getHex(b);
    }


}
