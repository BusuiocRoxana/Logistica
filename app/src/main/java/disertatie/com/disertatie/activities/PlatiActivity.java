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

public class PlatiActivity extends AppCompatActivity {

    private Button btnSalveazaPlata;
    private TextView tvDataPlata;
    private EditText etDiferentaPlata;
    private EditText etMaterial;
    private EditText etPret;
    private EditText etCantitate;
    private EditText etValoareTotala;
    private EditText etTaxaAplicata;
    private EditText etSumaPlatita;
    private Spinner spinnerFacturi;
    private int year, month, day;
    private Context context;
    private ArrayList<Integer> listaReferinteFacturi = new ArrayList<>();
    private ArrayList<Factura> listaFacturi = new ArrayList<>();
    private DatabaseHelper databaseHelper;
    private Comanda comanda = new Comanda();
    private int idFacturaSelectata = -1;
    private Factura factura =  new Factura();
    private static String TAG = "Logistica";
    private Calendar calendar;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plati);  // toolbar setup
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            // use toolbar as actionbar
            setSupportActionBar(toolbar);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowTitleEnabled(false);
            }
            TextView tv = (TextView) toolbar.findViewById(R.id.toolbar_title);
            tv.setText(R.string.plata);
        }


        context = this;
        tvDataPlata = (TextView) findViewById(R.id.tvDataPlata);
        etDiferentaPlata = (EditText) findViewById(R.id.etDiferentaPlata);
        tvDataPlata.setText(DateConvertor.dateToString(new Date()));

        spinnerFacturi= (Spinner) findViewById(R.id.spinnerReferintaPlata);
        etMaterial = (EditText) findViewById(R.id.etDenumireMat);
        etCantitate= (EditText) findViewById(R.id.etCantitate);
        etPret= (EditText) findViewById(R.id.etPret);
        etValoareTotala= (EditText) findViewById(R.id.etValoare);
        etTaxaAplicata= (EditText) findViewById(R.id.etTaxaAplicata);
        etSumaPlatita =  (EditText) findViewById(R.id.etSumaPlatita);
     //   btnSalveazaPlata = (Button)findViewById(R.id.btnSalveazaPlata);


        databaseHelper = new DatabaseHelper(context);
        //listaFacturi = databaseHelper.selectFacturi();
        Log.d(TAG,"VERIFICA-ID:"+listaFacturi.toString());
        for (int i = 0; i < listaFacturi.size(); i++) {
            listaReferinteFacturi.add(listaFacturi.get(i).getCod_factura());
            Log.d(TAG,"VERIFICA-ID-int:"+listaReferinteFacturi.toString());
        }

        ArrayAdapter<Integer> spinnerFacturiArrayAdapter = new ArrayAdapter<Integer>(context,
                android.R.layout.simple_spinner_dropdown_item, listaReferinteFacturi);

        spinnerFacturi.setAdapter(spinnerFacturiArrayAdapter);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);


        tvDataPlata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                tvDataPlata.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, year, month, day);
                datePickerDialog.show();

            }
        });

        spinnerFacturi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idFacturaSelectata = listaReferinteFacturi.get(position);
                factura =  selectFactura(idFacturaSelectata, listaFacturi);
                Log.d(TAG, "test factura="+comanda.toString());
                if(factura != null) {
                    etCantitate.setText("");
                    etMaterial.setText(factura.getReceptie().getComanda().getCerereOferta().getMaterial() + "");
                    etPret.setText(factura.getReceptie().getComanda().getCerereOferta().getCantitate() + "");

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        etSumaPlatita.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    double diferenta = Double.parseDouble(etValoareTotala.getText().toString()) -
                            Double.parseDouble(etSumaPlatita.getText().toString());
                    etDiferentaPlata.setText(diferenta + "");
                }
            }
        });


        btnSalveazaPlata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             /*   double cantReceptionata = Double.parseDouble(etCantitateReceptionata.getText().toString());
                tvDiferenta.setText(comanda.getCerereOferta().getCantitate()-cantReceptionata+"");
                plata.setCantitate_receptionata(cantReceptionata);
                receptie.setData_receptie(tvDataReceptie.getText().toString());
                receptie.setComanda(comanda);

                databaseHelper.insertReceptie(receptie);
                Log.d(TAG,"test-receptie"+receptie.toString());

                Log.e(TAG, "Sending email");
                Log.i("Send email", "");
                String[] TO = {receptie.getComanda().getCerereOferta().getFurnizor().getEmail()};
                String[] CC = {""};
                String textReceptie = "Receptie cu referinta la Comanda Nr.#" + receptie.getComanda().getCerereOferta().getCod_cerere_oferta() + "\n\n"
                        + "Data Receptie\t" + receptie.getData_receptie() + "\n"
                        + "Material " + receptie.getComanda().getCerereOferta().getMaterial().getDenumire_material().toUpperCase() + "\n"
                        + "Cantitate comandata\t" + receptie.getComanda().getCerereOferta().getCantitate() + "\tbucati\n"
                        + "Cantitate receptionata\t" + receptie.getCantitate_receptionata()+"\n"
                        + "Diferenta \t" +tvDiferenta.getText();

                // +"Adresa Livrare\t"+"+"";

                //de luat adresa companie si introdus la livrare

                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.setType("text/plain");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
                emailIntent.putExtra(Intent.EXTRA_CC, CC);
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Receptie Nr.#" + receptie.getCod_receptie());
                emailIntent.putExtra(Intent.EXTRA_TEXT, textReceptie);
                startActivity(Intent.createChooser(emailIntent, "Pick an Email provider"));
                */
            }
        });
    }

    public Factura selectFactura(int id, ArrayList<Factura> facturi) {
        Factura fact = new Factura();
        for (int i = 0; i < facturi.size(); i++) {
            try {
                if (facturi.get(i).getCod_factura() == id) {
                    fact = facturi.get(i);
                }
            }catch (Exception ex){
            }
        }
        return fact;
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
