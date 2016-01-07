package syncTests;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;

import model.DirectoryTree;
import model.Syncer;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class testSyncCheck {

    private ArrayList<File> toTest1;
    private ArrayList<File> toTest2;
    private Syncer sync;

    @BeforeClass
    public static void setUpOnce() {
    }
    @Before
    public void setUp() throws Exception {
        this.toTest1 = new ArrayList<>();
        this.toTest2 = new ArrayList<>();
        this.sync = new Syncer();
        String[] dirs = { "Dir1Identical", "Dir2Different", "Dir2Different2",
                "Dir2Different3", "Dir2Different4", "Dir2Different5",
                "Dir2Different7", "Dir2Different8", "Dir3DiffHighLev" };
        ArrayList<String> preproc1 = new ArrayList<>(Arrays.asList(dirs));
        preproc1 = addPrefix("/LocalStorage/", preproc1);
        for (String s : preproc1) {
            this.toTest1.add(this.getResource(s));
        }
        dirs[dirs.length - 1] = "Dir3DiffHighLe";
        ArrayList<String> preproc2 = new ArrayList<>(Arrays.asList(dirs));
        preproc2 = addPrefix("/USBStorage/", preproc2);

        for (String s : preproc2) {
            this.toTest2.add(this.getResource(s));
        }
    }

    private File getResource(String path) throws URISyntaxException {
        URL url = this.getClass().getResource(path);
        return new File(url.getFile());
    }

    private ArrayList<String> addPrefix(String prefix,
            ArrayList<String> toChange) {
        ArrayList<String> result = new ArrayList<>();
        for (String s : toChange) {
            result.add(prefix + s);
        }
        return result;
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testSyncingWithExamples() throws NoSuchAlgorithmException,
            IOException {
        int iteration = 1;
        for (int i = 0, j = 0; (i < this.toTest1.size())
                && (j < this.toTest2.size()); i++, j++) {
            File localFile = this.toTest1.get(i);
            File usbFile = this.toTest2.get(j);

            System.out.println("Local: " + localFile.getAbsolutePath());
            System.out.println("USB: " + usbFile.getAbsolutePath());

            boolean result = this.sync.isSynced(new DirectoryTree(localFile),
                    new DirectoryTree(usbFile));
            switch (iteration) {
            case 1:
                assertTrue(result);
                System.out.println("Directories Identical");
                break;
            default:
                assertTrue(!result);
            }
            // Expected output: Identical, correct error output from 2-10
            iteration++;
        }
    }

}
