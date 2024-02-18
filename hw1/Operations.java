package cs250.hw1;

public class Operations {

    public static void main(String[] args) {
        System.out.println("Task 1");
        if (args.length != 3) {
            System.out.println("Incorrect number of arguments given. Program Terminating");
            return;
        }
        System.out.println("Correct number of arguments given.");


        System.out.println("Task 2");
        for (String input : args) {
            System.out.println(input + "=" + identifyNumberingSystem(input));
        }


        System.out.println("Task 3");
            boolean allValid = true;
            for (String input : args) {
                boolean isValid = validateInput(input);
                System.out.println(input + "=" + (isValid ? "true" : "false"));
                if (!isValid) {
                    allValid = false;
                }
            }

            if (!allValid) {
                System.out.println("Invalid input detected. Program Terminating!");
                return; // Exit if any input is invalid
            }


        System.out.println("Task 4");
        for (String input : args) {
            long num = parseInputCustom(input);
            // Convert to binary and hexadecimal for display
            String binary = decimalToBinary(num);
            String hex = decimalToHex(num);
            System.out.println("Start=" + input + ",Binary=" + binary + ",Decimal=" + num + ",Hexadecimal=" + hex);
        }


        // Task 5: 1's Complement
        System.out.println("Task 5");
        for (String input : args) {
            long num = parseInputCustom(input);
            String binary = decimalToBinary(num).substring(2); // Remove '0b' prefix
            String onesComp = onesComplement(binary); // Calculate 1's complement
            System.out.println(input + "=" + binary + "=>" + onesComp);
        }


        // Task 6: 2's Complement
        System.out.println("Task 6");
        for (String input : args) {
            long num = parseInputCustom(input);
            String binary = decimalToBinary(num).substring(2); // Remove '0b' prefix
            String twosComp = twosComplement(binary); // Calculate 2's complement
            System.out.println(input + "=" + binary + "=>" + twosComp);
        }


        // Task 7 - Bitwise Operations without using |, &, ^
        if (args.length >= 3) { // Ensure there are at 3 two numbers provided
            long num1 = parseInputCustom(args[0]);
            long num2 = parseInputCustom(args[1]);
            long num3 = parseInputCustom(args[2]);
            System.out.println("Task 7");
            performBitwiseOperations(num1, num2, num3);
        }


        // Task 8 - Bit Shifts without using <<, >>
        System.out.println("Task 8");
        if (args.length >= 3) { // Ensure there are at least three numbers provided
            // Parse input numbers and convert to binary strings, excluding '0b' prefix
            String binary1 = decimalToBinary(parseInputCustom(args[0])).substring(2);
            String binary2 = decimalToBinary(parseInputCustom(args[1])).substring(2);
            String binary3 = decimalToBinary(parseInputCustom(args[2])).substring(2);
            int shiftAmount = 2; // Example shift amount, adjust as necessary

            // Perform left and right shift operations on the binary representations
            performShiftOperations(binary1, binary2, binary3, shiftAmount);
        } else {
            System.out.println("Please provide at least three numbers as arguments.");
        }

    }


    //*********************************************
    // **************** For Task 2 ****************
    //*********************************************

    // Identifies the numbering system based on the input prefix
    private static String identifyNumberingSystem(String input) {
        if (input.startsWith("0b")) {
            return "Binary";
        } else if (input.startsWith("0x")) {
            return "Hexadecimal";
        } else if (input.matches("\\d+")) {
            // If the input consists only of digits, it's identified as "Decimal"
            return "Decimal";
        } else {
            // If the input is anything else, it's "not a number"
            return "not a number";
        }
    }
    

    //*********************************************
    // **************** For Task 3 ****************
    //*********************************************

    // Validates the input based on its identified numbering system
    
    private static boolean validateInput(String input) {
        if (input.startsWith("0b")) {
            // Binary validation: check if the rest of the string contains only '0' or '1'
            return input.substring(2).matches("[01]+");
        } else if (input.startsWith("0x")) {
            // Hexadecimal validation: check if the rest of the string contains only hexadecimal digits (0-9, a-f, A-F)
            return input.substring(2).matches("[0-9a-fA-F]+");
        } else {
            // Decimal validation: check if the string contains only digits (0-9)
            return input.matches("\\d+");
        }
    }    


    //*********************************************
    // **************** For Task 4 ****************
    //*********************************************

