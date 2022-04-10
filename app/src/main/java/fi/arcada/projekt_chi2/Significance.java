package fi.arcada.projekt_chi2;

import java.lang.reflect.Array;

public class Significance {

    /**
     * Metod som räknar ut Chi-två på basis av fyra observerade värden (o1 - o4).
     */
    public static double chiSquared(int o1, int o2, int o3, int o4) {
        /**
         *  Implementera din egen Chi-två-uträkning här!
         *
         *  1.  Räkna de förväntade värdena, spara resultaten i e1 - e4
         *
         *  2.  Använd de observerade värdena (o1 - o4) och de förväntade
         *      värdena (e1 - e4) för att räkna ut Chi-två enligt formeln.
         *
         *  3.  returnera resultatet
         *      (använd det sedan för att få p-värdet via getP()
         *
         * */

        // Deklarerar och räknar ut totala värden i rader och kolumner
        double totalRow1, totalRow2, totalCol1, totalCol2, totalGrand;

        totalRow1 = o1 + o2;
        totalRow2 = o3 + o4;
        totalCol1 = o1 + o3;
        totalCol2 = o2 + o4;
        totalGrand = o1 + o2 + o3 + o4;

        // förväntade värdena sparas i double variabler
        double e1, e2, e3, e4;

        e1 = totalRow1 * totalCol1 / totalGrand;
        e2 = totalRow1 * totalCol2 / totalGrand;
        e3 = totalRow2 * totalCol1 / totalGrand;
        e4 = totalRow2 * totalCol2 / totalGrand;

        // komponenter av Chi2 summan räknas enligt formeln
        double comp1, comp2, comp3, comp4, chi2;

        comp1 = Math.pow(o1 - e1, 2) / e1;
        comp2 = Math.pow(o2 - e2, 2) / e2;
        comp3 = Math.pow(o3 - e3, 2) / e3;
        comp4 = Math.pow(o4 - e4, 2) / e4;
        chi2 = comp1 + comp2 + comp3 + comp4;

        return chi2;
    }


    /**
     * Metod som tar emot resultatet från Chi-två-uträkningen
     * och returnerar p-värde enligt tabellen (en frihetsgrad)
     * (De mest extrema värdena har lämnats bort, det är ok för våra syften)
     *
     * exempel: getP(2.82) returnerar ett p-värde på 0.1
     *
     */
    public static double getP(double chiResult) {

        double p = 0.99;

        if (chiResult >= 1.642) p = 0.2;
        if (chiResult >= 2.706) p = 0.1;
        if (chiResult >= 3.841) p = 0.05;
        if (chiResult >= 5.024) p = 0.025;
        if (chiResult >= 5.412) p = 0.02;
        if (chiResult >= 6.635) p = 0.01;
        if (chiResult >= 7.879) p = 0.005;
        if (chiResult >= 9.550) p = 0.002;

        return p;

    }

    public static double getPercentage(int value1, int value2) {
        double dValue1 = Double.valueOf(value1);
        double dValue2 = Double.valueOf(value2);
        return  dValue1 / (dValue1 +  dValue2) * 100;
    };

    public static String getExplanation(double p, double a, String h0) {
        if(p <= a) {
            return "Resultatet är signifikant. Det är beroende med minst " + (100 - p * 100) + "% sannolikhet. Nollhypotesen \"" + h0 + "\" är inte sann.";
        } else {
            return "Resultatet är inte signifikant och oberoende med mist " + p * 100  + "% sannolikhet. Nollhypotesen \"" + h0 + "\" är sann.";
        }
    }
}
