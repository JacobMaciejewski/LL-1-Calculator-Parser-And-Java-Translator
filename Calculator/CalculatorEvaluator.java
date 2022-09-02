import java.io.InputStream;
import java.io.IOException;
import java.util.Stack;

// main class traversing the input
// parsing and evaluating the given arithmetic expression
class CalculatorEvaluator {
    private final InputStream input;

    // token that we are currently checking
    private int lookahead;

    private static int pow(int base, int exponent) {
        if (exponent < 0)
            return 0;
        if (exponent == 0)
            return 1;
        if (exponent == 1)
            return base;    
    
        if (exponent % 2 == 0) //even exp -> b ^ exp = (b^2)^(exp/2)
            return pow(base * base, exponent/2);
        else                   //odd exp -> b ^ exp = b * (b^2)^(exp/2)
            return base * pow(base * base, exponent/2);
    }


    public CalculatorEvaluator(InputStream input) throws IOException {
        this.input = input;
        lookahead = input.read();
    }

    // checks if the current token is the expected symbol
    // reads the next token
    private void consume(int symbol) throws IOException, ParseError {
        if (lookahead == symbol)
            lookahead = input.read();
        else
            throw new ParseError();
    }

    private boolean isDigit(int c) {
        return '0' <= c && c <= '9';
    }

    private boolean isNonZeroDigit(int c) {
        return '0' < c && c <= '9';
    }

    // translates the character into the corresponding digit
    private int evalDigit(int c) {
        return c - '0';
    }

    public int evaluate() throws IOException, ParseError {
        int value = Expr();

        // evaluation must end with a newline or an end of file symbol
        // implying that the whole input has been parsed successfully
        if (lookahead != -1 && lookahead != '\n')
            throw new ParseError();

        return value;
    }


    private int Expr() throws IOException, ParseError {

        return Term() + Expr2();
    }

    private int Expr2() throws IOException, ParseError {

        switch(lookahead) {
            case '+':
                consume(lookahead);
                return 1 * Term() + Expr2();
            case '-':
                consume(lookahead);
                return -1 * Term() + Expr2();
            default:
                return 0;
        }
    }

    private int Term() throws IOException, ParseError {

        int factor = Factor();
        int exponent = Exponent();

        return pow(factor, exponent);
    }

    private int Factor() throws IOException, ParseError {

        int factorValue;

        switch(lookahead) {
            case '(':
                consume(lookahead);
                factorValue = Expr();

                if(lookahead != ')'){
                    throw new ParseError();
                }
                consume(lookahead);
                return factorValue;
            default:
                return Number();
        }
    }

    private int Exponent() throws IOException, ParseError {

        switch(lookahead) {
            case '*':
                consume(lookahead);

                // check if the first asterisk is followed
                // by a second one
                switch(lookahead) {
                    case '*':
                        consume(lookahead);
                        break;
                    default:
                        throw new ParseError();
                }
                break;
            default: 
                // no asterisk in sight, empty string rule
                // backtrack to previous rule
                return 1;
        }

        int factor = Factor();
        int exponent = Exponent();

        return pow(factor, exponent);
    }



    // traverses the stack of digits representing the multidigit number
    // multiplies each digit with the corresponding power of 10 
    // constructing the initial number
    private int evaluateMultidigitNumber(Stack<Integer> digitsStack) {

        int multiplier = 1;
        int currentDigit;
        int multidigitNumber = 0;

        while(!digitsStack.isEmpty()) {

            currentDigit = digitsStack.pop();
            multidigitNumber += multiplier * currentDigit;

            multiplier *= 10;
        }  

        return multidigitNumber;
    }

    // checks if we are still traversing the digits of a number
    // if so, keep parsing
    private void Number2(Stack<Integer> digitsStack) throws IOException, ParseError {

        if(isDigit(lookahead))
        {
            digitsStack.push(evalDigit(lookahead));
            consume(lookahead);
            Number2(digitsStack);
        }
    }


    private int Number() throws IOException, ParseError {

        // each digit of a multidigit number
        // will be put into a stack so
        // we can reconstruct the corresponding number
        // multiplying each digit with the corresponding 10 power
        Stack<Integer> digitsStack = new Stack<Integer>();

        // number can be a single digit, evaluate and return it
        if(lookahead == '0') {

            consume(lookahead);
            return 0;
        }
        // traverse the digits, store them in a stack
        // reconstruct the multidigit number
        if(isNonZeroDigit(lookahead)) {
        
            digitsStack.push(evalDigit(lookahead));
            consume(lookahead);
            Number2(digitsStack);

            return evaluateMultidigitNumber(digitsStack);
        }

        throw new ParseError();

    }

}