package disertatie.com.disertatie.charts;

import com.jjoe64.graphview.series.DataPoint;

import java.util.Date;

/**
 * Created by Roxana on 6/17/2017.
 */

public class CustomDataPoint extends DataPoint {

    String name;


    public CustomDataPoint(double x, double y) {
        super(x, y);
    }

    public CustomDataPoint(Date x, double y) {
        super(x, y);
    }

    public CustomDataPoint(String name, int x, double y){
        super(x, y);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {

        return "CustomDataPoint{" +
                "name='" + name + '\'' +
                "x='" + getX() + '\'' +
                "y='" + getY() + '\'' +
                '}';
    }
}
