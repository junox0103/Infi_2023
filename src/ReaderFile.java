package src;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
public class ReaderFile
{
   private String file;

    public ReaderFile(String file) throws FileNotFoundException {
        this.file = file;
    }
public void lesen() throws IOException {
    int ch;


    FileReader fr = null;
    try {
        fr = new FileReader(file);
    } catch (FileNotFoundException fe) {
        System.out.println("File not found");
    }


    while ((ch = fr.read()) != -1)
        System.out.print((char) ch);


    fr.close();
}

}