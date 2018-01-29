package com.madhatters.wazan.controllers;

public class FormulaCalulator {

    public static double calculate(double weight, double height, double age, Gender gender) {
        double bmi = bmi(weight, height);
        double ibw = ibw(bmi, height, weight, gender);
        return bmr(ibw, height, age, gender);
    }

    private static double bmi(double weight, double height) {
        return weight / (Math.pow((height / 100), 2));
    }

    private static double ibw(double bmi,
                              double height,
                              double weight,
                              Gender gender) {
        double bwi = -1;
        switch (gender) {
            case MALE:
                bwi = ibwMale(bmi, height, weight);
                break;
            case FEMALE:
                bwi = ibwFemale(bmi, height, weight);
                break;
        }
        return bwi;
    }

    private static double ibwMale(double bmi, double height, double weight) {
        double ibw = ((height - 150) * 1.1) + 48.1;
        if (isObese(bmi)) {
            ibw = ((weight - ibw) * 0.5) + ibw;
        }
        return ibw;
    }

    private static double ibwFemale(double bmi, double height, double weight) {
        double ibw = ((height - 150) * 0.9) + 45.4;
        if (isObese(bmi)) {
            ibw = ((weight - ibw) * 0.5) + ibw;
        }
        return ibw;
    }

    private static double bmr(double ibw, double height, double age, Gender gender) {
        double bmr = -1;
        switch (gender) {
            case MALE:
                bmr = bmrMale(ibw, height, age);
                break;
            case FEMALE:
                bmr = brmFemale(ibw, height, age);
                break;
        }
        return bmr;
    }

    private static double bmrMale(double ibw, double height, double age) {
        return 66.5 + (13.8 * ibw) + (5 * height) - (6.8 * age);
    }

    private static double brmFemale(double ibw, double height, double age) {
        return 655.1 + (9.6 * ibw) + (1.9 * height) - (4.7 * age);
    }

    private static boolean isObese(double bmi) {
        return bmi > 30;
    }

    public enum Gender {
        MALE, FEMALE
    }
}
