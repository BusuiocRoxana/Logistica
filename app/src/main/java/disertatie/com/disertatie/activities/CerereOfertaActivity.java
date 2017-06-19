package disertatie.com.disertatie.activities;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import disertatie.com.disertatie.Database.DatabaseHelper;
import disertatie.com.disertatie.R;
import disertatie.com.disertatie.Utils.DateConvertor;
import disertatie.com.disertatie.Utils.GeneratorCerereDeOferta;
import disertatie.com.disertatie.entities.CerereOferta;
import disertatie.com.disertatie.entities.Furnizor;
import disertatie.com.disertatie.entities.Material;

public class CerereOfertaActivity extends AppCompatActivity {

    private static final String CERERE_OFERTA = "CERERE_OFERTA";
    private Toolbar toolbar;
    private LinearLayout llTermenRaspuns;
    private LinearLayout llDataLivrare;
    private TextView tvTermenRaspuns;
    private TextView tvDataLivrare;
    private TextView tvValoare;
    private TextView tvStatus;

    private EditText etCantitate;
    private EditText etPret;
    private Spinner spinnerMaterial;
    private Spinner spinnerFurnizor;
    private Button btnVerificaCerere;
    private Button btnTrimiteCerere;
    private FloatingActionButton flAdaugaComanda;
    private Calendar calendar;
    private int year, month, day;
    private Context context;
    private CerereOferta cerereOferta = new CerereOferta();
    private DatabaseHelper databaseHelper;
    private ArrayList<Material> listaMateriale = new ArrayList<>();
    private ArrayList<Furnizor> listaFurnizori = new ArrayList<>();

    private boolean isConfirmed = false;
    private static String TAG = "log-async";

    private String STATUS_NEDEFINIT = "NEDEFINIT";

    private int indexFurnizor =-1;
    private int indexMaterial = -1;

