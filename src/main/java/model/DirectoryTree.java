package model;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class DirectoryTree {

    private SyncDirectory<SyncElement> root;

    public DirectoryTree(File toIndex)
            throws NoSuchAlgorithmException, IOException {
        this.root = new SyncDirectory<SyncElement>(toIndex.getName(), toIndex);
    }

    public boolean isSyncedWith(DirectoryTree compareTo) {
        return this.root.isSyncedWith(compareTo.root);
    }

}
