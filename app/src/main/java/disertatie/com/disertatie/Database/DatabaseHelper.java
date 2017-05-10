package disertatie.com.disertatie.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.ParseException;
import java.util.ArrayList;

import disertatie.com.disertatie.entities.Material;

/**
 * Created by Roxana on 5/9/2017.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String SPACE = " ";

    public static final String DATABASE_NAME = "LOGISTICA";
    public static final String TABLE_COMPANIE = "COMPANIE";
    public static final String TABLE_MATERIALE = "MATERIALE";

    public static final String COLUMN_COD_COMPANIE = "COD_COMPANIE";
    public static final String COLUMN_DENUMIRE_COMPANIE = "DENUMIRE_COMPANIE";
    public static final String COLUMN_NR_INREG_RC = "NR_INREG_RC";
    public static final String COLUMN_EMAIL_COMPANIE = "EMAIL_COMPANIE";
    public static final String COLUMN_COD_ADRESA = "COD_ADRESA";
    public static final String COLUMN_TELEFON_COMPANIE = "TELEFON_COMPANIE";

    public static final String COLUMN_COD_MATERIAL = "COD_MATERIAL";
    public static final String COLUMN_DENUMIRE_MATERIAL = "DENUMIRE_MATERIAL";
    public static final String COLUMN_STOC_CURENT = "STOC_CURENT";
    public static final String COLUMN_STOC_MINIM = "STOC_MINIM";





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

        db.execSQL("create table"+SPACE+TABLE_MATERIALE+"("+COLUMN_COD_MATERIAL+SPACE+"integer primary key AUTOINCREMENT NOT NULL,"
                +COLUMN_DENUMIRE_MATERIAL+SPACE+"text,"
                +COLUMN_STOC_MINIM+SPACE+" real,"
                +COLUMN_STOC_CURENT+SPACE+"real)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS"+SPACE+TABLE_COMPANIE);
        db.execSQL("DROP TABLE IF EXISTS"+SPACE+TABLE_MATERIALE);
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

    public boolean insertMaterial(String denumire_material, double stoc_curent, double stoc_minim) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_DENUMIRE_MATERIAL, denumire_material);
        contentValues.put(COLUMN_STOC_CURENT, stoc_curent);
        contentValues.put(COLUMN_STOC_MINIM, stoc_minim);
        db.insert(TABLE_MATERIALE, null, contentValues);
        return true;
    }

    public Cursor getCompany() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from"+SPACE+TABLE_COMPANIE+SPACE, null );
        return res;
    }

    public Cursor getMaterial(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from"+SPACE+TABLE_MATERIALE+SPACE+"where "+COLUMN_COD_MATERIAL+"="+id+"", null );
        return res;
    }


    public int numberOfRowsMaterials(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_MATERIALE);
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
    public boolean updateMaterial(int cod_material, String denumire_material, double stoc_curent,  double stoc_minim) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_DENUMIRE_MATERIAL, denumire_material);
        contentValues.put(COLUMN_STOC_CURENT, stoc_curent);
        contentValues.put(COLUMN_STOC_MINIM, stoc_minim);
        db.update(TABLE_MATERIALE, contentValues, COLUMN_COD_MATERIAL+" = ?", new String[] { Integer.toString(cod_material) } );
        return true;
    }

    public Integer deleteMaterial (int cod_material) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_MATERIALE,
                COLUMN_COD_MATERIAL+" = ? ",
                new String[] { Integer.toString(cod_material) });
    }


    public ArrayList<Material> getAllMaterials() throws ParseException {
        ArrayList<Material> materialList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT "+COLUMN_COD_MATERIAL+","
                                +COLUMN_DENUMIRE_MATERIAL+","
                                +COLUMN_STOC_CURENT+","
                                +COLUMN_STOC_MINIM+SPACE+
                             " FROM " + TABLE_MATERIALE+SPACE
                              +"ORDER BY "+COLUMN_COD_MATERIAL;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Material mat = new Material();
                mat.setCod_material(cursor.getInt(0));
                mat.setDenumire_material(cursor.getString(1));
                mat.setStoc_curent(cursor.getDouble(2));
                mat.setStoc_minim(cursor.getDouble(3));
                materialList.add(mat);
            }
            while (cursor.moveToNext());
        }
        return materialList;
    }
}
