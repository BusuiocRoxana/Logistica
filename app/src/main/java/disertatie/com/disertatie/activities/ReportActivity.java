package disertatie.com.disertatie.activities;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LabelFormatter;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import disertatie.com.disertatie.Database.DatabaseHelper;
import disertatie.com.disertatie.R;
import disertatie.com.disertatie.Utils.DateConvertor;
import disertatie.com.disertatie.Utils.StaticLabelsFormatter;
import disertatie.com.disertatie.entities.Plata;

public class ReportActivity extends AppCompatActivity {

    private static final String TAG = "Logistica";
    private Toolbar toolbar;
    DatabaseHelper databaseHelper;
    Plata plata = new Plata();
    ArrayList<Plata> plati =  new ArrayList<Plata>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            // use toolbar as actionbar
            setSupportActionBar(toolbar);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowTitleEnabled(false);
            }
            TextView tv = (TextView) toolbar.findViewById(R.id.toolbar_title);
            tv.setText(R.string.raportare);
        }


        databaseHelper = new DatabaseHelper(this);
        try {
            plati  = databaseHelper.selectPlati();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // plati  = databaseHelper.getMonth();
        Log.d(TAG, "Plati:"+plati.toString());

        Log.d(TAG, "lunile="+databaseHelper.getMonth());

        // generate Dates
        Calendar calendar = Calendar.getInstance();
        Date d1 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d2 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d3 = calendar.getTime();

        GraphView graph = (GraphView) findViewById(R.id.graph);
        GraphView graph_barchart = (GraphView) findViewById(R.id.graph_barchart);
       
       DataPoint[] dataPointArray = generateDataPoint();
        LineGraphSeries<DataPoint> seriesPlati = new LineGraphSeries<>(dataPointArray);
        PointsGraphSeries<DataPoint> seriesPoints = new PointsGraphSeries<>(dataPointArray);
        graph.addSeries(seriesPoints);
        seriesPoints.setShape(PointsGraphSeries.Shape.POINT);
        graph.addSeries(seriesPlati);
        // styling
       /* seriesPlati.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint data) {
                return Color.rgb((int) data.getX()*255/4, (int) Math.abs(data.getY()*255/6), 100);
            }
        });*/

      //  seriesPlati.setSpacing(50);


       // graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(this));
        graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter());
        //graph.getGridLabelRenderer().setLabelFormatter(new StaticLabelsFormatter(graph));
        graph.getGridLabelRenderer().setNumHorizontalLabels(12);
        graph.getViewport().setMinX(1);
        graph.getViewport().setMaxX(12);
        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxY(100);
        graph.setTitle("Platile efectuate in anul curent");
       // seriesPlati.setSpacing(10);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getGridLabelRenderer().setHumanRounding(true);

        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
        ArrayList<String> als = new ArrayList<>(Arrays.asList("old", "middle", "new"));
        staticLabelsFormatter.setHorizontalLabels(als);
        staticLabelsFormatter.setVerticalLabels(new ArrayList<String>(Arrays.asList("low", "middle", "high")));
        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);

    }

    public DataPoint[] generateDataPoint(){
        DataPoint[] dataPointArray = new DataPoint[plati.size()];
        for(int i=0;i<plati.size();i++) {
            Log.d(TAG, "Plata:"+plati.get(i).toString());
            Calendar c = Calendar.getInstance();
            c.setTime(DateConvertor.textToDate(plati.get(i).getData_plata()));
            Log.d(TAG, "Plata date: "+c.get(Calendar.DAY_OF_MONTH)+" "+c.get(Calendar.MONTH)+" "+c.get(Calendar.YEAR));
            dataPointArray[i] = new DataPoint(c.get(Calendar.MONTH)+1, plati.get(i).getSuma_platita());


        }
        Log.d(TAG, "DataPoint:"+dataPointArray.toString());
        return dataPointArray;
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

    private class CustomLabelFormatter implements LabelFormatter {
        private String[] names;
        public CustomLabelFormatter(String[] names)
        {
            this.names=names;
        }
        @Override
        public String formatLabel(double value, boolean isValueX) {
            if(isValueX)
            {
                int i = (int)Math.round(value);
                if(i>= 0 && i<names.length)
                    return names[i];
                else
                    return "NULL_ERROR";
            }
            else
            {
                return String.valueOf(value);
            }
        }

        @Override
        public void setViewport(Viewport viewport) {

        }
    }
}
