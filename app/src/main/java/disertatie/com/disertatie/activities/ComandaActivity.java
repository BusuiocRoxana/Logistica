package disertatie.com.disertatie.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.util.ArrayList;

import disertatie.com.disertatie.Database.DatabaseHelper;
import disertatie.com.disertatie.R;
import disertatie.com.disertatie.entities.CerereOferta;
import disertatie.com.disertatie.entities.Comanda;
import disertatie.com.disertatie.entities.Furnizor;
import disertatie.com.disertatie.entities.Taxa;


public class ComandaActivity extends AppCompatActivity {

    private static final String TAG = "Logistica";
    TextView tvCodCerereOferta;
    TextView tvMaterial;
    TextView tvFurnizor;
    TextView tvValoare;
    EditText etCantitate;
    EditText etPret;
    Spinner spinnerTaxa;
    TextView tvProcentTaxa;
    TextView tvDataLivrare;
    Button btnTrimiteComanda;
    private Toolbar toolbar;
    private static final String CERERE_OFERTA = "CERERE_OFERTA";

    Comanda comanda = new Comanda();
    private ArrayList<Taxa> listaTaxe = new ArrayList<>();
    DatabaseHelper databaseHelper;
    Context context;
    double valoareTotala;
    double valoareTaxa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comanda);

        context = this;
        // toolbar setup
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowTitleEnabled(false);
            }
            TextView tv = (TextView) toolbar.findViewById(R.id.toolbar_title);
            tv.setText(R.string.comanda);
        }

        tvCodCerereOferta = (TextView) findViewById(R.id.tvCodCerereOferta);
        tvMaterial = (TextView) findViewById(R.id.tvMaterial);
        tvFurnizor = (TextView) findViewById(R.id.tvFurnizor);
        tvProcentTaxa = (TextView) findViewById(R.id.tvProcentTaxa);
        tvDataLivrare = (TextView) findViewById(R.id.tvDataLivrare);
        tvValoare = (TextView)findViewById(R.id.tvValoare);
        etCantitate = (EditText) findViewById(R.id.etCantitate);
        etPret = (EditText) findViewById(R.id.etPret);
        btnTrimiteComanda = (Button) findViewById(R.id.btnTrimiteComanda);
        spinnerTaxa = (Spinner)findViewById(R.id.spinnerTaxe);

        if (getIntent().getExtras() != null) {
            CerereOferta cerereOferta = (CerereOferta) getIntent().getExtras().get(CERERE_OFERTA);

            tvCodCerereOferta.setText(cerereOferta.getCod_cerere_oferta()+"");
            tvMaterial.setText(cerereOferta.getMaterial().getDenumire_material());
            tvFurnizor.setText(cerereOferta.getFurnizor().getDenumire_furnizor());
            tvDataLivrare.setText(cerereOferta.getData_livrare());
            etCantitate.setText(cerereOferta.getCantitate()+"");
            etPret.setText(cerereOferta.getPret()+"");

        comanda.setCerereOferta(cerereOferta);
        }

        Toast.makeText(this, "Alegeti taxa aplicata", Toast.LENGTH_SHORT).show();
        databaseHelper = new DatabaseHelper(context);
        try {
            listaTaxe = databaseHelper.selecteazaTaxe();
            Log.d(TAG,"taxe="+ listaTaxe.toString());

        } catch (ParseException e) {
            e.printStackTrace();
        }

        ArrayAdapter<Taxa> spinnerTaxeArrayAdapter = new ArrayAdapter<Taxa>(context,
                android.R.layout.simple_spinner_dropdown_item, listaTaxe);

        spinnerTaxa.setAdapter(spinnerTaxeArrayAdapter);

        spinnerTaxa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                comanda.setTaxa(listaTaxe.get(position));
                tvProcentTaxa.setText(comanda.getTaxa().getProcent_taxa()+"");
                valoareTaxa =  comanda.getCerereOferta().calculeazaValoare()*comanda.getTaxa().getProcent_taxa()/100;
                valoareTotala =valoareTaxa+comanda.getCerereOferta().calculeazaValoare();
                tvValoare.setText(valoareTotala+"");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnTrimiteComanda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             long  row_id =  databaseHelper.insertComanda(comanda);
             int cod_comanda =-1;
               if(row_id != -1) {
                    cod_comanda =  databaseHelper.getPrimaryKeyByRowId(row_id, DatabaseHelper.TABLE_COMENZI, DatabaseHelper.COLUMN_COD_COMANDA);
                    Log.e(TAG, "Sending email");
                    Log.i("Send email", "");
                    String[] TO = {comanda.getCerereOferta().getFurnizor().getEmail()};
                    String[] CC = {""};
                    String textComanda = "Comanda cu referinta la Cererea de Oferta Nr.#" + comanda.getCerereOferta().getCod_cerere_oferta() + "\n\n"
                            + "Material " + comanda.getCerereOferta().getMaterial().getDenumire_material().toUpperCase() + "\n"
                            + "Cantitate\t" + comanda.getCerereOferta().getCantitate() + "bucati\n"
                            + "Pret\t" + comanda.getCerereOferta().getPret() + " LEI\n"
                            + "Taxa\t" + comanda.getTaxa().getDenumire_taxa().toUpperCase()
                            + "\nProcent Taxa\t" + comanda.getTaxa().getProcent_taxa()
                            + " = " + valoareTaxa + " LEI\n"
                            + "-------------------------------------------------------\n"
                            + "Valoare Totala\t" + valoareTotala + " LEI\n"
                            + "Data Livrare\t" + comanda.getCerereOferta().getData_livrare() + "";
                    // +"Adresa Livrare\t"+"+"";

                    //de luat adresa companie si introdus la livrare

                    Intent emailIntent = new Intent(Intent.ACTION_SEND);
                    emailIntent.setData(Uri.parse("mailto:"));
                    emailIntent.setType("text/plain");
                    emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
                    emailIntent.putExtra(Intent.EXTRA_CC, CC);
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Comanda Nr.#" + cod_comanda);
                    emailIntent.putExtra(Intent.EXTRA_TEXT, textComanda);
                    startActivity(Intent.createChooser(emailIntent, "Pick an Email provider"));
                    finish();
                }
            }
        });

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