    // MANUALLY arses the input into a decimal long value
    private static long parseInputCustom(String input) {
        try {
            if (input.startsWith("0b")) {
                // Manually parse binary
                return manualParseBinary(input.substring(2));
            } else if (input.startsWith("0x")) {
                // Manually parse hexadecimal
                return manualParseHexadecimal(input.substring(2));
            } else {
                // Manually arse decimal directly, assuming all decimal inputs are valid numbers
                long result = 0;
                for (int i = 0; i < input.length(); i++) {
                    result = result * 10 + (input.charAt(i) - '0');
                }
                return result;
            }
        } catch (Exception e) {
            System.out.println("Invalid input format: " + input);
            return -1; // Indicate parsing error
        }
    }
    
    private static long manualParseBinary(String binary) {
        long result = 0;
        for (int i = 0; i < binary.length(); i++) {
            result <<= 1;
            result += binary.charAt(i) - '0';
        }
        return result;
    }
    
    private static long manualParseHexadecimal(String hex) {
        long result = 0;
    for (int i = 0; i < hex.length(); i++) {
        char c = hex.charAt(i);
        int digit = Character.digit(c, 16);
        result = (result << 4) + digit;
    }
    return result;
}


    // Manually convert a decimal number to a binary string
    private static String decimalToBinary(long value) {
        if (value == 0) return "0b0";
        StringBuilder binary = new StringBuilder();
        while (value > 0) {
            binary.insert(0, (value % 2));
            value /= 2;
        }
        return "0b" + binary.toString();
    }

    // Manually convert a decimal number to a hexadecimal string
    private static String decimalToHex(long value) {
        if (value == 0) return "0x0";
        StringBuilder hex = new StringBuilder();
        char[] hexChars = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        while (value > 0) {
            hex.insert(0, hexChars[(int) (value % 16)]);
            value /= 16;
        }
        return "0x" + hex.toString();
    }

    //*********************************************
    // **************** For Task 5 ****************
    //*********************************************

    // Calculates the 1's complement of a binary string
    private static String onesComplement(String binary) {
        StringBuilder result = new StringBuilder();
        for (char bit : binary.toCharArray()) {
            if (bit == '0') {
                result.append('1');
            } else {
                result.append('0');
            }
        }
        return result.toString();
    }
    
    
    //*********************************************
    // **************** For Task 6 ****************
    //*********************************************
    
    // Calculates the 2's complement of a binary string
    private static String twosComplement(String binary) {
        // First, calculate the 1's complement by flipping all bits
        String onesComp = onesComplement(binary); // Reuse Task 5's method for 1's complement
    
        // Then, manually add 1 to the 1's complement to get the 2's complement
        // Start from the end of the string and move towards the beginning
        StringBuilder result = new StringBuilder(onesComp);
        int carry = 1; // Initialize carry as 1, since we're effectively adding 1
        for (int i = onesComp.length() - 1; i >= 0; i--) {
            if (carry == 0) {
                break; // No carry left, break out of the loop
            }
            if (result.charAt(i) == '1') {
                result.setCharAt(i, '0'); // Flip and continue carry
            } else {
                result.setCharAt(i, '1'); // Flip and clear carry
                carry = 0;
            }
        }
    
        // If carry is still 1 after processing all bits, prepend a '1' to the result
        if (carry == 1) {
            result.insert(0, '1');
        }
    
        return result.toString();
    }


    //*********************************************
    // **************** For Task 7 ****************
    //*********************************************

    private static void performBitwiseOperations(long num1, long num2, long num3) {
        String binary1 = decimalToBinary(num1).substring(2); // Exclude "0b" prefix
        String binary2 = decimalToBinary(num2).substring(2); // Exclude "0b" prefix
        String binary3 = decimalToBinary(num3).substring(2); // Exclude "0b" prefix
        StringBuilder sb1 = new StringBuilder(binary1);
        StringBuilder sb2 = new StringBuilder(binary2);
        StringBuilder sb3 = new StringBuilder(binary3);
        

        String orResultBinary = bitwiseOR(sb1.toString(), sb2.toString(), sb3.toString());
        String andResultBinary = bitwiseAND(sb1.toString(), sb2.toString(), sb3.toString());
        String xorResultBinary = bitwiseXOR(sb1.toString(), sb2.toString(), sb3.toString());

        System.out.println(binary1 + "|" + binary2 + "|" + binary3 + "=" + orResultBinary);
        System.out.println(binary1 + "&" + binary2 + "&" + binary3 + "=" + andResultBinary);
        System.out.println(binary1 + "^" + binary2 + "^" + binary3 + "=" + xorResultBinary);
    }

