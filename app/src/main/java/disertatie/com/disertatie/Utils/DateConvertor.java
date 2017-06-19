package disertatie.com.disertatie.Utils;



import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.content.ContentValues.TAG;

/**
 * Created by Roxana on 5/13/2017.
 */

public class DateConvertor {

    private static final String TAG = "Logistica";

    public static Date textToDate(String dtStart) {
        //String dtStart = "2010-10-15T09:27:37Z";
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        {
            Date date = null;
            try {
                date = format.parse(dtStart);
                Log.d(TAG, "textToDate-date="+date);
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
            System.out.println(date);
            return date;
        }
    }

    public static String dateToString(Date date){
        SimpleDateFormat dateformat = new SimpleDateFormat("dd-MM-yyyy");
        String dateTime;
        dateTime = dateformat.format(date);
        System.out.println("Current Date Time : " + dateTime);
        return  dateTime;
    }
    public String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd-MM-yyyy", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }


    public static String formatTextAsDate(String dtStart) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String returnedDate = "";
        {
            Date date = new Date(dtStart);
            try {
                date = format.parse(dtStart);
                returnedDate = date.toString();
                Log.d(TAG, "textToDate-date="+date);
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
            System.out.println(date);
            return returnedDate;
        }
    }


}
