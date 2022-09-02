import java.io.IOException;

class Main {
    public static void main(String[] args) {
        try {
            System.out.println((new CalculatorEvaluator(System.in)).evaluate());
        } catch (IOException | ParseError error) {
            System.err.println(error.getMessage());
        }
    }
}