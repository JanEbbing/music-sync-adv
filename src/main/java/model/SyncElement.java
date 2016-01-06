package model;

public abstract class SyncElement {

    protected String md5checksum;

    public String getChecksum() {
        return this.md5checksum;
    }

    public abstract void calculateChecksum() throws Exception;

}
