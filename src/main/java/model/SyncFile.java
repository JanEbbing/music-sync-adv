package model;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class SyncFile extends SyncElement {

    private File self;

    public SyncFile(File toCreateFrom) {
        this.self = toCreateFrom;
    }

    @Override
    public void calculateChecksum() throws NoSuchAlgorithmException,
            IOException {
        this.md5checksum = FileHasher.getMD5Checksum(this.self);
    }

}
