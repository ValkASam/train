package file;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by Valk on 20.12.15.
 */
public class FileTest {
    public static void main(String[] arg) throws IOException {
        {
            File file = new File("d:/ q.txt");
            file.createNewFile();
            //
            Path path = Paths.get("d:/qq.txt");
            path.toFile().createNewFile();
        }
        //
        {
            ZipOutputStream zip = new ZipOutputStream(new FileOutputStream("d:/aaa.zip"));
            zip.putNextEntry(new ZipEntry("a.txt"));
            Path path = Paths.get("d:/qqq.txt");
            Files.copy(path, zip);
            zip.close();
        }
        {
            Properties properties = new Properties();
            properties.load(new FileInputStream("d:/a.properties"));
            System.out.println(properties.get("a"));
            System.out.println(properties.get("b"));
            properties.setProperty("a", "3");
            properties.store(new FileOutputStream("d:/a.properties"), "changed");
            System.out.println(properties.get("a"));
            System.out.println(properties.get("b"));
        }

        {
            RandomAccessFile randomAccessFile = new RandomAccessFile("d:/z.txt", "rw");
        }

        {
            StringReader stringReader = new StringReader("qqqqqqqq");
            StringWriter stringWriter = new StringWriter();
            stringWriter.write(new BufferedReader(stringReader).readLine());
            stringWriter.append("A").append("B");
            System.out.println(stringWriter);
            //
            Path file = Paths.get("d:/xx.txt");
            Files.setAttribute(file, "dos:readonly", true);
            PrintStream printStream = new PrintStream(file.toFile());
            printStream.print("asdf");
            System.out.println(printStream.checkError());
        }

    }

}
