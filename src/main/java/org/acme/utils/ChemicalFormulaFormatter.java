package org.acme.utils;
public class ChemicalFormulaFormatter {
    public static void main(String[] args) {
        String formulaWithSubscripts = "K(UO2)(AsO4)·3H2O[1]/242";
        String formattedFormula = formatChemicalFormula(formulaWithSubscripts);
        System.out.println(formattedFormula);
    }

    private static String formatChemicalFormula(String input) {
        // Split the input formula into parts before and after "/"
        String[] parts = input.split("/");

        // Check if there are numbers after "/"
        if (parts.length == 2) {
            String formula = parts[0];
            String subscriptNumbers = parts[1];

            // Apply subscript formatting based on the numbers
            for (int i = 0; i < subscriptNumbers.length(); i++) {
                char subscript = subscriptNumbers.charAt(i);
                int index = Character.getNumericValue(subscript);
                if (index > 0 && index <= formula.length()) {
                    formula = formula.substring(0, index - 1) + "<sub>" + formula.charAt(index - 1) + "</sub>" +
                            formula.substring(index);
                }
            }

            return formula;
        } else {
            // No numbers after "/", return the original formula
            return input;
        }
    }
}

