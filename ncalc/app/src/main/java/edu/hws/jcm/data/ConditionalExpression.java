package edu.hws.jcm.data;

/**
 * A "conditional expression" is an expression using the "?" operator, such as "(x > 0)? x : -x"
 * or "x <> 3 ? 1 / (x - 3)".  Note that the second case, which follows the ":", is optional.
 * If not present, the expression has the value Double.NaN when the boolean condition evaluates
 * to false.  A ConditionalExpression object is generated by a parser when it encounters a
 * "?" operator in the string it is parsing (provided the BOOLEANS option is turned on in that parser).
 * <p>A ConditionalExpression object holds the two expressions that are the cases in a conditional expression.
 * Note that the boolean condition is NOT stored in the ConditionalExpression object; it is part of
 * the ExpressionProgram for the expression in which the conditional expression occurs.
 * A ConditionalExpression is an ExpressionCommand, meaning that it can occur in an ExpressionProgram.
 * When the apply() method in this class is called, the boolean condition has already been evaluated,
 * and the result is on the top of the stack.  The ConditionalExpression will look at this result
 * and replace it with the value of one of the two expressions that it contains.
 * <p>It is unlikely that there will be any reason for anyone to use or understand this class,
 * except possibly as an example of an ExpressionCommand.
 */
public class ConditionalExpression implements ExpressionCommand {

    private ExpressionProgram trueCase;    // Expression to be evaluated if condition is true.

    private ExpressionProgram falseCase;   // Expression to be evaluated if condition is false.
    // This can be null, representing the value Double.NaN.


    /**
     * Create a ConditionalExpression object containing the two given expressions.
     * trueCase must not be null, but falseCase can be null.
     */
    public ConditionalExpression(ExpressionProgram trueCase, ExpressionProgram falseCase) {
        this.trueCase = trueCase;
        this.falseCase = falseCase;
    }


    // ---------------- Methods from the ExpressionCommand interface -------------------------

    /**
     * Apply this ConditionalExpression to the stack.
     * (Get the top item from the stack.  If it is non-zero (representing a boolean
     * value of true), evaluate trueCase and put it's value on the stack.  Otherwise,
     * evaluate falseCase and put its value on the stack.  If cases is non-null, record
     * case values for the boolean condition and for the expression that is evaluated.)
     */
    public void apply(StackOfDouble stack, Cases cases) {
        double test = stack.pop();
        if (cases != null)
            cases.addCase((int) test);
        if (test != 0)
            stack.push(trueCase.getValueWithCases(cases));
        else if (falseCase != null)
            stack.push(falseCase.getValueWithCases(cases));
        else
            stack.push(Double.NaN);
    }

    /**
     * Add commands to deriv that evaluate the derivative of this conditional expression with
     * respect to the variable wrt.  Assume that the ConditionalExpression Object
     * occurs in the program prog at index myIndex.
     * (The derivative of a conditional expression is another conditional expression.
     * The boolean test for the derivative is the same as the test for this expression,
     * so copy that test onto deriv.  Than add a new ConditionalExpression object to deriv
     * whose trueCase is the derivative of this.trueCase and whose falseCase is the derivative of
     * this.falseCase.)
     */
    public void compileDerivative(ExpressionProgram prog, int myIndex, ExpressionProgram deriv, Variable wrt) {
        prog.copyExpression(myIndex - 1, deriv);
        ExpressionProgram trueDeriv = (ExpressionProgram) trueCase.derivative(wrt);
        ExpressionProgram falseDeriv = (falseCase == null) ? null : (ExpressionProgram) falseCase.derivative(wrt);
        deriv.addCommandObject(new ConditionalExpression(trueDeriv, falseDeriv));
    }

    /**
     * Assume that this ConditionalExpression object occurs in prog at index myIndex.
     * Compute the total number of commands in prog used by the conditional expression,
     * including the boolean test, which occurs in prog at position myIndex-1.
     * (The number of commands in prog used by the conditional expression is 1 (for the
     * ConditionalExpression object itself) plus the number of commands in the
     * boolean condition.)
     */
    public int extent(ExpressionProgram prog, int myIndex) {
        return 1 + prog.extent(myIndex - 1);
    }

    /**
     * Returns true if x occurs in either the trueCase or the falseCase expression.
     */
    public boolean dependsOn(Variable x) {
        return trueCase.dependsOn(x) || (falseCase != null && falseCase.dependsOn(x));
    }

    /**
     * Append the string representation of the expression (including the boolean
     * condition) to the buffer.  Assume that this ConditionalExpression occurs as
     * a command in prog at index myIndex (so the boolean condition starts at index myIndex-1).
     */
    public void appendOutputString(ExpressionProgram prog, int myIndex, StringBuffer buffer) {
        buffer.append('(');
        prog.appendOutputString(myIndex - 1, buffer);
        buffer.append(") ? (");
        buffer.append(trueCase.toString());
        buffer.append(')');
        if (falseCase != null) {
            buffer.append(" : (");
            buffer.append(falseCase.toString());
            buffer.append(')');
        }
    }

} // end class ConditionalExpression

