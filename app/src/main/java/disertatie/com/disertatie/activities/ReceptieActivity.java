package disertatie.com.disertatie.activities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import java.util.Date;

import disertatie.com.disertatie.Database.DatabaseHelper;
import disertatie.com.disertatie.R;
import disertatie.com.disertatie.Utils.DateConvertor;
import disertatie.com.disertatie.entities.Comanda;
import disertatie.com.disertatie.entities.Furnizor;
import disertatie.com.disertatie.entities.Material;
import disertatie.com.disertatie.entities.Receptie;

public class ReceptieActivity extends AppCompatActivity {

    private Button btnSalveazaReceptie;
    private Button btnVerificaReceptie;
    private TextView tvDataReceptie;
    private TextView tvDiferenta;
    private EditText etMaterial;
    private EditText etCantitateComandata;
    private EditText etCantitateReceptionata;
    private Spinner spinnerComenzi;
    private int year, month, day;
    private Context context;
    private ArrayList<Integer> listaReferinteComenzi = new ArrayList<>();
    private ArrayList<Comanda> listaComenzi = new ArrayList<>();
    private DatabaseHelper databaseHelper;
    private Comanda comanda = new Comanda();
    private int idComandaSelectata = -1;
    private Receptie receptie =  new Receptie();
    private static String TAG = "Logistica";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receptie);

        context = this;
        tvDataReceptie = (TextView) findViewById(R.id.tvDataReceptie);
        tvDiferenta = (TextView) findViewById(R.id.tvDiferenta);
        tvDataReceptie.setText(DateConvertor.dateToString(new Date()));

        spinnerComenzi = (Spinner) findViewById(R.id.spinnerReferintaReceptie);
        etMaterial = (EditText) findViewById(R.id.etDenumireMaterial);
        etCantitateComandata = (EditText) findViewById(R.id.etCantComandata);
        etCantitateReceptionata = (EditText) findViewById(R.id.etCantReceptionata);
        btnSalveazaReceptie = (Button)findViewById(R.id.btnSalveazaReceptie);
        btnVerificaReceptie = (Button)findViewById(R.id.btnVerificaReceptie);
        btnSalveazaReceptie.setVisibility(View.GONE);
        btnVerificaReceptie.setVisibility(View.VISIBLE);


        databaseHelper = new DatabaseHelper(context);
        try {
            listaComenzi = databaseHelper.selectComenzi();
            Log.d(TAG,"VERIFICA-ID:"+listaComenzi.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < listaComenzi.size(); i++) {
            listaReferinteComenzi.add(listaComenzi.get(i).getCod_comanda());
            Log.d(TAG,"VERIFICA-ID-int:"+listaReferinteComenzi.toString());
        }

        ArrayAdapter<Integer> spinnerComenziArrayAdapter = new ArrayAdapter<Integer>(context,
                android.R.layout.simple_spinner_dropdown_item, listaReferinteComenzi);

        spinnerComenzi.setAdapter(spinnerComenziArrayAdapter);

        tvDataReceptie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                tvDataReceptie.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, year, month, day);
                datePickerDialog.show();

            }
        });

        spinnerComenzi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idComandaSelectata = listaReferinteComenzi.get(position);
                selectComanda(idComandaSelectata, listaComenzi);
                if(comanda!=null) {
                 //   etMaterial.setText(comanda.getCerereOferta().getMaterial() + "");
                   // etCantitateComandata.setText(comanda.getCerereOferta().getCantitate() + "");

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnVerificaReceptie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double cantReceptionata = Double.parseDouble(etCantitateReceptionata.getText().toString());
                tvDiferenta.setText(comanda.getCerereOferta().getCantitate()-cantReceptionata+"");
                btnVerificaReceptie.setVisibility(View.GONE);
                btnSalveazaReceptie.setVisibility(View.VISIBLE);

                receptie.setCantitate_receptionata(cantReceptionata);
                receptie.setData_receptie(tvDataReceptie.getText().toString());
                receptie.setComanda(comanda);
            }
        });
        btnSalveazaReceptie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHelper.insertReceptie(receptie);
            }
        });
    }

    public void selectComanda(int id, ArrayList<Comanda> comenzi) {
        for (int i = 0; i < comenzi.size(); i++) {
            if (comenzi.get(i).equals(id)) {
                comanda = comenzi.get(i);
            }

        }
    }
}
