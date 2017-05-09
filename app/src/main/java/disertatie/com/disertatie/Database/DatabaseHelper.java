package disertatie.com.disertatie.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Roxana on 5/9/2017.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String SPACE = " ";

    public static final String DATABASE_NAME = "LOGISTICA";
    public static final String TABLE_COMPANIE = "COMPANIE";

    public static final String COLUMN_COD_COMPANIE = "COD_COMPANIE";
    public static final String COLUMN_DENUMIRE_COMPANIE = "DENUMIRE_COMPANIE";
    public static final String COLUMN_NR_INREG_RC = "NR_INREG_RC";
    public static final String COLUMN_EMAIL_COMPANIE = "EMAIL_COMPANIE";
    public static final String COLUMN_COD_ADRESA = "COD_ADRESA";
    public static final String COLUMN_TELEFON_COMPANIE = "TELEFON_COMPANIE";




    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table"
                + SPACE + TABLE_COMPANIE +
                "("
                + COLUMN_COD_COMPANIE + SPACE
                + "integer primary key AUTOINCREMENT NOT NULL,"
                + COLUMN_DENUMIRE_COMPANIE + SPACE +
                "text," +
                COLUMN_NR_INREG_RC + SPACE +
                "text," +
                COLUMN_EMAIL_COMPANIE + SPACE +
                "text," +
                COLUMN_COD_ADRESA + SPACE +
                "integer," +
                COLUMN_TELEFON_COMPANIE + SPACE +
                "text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS"+SPACE+TABLE_COMPANIE);
        onCreate(db);
    }

    public boolean insertCompanie(String denumire_companie, String nr_inreg_rc, String email, int cod_adresa,String telefon) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_DENUMIRE_COMPANIE, denumire_companie);
        contentValues.put(COLUMN_NR_INREG_RC, nr_inreg_rc);
        contentValues.put(COLUMN_EMAIL_COMPANIE, email);
        contentValues.put(COLUMN_COD_ADRESA, cod_adresa);
        contentValues.put(COLUMN_TELEFON_COMPANIE, telefon);
        db.insert(TABLE_COMPANIE, null, contentValues);
        return true;
    }

    public Cursor getData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from"+SPACE+TABLE_COMPANIE+SPACE, null );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_COMPANIE);
        return numRows;
    }

    public boolean updateCompanie (int cod_companie, String denumire_companie, String nr_inreg_rc, String email, int cod_adresa,String telefon) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_DENUMIRE_COMPANIE, denumire_companie);
        contentValues.put(COLUMN_NR_INREG_RC, nr_inreg_rc);
        contentValues.put(COLUMN_EMAIL_COMPANIE, email);
        contentValues.put(COLUMN_COD_ADRESA, cod_adresa);
        contentValues.put(COLUMN_TELEFON_COMPANIE, telefon);
        db.update(TABLE_COMPANIE, contentValues,COLUMN_COD_COMPANIE+"= ? ", new String[]{Integer.toString(cod_companie)});
        return true;
    }

}
