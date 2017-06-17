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
import com.jjoe64.graphview.LegendRenderer;
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
import disertatie.com.disertatie.charts.CustomDataPoint;
import disertatie.com.disertatie.entities.Material;
import disertatie.com.disertatie.entities.Plata;

public class ReportActivity extends AppCompatActivity {

    private static final String TAG = "Logistica";
    private Toolbar toolbar;
    DatabaseHelper databaseHelper;
    Plata plata = new Plata();
    ArrayList<Plata> plati =  new ArrayList<Plata>();
    ArrayList<Material> materiale =  new ArrayList<Material>();

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
            materiale =  databaseHelper.getAllMaterials();
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

        /*StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
        ArrayList<String> als = new ArrayList<>(Arrays.asList("old", "middle", "new"));
        staticLabelsFormatter.setHorizontalLabels(als);
        staticLabelsFormatter.setVerticalLabels(new ArrayList<String>(Arrays.asList("low", "middle", "high")));
        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);*/

        CustomDataPoint[] customDataPoints = generateMaterials();
        BarGraphSeries<CustomDataPoint> seriesMaterials = new BarGraphSeries<>(customDataPoints);
        graph_barchart.getGridLabelRenderer().setLabelFormatter(new CustomLabelFormatter(customDataPoints));
        graph_barchart.getGridLabelRenderer().setHighlightZeroLines(false);
        seriesMaterials.setSpacing(1);
        graph_barchart.getGridLabelRenderer().setNumHorizontalLabels(materiale.size());
        graph_barchart.addSeries(seriesMaterials);
        graph_barchart.setTitle("Situatie stocuri materiale");
        // styling

        seriesMaterials.setValueDependentColor(new ValueDependentColor<CustomDataPoint>() {
            @Override
            public int get(CustomDataPoint data) {
                return Color.rgb((int) data.getX()*255/4, (int) Math.abs(data.getY()*255/6), 100);
            }
        });
        //graph_barchart.getViewport().setMinX(seriesMaterials.getLowestValueX() - (1.0/2.0));
        //graph_barchart.getViewport().setMaxX(seriesMaterials.getHighestValueX() + (1.0/2.0));
        //graph_barchart.getViewport().setXAxisBoundsManual(true);


        // draw values on top
       // seriesMaterials.setDrawValuesOnTop(true);
       // seriesMaterials.setValuesOnTopColor(Color.RED);

        //graph_barchart.getViewport().setMinY(0);
        //graph_barchart.getViewport().setMaxY(100);
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

    public CustomDataPoint[] generateMaterials(){
        CustomDataPoint[] dataPointArray = new CustomDataPoint[materiale.size()];
        for(int i=0;i<materiale.size();i++) {
            dataPointArray[i] = new CustomDataPoint(materiale.get(i).getDenumire_material(), i, materiale.get(i).getStoc_curent());
            Log.d(TAG, "DataPoint:"+dataPointArray[i].toString());
        }

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
        private static final int MAX_LENGTH_NAME = 8;
        private CustomDataPoint[] points;
        public CustomLabelFormatter(CustomDataPoint[] points)
        {
            this.points=points;
        }
        @Override
        public String formatLabel(double value, boolean isValueX) {
            if(isValueX)
            {
                int i = (int)Math.round(value);
                if(i>= 0 && i<points.length)
                {
                    String s = points[i].getName();
                    //if(s.length()>MAX_LENGTH_NAME)
                    //    s = s.substring(0,MAX_LENGTH_NAME)+"...";
                    return s;
                }
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
    private class CustomLegendRenderer extends LegendRenderer
    {

        public CustomLegendRenderer(GraphView graphView) {
            super(graphView);
        }
    }
}
