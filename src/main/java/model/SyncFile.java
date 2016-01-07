package model;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class SyncFile extends SyncElement {

    private File self;
    private String name;

    public SyncFile(String name, File toCreateFrom) {
        this.name = name;
        this.self = toCreateFrom;
    }

    @Override
    public void calculateChecksum() throws NoSuchAlgorithmException,
            IOException {
        this.md5checksum = FileHasher.getMD5Checksum(this.self);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public boolean isSyncedWith(SyncElement other) {
        if (!(other instanceof SyncFile)) {
            System.out.println("Comparing file to directory: " + this.name
                    + " to " + other.getName());
            return false;
        }

        @SuppressWarnings("unchecked")
        SyncFile compareTo = (SyncFile) other;
        return this.md5checksum.equals(compareTo.md5checksum);
    }

}
