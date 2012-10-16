package jarvis.gribdownloadspring;

import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Adge
 */
public class GfsFileNameFilterTest {
    
    /**
     * Test of accept method, of class GfsFileNameFilter.
     */
    @Test
    public void testAcceptDateDirectory() {
        GenericFileMock file = new GenericFileMock("RD.20121010", true);
        GfsFileNameFilter instance = new GfsFileNameFilter();
        boolean expResult = true;
        boolean result = instance.accept(file);
        assertEquals(expResult, result);
    }

    @Test
    public void testRejectBadDirectory() {
        GenericFileMock file = new GenericFileMock("foo", true);
        GfsFileNameFilter instance = new GfsFileNameFilter();
        boolean expResult = false;
        boolean result = instance.accept(file);
        assertEquals(expResult, result);
    }

    @Test
    public void testAcceptFileTypeDirectory() {
        GenericFileMock file = new GenericFileMock("RD.20121010/PT.grid_DF.gr2", true);
        GfsFileNameFilter instance = new GfsFileNameFilter();
        boolean expResult = true;
        boolean result = instance.accept(file);
        assertEquals(expResult, result);
    }

    @Test
    public void testRejectWrongFileTypeDirectory() {
        GenericFileMock file = new GenericFileMock("RD.20121010/PT.grid_DF.gr1", true);
        GfsFileNameFilter instance = new GfsFileNameFilter();
        boolean expResult = false;
        boolean result = instance.accept(file);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testAcceptFile() {
        GenericFileMock file = new GenericFileMock("RD.20121010/PT.grid_DF.gr2/fh.0066_tl.press_gr.1p0deg", false);
        GfsFileNameFilter instance = new GfsFileNameFilter();
        boolean expResult = true;
        boolean result = instance.accept(file);
        assertEquals(expResult, result);
    }

}
