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
import disertatie.com.disertatie.entities.Plata;
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
    private Plata plata = new Plata();
    private double sumaRamasa = -1;

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
        btnSalveazaPlata = (Button)findViewById(R.id.btnSalveazaPlata);


        databaseHelper = new DatabaseHelper(context);
        try {
            listaFacturi = databaseHelper.selectFacturi();
            Log.d(TAG, "test facturi:"+listaFacturi.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
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
                                if(monthOfYear < 9) {
                                    tvDataPlata.setText(dayOfMonth + "-0" + (monthOfYear + 1) + "-" + year);
                                }else if(monthOfYear >= 9){
                                    tvDataPlata.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                }

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
                    etCantitate.setText(factura.getCantitate_facturata()+"");
                    etMaterial.setText(factura.getReceptie().getComanda().getCerereOferta().getMaterial() + "");
                    etPret.setText(factura.getReceptie().getComanda().getCerereOferta().getPret() + "");
                    etValoareTotala.setText(factura.getReceptie().getComanda().getCerereOferta().getPret()+"");
                    etTaxaAplicata.setText(factura.getReceptie().getComanda().getTaxa().getDenumire_taxa()+" ("
                            +factura.getReceptie().getComanda().getTaxa().getProcent_taxa()+" %)");
                    etValoareTotala.setText(factura.getValoareTotala()+"");

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
                if(etSumaPlatita.getText().length() >0) {
                    //  double cantReceptionata = Double.parseDouble(etCantitateReceptionata.getText().toString());
                    plata.setData_plata(tvDataPlata.getText().toString());
                    plata.setFactura(selectFactura(Integer.parseInt(spinnerFacturi.getSelectedItem().toString()), listaFacturi));
                    plata.setSuma_platita(Double.parseDouble(etSumaPlatita.getText().toString()));
                    //    sumaRamasa = Double.parseDouble(etDiferentaPlata.getText().toString());
                    long row_id = databaseHelper.insertPlata(plata);
                    if (row_id != -1) {
                        int cod_plata = databaseHelper.getPrimaryKeyByRowId(row_id, DatabaseHelper.TABLE_PLATI, DatabaseHelper.COLUMN_COD_PLATA);
                        Log.d(TAG, "test-plata" + plata.toString());
                        Log.e(TAG, "Sending email");
                        Log.i("Send email", "");
                        String[] TO = {plata.getFactura().getReceptie().getComanda().getCerereOferta().getFurnizor().getEmail()};
                        String[] CC = {""};
                        String textReceptie = "Plata cu referinta la Factura Nr.#" + plata.getFactura().getCod_factura() + "\n\n"
                                + "Data Plata\t" + plata.getData_plata() + "\n"
                                + "Suma totala " + plata.getFactura().getValoareTotala() + "\tLEI\n"
                                + "Suma platita\t" + plata.getSuma_platita() + "\tLEI\n";
                        // + "Diferenta neplatita\t" + sumaRamasa+"\n";


                        Intent emailIntent = new Intent(Intent.ACTION_SEND);
                        emailIntent.setData(Uri.parse("mailto:"));
                        emailIntent.setType("text/plain");
                        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
                        emailIntent.putExtra(Intent.EXTRA_CC, CC);
                        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Plata Nr.#" + cod_plata);
                        emailIntent.putExtra(Intent.EXTRA_TEXT, textReceptie);
                        startActivity(Intent.createChooser(emailIntent, "Pick an Email provider"));
                    }else{
                            Toast.makeText(context, "Introduceti suma platita", Toast.LENGTH_SHORT).show();
                        }
                }
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