    private static String bitwiseOR(String binary1, String binary2, String binary3) {
        // Determine the maximum length of the three binary strings
        int maxLength = Math.max(binary1.length(), Math.max(binary2.length(), binary3.length()));
        
        // Prepend zeros to make all strings the same length
        while (binary1.length() < maxLength) {
            binary1 = "0" + binary1;
        }
        while (binary2.length() < maxLength) {
            binary2 = "0" + binary2;
        }
        while (binary3.length() < maxLength) {
            binary3 = "0" + binary3;
        }
    
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < maxLength; i++) {
            // Append '1' to the result if any corresponding bit is '1'
            if (binary1.charAt(i) == '1' || binary2.charAt(i) == '1' || binary3.charAt(i) == '1') {
                result.append('1');
            } else {
                result.append('0');
            }
        }
        return result.toString();
    }    


    private static String bitwiseAND(String binary1, String binary2, String binary3) {
        // Determine the maximum length of the three binary strings
        int maxLength = Math.max(binary1.length(), Math.max(binary2.length(), binary3.length()));
        
        // Prepend zeros to make all strings the same length
        while (binary1.length() < maxLength) {
            binary1 = "0" + binary1;
        }
        while (binary2.length() < maxLength) {
            binary2 = "0" + binary2;
        }
        while (binary3.length() < maxLength) {
            binary3 = "0" + binary3;
        }
    
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < maxLength; i++) {
            // Append '1' to the result if all corresponding bits are '1'
            if (binary1.charAt(i) == '1' && binary2.charAt(i) == '1' && binary3.charAt(i) == '1') {
                result.append('1');
            } else {
                result.append('0');
            }
        }
        return result.toString();
    }
    

    private static String bitwiseXOR(String binary1, String binary2, String binary3) {
        // Determine the maximum length of the three binary strings
        int maxLength = Math.max(binary1.length(), Math.max(binary2.length(), binary3.length()));
        
        // Prepend zeros to make all strings the same length
        while (binary1.length() < maxLength) {
            binary1 = "0" + binary1;
        }
        while (binary2.length() < maxLength) {
            binary2 = "0" + binary2;
        }
        while (binary3.length() < maxLength) {
            binary3 = "0" + binary3;
        }
    
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < maxLength; i++) {
            // Count the number of 1s in the current bit position
            int countOnes = 0;
            if (binary1.charAt(i) == '1') countOnes++;
            if (binary2.charAt(i) == '1') countOnes++;
            if (binary3.charAt(i) == '1') countOnes++;
    
            // If the count of 1s is odd, the result bit is 1; otherwise, it's 0
            result.append((countOnes % 2 == 1) ? '1' : '0');
        }
        return result.toString();
    }
    


    //*********************************************
    // **************** For Task 8 ****************
    //*********************************************

        private static String leftShiftBinaryString(String binary, int shiftAmount) {
            // Append '0's to the end of the binary string
            return binary + "0".repeat(shiftAmount);
        }

        
        private static String rightShiftBinaryString(String binary, int shiftAmount) {
            // Check if the binary string is shorter than the shift amount
            if (binary.length() <= shiftAmount) {
                // If so, return a binary string that represents 0
                return "";
            } else {
                // Otherwise, trim 'shiftAmount' digits from the end of the binary string
                return binary.substring(0, binary.length() - shiftAmount);
            }
        }

    
        private static void performShiftOperations(String binary1, String binary2, String binary3, int shiftAmount) {
            // Perform left shift
            String leftShiftedBinary1 = leftShiftBinaryString(binary1, shiftAmount);
            String leftShiftedBinary2 = leftShiftBinaryString(binary2, shiftAmount);
            String leftShiftedBinary3 = leftShiftBinaryString(binary3, shiftAmount);
    
            // Perform right shift
            String rightShiftedBinary1 = rightShiftBinaryString(binary1, shiftAmount);
            String rightShiftedBinary2 = rightShiftBinaryString(binary2, shiftAmount);
            String rightShiftedBinary3 = rightShiftBinaryString(binary3, shiftAmount);
    
            // Print the results for left  and right shifts
            
            System.out.println(binary1 + "<<2=" + leftShiftedBinary1 + "," + binary1 + ">>2=" + rightShiftedBinary1);
            System.out.println(binary2 + "<<2=" + leftShiftedBinary2 + "," + binary2 + ">>2=" + rightShiftedBinary2);
            System.out.println(binary3 + "<<2=" + leftShiftedBinary3 + "," + binary3 + ">>2=" + rightShiftedBinary3);

        }
    
}
