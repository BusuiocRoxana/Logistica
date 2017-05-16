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
    private LinearLayout llActivitati;
    private LinearLayout llMateriale;
    private LinearLayout llFurnizori;
    private LinearLayout llCerereOferta;

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
        llActivitati=(LinearLayout)findViewById(R.id.llActivitati);
        llMateriale = (LinearLayout)findViewById(R.id.llMateriale);
        llFurnizori = (LinearLayout)findViewById(R.id.llFurnizori);
        llCerereOferta = (LinearLayout)findViewById(R.id.llCerereOferta);

        llDateInterne.setOnClickListener(clickListener);
        llActivitati.setOnClickListener(clickListener);
        llMateriale.setOnClickListener(clickListener);
        llFurnizori.setOnClickListener(clickListener);
        llCerereOferta.setOnClickListener(clickListener);

        materialList = new ArrayList<Material>();
        databaseHelper = new DatabaseHelper(this);
        try {
            materialList = databaseHelper.getAllMaterials();

        } catch (ParseException e) {
            e.printStackTrace();
        }

        /*for(int i=0;i<materialList.size();i++){
            materialNames.add(materialList.get(i).getDenumire_material());
        }*/

        GraphView graph = (GraphView) findViewById(R.id.graph);
        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[] {
                new DataPoint(0, -1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        graph.addSeries(series);

        // use static labels for horizontal and vertical labels
        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
        staticLabelsFormatter.setHorizontalLabels(materialNames);
        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);

// styling
        series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint data) {
                return Color.rgb((int) data.getX()*255/4, (int) Math.abs(data.getY()*255/6), 100);
            }
        });

        series.setSpacing(50);

// draw values on top
        series.setDrawValuesOnTop(true);
        series.setValuesOnTopColor(Color.RED);
//series.setValuesOnTopSize(50);

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
                case R.id.llActivitati:
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
                    startActivity(i);
                    break;
                case R.id.btnLogRegistrationId:
                    Log.i(TAG,"Registration ID = "+ FirebaseInstanceId.getInstance().getToken());
                    break;
            }
        }
    };
}
