package cs250.ec.order;

/*  This class computes the specified mathematical equation with the provided
    integer inputs x and y.*/

public class Equation {

//  Executes the computation of the equation f(x, y).

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java cs250.ec.order.Equation <x> <y>");
            return;
        }

        try {
            int x = Integer.parseInt(args[0]);
            int y = Integer.parseInt(args[1]);

//  Compute the equation f(x, y) = (log10(|xy|) - x^4) / (sqrt((xy)^2) + y^3 * x)
            double xy = x * y;
            double numerator = Math.log10(Math.abs(xy)) - Math.pow(x, 4);
            double denominator = Math.sqrt(Math.pow(xy, 2)) + Math.pow(y, 3) * x;
            double result = numerator / denominator;

            System.out.printf("f(x,y) = %.5f\n", result);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter valid integers for x and y.");
        }
    }
}

