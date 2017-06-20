package disertatie.com.disertatie.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;

import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import java.text.ParseException;
import java.util.ArrayList;

import disertatie.com.disertatie.Database.DatabaseHelper;
import disertatie.com.disertatie.R;
import disertatie.com.disertatie.Utils.StaticLabelsFormatter;
import disertatie.com.disertatie.entities.Material;

public class MainActivity extends AppCompatActivity {
    private LinearLayout llDateInterne;
    private LinearLayout llMateriale;
    private LinearLayout llFurnizori;
    private LinearLayout llCerereOferta;
    private LinearLayout llComenzi;
    private LinearLayout llListaComenzi;
    private LinearLayout llReceptie;
    private LinearLayout llTaxe;
    private LinearLayout llFactura;
    private LinearLayout llPlata;
    private LinearLayout llRaport;

    private Toolbar toolbar;

    private DatabaseHelper databaseHelper;
    private ArrayList<Material> materialList = new ArrayList<>();
    private ArrayList<String> materialNames;

    private static final String TAG = "Logistica";
    private Button btnLogRegistrationId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // toolbar setup
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                getSupportActionBar().setDisplayShowTitleEnabled(false);
            }
            TextView tv = (TextView) toolbar.findViewById(R.id.toolbar_title);
            tv.setText(R.string.pagina_principala);
        }

        llDateInterne=(LinearLayout)findViewById(R.id.llDateInterne);
        llMateriale = (LinearLayout)findViewById(R.id.llMateriale);
        llFurnizori = (LinearLayout)findViewById(R.id.llFurnizori);
        llCerereOferta = (LinearLayout)findViewById(R.id.llCerereOferta);
        llComenzi = (LinearLayout)findViewById(R.id.llComenzi);
        llListaComenzi = (LinearLayout)findViewById(R.id.llListaComenzi);
        llReceptie = (LinearLayout)findViewById(R.id.llReceptie);
        llTaxe = (LinearLayout)findViewById(R.id.llTaxe);
        llFactura = (LinearLayout)findViewById(R.id.llFacturare);
        llPlata = (LinearLayout)findViewById(R.id.llPlata);
        llRaport = (LinearLayout)findViewById(R.id.llRaport);

        llDateInterne.setOnClickListener(clickListener);
        llMateriale.setOnClickListener(clickListener);
        llFurnizori.setOnClickListener(clickListener);
        llCerereOferta.setOnClickListener(clickListener);
        llComenzi.setOnClickListener(clickListener);
        llListaComenzi.setOnClickListener(clickListener);
        llReceptie.setOnClickListener(clickListener);
        llTaxe.setOnClickListener(clickListener);
        llFactura.setOnClickListener(clickListener);
        llPlata.setOnClickListener(clickListener);
        llRaport.setOnClickListener(clickListener);

        materialList = new ArrayList<Material>();
        databaseHelper = new DatabaseHelper(this);
        try {
            materialList = databaseHelper.getAllMaterials();

        } catch (ParseException e) {
            e.printStackTrace();
        }



        btnLogRegistrationId = (Button)findViewById(R.id.btnLogRegistrationId);
        btnLogRegistrationId.setOnClickListener(clickListener);

    }

    View.OnClickListener clickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i;
            switch (v.getId())
            {
                case R.id.llDateInterne:
                    i=new Intent(MainActivity.this,DateInterneActivity.class);
                    startActivity(i);
                    break;
                case R.id.llComenzi:
                    i=new Intent(MainActivity.this,ListaCereriOfertaActivity.class);
                    startActivity(i);
                    break;
                case R.id.llTaxe:
                    i=new Intent(MainActivity.this, TaxeActivity.class);
                    startActivity(i);
                    break;
                case R.id.llMateriale:
                    i = new Intent(MainActivity.this, MaterialsActivity.class);
                    startActivity(i);
                    break;
                case R.id.llFurnizori:
                    i = new Intent(MainActivity.this, FurnizoriActivity.class);
                    startActivity(i);
                    break;
                case R.id.llCerereOferta:
                    i = new Intent(MainActivity.this, CerereOfertaActivity.class);
                    i.putExtra("CO_VIEW","CREATE");
                    startActivity(i);
                    break;
                case R.id.btnLogRegistrationId:
                    Log.i(TAG,"Registration ID = "+ FirebaseInstanceId.getInstance().getToken());
                    break;
                case R.id.llListaComenzi:
                    i = new Intent(MainActivity.this, ListaComenziActivity.class);
                    startActivity(i);
                    break;
                case R.id.llReceptie:
                    i = new Intent(MainActivity.this, ReceptieActivity.class);
                    i.putExtra("IS_FIRST_DISPLAYED",true);
                    startActivity(i);
                    break;
                case R.id.llFacturare:
                    i = new Intent(MainActivity.this, FacturaActivity.class);
                    startActivity(i);
                    break;
                case R.id.llPlata:
                    i = new Intent(MainActivity.this, PlatiActivity.class);
                    startActivity(i);
                    break;
                case R.id.llRaport:
                    i = new Intent(MainActivity.this, ReportActivity.class);
                    startActivity(i);
                    break;
            }
        }
    };

}
