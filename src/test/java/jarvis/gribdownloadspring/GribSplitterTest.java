package jarvis.gribdownloadspring;

/*
 * #%L
 * Camel for GFS data via FTP
 * %%
 * Copyright (C) 2012 Adrian Jarvis
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


import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import org.junit.*;
import static org.junit.Assert.*;

public class GribSplitterTest {
    
    /**
     * Test of splitFile method, of class GribSplitter.
     */
    @Test
    public void testSplitFile() throws URISyntaxException, IOException {
        final URL resource = this.getClass().getResource("/fh.0192_tl.press_gr.2p5deg");
        File file = new File(resource.toURI());
        GribSplitter instance = new GribSplitter(new File("./target/"));
//        List result = instance.splitFile(file);
//        assertEquals(360, result.size());
    }
}
