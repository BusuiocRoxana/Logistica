package disertatie.com.disertatie.activities;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import disertatie.com.disertatie.Database.DatabaseHelper;
import disertatie.com.disertatie.R;
import disertatie.com.disertatie.Utils.DateConvertor;
import disertatie.com.disertatie.entities.Comanda;
import disertatie.com.disertatie.entities.Factura;
import disertatie.com.disertatie.entities.Receptie;

public class FacturaActivity extends AppCompatActivity {

    private Button btnSalveazaFactura;
    private TextView tvDataFactura;
    private EditText etMaterial;
    private TextView tvFurnizor;
    private TextView tvValoareFactura;
    private EditText etPret;
    private EditText etCantitateFacturata;
    private EditText etTaxa;
    private EditText etCantitateReceptionata;
    private Spinner spinnerReceptii;
    private int year, month, day;
    private Context context;
    private ArrayList<Integer> listaReferinteReceptii = new ArrayList<>();
    private ArrayList<Receptie> listaReceptii = new ArrayList<>();
    private DatabaseHelper databaseHelper;
    private Receptie receptie = new Receptie();
    private int idReceptieSelectata = -1;
    private Factura factura =  new Factura();
    private static String TAG = "Logistica";
    private Calendar calendar;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_factura);

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
            tv.setText(R.string.factura);
        }


        context = this;
        tvDataFactura = (TextView) findViewById(R.id.tvDataFactura);
        tvFurnizor = (TextView) findViewById(R.id.tvFurnizor);
        tvValoareFactura = (TextView) findViewById(R.id.tvValoareFactura);
        tvDataFactura.setText(DateConvertor.dateToString(new Date()));
        spinnerReceptii = (Spinner) findViewById(R.id.spinnerReferintaFactura);
        etMaterial = (EditText) findViewById(R.id.etDenumireMat);
        etPret = (EditText) findViewById(R.id.etPret);
        etCantitateFacturata = (EditText) findViewById(R.id.etCantFacturata);
        etTaxa = (EditText) findViewById(R.id.etTaxa);
        etCantitateReceptionata = (EditText) findViewById(R.id.etCantReceptionata);
        btnSalveazaFactura = (Button)findViewById(R.id.btnSalveazaFactura);


        databaseHelper = new DatabaseHelper(context);
        try {
            listaReceptii = databaseHelper.selectReceptii();

        } catch (ParseException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < listaReceptii.size(); i++) {
            listaReferinteReceptii.add(listaReceptii.get(i).getCod_receptie());
            Log.d(TAG,"VERIFICA-ID-receptii:"+listaReferinteReceptii.toString());
        }

        ArrayAdapter<Integer> spinnerReceptiiArrayAdapter = new ArrayAdapter<Integer>(context,
                android.R.layout.simple_spinner_dropdown_item, listaReferinteReceptii);

        spinnerReceptii.setAdapter(spinnerReceptiiArrayAdapter);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);


        tvDataFactura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                tvDataFactura.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, year, month, day);
                datePickerDialog.show();

            }
        });

        spinnerReceptii.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idReceptieSelectata = listaReferinteReceptii.get(position);
                receptie =  selectReceptie(idReceptieSelectata, listaReceptii);
                Log.d(TAG, "test receptie="+receptie.toString());
                if(receptie != null) {
                    etCantitateReceptionata.setText("");
                    etMaterial.setText(receptie.getComanda().getCerereOferta().getMaterial().getDenumire_material() + "");
                    etCantitateReceptionata.setText(receptie.getCantitate_receptionata() + "");
                    etPret.setText(receptie.getComanda().getCerereOferta().getPret()+"");
                    tvFurnizor.setText(receptie.getComanda().getCerereOferta().getFurnizor().getDenumire_furnizor());
                    etTaxa.setText(receptie.getComanda().getTaxa().getDenumire_taxa()+"("+receptie.getComanda().getTaxa().getProcent_taxa()+")");

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        etCantitateFacturata.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                double procentTaxa = -1;
                double cantitateFacturata = -1;
                double pret = -1;
                double valoareTotala = -1;

                if ((!hasFocus) && (etCantitateFacturata.getText().length() > 0)) {
                    procentTaxa = Double.parseDouble(receptie.getComanda().getTaxa().getProcent_taxa() + "");
                    cantitateFacturata = Double.parseDouble(etCantitateFacturata.getText().toString() + "");
                    pret = Double.parseDouble(etPret.getText().toString());
                    ;
                    valoareTotala = (cantitateFacturata * pret) * procentTaxa + cantitateFacturata * pret;
                    tvValoareFactura.setText(valoareTotala + "");

                }
            }

        });


        btnSalveazaFactura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etCantitateFacturata.getText().length() > 0) {
                    double cantFacturata = Double.parseDouble(etCantitateFacturata.getText().toString());
                    factura.setCantitate_facturata(cantFacturata);
                    factura.setData_factura(tvDataFactura.getText().toString());
                    factura.setReceptie(receptie);

                    long row_id = databaseHelper.insertFactura(factura);
                    int cod_factura = databaseHelper.getPrimaryKeyByRowId(row_id, DatabaseHelper.TABLE_FACTURI, DatabaseHelper.COLUMN_COD_FACTURA);
                    Log.d(TAG, "test-receptie" + receptie.toString());

                    Log.e(TAG, "Sending email");
                    Log.i("Send email", "");
                    String[] TO = {factura.getReceptie().getComanda().getCerereOferta().getFurnizor().getEmail()};
                    String[] CC = {""};
                    String textReceptie = "Factura cu referinta la Receptia Nr.#" + factura.getReceptie().getComanda().getCerereOferta().getCod_cerere_oferta() + "\n\n"
                            + "Data Facturarii\t" + factura.getData_factura() + "\n"
                            + "Material " + factura.getReceptie().getComanda().getCerereOferta().getMaterial().getDenumire_material().toUpperCase() + "\n"
                            + "Cantitate facturata\t" + factura.getReceptie().getComanda().getCerereOferta().getCantitate() + "\tbucati\n"
                            + "Taxe \t" + etTaxa.getText().toString() + "\n"
                            + "Valoare totala \t" + tvValoareFactura.getText();

                    // +"Adresa Livrare\t"+"+"";

                    //de luat adresa companie si introdus la livrare

                    Intent emailIntent = new Intent(Intent.ACTION_SEND);
                    emailIntent.setData(Uri.parse("mailto:"));
                    emailIntent.setType("text/plain");
                    emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
                    emailIntent.putExtra(Intent.EXTRA_CC, CC);
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Factura Nr.#" + cod_factura);
                    emailIntent.putExtra(Intent.EXTRA_TEXT, textReceptie);
                    startActivity(Intent.createChooser(emailIntent, "Pick an Email provider"));
                }else{
                    Toast.makeText(context, "Introduceti cantitate facturata", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public Receptie selectReceptie(int id, ArrayList<Receptie> receptii) {
        Receptie receptie = new Receptie();
        for (int i = 0; i < receptii.size(); i++) {
            try {
                if (receptii.get(i).getCod_receptie() == id) {
                    receptie = receptii.get(i);
                }
            }catch (Exception ex){
            }
        }
        return receptie;
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
