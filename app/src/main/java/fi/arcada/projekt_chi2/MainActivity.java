package fi.arcada.projekt_chi2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // Deklarera 4 Button-objekt
    Button btn1, btn2, btn3, btn4;
    // Deklarera 4 heltalsvariabler för knapparnas värden
    int val1, val2, val3, val4;
    double significance;
    String hypothesis;
    // Deklarera etiketter för rader och kolumner
    TextView textViewCol1, textViewCol2, textViewRow1, textViewRow2,
            textViewPercentageLabel, textViewPercentageCol1, textViewPercentageCol2;
    // Delrarera textView dit uträkningen presenteras
    TextView dataOutput, textViewDataOutputResult;

    // Objekt för preferences
    SharedPreferences sharedPref;
    SharedPreferences.Editor sharedPrefEditor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Deklarerar SharedPreferences
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        // Koppla samman Button-objekten med knapparna i layouten
        btn1 = findViewById(R.id.button1);
        btn2 = findViewById(R.id.button2);
        btn3 = findViewById(R.id.button3);
        btn4 = findViewById(R.id.button4);

        // Kopplar etiketter med textfälterna
        textViewCol1 = findViewById(R.id.textViewCol1);
        textViewCol2 = findViewById(R.id.textViewCol2);
        textViewRow1 = findViewById(R.id.textViewRow1);
        textViewRow2 = findViewById(R.id.textViewRow2);
        textViewPercentageLabel = findViewById(R.id.textViewPercentageLabel);
        textViewPercentageCol1 = findViewById(R.id.textViewPercentageCol1);
        textViewPercentageCol2 = findViewById(R.id.textViewPercentageCol2);

        dataOutput = findViewById(R.id.textViewDataOutput);
        textViewDataOutputResult = findViewById(R.id.textViewDataOutputResult);

        // Ställer upp etiketter

        textViewCol1.setText(sharedPref.getString("col1", "Kolumn 1"));
        textViewCol2.setText(sharedPref.getString("col2", "Kolumn 2"));
        textViewRow1.setText(sharedPref.getString("row1", "Rad 1"));
        textViewRow2.setText(sharedPref.getString("row2", "Rad 2"));
        textViewPercentageLabel.setText(textViewRow1.getText());
    }

    /**
     *  Klickhanterare för knapparna
     */
    public void buttonClick(View view) {

        // Skapa ett Button-objekt genom att type-casta (byta datatyp)
        // på det View-objekt som kommer med knapptrycket
        Button btn = (Button) view;

        // Kontrollera vilken knapp som klickats, öka värde på rätt vaiabel
        if (view.getId() == R.id.button1) val1++;
        if (view.getId() == R.id.button2) val2++;
        if (view.getId() == R.id.button3) val3++;
        if (view.getId() == R.id.button4) val4++;

        // Slutligen, kör metoden som ska räkna ut allt!
        calculate();
    }

    public void openSettings(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    /**
     * Metod som uppdaterar layouten och räknar ut själva analysen.
     */
    public void calculate() {
        /**
         *  - Visa chi2 och pValue åt användaren på ett bra och tydligt sätt!
         *
         *  - Visa procentuella andelen jakande svar inom de olika grupperna.
         *    T.ex. (val1 / (val1+val3) * 100) och (val2 / (val2+val4) * 100
         *
         *  - Analysera signifikansen genom att jämföra p-värdet
         *    med signifikansnivån, visa reultatet åt användaren
         *
         */

        String output;

        // Uppdatera knapparna med de nuvarande värdena
        btn1.setText(String.valueOf(val1));
        btn2.setText(String.valueOf(val2));
        btn3.setText(String.valueOf(val3));
        btn4.setText(String.valueOf(val4));

        // Resultatet i procent
        if (val1 != 0 && val3 != 0) {
            output = String.format("%s: \n%.0f %%", textViewCol1.getText(), Significance.getPercentage(val1, val3));
            textViewPercentageCol1.setText(output);
        }
        if (val2 != 0 && val4 != 0) {
            output = String.format("%s: \n%.0f %%", textViewCol2.getText(), Significance.getPercentage(val2, val4));
            textViewPercentageCol2.setText(output);
        }

        // Mata in värdena i Chi-2-uträkningen och ta emot resultatet
        // i en Double-variabel
        double chi2 = Significance.chiSquared(val1, val2, val3, val4);

        // Mata in chi2-resultatet i getP() och ta emot p-värdet
        double pValue = Significance.getP(chi2);
        significance = Double.parseDouble(sharedPref.getString("significance", "0.05"));
        hypothesis = sharedPref.getString("hypothesis", "Ingen skillnad mellan alternativen");

        output = String.format("Nollhypotes (H0): %s" +
                        "\n\nSignifikansnivå: %s" +
                        "\n\nChi-2 resultat: %.2f" +
                        "\n\nP-värde %.3f",
                        hypothesis, significance, chi2, pValue);
        dataOutput.setText(output);

        output = String.format("%s", Significance.getExplanation(pValue, significance, hypothesis));
        textViewDataOutputResult.setText(output);
    }

    public void resetValues(View view) {
        val1 = 0;
        val2 = 0;
        val3 = 0;
        val4 = 0;

        btn1.setText(String.valueOf(val1));
        btn2.setText(String.valueOf(val2));
        btn3.setText(String.valueOf(val3));
        btn4.setText(String.valueOf(val4));

        dataOutput.setText("");
        textViewDataOutputResult.setText("");
        textViewPercentageCol1.setText("");
        textViewPercentageCol2.setText("");
    }


}