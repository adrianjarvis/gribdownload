package jarvis.gribdownloadspring;

/*
 * #%L
 * Camel for GFS data via FTP
 * %%
 * Copyright (C) 2012 - 2014 Adrian Jarvis
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the 
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public 
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */


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
        GenericFileMock file = new GenericFileMock("RD.20121010/PT.grid_DF.gr2/fh.0006_tl.press_gr.1p0deg", false);
        GfsFileNameFilter instance = new GfsFileNameFilter();
        boolean expResult = true;
        boolean result = instance.accept(file);
        assertEquals(expResult, result);
    }

}
