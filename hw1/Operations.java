public class Operations {

    public static void main(String[] args) {
        // Check if three arguments are provided
        if (args.length != 3) {
            System.out.println("Please provide three numbers in binary (0b prefix), hexadecimal (0x prefix), or decimal format.");
            return;
        }
        
        // Variables to store converted numbers
        long[] decimalNumbers = new long[3];
        
        // Parse and validate inputs
        for (int i = 0; i < 3; i++) {
            decimalNumbers[i] = parseInput(args[i]);
            if (decimalNumbers[i] == -1) {
                System.out.println("Invalid input: " + args[i]);
                return;
            }
        }
        
        // Convert numbers to other bases and calculate complements
        for (long number : decimalNumbers) {
            printConversionsAndComplements(number);
        }
        
        // Perform bitwise operations
        performBitwiseOperations(decimalNumbers);
        
        // Perform bitwise shifts
        performBitwiseShifts(decimalNumbers);
    }

    private static long parseInput(String input) {
        try {
            if (input.startsWith("0b")) {
                // Binary
                return Long.parseLong(input.substring(2), 2);
            } else if (input.startsWith("0x")) {
                // Hexadecimal
                return Long.parseLong(input.substring(2), 16);
            } else {
                // Decimal
                return Long.parseLong(input);
            }
        } catch (NumberFormatException e) {
            return -1; // Indicate invalid input
        }
    }

    private static void printConversionsAndComplements(long number) {
        System.out.println("Decimal: " + number);
        System.out.println("Binary: " + Long.toBinaryString(number));
        System.out.println("Hexadecimal: " + Long.toHexString(number));
        System.out.println("1's Complement: " + Long.toBinaryString(~number));
        System.out.println("2's Complement: " + Long.toBinaryString(~number + 1));
    }

    private static void performBitwiseOperations(long[] numbers) {
        long orResult = numbers[0] | numbers[1] | numbers[2];
        long andResult = numbers[0] & numbers[1] & numbers[2];
        long xorResult = numbers[0] ^ numbers[1] ^ numbers[2];
        
        System.out.println("Bitwise OR: " + orResult);
        System.out.println("Bitwise AND: " + andResult);
        System.out.println("Bitwise XOR: " + xorResult);
    }

    private static void performBitwiseShifts(long[] numbers) {
        for (long number : numbers) {
            System.out.println("Original: " + number);
            System.out.println("Left Shift (<< 2): " + (number << 2));
            System.out.println("Right Shift (>> 2): " + (number >> 2));
            System.out.println("Unsigned Right Shift (>>> 2): " + (number >>> 2));
        }
    }
}
