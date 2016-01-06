package model;

import java.io.IOException;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;

public class DirectoryTree {

    private SyncTreeNode<SyncElement> root;

    public DirectoryTree(Path directoryToIndex)
            throws NoSuchAlgorithmException, IOException {
        this.root = new SyncTreeNode<SyncElement>(directoryToIndex.toFile());
    }

    public boolean isSyncedWith(DirectoryTree compareTo) {
        // TODO
        return false;
    }

}
