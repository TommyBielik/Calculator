import java.util.HashMap;
import java.util.Stack;
import java.util.Scanner;

public class TheShuntingYard {

    private static final HashMap<Character, Integer> precedence = new HashMap<>();
    static {
        precedence.put('+', 2);
        precedence.put('-', 1);
        precedence.put('*', 3);
        precedence.put('%', 3);
        precedence.put('/', 4);
        precedence.put('^', 5);
    }

    // HELPER FUNCTIONS
    private static boolean isOperator(char c) {
        return precedence.containsKey(c);
    }

    private static int getPrecedence(char c) {
        return precedence.get(c);
    }


    public static boolean isPartOfNumber(char token, int index, char previousSymbol) {
        if(Character.isDigit(token) || token == ',' || (index == 0 && token == '-') || token == '-' && isOperator(previousSymbol)) {
            return true;
        }
        return false;
    }


    public static int handleNumbers(String infix, int index, StringBuilder output) {

        StringBuilder number = new StringBuilder();
        boolean hasDecimalPoint = false;

        output.append(infix.charAt(index));
        index++;

        while (index < infix.length() && (Character.isDigit(infix.charAt(index)) || (!hasDecimalPoint && infix.charAt(index) == ','))) {
            if(infix.charAt(index) == ',') {
                hasDecimalPoint = true;
            }

            number.append(infix.charAt(index++));
        }
                
        output.append(number).append(' ');
        return index;
    }

    public static void handleOperators(Stack<Character> holdingStack, char token, StringBuilder output) {

        while(!holdingStack.isEmpty() && isOperator(holdingStack.peek()) && getPrecedence(token) <= getPrecedence(holdingStack.peek())){
            output.append(holdingStack.pop()).append(' ');
        }
        holdingStack.push(token);
    }

    public static String infixToPostfix(String infix) {

        StringBuilder output = new StringBuilder();
        Stack<Character> holdingStack = new Stack<Character>();

        int i = 0;
        char previousSymbol = ' ';

        while(i < infix.length()) {

            char token = infix.charAt(i);

            // HANDLE NUMBERS
            if (isPartOfNumber(token, i, previousSymbol)) {
                i = handleNumbers(infix, i, output);
                previousSymbol = infix.charAt(i - 1);
            } 
            
            // HANDLE OPERATOR TOKENS
            else if(isOperator(token)){
                handleOperators(holdingStack, token, output);
                previousSymbol = infix.charAt(i);
                i++;
            }
            // HANDLE UNEXPECTED SPACES
            else if(token == ' ') {
                i++;
            }
        }

        // ADD REMAINING OPERATORS TO THE OUTPUT
        while(!holdingStack.isEmpty()) {
            output.append(holdingStack.pop()).append(' ');
        }

        return output.toString().trim();
    }


    public static double evaluatePostfix(String postfix) {

        Stack<Double> solveStack = new Stack<Double>();

        Scanner scanner = new Scanner(postfix);

        while (scanner.hasNext()) {

            if(scanner.hasNextDouble()) {
                solveStack.push(scanner.nextDouble());
            } else {

                char token = scanner.next().charAt(0);

                if(isOperator(token)) {

                    double b = solveStack.pop();
                    double a = solveStack.pop();

                    switch (token) {
                        case '+': solveStack.push(a + b); break;
                        case '-': solveStack.push(a - b); break;
                        case '*': solveStack.push(a * b); break;
                        case '/': solveStack.push(a / b); break;
                        case '%': solveStack.push(a % b); break;
                        case '^': solveStack.push(Math.pow(a, b)); break;
                    }
                }
            }
        }
        scanner.close();

        return solveStack.pop();
    }
}
