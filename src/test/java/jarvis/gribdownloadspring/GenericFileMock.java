/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jarvis.gribdownloadspring;

import java.io.File;
import org.apache.camel.component.file.GenericFile;

/**
 *
 * @author Adge
 */
public class GenericFileMock extends GenericFile<String> {

    public GenericFileMock(String fileName, boolean isDirectory) {
        this.setFileName(fileName);
        this.setDirectory(isDirectory);
        this.setFileNameOnly((new File(fileName)).getName());
    }

    @Override
    public char getFileSeparator() {
        return '/';
    }

}
