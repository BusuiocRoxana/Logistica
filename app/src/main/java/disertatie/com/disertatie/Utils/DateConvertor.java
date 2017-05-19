package disertatie.com.disertatie.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Roxana on 5/13/2017.
 */

public class DateConvertor {


    public static Date textToDate(String dtStart) {
        //String dtStart = "2010-10-15T09:27:37Z";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        {
            Date date = null;
            try {
                date = format.parse(dtStart);
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

}
