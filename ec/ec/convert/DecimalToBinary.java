package cs250.ec.convert;

/*  Provides a method to convert a decimal number between 0 and 1 (exclusive)
    to its binary representation. */
public class DecimalToBinary {

//  Takes a decimal number as input and prints its binary representation.
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java cs250.ec.convert.DecimalToBinary <decimal-number>");
            return;
        }

        try {
            double decimal = Double.parseDouble(args[0]);
            if (decimal <= 0 || decimal >= 1) {
                System.out.println("The number must be between 0 and 1, exclusively.");
                return;
            }
            String binary = "0.";
            while (decimal > 0) {
                decimal *= 2;
                if (decimal >= 1) {
                    binary += "1";
                    decimal -= 1;
                } else {
                    binary += "0";
                }
            }
            System.out.println(args[0] + " -> " + binary);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Enter a valid decimal number.");
        }
    }
}
