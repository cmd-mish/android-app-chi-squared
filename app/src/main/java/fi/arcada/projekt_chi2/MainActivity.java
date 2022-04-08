package fi.arcada.projekt_chi2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // Deklarera 4 Button-objekt
    Button btn1, btn2, btn3, btn4;
    // Deklarera 4 heltalsvariabler för knapparnas värden
    int val1, val2, val3, val4;
    // Deklarera etiketter för rader och kolumner
    TextView textViewCol1, textViewCol2, textViewRow1, textViewRow2,
            textViewPercentageLabel, textViewPercentageCol1, textViewPercentageCol2;
    // Delrarera textView dit uträkningen presenteras
    TextView dataOutput;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        // Ställer upp etiketter
        textViewCol1.setText("Ny design");
        textViewCol2.setText("Gammal design");
        textViewRow1.setText("Köpte något");
        textViewRow2.setText("Köpte inte");
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

    /**
     * Metod som uppdaterar layouten och räknar ut själva analysen.
     */
    public void calculate() {

        // Uppdatera knapparna med de nuvarande värdena
        btn1.setText(String.valueOf(val1));
        btn2.setText(String.valueOf(val2));
        btn3.setText(String.valueOf(val3));
        btn4.setText(String.valueOf(val4));

        // Mata in värdena i Chi-2-uträkningen och ta emot resultatet
        // i en Double-variabel
        double chi2 = Significance.chiSquared(val1, val2, val3, val4);
        System.out.println(chi2);

        // Mata in chi2-resultatet i getP() och ta emot p-värdet
        double pValue = Significance.getP(chi2);

        String output = String.format("Chi-2 resultat: %.2f" +
                        "\n\np-värde %.3f",
                        chi2, pValue);

        dataOutput.setText(output);

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

    }


}