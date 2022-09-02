import java_cup.runtime.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

class Driver {
    public static void main(String[] argv) throws Exception{
        String fileName = "./input.txt";
        System.err.println("Following code will also be stored in the output .txt file: ");
        Parser p = new Parser(new Scanner(new InputStreamReader(
            new FileInputStream(fileName), StandardCharsets.UTF_8)));
        p.parse();
    }
}
