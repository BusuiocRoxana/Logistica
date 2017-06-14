package disertatie.com.disertatie.activities;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import disertatie.com.disertatie.Database.DatabaseHelper;
import disertatie.com.disertatie.R;
import disertatie.com.disertatie.Utils.DateConvertor;
import disertatie.com.disertatie.entities.Plata;

public class ReportActivity extends AppCompatActivity {

    private static final String TAG = "Logistica";

    DatabaseHelper databaseHelper;
    Plata plata = new Plata();
    ArrayList<Plata> plati =  new ArrayList<Plata>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        databaseHelper = new DatabaseHelper(this);
        try {
            plati  = databaseHelper.selectPlati();
            Log.d(TAG, "Plati:"+plati.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }


        // generate Dates
        Calendar calendar = Calendar.getInstance();
        Date d1 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d2 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d3 = calendar.getTime();

        GraphView graph = (GraphView) findViewById(R.id.graph);
       /* BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[] {
                new DataPoint(d1, -1),
                new DataPoint(d2, 5),
                new DataPoint(d2, 3),
        });*/
        BarGraphSeries<DataPoint> seriesPlati = new BarGraphSeries<>(generateDataPoint());
        graph.addSeries(seriesPlati);
        // styling
        seriesPlati.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint data) {
                return Color.rgb((int) data.getX()*255/4, (int) Math.abs(data.getY()*255/6), 100);
            }
        });

      //  seriesPlati.setSpacing(50);


        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(this));
        graph.getGridLabelRenderer().setNumHorizontalLabels(plati.size());
        //graph.getViewport().setMinX(d1.getTime());
       // graph.getViewport().setMaxX(d3.getTime());
        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxY(100);
        seriesPlati.setSpacing(50);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getGridLabelRenderer().setHumanRounding(false);

    }

    public DataPoint[] generateDataPoint(){
        DataPoint[] dataPointArray = new DataPoint[plati.size()];
        for(int i=0;i<plati.size();i++) {
            dataPointArray[i] = new DataPoint(DateConvertor.textToDate(plati.get(i).getData_plata()), plati.get(i).getSuma_platita());


        }
        Log.d(TAG, "DataPoint:"+dataPointArray.toString());
        return dataPointArray;
    }
}
