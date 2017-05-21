package disertatie.com.disertatie.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import disertatie.com.disertatie.Database.DatabaseHelper;
import disertatie.com.disertatie.R;
import disertatie.com.disertatie.entities.Material;
import disertatie.com.disertatie.entities.Taxa;

public class TaxeActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText etDenumireTaxa;
    private EditText etProcent;
    private Button btnAdaugaTaxa;
    private Button btnModificaTaxa;

    DatabaseHelper databaseHelper;
    private static String TAXA = "TAXA";
    private static int cod_taxa = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taxe);
        // toolbar setup
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            // use toolbar as actionbar
            setSupportActionBar(toolbar);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowTitleEnabled(false);
            }
            TextView tv = (TextView) toolbar.findViewById(R.id.toolbar_title);
            tv.setText(R.string.adauga_taxa);
        }

        etDenumireTaxa = (EditText) findViewById(R.id.etDenumireTaxa);
        etProcent = (EditText) findViewById(R.id.etProcentTaxa);
        btnAdaugaTaxa = (Button) findViewById(R.id.btnAdaugaTaxa);
        btnModificaTaxa = (Button) findViewById(R.id.btnModificaTaxa);


        databaseHelper = new DatabaseHelper(this);

        btnAdaugaTaxa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHelper.insertTaxa(etDenumireTaxa.getText().toString(),
                        Double.parseDouble(etProcent.getText().toString()));

                etDenumireTaxa.setText("");
                etProcent.setText("");
                finish();
            }
        });

        if (getIntent().getExtras() != null) {
            btnAdaugaTaxa.setVisibility(View.GONE);
            btnModificaTaxa.setVisibility(View.VISIBLE);
            Taxa taxa = (Taxa) getIntent().getExtras().getSerializable(TAXA);
            etDenumireTaxa.setText(taxa.getDenumire_taxa());
            etProcent.setText(taxa.getProcent_taxa() + "");
            cod_taxa = taxa.getCod_taxa();
        }

        btnModificaTaxa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHelper.updateTaxa(cod_taxa, etDenumireTaxa.getText().toString(),
                        Double.parseDouble(etProcent.getText().toString()));
                finish();
            }
        });
    }

    @Override
    public void finish() {
        Log.d("Result", "Result-ok=" + RESULT_OK);
        setResult(RESULT_OK);
        super.finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}