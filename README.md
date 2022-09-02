# LL-1-Calculator-Parser-And-Java-Translator
Implementation of a Parser evaluating simple mathematical expressions and Translator of string operation language into Java Code 🔠

## LL(1) Calculator Parser:

This part of the project implements a simple calculator, that accepts expressions with the numeric operations of addition (`+`), subtraction (`-`) and exponent (`**`).
Proper evaluation of expressions including parentheses (`()`) is also supported. Given the grammar for the above mathematical expressions,
we had to come up with an updated one that supports priority between the different operators and allows for proper evaluation.
In order for the grammar to be parsable, we had to get rid of **Left Recursion** and the **Ambiguity** stemming from the same unfolding of heads
of different rules. The final grammar, as follows:


```
1. Expr -> Term Expr2

2. Expr2 -> + Term Expr2
          | - Term Expr2
          | ε
          
3. Term -> Factor Exponent

4. Factor -> ( Expr )
           | Number
           
5. Exponent -> *
             | ε

6. Num  -> Digit Num2

7. Num2 -> Num
         | ε
         
8.  Digit -> 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9
```

### Compilation & Execution:

In order to compile the Java source code, enter the `/Calculator` directory and type: `make compile`.
With the *Parser* class at hand, run the calculator by typing: `make execute`.

Now type the mathematical expression of your choice into the terminal. If it follows the rules of the previously derived grammar, *Parser*
traverses the input successfully, computes and prints the evaluation of the expression. If not, parsing error is displayed.

## Translator to Java:

This part of the project implements a **Parser** and a **Translator** for a simple, Java-like language supporting basic string operations.
Supported operations include concatenation (`+`), function definitions and calls, conditionals (`if` & `else`).
Each *if* has followed by an *else*. Finally, the following logical expressions are supported:

* **X `prefix` Y** - whether X is a prefix of Y
* **X `suffix` Y** - whether X is a suffix of Y

Here *Parser*, which is based on a **Context Free Grammar**, translates the input string based language into Java.
**JavaCUP** is used for the generation of the parser. It is combined with an auto-generated lexer, produced by **JFlex**.

### Compilation & Execution:

In order to compile the Java source code, enter the `/Translator` directory and type: `make compile`.
With the *Parser* class at hand, run the translator by typing: `make execute`.

Now type a string based program of your choice into the terminal. If it follows the **Context Free Grammar** of the previously defines subset of *Java*,
mathematical expression of your choice into the terminal. If it follows the rules of the previously derived grammar, *Parser*
traverses the input and simultaneously prints the equivalent *Java* code. If not, parsing error is displayed.

### Input/Output Example:

* **Input Code**

```
findLangType(langName) {
    if ("Java" prefix langName)
        if(langName prefix "Java")
            "Static"
        else
            if("script" suffix langName)
                "Dynamic"
            else
                "Unknown"
    else
        if ("script" suffix langName)
            "Probably Dynamic"
        else
            "Unknown"
}

findLangType("Java")
findLangType("Javascript")
findLangType("Typescript")
```

* **Output Code**

```
import java.lang.*;

public class Main {
        public static void main(String[] args) {
                System.out.println(findLangType("Java"));
                System.out.println(findLangType("Javascript"));
                System.out.println(findLangType("Typescript"));
        }

        public static String findLangType(String langName) {
                 return (langName.startsWith("Java")) ? ("Java".startsWith(langName)) ? "Static" : (langName.endsWith("script")) ? "Dynamic" : "Unknown" : (langName.endsWith("script")) ? "Probably Dynamic " : "Unknown";
        }

}
```
### Further Information:

*Built as part of the course: Compilers , Summer of 2021. University of Athens, DiT.*

