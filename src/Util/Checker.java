package Util;
public class Checker {
    public static int parsePositiveInt(String text) throws PositiveIntegerException {
        try {
            int value = Integer.parseInt(text.trim());
            if (value <= 0) {
                throw new PositiveIntegerException("Value must be a positive integer");
            }
            return value;
        } catch (NumberFormatException e) {
            throw new PositiveIntegerException("Value must be a valid integer");
        }
    }

    public static double parsePositiveDouble(String text) throws PositiveDoubleException {
        try {
            double value = Double.parseDouble(text.trim());
            if (value <= 0) {
                throw new PositiveDoubleException("Value must be a positive integer");
            }
            return value;
        } catch (NumberFormatException e) {
            throw new PositiveDoubleException("Value must be a valid integer");
        }
    }

    public static String isAlphabet(String s)throws IsAlphabet{
        if( s != null && s.matches("[A-Za-z]+")){
            return s;
        }else {
            throw new IsAlphabet("Must have only characters");
        }
    }
}