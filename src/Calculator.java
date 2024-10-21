import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.EmptyStackException;

public class Calculator implements ActionListener{

    private UserInterface ui;
    private StringBuilder infix;
    private double result;
    private boolean lastOperator;
    private DecimalFormat wholeNumber;
    private DecimalFormat fiveDecimals;
    
    Calculator(UserInterface ui){

        // INITIALIZE VARIABLES
        this.ui = ui;
        this.infix = new StringBuilder();
        this.result = 0;
        this.lastOperator = false;
        this.wholeNumber = new DecimalFormat("#");
        this.fiveDecimals = new DecimalFormat("#.#####");
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String command = e.getActionCommand();

        if (command.equals("=")) {

            try {
                if(lastOperator) {
                    return;
                }
                
                String infixStr = infix.toString();
                String postfix = TheShuntingYard.infixToPostfix(infixStr);
                result = TheShuntingYard.evaluatePostfix(postfix);
                String resultStr = "";

                if(Double.isInfinite(result) || Double.isNaN(result)) {
                    infix.setLength(0);
                    ui.getDisplay().setText("OVERFLOW");
                    throw new ArithmeticException("ERROR");
                }

                // FORMATTING OUTPUT
                if(result % 1 == 0) {
                    resultStr = wholeNumber.format(result);
                } else {
                    resultStr = fiveDecimals.format(result);
                }
  
                infix.setLength(0);
                infix.append(resultStr);
                ui.getDisplay().setText(infix.toString());
                lastOperator = true;
            }
            catch(EmptyStackException | IllegalArgumentException a) {
                infix.setLength(0);
                ui.getDisplay().setText("ERROR");
            }
        } 

        else if(command.equals("C")) {
            infix.setLength(0);
            ui.getDisplay().setText(infix.toString());
            lastOperator = false;
        } 

        else if (command.equals("<-")) {

            if(!infix.toString().isEmpty()) {
                infix.setLength(infix.length()-1);
                ui.getDisplay().setText(infix.toString());
            }
            lastOperator = false;
        } else {
            infix.append(command);
            ui.getDisplay().setText(infix.toString());
            lastOperator = false;
        }
    }        
}
