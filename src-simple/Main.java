import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        String path = "./texts/dorrie.txt";//argv[0];
        var reader = new FileReader(path);
        var buf = new char[20];
        try {
            reader.read(buf);
            System.out.println(buf);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}