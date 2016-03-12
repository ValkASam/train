package file.zip;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by Valk on 21.01.16.
 */
public class ZipTest {
    public static void main(String[] args) throws IOException {
        ZipOutputStream zaos = new ZipOutputStream(new FileOutputStream("d:/a.zip"));
        zaos.putNextEntry(new ZipEntry("ff/ff/aaa.txt"));
        Path path = Paths.get("d:/f/aa.txt");
        Files.copy(path, zaos);
        //
        zaos.putNextEntry(new ZipEntry("aaa.txt"));
        Files.copy(path, zaos);
        //
        zaos.putNextEntry(new ZipEntry("ccc.txt"));
        zaos.write(new byte[]{'c','v','b'});
        zaos.close();
    }
}
