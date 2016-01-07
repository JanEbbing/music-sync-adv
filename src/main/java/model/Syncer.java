package model;

public class Syncer {

    public boolean isSynced(DirectoryTree d1, DirectoryTree d2) {
        return d1.isSyncedWith(d2);
    }

}