    private long row_id= -1;
    private int cod_cerere_oferta = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cerere_oferta);

        context = this;

        context = this;
        databaseHelper = new DatabaseHelper(context);
        try {
            listaMateriale = databaseHelper.getAllMaterials();
            listaFurnizori = databaseHelper.getAllFurnizori();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // toolbar setup
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowTitleEnabled(false);
            }
            TextView tv = (TextView) toolbar.findViewById(R.id.toolbar_title);
            tv.setText(R.string.cerere_oferta);
        }
        llTermenRaspuns = (LinearLayout)findViewById(R.id.llTermenRaspuns);
        llDataLivrare = (LinearLayout) findViewById(R.id.llDataLivrare);


        tvTermenRaspuns = (TextView) findViewById(R.id.tvTermenRaspuns);
        tvDataLivrare = (TextView)findViewById(R.id.tvDataLivrare);
        tvValoare = (TextView) findViewById(R.id.tvValoare);
        tvStatus = (TextView) findViewById(R.id.tvStatus);
        etCantitate = (EditText) findViewById(R.id.etCantitate);
        etPret = (EditText) findViewById(R.id.etPret);
        spinnerFurnizor = (Spinner)findViewById(R.id.spinnerFurnizor);
        spinnerMaterial = (Spinner)findViewById(R.id.spinnerMaterial);
        btnVerificaCerere = (Button) findViewById(R.id.btnVerificaCerere);
        btnTrimiteCerere = (Button) findViewById(R.id.btnTrimiteCerere);
        flAdaugaComanda = (FloatingActionButton) findViewById(R.id.flAdaugaComanda);
        //flAdaugaComanda.setVisibility(View.GONE);


        ArrayAdapter<Material> spinnerMaterialeArrayAdapter = new ArrayAdapter<Material>(context,
                android.R.layout.simple_spinner_dropdown_item,listaMateriale);

        ArrayAdapter<Furnizor> spinnerFurnizoriArrayAdapter = new ArrayAdapter<Furnizor>(context,
                android.R.layout.simple_spinner_dropdown_item,listaFurnizori);

        spinnerMaterial.setAdapter(spinnerMaterialeArrayAdapter);
        spinnerFurnizor.setAdapter(spinnerFurnizoriArrayAdapter);


        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);


       tvDataLivrare.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                       new DatePickerDialog.OnDateSetListener() {

                           @Override
                           public void onDateSet(DatePicker view, int year,
                                                 int monthOfYear, int dayOfMonth) {

                               tvDataLivrare.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                           }
                       }, year, month, day);
               datePickerDialog.show();

           }
       });
        tvTermenRaspuns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialogTermen = new DatePickerDialog(context,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                tvTermenRaspuns.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, year, month, day);
                datePickerDialogTermen.show();

            }
        });

        btnVerificaCerere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cerereOferta = getCerereOfertaInput();
                row_id = databaseHelper.insertCerereOferta(cerereOferta.getFurnizor(), cerereOferta.getMaterial(),
                        cerereOferta.getStatus().toString(),cerereOferta.getPret(), cerereOferta.getCantitate(), cerereOferta.getTermen_limita_raspuns(),
                        cerereOferta.getData_livrare());
                if(row_id != -1){
                    cod_cerere_oferta = databaseHelper.getPrimaryKeyByRowId(row_id, DatabaseHelper.TABLE_CERERI_OFERTA,
                                                                                    DatabaseHelper.COLUMN_COD_CERERE_OFERTA);
                }


            }
        });
        btnTrimiteCerere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isStoragePermissionGranted()) {
                    new AsyncFileMaker().execute("cerere_oferta.html");
                }
                }

        });
        checkView();

        flAdaugaComanda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CerereOfertaActivity.this, ComandaActivity.class);
                Bundle bundle = new Bundle();
                Log.d(TAG, cerereOferta.toString());
                Log.d(TAG, "cerere-oferta="+cerereOferta.toString());
                bundle.putSerializable(CERERE_OFERTA, cerereOferta);
                i.putExtras(bundle);
                startActivity(i);
            }
        });



    }
    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted");
                return true;
            } else {

                Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted");
            return true;
        }
    }

    private class AsyncFileMaker extends AsyncTask<String,Void,Void>
    {
        String fileName;
        File f;
        String tokenId;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            tokenId = FirebaseInstanceId.getInstance().getToken();
            if(tokenId==null || tokenId.length()==0)
            {
                tokenId = "CouldNotGetTokenId";
            }
        }

        @Override
        protected Void doInBackground(String... params) {


            fileName=params[0];
            Log.e(TAG,"Creating file");
            try {
                f = new File(getExternalFilesDir(null), fileName);
                FileOutputStream os = new FileOutputStream(f);
                String text = GeneratorCerereDeOferta.generate(databaseHelper.getMaxIdCerereOferta(),cerereOferta.getTermen_limita_raspuns(),cerereOferta.getFurnizor(), cerereOferta.getMaterial(),
                        cerereOferta.getCantitate(), cerereOferta.getPret(),cerereOferta.getData_livrare(), tokenId);
                os.write(text.getBytes());
                os.close();
            }catch (FileNotFoundException e){
                Log.e(TAG,"Could not find file : "+f.getAbsolutePath());
                cancel(false);
                return null;
            }catch (IOException e){
                Log.e(TAG,"Could not write file : "+f.getAbsolutePath());
                cancel(false);
                return null;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Log.e(TAG,"Sending email");
            Log.i("Send email", "");
            String[] TO = {cerereOferta.getFurnizor().getEmail()};
            String[] CC = {""};
            Intent emailIntent = new Intent(Intent.ACTION_SEND);

            String textCerereOferta = "Descarcati documentul si vizualizati cererea de oferta primita.";

            emailIntent.setData(Uri.parse("mailto:"));
            emailIntent.setType("text/plain");
            emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
            emailIntent.putExtra(Intent.EXTRA_CC, CC);
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Cerere de Oferta Nr."+cod_cerere_oferta);
            emailIntent.putExtra(Intent.EXTRA_STREAM,Uri.fromFile(f));
            emailIntent.putExtra(Intent.EXTRA_TEXT, textCerereOferta);
            startActivity(Intent.createChooser(emailIntent, "Pick an Email provider"));
            finish();


        }


        }




    private CerereOferta getCerereOfertaInput() {
        String collectedErrors = "";
        CerereOferta cerereOf = new CerereOferta();
        if(spinnerMaterial.getSelectedItem() != null){
            int cod_material = ((Material) spinnerMaterial.getSelectedItem()).getCod_material();
            cerereOf.setMaterial((Material)spinnerMaterial.getSelectedItem());
        }else{
            collectedErrors += "Selectati material\n";
        }
        if(spinnerFurnizor.getSelectedItem() != null) {
            cerereOf.setFurnizor((Furnizor) spinnerFurnizor.getSelectedItem());
        }else{
            collectedErrors += "Selectati furnizor\n";

        }
        cerereOf.setData_livrare(tvDataLivrare.getText().toString());
        cerereOf.setTermen_limita_raspuns(tvTermenRaspuns.getText().toString());
        if(etCantitate.getText().length()>0) {
            cerereOf.setCantitate(Double.parseDouble(etCantitate.getText().toString()));
        }else{
            collectedErrors += "Introduceti cantitatea\n";
        }
        if(etPret.getText().length()>0) {
            cerereOf.setPret(Double.parseDouble(etPret.getText().toString()));
        }else{
            collectedErrors += "Introduceti pretul\n";

        }
        cerereOf.setStatus(CerereOferta.Status.NEDEFINIT);
        collectedErrors =  collectedErrors.trim();
        if(collectedErrors.length() > 0){
            Toast.makeText(context, collectedErrors,Toast.LENGTH_SHORT).show();
        }else {
            double valoare = cerereOf.calculeazaValoare(cerereOf.getPret(),cerereOf.getCantitate());
            tvValoare.setText(valoare+"");
            Toast.makeText(context, "Document complet",Toast.LENGTH_SHORT).show();
        }
        return cerereOf;
    }


    public void checkView(){
        if(getIntent().getExtras()!=null) {
            String param = getIntent().getExtras().getString("CO_VIEW");
            switch (param) {
                case "CREATE":
                    spinnerFurnizor.setEnabled(true);
                    spinnerMaterial.setEnabled(true);
                    etPret.setEnabled(true);
                    etCantitate.setEnabled(true);
                    tvTermenRaspuns.setEnabled(true);
                    tvDataLivrare.setEnabled(true);
                    btnTrimiteCerere.setEnabled(true);
                    btnVerificaCerere.setEnabled(true);
                    tvStatus.setEnabled(true);

                    etPret.setText("");
                    etCantitate.setText("");
                    tvValoare.setText("0.00");
                    tvDataLivrare.setText(DateConvertor.dateToString(new Date()));
                    tvTermenRaspuns.setText(DateConvertor.dateToString(new Date()));
                    tvStatus.setText("NEDEFINIT");
                    flAdaugaComanda.setVisibility(View.GONE);

                    break;
                case "EDIT":
                    cerereOferta = (CerereOferta) getIntent().getExtras().getSerializable("CERERE_OFERTA") ;
                    indexFurnizor = findIndexByCode(listaFurnizori, cerereOferta);
                    spinnerFurnizor.setSelection(indexFurnizor);
                    indexMaterial = findIndexByCodeMaterial(listaMateriale, cerereOferta);
                    spinnerMaterial.setSelection(indexMaterial);
                    etPret.setText(cerereOferta.getPret()+"");
                    etCantitate.setText(cerereOferta.getCantitate()+"");
                    tvValoare.setText(cerereOferta.calculeazaValoare(cerereOferta.getPret(),cerereOferta.getCantitate())+"");
                    tvDataLivrare.setText(cerereOferta.getData_livrare());
                    tvTermenRaspuns.setText(cerereOferta.getTermen_limita_raspuns());

                    spinnerFurnizor.setEnabled(true);
                    spinnerMaterial.setEnabled(true);
                    etPret.setEnabled(true);
                    etCantitate.setEnabled(true);
                    tvTermenRaspuns.setEnabled(true);
                    tvDataLivrare.setEnabled(true);
                    btnTrimiteCerere.setEnabled(true);
                    btnVerificaCerere.setEnabled(true);
                    tvStatus.setEnabled(true);
                    flAdaugaComanda.setVisibility(View.GONE);

                    break;
                case "VIEW":
                    cerereOferta = (CerereOferta) getIntent().getExtras().getSerializable(CERERE_OFERTA);
                    Log.d(TAG,"status cerere oferta="+cerereOferta.getStatus());
                    if(cerereOferta.getStatus().equals(CerereOferta.Status.ACCEPTAT) ||
                            cerereOferta.getStatus().equals(CerereOferta.Status.MODIFICAT)){
                        flAdaugaComanda.setVisibility(View.VISIBLE);
                    }else{
                        flAdaugaComanda.setVisibility(View.GONE);
                    }

                    indexFurnizor = findIndexByCode(listaFurnizori, cerereOferta);
                    spinnerFurnizor.setSelection(indexFurnizor);
                    indexMaterial = findIndexByCodeMaterial(listaMateriale, cerereOferta);
                    spinnerMaterial.setSelection(indexMaterial);
                    etPret.setText(cerereOferta.getPret()+"");
                    etCantitate.setText(cerereOferta.getCantitate()+"");
                    tvValoare.setText(cerereOferta.calculeazaValoare(cerereOferta.getPret(),cerereOferta.getCantitate())+"");
                    tvDataLivrare.setText(cerereOferta.getData_livrare());
                    tvTermenRaspuns.setText(cerereOferta.getTermen_limita_raspuns());
                    tvStatus.setText(cerereOferta.getStatus()+"");

                    spinnerFurnizor.setEnabled(false);
                    spinnerMaterial.setEnabled(false);
                    etPret.setEnabled(false);
                    etCantitate.setEnabled(false);
                    tvTermenRaspuns.setEnabled(false);
                    tvDataLivrare.setEnabled(false);
                    btnTrimiteCerere.setEnabled(false);
                    btnVerificaCerere.setEnabled(false);
                    tvStatus.setEnabled(false);
                    break;
            }
        }
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

    public int findIndexByCode(ArrayList<Furnizor> furnizori, CerereOferta cerereOferta){
        int index = -1;
        for(int i=0;i<listaFurnizori.size();i++){
            if((listaFurnizori.get(i).getCod_furnizor())==cerereOferta.getFurnizor().getCod_furnizor()){
                index = i;
            }
        }
        return index;
    }
    public int findIndexByCodeMaterial(ArrayList<Material> materiale, CerereOferta cerereOferta){
        int index = -1;
        for(int i=0;i<listaMateriale.size();i++){
            if((listaMateriale.get(i).getCod_material())==cerereOferta.getMaterial().getCod_material()){
                index = i;
            }
        }
        return index;
    }
}
