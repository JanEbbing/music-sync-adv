package model;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.LinkedList;

public class SyncDirectory<E extends SyncElement> extends SyncElement {

    // TODO remove file again
    private File f;
    private String name;
    private Collection<SyncElement> children;

    public SyncDirectory(String name) {
        this.name = name;
        this.children = new LinkedList<SyncElement>();
    }

    public SyncDirectory(String name, File directoryToIndex)
            throws NoSuchAlgorithmException,
            IOException {
        this.f = directoryToIndex;
        this.name = name;
        this.children = new LinkedList<SyncElement>();
        for (File fileInDir : directoryToIndex.listFiles()) {
            if (fileInDir.isDirectory()) {
                // Handle Dir
                this.addChild(new SyncDirectory<E>(fileInDir.getName(),
                        fileInDir));
            } else {
                // Handle Files
                SyncFile sf = new SyncFile(fileInDir.getName(), fileInDir);
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

    @Override
    public boolean isSyncedWith(SyncElement other) {
        if(!(other instanceof SyncDirectory<?>)) {
            System.out.println("Comparing directory to file: " + this.name + " to " + other.getName());
            return false;
        }

        @SuppressWarnings("unchecked")
        SyncDirectory<E> compareTo = (SyncDirectory<E>) other;
        // TODO possibly unintuitive? Difficult to implement syncing like this
        if (!this.name.equals(compareTo.name)) {
            System.out.println("Folders are called differently: " + this.name
                    + " and " + compareTo.name);
            return false;
        }

        int sizeA = this.children.size();
        int sizeB = compareTo.children.size();
        if (sizeA != sizeB) {
            System.out
                    .println("Folders have different number of files/directories in them: "
                            + sizeA
                            + " and "
                            + sizeB
                            + ", name "
                            + compareTo.name
                            + " and "
                            + this.f.getAbsolutePath());
            return false;
        }

        // TODO: Change with some hashing to make it fast (O(1) per iteration)
        // instead of O(n)

        for (SyncElement e : this.children) {
            SyncElement partner = compareTo.getChild(e.getName());
            if (!e.isSyncedWith(partner)) {
                System.out
                        .println("Files are not identical  (according to our hashfunction) or directory contents are not identical "
                                + e.getName() + " in " + this.name);
                return false;
            }
        }
        return true;
    }

    @Override
    public String getName() {
        return this.name;
    }

    // TODO make faster
    public SyncElement getChild(String name) {
        for (SyncElement e : this.children) {
            if (e.getName().equals(name)) {
                return e;
            }
        }
        return null;
    }

}
