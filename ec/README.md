
## Credits
This code is created by Ariadna Shamraeva for CS250 at CSU for an Extra Crefit Assignment in Spring 2024.

## Folder Structure

The `cs250` workspace contains two folders, where:

- `ec`: the folder to maintain sources
- `lib`: the folder to maintain dependencies (default created by VSCode)

## ec Folder Structure

The `ec` folder contains three sub-folders that each contain one file:

- `convert` contains file `DecimalToBinary.java`. The file performs as a Decimal to Binary Converter (Task 1). This code converts a decimal number between 0 and 1 (exclusive) to a binary string. It handles inputs and ensures they are within the specified range.
 Command line execution for this file: java cs250.ec.convert.DecimalToBinary <decimal-number>

- `counting` contains file `AlphaHistogram.java`. This file is an Alphabet Histogram (Task 2). The code counts the occurrences of each letter in a provided sentence, treating uppercase and lowercase letters as the same. It uses a basic bubble sort to order the counts.
 Command line execution for this file: java cs250.ec.counting.AlphaHistogram “<sentence>"

- `order` contains file `Equation.java`. This file serves as a Mathematical Equation Solver (Task 3). This code computes the specified equation and formats the output to five decimal places. It validates the input to ensure it contains valid integers. The formula used is: 
 `f(x, y) = (log10(|xy|) - x^4) / (sqrt((xy)^2) + y^3 * x)`
 Command line execution for this file: java cs250.ec.order.Equation <x> <y>