package model;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.LinkedList;

public class SyncTreeNode<E extends SyncElement> extends SyncElement {

    private Collection<SyncElement> children;

    public SyncTreeNode() {
        this.children = new LinkedList<SyncElement>();
    }

    public SyncTreeNode(File directoryToIndex) throws NoSuchAlgorithmException,
            IOException {
        this.children = new LinkedList<SyncElement>();
        for (File fileInDir : directoryToIndex.listFiles()) {
            if (fileInDir.isDirectory()) {
                // Handle Dir
                this.addChild(new SyncTreeNode<E>(fileInDir));
            } else {
                // Handle Files
                SyncFile sf = new SyncFile(fileInDir);
                sf.calculateChecksum();
                this.addChild(sf);
            }
        }
    }

    public <T extends SyncElement> void addChild(T child) {
        this.children.add(child);
    }

    public Collection<SyncElement> getChildren() {
        return this.children;
    }

    @Override
    public void calculateChecksum() throws Exception {
        this.md5checksum = "INVALID";
        throw new IllegalStateException("Should not be called on directories");
    }

}
