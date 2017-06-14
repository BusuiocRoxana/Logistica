package disertatie.com.disertatie.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.ParseException;
import java.util.ArrayList;

import disertatie.com.disertatie.Constants.Constants;
import disertatie.com.disertatie.entities.Adresa;
import disertatie.com.disertatie.entities.CerereOferta;
import disertatie.com.disertatie.entities.Comanda;
import disertatie.com.disertatie.entities.Companie;
import disertatie.com.disertatie.entities.Factura;
import disertatie.com.disertatie.entities.Furnizor;
import disertatie.com.disertatie.entities.Material;
import disertatie.com.disertatie.entities.Plata;
import disertatie.com.disertatie.entities.Receptie;
import disertatie.com.disertatie.entities.Taxa;

import static disertatie.com.disertatie.R.string.furnizor;

/**
 * Created by Roxana on 5/9/2017.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String SPACE = " ";

    public static final String DATABASE_NAME = "LOGISTICA";
    public static final String TABLE_COMPANIE = "COMPANIE";
    public static final String TABLE_MATERIALE = "MATERIALE";
    public static final String TABLE_FURNIZORI = "FURNIZORI";
    public static final String TABLE_ADRESE = "ADRESE";
    public static final String TABLE_CERERI_OFERTA = "CERERI_OFERTA";
    public static final String TABLE_COMENZI= "COMENZI";
    public static final String TABLE_TAXE = "TAXE";
    public static final String TABLE_RECEPTII = "RECEPTII";
    public static final String TABLE_FACTURI = "FACTURI";
    public static final String TABLE_PLATI = "PLATI";

    public static final String COLUMN_COD_COMPANIE = "COD_COMPANIE";
    public static final String COLUMN_DENUMIRE_COMPANIE = "DENUMIRE_COMPANIE";
    public static final String COLUMN_NR_INREG_RC = "NR_INREG_RC";
    public static final String COLUMN_EMAIL_COMPANIE = "EMAIL_COMPANIE";
    public static final String COLUMN_COD_ADRESA = "COD_ADRESA";
  //  public static final String COLUMN_TELEFON_COMPANIE = "TELEFON_COMPANIE";

    public static final String COLUMN_COD_MATERIAL = "COD_MATERIAL";
    public static final String COLUMN_DENUMIRE_MATERIAL = "DENUMIRE_MATERIAL";
    public static final String COLUMN_STOC_CURENT = "STOC_CURENT";
    public static final String COLUMN_STOC_MINIM = "STOC_MINIM";

    public static final String COLUMN_COD_FURNIZOR = "COD_FURNIZOR";
    public static final String COLUMN_DENUMIRE_FURNIZOR = "DENUMIRE_FURNIZOR";
    public static final String COLUMN_NR_INREG_RC_FURNIZORI = "NR_INREG_RC_FURNIZORI";
    //public static final String COLUMN_COD_ADRESA = "COD_ADRESA";
    public static final String COLUMN_RATING = "RATING";
    public static final String COLUMN_EMAIL_FURNIZOR = "EMAIL_FURNIZOR";


    public static final String COLUMN_NUMAR = "NUMAR";
    public static final String COLUMN_STRADA = "STRADA";
    public static final String COLUMN_LOCALITATE = "LOCALITATE";
    public static final String COLUMN_JUDET_SECTOR = "JUDET_SECTOR";
    public static final String COLUMN_TARA = "TARA";
    public static final String COLUMN_TELEFON = "TELEFON";


    public static final String COLUMN_COD_CERERE_OFERTA = "COD_CERERE_OFERTA";
    //public static final String COLUMN_COD_FURNIZOR = "COD_FURNIZOR";
   // public static final String COLUMN_COD_MATERIAL = "COD_MATERIAL";
    public static final String COLUMN_STATUS = "STATUS_CERERE_OFERTA";
    public static final String COLUMN_PRET = "PRET";
    public static final String COLUMN_CANTITATE = "CANTITATE";
    public static final String COLUMN_DATA_LIVRARE = "DATA_LIVRARE";
    public static final String COLUMN_DATA_DOCUMENT= "DATA_DOCUMENT";
    public static final String COLUMN_TERMEN_RASPUNS= "TERMEN_RASPUNS";
    private static final String TAG = "Logistica" ;


    public static final String COLUMN_COD_COMANDA = "COD_COMANDA";
    public static final String COLUMN_COD_TAXA = "COD_TAXA";
    //public static final String COLUMN_COD_CERERE_OFERTA = "COD_CERERE_OFERTA";

    public static final String COLUMN_DENUMIRE_TAXA = "DENUMIRE_TAXA";
    public static final String COLUMN_PROCENT_TAXA = "PROCENT_TAXA";

    public static final String COLUMN_COD_RECEPTIE= "COD_RECEPTIE";
    //COD_COMANDA
    public static final String COLUMN_CANTITATE_RECEPTIONATA= "CANTITATE_RECEPTIONATA";
    public static final String COLUMN_DATA_RECEPTIE =  "DATA_RECEPTIE";

    public static final String COLUMN_COD_FACTURA = "COD_FACTURA";
  //  public static final String COLUMN_COD_RECEPTIE = "COD_RECEPTIE";
    public static final String COLUMN_CANTITATE_FACTURATA= "CANTITATE_FACTURATA";
    public static final String COLUMN_DATA_FACTURA = "DATA_FACTURA";
    public static final String COLUMN_SUMA_RAMASA = "SUMA_RAMASA";

    public static final String COLUMN_COD_PLATA = "COD_PLATA";
   // public static final String COLUMN_COD_FACTURA = "COD_FACTURA";
    public static final String COLUMN_SUMA_PLATITA = "SUMA_PLATITA";
    public static final String COLUMN_DATA_PLATA = "DATA_PLATA";




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
                "integer)");

        db.execSQL("create table"+SPACE+TABLE_MATERIALE+"("+COLUMN_COD_MATERIAL+SPACE+"integer primary key AUTOINCREMENT NOT NULL,"
                +COLUMN_DENUMIRE_MATERIAL+SPACE+"text,"
                +COLUMN_STOC_MINIM+SPACE+" real,"
                +COLUMN_STOC_CURENT+SPACE+"real)");

        db.execSQL("create table"+ SPACE+TABLE_FURNIZORI+"("+COLUMN_COD_FURNIZOR+SPACE+"integer primary key AUTOINCREMENT NOT NULL,"
                +COLUMN_DENUMIRE_FURNIZOR+SPACE+"text,"
                +COLUMN_NR_INREG_RC_FURNIZORI+SPACE+"text,"
                +COLUMN_COD_ADRESA+SPACE+"integer,"
                +COLUMN_RATING+SPACE+"integer,"
                +COLUMN_EMAIL_FURNIZOR+SPACE+"text,"+
                        " FOREIGN KEY ("+COLUMN_COD_ADRESA+") REFERENCES "+TABLE_ADRESE+"("+COLUMN_COD_ADRESA+"))"

        );
        db.execSQL("create table"+SPACE+TABLE_ADRESE+"("+COLUMN_COD_ADRESA+SPACE+"integer primary key AUTOINCREMENT NOT NULL,"
                +COLUMN_NUMAR+SPACE+"integer,"
                +COLUMN_STRADA+SPACE+"text,"
                +COLUMN_LOCALITATE+SPACE+"text,"
                +COLUMN_JUDET_SECTOR+SPACE+"text,"
                +COLUMN_TELEFON+SPACE+"text,"
                +COLUMN_TARA+SPACE+"text)");

        db.execSQL("create table"+SPACE+TABLE_CERERI_OFERTA+"("
                +COLUMN_COD_CERERE_OFERTA+SPACE+"integer primary key AUTOINCREMENT not null,"
                +COLUMN_COD_FURNIZOR+SPACE+"integer,"
                +COLUMN_COD_MATERIAL+SPACE+"integer,"
                +COLUMN_STATUS+SPACE+"text CHECK("+SPACE+COLUMN_STATUS+SPACE+" IN ('"+ Constants.NEDEFINIT+
                                                                                "','"+ Constants.ACCEPTAT+
                                                                                "','" + Constants.MODIFICAT+
                                                                                 "','"+Constants.RESPINS+"')) NOT NULL DEFAULT '"+Constants.NEDEFINIT+"',"
                +COLUMN_PRET+SPACE+"real,"
                +COLUMN_CANTITATE+SPACE+"real,"
                +COLUMN_DATA_LIVRARE+SPACE+"text,"
                //+COLUMN_DATA_DOCUMENT+SPACE+"date DEFAULT datetime('now', 'localtime'),"
                +COLUMN_DATA_DOCUMENT+SPACE+"text,"
                +COLUMN_TERMEN_RASPUNS+SPACE+"text,"+SPACE
                +" FOREIGN KEY ("+COLUMN_COD_FURNIZOR+") REFERENCES "+TABLE_FURNIZORI+"("+COLUMN_COD_FURNIZOR+"),"
                +" FOREIGN KEY ("+COLUMN_COD_MATERIAL+") REFERENCES "+TABLE_MATERIALE+"("+COLUMN_COD_MATERIAL+"))"
        );
        db.execSQL("create table"+SPACE+TABLE_TAXE+"("+COLUMN_COD_TAXA+SPACE+"integer primary key AUTOINCREMENT NOT NULL,"
                +COLUMN_DENUMIRE_TAXA+SPACE+"text,"
                +COLUMN_PROCENT_TAXA+SPACE+"real)"
        );

        db.execSQL("create table"+SPACE+TABLE_COMENZI+"("+COLUMN_COD_COMANDA+SPACE+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                +COLUMN_COD_CERERE_OFERTA+SPACE+"integer,"
                +COLUMN_COD_TAXA+SPACE+"integer,"
                +" FOREIGN KEY ("+COLUMN_COD_CERERE_OFERTA+") REFERENCES "+TABLE_CERERI_OFERTA+"("+COLUMN_COD_CERERE_OFERTA+"),"
                +" FOREIGN KEY ("+COLUMN_COD_TAXA+") REFERENCES "+TABLE_TAXE+"("+COLUMN_COD_TAXA+"))"
        );
        db.execSQL("create table"+SPACE+TABLE_RECEPTII+"("+COLUMN_COD_RECEPTIE+SPACE+"integer primary key AUTOINCREMENT NOT NULL,"
                +COLUMN_COD_COMANDA+SPACE+"integer,"
                +COLUMN_CANTITATE_RECEPTIONATA+SPACE+"real,"
                +COLUMN_DATA_RECEPTIE+SPACE+"text,"
                +" FOREIGN KEY ("+COLUMN_COD_COMANDA+") REFERENCES "+TABLE_COMENZI+"("+COLUMN_COD_COMANDA+"))"
        );
        db.execSQL("create table"+SPACE+TABLE_FACTURI+"("+COLUMN_COD_FACTURA+SPACE+"integer primary key AUTOINCREMENT NOT NULL,"
                +COLUMN_COD_RECEPTIE+SPACE+"integer,"
                +COLUMN_CANTITATE_FACTURATA+SPACE+"real,"
                +COLUMN_DATA_FACTURA+SPACE+"text,"
                +" FOREIGN KEY ("+COLUMN_COD_RECEPTIE+") REFERENCES "+TABLE_RECEPTII+"("+COLUMN_COD_RECEPTIE+"))"
        );
        db.execSQL("create table"+SPACE+TABLE_PLATI+"("+COLUMN_COD_PLATA+SPACE+"integer primary key AUTOINCREMENT NOT NULL,"
                +COLUMN_COD_FACTURA+SPACE+"integer,"
                +COLUMN_SUMA_PLATITA+SPACE+"real,"
                +COLUMN_DATA_PLATA+SPACE+"text,"
                +" FOREIGN KEY ("+COLUMN_COD_FACTURA+") REFERENCES "+TABLE_FACTURI+"("+COLUMN_COD_FACTURA+"))"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS"+SPACE+TABLE_COMPANIE);
        db.execSQL("DROP TABLE IF EXISTS"+SPACE+TABLE_MATERIALE);
        db.execSQL("DROP TABLE IF EXISTS"+SPACE+TABLE_FURNIZORI);
        db.execSQL("DROP TABLE IF EXISTS"+SPACE+TABLE_ADRESE);
        db.execSQL("DROP TABLE IF EXISTS"+SPACE+TABLE_CERERI_OFERTA);
        db.execSQL("DROP TABLE IF EXISTS"+SPACE+TABLE_TAXE);
        db.execSQL("DROP TABLE IF EXISTS"+SPACE+TABLE_COMENZI);
        db.execSQL("DROP TABLE IF EXISTS"+SPACE+TABLE_RECEPTII);
        db.execSQL("DROP TABLE IF EXISTS"+SPACE+TABLE_FACTURI);
        db.execSQL("DROP TABLE IF EXISTS"+SPACE+TABLE_PLATI);
        onCreate(db);
    }

    public boolean insertCompanie(String denumire_companie, String nr_inreg_rc, String email, int cod_adresa) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_DENUMIRE_COMPANIE, denumire_companie);
        contentValues.put(COLUMN_NR_INREG_RC, nr_inreg_rc);
        contentValues.put(COLUMN_EMAIL_COMPANIE, email);
        contentValues.put(COLUMN_COD_ADRESA, cod_adresa);
        db.insert(TABLE_COMPANIE, null, contentValues);
        db.close();
        return true;
    }

    public boolean insertMaterial(String denumire_material, double stoc_curent, double stoc_minim) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_DENUMIRE_MATERIAL, denumire_material);
        contentValues.put(COLUMN_STOC_CURENT, stoc_curent);
        contentValues.put(COLUMN_STOC_MINIM, stoc_minim);
        db.insert(TABLE_MATERIALE, null, contentValues);
        db.close();
        return true;
    }

    public boolean insertFurnizor(String denumire_furnizor, String nr_inreg_rc, int cod_adresa, int rating, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_DENUMIRE_FURNIZOR, denumire_furnizor);
        contentValues.put(COLUMN_NR_INREG_RC_FURNIZORI, nr_inreg_rc);
        contentValues.put(COLUMN_COD_ADRESA, cod_adresa);
        contentValues.put(COLUMN_RATING, rating);
        contentValues.put(COLUMN_EMAIL_FURNIZOR, email);
        db.insert(TABLE_FURNIZORI, null, contentValues);
        db.close();
        return true;
    }

    public boolean insertAdresa(int numar, String strada, String localitate, String judet_sector, String tara, String telefon) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NUMAR, numar);
        contentValues.put(COLUMN_STRADA, strada);
        contentValues.put(COLUMN_LOCALITATE, localitate);
        contentValues.put(COLUMN_JUDET_SECTOR, judet_sector);
        contentValues.put(COLUMN_TARA, tara);
        contentValues.put(COLUMN_TELEFON, telefon);
        db.insert(TABLE_ADRESE, null, contentValues);
        db.close();
        return true;
    }

    public boolean insertCerereOferta(Furnizor furnizor, Material material, String status, double pret,
                                      double cantitate, String termen_raspuns, String data_livrare) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_COD_FURNIZOR, furnizor.getCod_furnizor());
        contentValues.put(COLUMN_COD_MATERIAL, material.getCod_material());
        contentValues.put(COLUMN_STATUS, status);
        contentValues.put(COLUMN_PRET, pret);
        contentValues.put(COLUMN_CANTITATE, cantitate);
        contentValues.put(COLUMN_DATA_DOCUMENT, "datetime('now','localtime')");
        contentValues.put(COLUMN_TERMEN_RASPUNS, termen_raspuns);
        contentValues.put(COLUMN_DATA_LIVRARE, data_livrare);
        db.insert(TABLE_CERERI_OFERTA, null, contentValues);
        db.close();
        return true;
    }

    public boolean insertComanda(Comanda comanda) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_COD_CERERE_OFERTA, comanda.getCerereOferta().getCod_cerere_oferta());
        contentValues.put(COLUMN_COD_TAXA, comanda.getTaxa().getCod_taxa());
        db.insert(TABLE_COMENZI, null, contentValues);
        db.close();
        Log.e(TAG, "cod_comanda="+comanda.getCod_comanda());
        return true;
    }

    public boolean insertReceptie(Receptie receptie) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_COD_COMANDA, receptie.getComanda().getCod_comanda());
        contentValues.put(COLUMN_DATA_RECEPTIE, receptie.getData_receptie());
        contentValues.put(COLUMN_CANTITATE_RECEPTIONATA, receptie.getCantitate_receptionata());
        db.insert(TABLE_RECEPTII, null, contentValues);
        db.close();
        Log.e(TAG, "cod_receptie="+receptie.getCod_receptie());
        return true;
    }
    public boolean insertFactura(Factura factura) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
       // contentValues.put(COLUMN_COD_FACTURA, factura.getCod_factura());
        contentValues.put(COLUMN_COD_RECEPTIE, factura.getReceptie().getCod_receptie());
        contentValues.put(COLUMN_CANTITATE_FACTURATA, factura.getCantitate_facturata());
        contentValues.put(COLUMN_DATA_FACTURA, factura.getData_factura());
        db.insert(TABLE_FACTURI, null, contentValues);
        db.close();

        return true;
    }

    public boolean insertPlata(Plata plata) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_COD_FACTURA, plata.getFactura().getCod_factura());
        contentValues.put(COLUMN_DATA_PLATA, plata.getData_plata());
        contentValues.put(COLUMN_SUMA_PLATITA, plata.getSuma_platita());
        db.insert(TABLE_PLATI, null, contentValues);
        db.close();
        return true;
    }


    public int getMaxIdCerereOferta(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT "+COLUMN_COD_CERERE_OFERTA+" FROM "+TABLE_CERERI_OFERTA+
                    " ORDER BY "+COLUMN_COD_CERERE_OFERTA+" DESC LIMIT 1";
        Cursor cursor = db.rawQuery(query, null);
        int cod_cerere_oferta = -1;
        if(cursor.moveToFirst()){
            do{
                cod_cerere_oferta = cursor.getInt(cursor.getColumnIndex(COLUMN_COD_CERERE_OFERTA));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return cod_cerere_oferta;
    }

    public void updateCerereOferta(int codDocument, double cantitate,double pret, String dataLivrare, CerereOferta.Status status){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_CANTITATE, cantitate);
        contentValues.put(COLUMN_PRET, pret);
        contentValues.put(COLUMN_DATA_LIVRARE, dataLivrare);
        contentValues.put(COLUMN_STATUS, status.toString());

        db.update(TABLE_CERERI_OFERTA, contentValues,COLUMN_COD_CERERE_OFERTA+"= ? ", new String[]{Integer.toString(codDocument)});
        Log.d(TAG,"updateCerereOferta-bam-worked");
        db.close();
    }

    public int printAutoIncrements(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM SQLITE_SEQUENCE";
        Cursor cursor = db.rawQuery(query, null);
        int cod_adresa = -1;
        if (cursor.moveToFirst()){
            do{
                System.out.println("tableName: " +cursor.getString(cursor.getColumnIndex("name")));
                System.out.println("autoInc: " + cursor.getString(cursor.getColumnIndex("seq")));
                cod_adresa = cursor.getShort(cursor.getColumnIndex("seq"));

            }while (cursor.moveToNext());
        }

        cursor.close();
        return cod_adresa;
    }

    public int getMaxIdAdresa(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT "+COLUMN_COD_ADRESA+" FROM "+TABLE_ADRESE+
                " ORDER BY "+COLUMN_COD_ADRESA +" DESC LIMIT 1";
        Cursor cursor = db.rawQuery(query, null);
        int cod_adresa = -1;
        if(cursor.moveToFirst()){
            do{
                cod_adresa = cursor.getInt(cursor.getColumnIndex(COLUMN_COD_ADRESA));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return cod_adresa;
    }

    public Companie getCompany() {
        SQLiteDatabase db = this.getReadableDatabase();
        Companie companie = new Companie();
        String query = "SELECT "
                +TABLE_COMPANIE+"."+COLUMN_COD_COMPANIE+","
                +TABLE_COMPANIE+"."+COLUMN_DENUMIRE_COMPANIE+","
                +TABLE_COMPANIE+"."+COLUMN_NR_INREG_RC+","
                +TABLE_COMPANIE+"."+COLUMN_EMAIL_COMPANIE+SPACE+","
                +TABLE_ADRESE+"."+COLUMN_COD_ADRESA+SPACE+","
                +TABLE_ADRESE+"."+COLUMN_NUMAR+SPACE+","
                +TABLE_ADRESE+"."+COLUMN_STRADA+SPACE+","
                +TABLE_ADRESE+"."+COLUMN_LOCALITATE+SPACE+","
                +TABLE_ADRESE+"."+COLUMN_JUDET_SECTOR+SPACE+","
                +TABLE_ADRESE+"."+COLUMN_TELEFON+SPACE+","
                +TABLE_ADRESE+"."+COLUMN_TARA+SPACE+
                " FROM " + TABLE_COMPANIE+","+TABLE_ADRESE+SPACE+
                "WHERE "+TABLE_COMPANIE+"."+COLUMN_COD_ADRESA+"="+TABLE_ADRESE+"."+COLUMN_COD_ADRESA+SPACE;

        Cursor cursor = db.rawQuery(query, null);

        cursor.moveToFirst();
            if (cursor.getCount() != 0) {
                companie.setCod_companie(cursor.getInt(0));
                companie.setDenumire_companie(cursor.getString(1));
                companie.setNr_inreg_RC(cursor.getString(2));
                companie.setEmail(cursor.getString(3));
                Adresa adresa = new Adresa();
                adresa.setCod_adresa(cursor.getInt(4));
                adresa.setNumar(cursor.getInt(5));
                adresa.setStrada(cursor.getString(6));
                adresa.setLocalitate(cursor.getString(7));
                adresa.setJudet_sector(cursor.getString(8));
                adresa.setTelefon(cursor.getString(9));
                adresa.setTara(cursor.getString(10));
                companie.setAdresa(adresa);
            }
                if (!cursor.isClosed()) {
                    cursor.close();
                }

        return companie;

    }

    public Cursor getMaterial(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from"+SPACE+TABLE_MATERIALE+SPACE+"where "+COLUMN_COD_MATERIAL+"="+id+"", null );
        res.close();
        db.close();
        return res;
    }


    public int numberOfRowsCompanie(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_COMPANIE);
        db.close();
        return numRows;
    }

    public boolean updateCompanie (int cod_companie, String denumire_companie, String nr_inreg_rc, String email, Adresa adresa) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_DENUMIRE_COMPANIE, denumire_companie);
        contentValues.put(COLUMN_NR_INREG_RC, nr_inreg_rc);
        contentValues.put(COLUMN_EMAIL_COMPANIE, email);
        //contentValues.put(COLUMN_COD_ADRESA, cod_adresa);
        ContentValues contentValuesAdresa = new ContentValues();
        contentValuesAdresa.put(COLUMN_STRADA, adresa.getStrada());
        contentValuesAdresa.put(COLUMN_NUMAR, adresa.getNumar());
        contentValuesAdresa.put(COLUMN_LOCALITATE, adresa.getLocalitate());
        contentValuesAdresa.put(COLUMN_JUDET_SECTOR, adresa.getJudet_sector());
        contentValuesAdresa.put(COLUMN_TELEFON, adresa.getTelefon());
        contentValuesAdresa.put(COLUMN_TARA, adresa.getTara());

        db.update(TABLE_COMPANIE, contentValues,COLUMN_COD_COMPANIE+"= ? ", new String[]{Integer.toString(cod_companie)});
        db.update(TABLE_ADRESE, contentValuesAdresa,
                COLUMN_COD_ADRESA+" = ?", new String[] { Integer.toString(adresa.getCod_adresa()) } );
        db.close();
        return true;
    }
    public boolean updateMaterial(int cod_material, String denumire_material, double stoc_curent,  double stoc_minim) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_DENUMIRE_MATERIAL, denumire_material);
        contentValues.put(COLUMN_STOC_CURENT, stoc_curent);
        contentValues.put(COLUMN_STOC_MINIM, stoc_minim);
        db.update(TABLE_MATERIALE, contentValues, COLUMN_COD_MATERIAL+" = ?", new String[] { Integer.toString(cod_material) } );
        db.close();
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
        cursor.close();
        db.close();
        return materialList;
    }

    public Material selectMaterial(int cod_material) throws ParseException {
        Material mat = new Material();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT "+COLUMN_COD_MATERIAL+","
                +COLUMN_DENUMIRE_MATERIAL+","
                +COLUMN_STOC_CURENT+","
                +COLUMN_STOC_MINIM+SPACE+
                " FROM " + TABLE_MATERIALE+SPACE+"WHERE"+SPACE+COLUMN_COD_MATERIAL+"="+cod_material;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                mat.setCod_material(cursor.getInt(0));
                mat.setDenumire_material(cursor.getString(1));
                mat.setStoc_curent(cursor.getDouble(2));
                mat.setStoc_minim(cursor.getDouble(3));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return mat;
    }

    public boolean updateFurnizor(int cod_furnizor, String denumire_furnizor, String nr_inreg_Rc,
                                Adresa adresa, int rating, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_DENUMIRE_FURNIZOR, denumire_furnizor);
        contentValues.put(COLUMN_NR_INREG_RC_FURNIZORI, nr_inreg_Rc);
        //contentValues.put(COLUMN_COD_ADRESA, cod_adresa);
        contentValues.put(COLUMN_RATING, rating);
        contentValues.put(COLUMN_EMAIL_FURNIZOR, email);
        ContentValues contentValuesAdresa = new ContentValues();
        contentValuesAdresa.put(COLUMN_STRADA, adresa.getStrada());
        contentValuesAdresa.put(COLUMN_NUMAR, adresa.getNumar());
        contentValuesAdresa.put(COLUMN_LOCALITATE, adresa.getLocalitate());
        contentValuesAdresa.put(COLUMN_JUDET_SECTOR, adresa.getJudet_sector());
        contentValuesAdresa.put(COLUMN_TELEFON, adresa.getTelefon());
        contentValuesAdresa.put(COLUMN_TARA, adresa.getTara());

        db.update(TABLE_FURNIZORI, contentValues,
                COLUMN_COD_FURNIZOR+" = ?", new String[] { Integer.toString(cod_furnizor) } );
        db.update(TABLE_ADRESE, contentValuesAdresa,
                COLUMN_COD_ADRESA+" = ?", new String[] { Integer.toString(adresa.getCod_adresa()) } );

        db.close();
        return true;
    }

    public Integer deleteFurnizor (int cod_furnizor) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_FURNIZORI,
                COLUMN_COD_FURNIZOR+" = ? ",
                new String[] { Integer.toString(cod_furnizor) });
    }


    public ArrayList<Furnizor> getAllFurnizori() throws ParseException {
        ArrayList<Furnizor> furnizorList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT "
                +TABLE_FURNIZORI+"."+COLUMN_COD_FURNIZOR+","
                +TABLE_FURNIZORI+"."+COLUMN_DENUMIRE_FURNIZOR+","
                +TABLE_FURNIZORI+"."+COLUMN_NR_INREG_RC_FURNIZORI+","
                +TABLE_FURNIZORI+"."+COLUMN_COD_ADRESA+SPACE+","
                +TABLE_FURNIZORI+"."+COLUMN_RATING+SPACE+","
                +TABLE_FURNIZORI+"."+COLUMN_EMAIL_FURNIZOR+SPACE+","
                +TABLE_ADRESE+"."+COLUMN_COD_ADRESA+SPACE+","
                +TABLE_ADRESE+"."+COLUMN_NUMAR+SPACE+","
                +TABLE_ADRESE+"."+COLUMN_STRADA+SPACE+","
                +TABLE_ADRESE+"."+COLUMN_LOCALITATE+SPACE+","
                +TABLE_ADRESE+"."+COLUMN_JUDET_SECTOR+SPACE+","
                +TABLE_ADRESE+"."+COLUMN_TELEFON+SPACE+","
                +TABLE_ADRESE+"."+COLUMN_TARA+SPACE+
        " FROM " + TABLE_FURNIZORI+","+TABLE_ADRESE+SPACE+
                "WHERE "+TABLE_FURNIZORI+"."+COLUMN_COD_ADRESA+"="+TABLE_ADRESE+"."+COLUMN_COD_ADRESA+SPACE
                +"ORDER BY "+TABLE_FURNIZORI+"."+COLUMN_COD_FURNIZOR;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Furnizor furnizor = new Furnizor();
                furnizor.setCod_furnizor(cursor.getInt(0));
                furnizor.setDenumire_furnizor(cursor.getString(1));
                furnizor.setNr_inregistrare_RC(cursor.getString(2));
                furnizor.setCod_adresa(cursor.getInt(3));
                furnizor.setRating(cursor.getInt(4));
                furnizor.setEmail(cursor.getString(5));
                Adresa adresa = new Adresa();
                adresa.setCod_adresa(cursor.getInt(6));
                adresa.setNumar(cursor.getInt(7));
                adresa.setStrada(cursor.getString(8));
                adresa.setLocalitate(cursor.getString(9));
                adresa.setJudet_sector(cursor.getString(10));
                adresa.setTelefon(cursor.getString(11));
                adresa.setTara(cursor.getString(12));
                furnizor.setAdresa(adresa);
                furnizorList.add(furnizor);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return furnizorList;
    }

    public Furnizor selectFurnizor(int cod_furnizor){
        Furnizor furnizor = new Furnizor();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT "
                +TABLE_FURNIZORI+"."+COLUMN_COD_FURNIZOR+","
                +TABLE_FURNIZORI+"."+COLUMN_DENUMIRE_FURNIZOR+","
                +TABLE_FURNIZORI+"."+COLUMN_NR_INREG_RC_FURNIZORI+","
                +TABLE_FURNIZORI+"."+COLUMN_COD_ADRESA+SPACE+","
                +TABLE_FURNIZORI+"."+COLUMN_RATING+SPACE+","
                +TABLE_FURNIZORI+"."+COLUMN_EMAIL_FURNIZOR+SPACE+","
                +TABLE_ADRESE+"."+COLUMN_COD_ADRESA+SPACE+","
                +TABLE_ADRESE+"."+COLUMN_NUMAR+SPACE+","
                +TABLE_ADRESE+"."+COLUMN_STRADA+SPACE+","
                +TABLE_ADRESE+"."+COLUMN_LOCALITATE+SPACE+","
                +TABLE_ADRESE+"."+COLUMN_JUDET_SECTOR+SPACE+","
                +TABLE_ADRESE+"."+COLUMN_TELEFON+SPACE+","
                +TABLE_ADRESE+"."+COLUMN_TARA+SPACE+
                " FROM " + TABLE_FURNIZORI+","+TABLE_ADRESE+SPACE+
                "WHERE "+TABLE_FURNIZORI+"."+COLUMN_COD_ADRESA+"="+TABLE_ADRESE+"."+COLUMN_COD_ADRESA+SPACE+
                "AND "+SPACE+TABLE_FURNIZORI+"."+COLUMN_COD_FURNIZOR+"="+cod_furnizor;

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                furnizor.setCod_furnizor(cursor.getInt(0));
                furnizor.setDenumire_furnizor(cursor.getString(1));
                furnizor.setNr_inregistrare_RC(cursor.getString(2));
                furnizor.setCod_adresa(cursor.getInt(3));
                furnizor.setRating(cursor.getInt(4));
                furnizor.setEmail(cursor.getString(5));
                Adresa adresa = new Adresa();
                adresa.setCod_adresa(cursor.getInt(6));
                adresa.setNumar(cursor.getInt(7));
                adresa.setStrada(cursor.getString(8));
                adresa.setLocalitate(cursor.getString(9));
                adresa.setJudet_sector(cursor.getString(10));
                adresa.setTelefon(cursor.getString(11));
                adresa.setTara(cursor.getString(12));
                furnizor.setAdresa(adresa);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return furnizor;
    }

    public ArrayList<CerereOferta> selectCereriOferta() throws ParseException {
        ArrayList<CerereOferta> listaCereriOferta = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query =  "SELECT "
                +TABLE_FURNIZORI+"."+COLUMN_COD_FURNIZOR+","
                +TABLE_MATERIALE+"."+COLUMN_COD_MATERIAL+","
                +TABLE_CERERI_OFERTA+"."+COLUMN_PRET+","
                +TABLE_CERERI_OFERTA+"."+COLUMN_CANTITATE+SPACE+","
                +TABLE_CERERI_OFERTA+"."+COLUMN_TERMEN_RASPUNS+SPACE+","
                +TABLE_CERERI_OFERTA+"."+COLUMN_DATA_LIVRARE+SPACE+","
                +TABLE_CERERI_OFERTA+"."+COLUMN_STATUS+SPACE+","
                +TABLE_CERERI_OFERTA+"."+COLUMN_COD_CERERE_OFERTA+SPACE+
                " FROM " + TABLE_FURNIZORI+","+TABLE_MATERIALE+","+TABLE_CERERI_OFERTA+SPACE+
                "WHERE "+TABLE_FURNIZORI+"."+COLUMN_COD_FURNIZOR+"="+TABLE_CERERI_OFERTA+"."+COLUMN_COD_FURNIZOR+SPACE+
                "AND "+TABLE_MATERIALE+"."+COLUMN_COD_MATERIAL+"="+TABLE_CERERI_OFERTA+"."+COLUMN_COD_MATERIAL+SPACE
                +"ORDER BY "+TABLE_CERERI_OFERTA+"."+COLUMN_COD_CERERE_OFERTA;
        Cursor cursor = db.rawQuery(query, null);


        if (cursor.moveToFirst()) {
            do {
                CerereOferta cerereOferta = new CerereOferta();
                cerereOferta.setFurnizor(selectFurnizor(Integer.parseInt(cursor.getString(0))));
                cerereOferta.setMaterial(selectMaterial(Integer.parseInt(cursor.getString(1))));
                cerereOferta.setPret(cursor.getDouble(2));
                cerereOferta.setCantitate(cursor.getDouble(3));
                cerereOferta.setTermen_limita_raspuns(cursor.getString(4));
                cerereOferta.setData_livrare(cursor.getString(5));
                cerereOferta.setStatus(CerereOferta.Status.valueOf(cursor.getString(6).toUpperCase()));
                cerereOferta.setCod_cerere_oferta(cursor.getInt(7));

                listaCereriOferta.add(cerereOferta);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return listaCereriOferta;
    }
    public CerereOferta selectCerereOferta(int cod_cerere_oferta) throws ParseException {
        CerereOferta cerereOferta = new CerereOferta();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT "
                +TABLE_CERERI_OFERTA+"."+COLUMN_COD_CERERE_OFERTA+","
                +TABLE_CERERI_OFERTA+"."+COLUMN_COD_FURNIZOR+","
                +TABLE_CERERI_OFERTA+"."+COLUMN_COD_MATERIAL+","
                +TABLE_CERERI_OFERTA+"."+COLUMN_PRET+","
                +TABLE_CERERI_OFERTA+"."+COLUMN_CANTITATE+SPACE+","
                +TABLE_CERERI_OFERTA+"."+COLUMN_STATUS+SPACE+","
                +TABLE_CERERI_OFERTA+"."+COLUMN_DATA_LIVRARE+SPACE+","
                +TABLE_CERERI_OFERTA+"."+COLUMN_TERMEN_RASPUNS+SPACE+
                " FROM " + TABLE_CERERI_OFERTA+SPACE+
                "WHERE "+TABLE_CERERI_OFERTA+"."+COLUMN_COD_CERERE_OFERTA+"="+cod_cerere_oferta;

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                cerereOferta.setCod_cerere_oferta(cursor.getInt(0));
                cerereOferta.setFurnizor(selectFurnizor(cursor.getInt(1)));
                cerereOferta.setMaterial(selectMaterial(cursor.getInt(2)));
                cerereOferta.setPret(cursor.getDouble(3));
                cerereOferta.setCantitate(cursor.getDouble(4));
                cerereOferta.setStatus(CerereOferta.Status.valueOf(cursor.getString(5).toUpperCase()));
                cerereOferta.setData_livrare(cursor.getString(6));
                cerereOferta.setTermen_limita_raspuns(cursor.getString(7));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return cerereOferta;
    }

    public boolean insertTaxa(String denumire, double procent) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_DENUMIRE_TAXA, denumire);
        contentValues.put(COLUMN_PROCENT_TAXA, procent);
        db.insert(TABLE_TAXE, null, contentValues);
        db.close();
        return true;
    }

    public boolean updateTaxa(int cod_taxa, String denumire_taxa, double procent) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_DENUMIRE_TAXA, denumire_taxa);
        contentValues.put(COLUMN_PROCENT_TAXA, procent);
        db.update(TABLE_TAXE, contentValues, COLUMN_COD_TAXA+" = ?", new String[] { Integer.toString(cod_taxa) } );
        db.close();
        return true;
    }

    public Integer deleteTaxa (int cod_taxa) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_TAXE,
                COLUMN_COD_TAXA+" = ? ",
                new String[] { Integer.toString(cod_taxa) });

    }


    public ArrayList<Taxa> selecteazaTaxe() throws ParseException {
        ArrayList<Taxa> listaTaxe = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT "
                +COLUMN_COD_TAXA+","
                +COLUMN_DENUMIRE_TAXA+","
                +COLUMN_PROCENT_TAXA+SPACE+
                " FROM " + TABLE_TAXE+SPACE;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Taxa taxa = new Taxa();
                taxa.setCod_taxa(cursor.getInt(0));
                taxa.setDenumire_taxa(cursor.getString(1));
                taxa.setProcent_taxa(cursor.getDouble(2));
                listaTaxe.add(taxa);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return listaTaxe;
    }

    public Taxa selectTaxa(int cod_taxa) throws ParseException {
        Taxa taxa= new Taxa();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT "+COLUMN_COD_TAXA+","
                +COLUMN_DENUMIRE_TAXA+","
                +COLUMN_PROCENT_TAXA+SPACE+
                " FROM " + TABLE_TAXE+SPACE+"WHERE"+SPACE+COLUMN_COD_TAXA+"="+cod_taxa;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                taxa.setCod_taxa(cursor.getInt(0));
                taxa.setDenumire_taxa(cursor.getString(1));
                taxa.setProcent_taxa(cursor.getDouble(2));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return taxa;
    }

    public ArrayList<Comanda> selectComenzi() throws ParseException {
        ArrayList<Comanda> listaComenzi = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query =  "SELECT "
                +COLUMN_COD_CERERE_OFERTA+SPACE+","
                +COLUMN_COD_TAXA+SPACE+","
                +COLUMN_COD_COMANDA+SPACE+""+
                " FROM " + TABLE_COMENZI+SPACE+
                "ORDER BY "+COLUMN_COD_COMANDA;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Comanda comanda = new Comanda();
                comanda.setCerereOferta(selectCerereOferta(cursor.getInt(0)));
                comanda.setTaxa(selectTaxa(cursor.getInt(1)));
                comanda.setCod_comanda(cursor.getInt(2));

                listaComenzi.add(comanda);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return listaComenzi;
    }
    public Comanda selectComanda(int cod_comanda) throws ParseException {
        Comanda comanda = new Comanda();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT "
                +TABLE_COMENZI+"."+COLUMN_COD_COMANDA+","
                +TABLE_COMENZI+"."+COLUMN_COD_CERERE_OFERTA+","
                +TABLE_COMENZI+"."+COLUMN_COD_TAXA+SPACE+
                " FROM " + TABLE_COMENZI+SPACE+
                "WHERE "+TABLE_COMENZI+"."+COLUMN_COD_COMANDA+"="+cod_comanda;

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                comanda.setCod_comanda(cursor.getInt(0));
                comanda.setCerereOferta(selectCerereOferta(cursor.getInt(1)));
                comanda.setTaxa(selectTaxa(cursor.getInt(2)));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return comanda;
    }


    public ArrayList<Receptie> selectReceptii() throws ParseException {
        ArrayList<Receptie> listaReceptii = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query =  "SELECT "
                +COLUMN_COD_RECEPTIE+SPACE+","
                +COLUMN_COD_COMANDA+SPACE+","
                +COLUMN_DATA_RECEPTIE+SPACE+","
                +COLUMN_CANTITATE_RECEPTIONATA+SPACE+""+
                " FROM " + TABLE_RECEPTII+SPACE+
                "ORDER BY "+COLUMN_COD_RECEPTIE;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Receptie receptie = new Receptie();
                receptie.setCod_receptie(cursor.getInt(0));
                receptie.setComanda(selectComanda(cursor.getInt(1)));
                receptie.setData_receptie(cursor.getString(2));
                receptie.setCantitate_receptionata(cursor.getDouble(3));

                listaReceptii.add(receptie);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return listaReceptii;
    }

    public Receptie selectReceptie(int cod_receptie) throws ParseException {
        Receptie receptie = new Receptie();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT "
                +TABLE_RECEPTII+"."+COLUMN_COD_RECEPTIE+","
                +TABLE_RECEPTII+"."+COLUMN_COD_COMANDA+","
                +TABLE_RECEPTII+"."+COLUMN_DATA_RECEPTIE+","
                +TABLE_RECEPTII+"."+COLUMN_CANTITATE_RECEPTIONATA+SPACE+
                " FROM " + TABLE_RECEPTII+SPACE+
                "WHERE "+TABLE_RECEPTII+"."+COLUMN_COD_RECEPTIE+"="+cod_receptie;

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                receptie.setCod_receptie(cursor.getInt(0));
                receptie.setComanda(selectComanda(cursor.getInt(1)));
                receptie.setData_receptie(cursor.getString(2));
                receptie.setCantitate_receptionata(cursor.getDouble(3));

            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return receptie;
    }
    public ArrayList<Factura> selectFacturi() throws ParseException {
        ArrayList<Factura> listaFacturi = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query =  "SELECT "
                +COLUMN_COD_FACTURA+SPACE+","
                +COLUMN_CANTITATE_FACTURATA+SPACE+","
                +COLUMN_DATA_FACTURA+SPACE+","
                +COLUMN_COD_RECEPTIE+SPACE+""+
                " FROM " + TABLE_FACTURI+SPACE+
                "ORDER BY "+COLUMN_COD_FACTURA;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Factura factura =  new Factura();
                factura.setCod_factura(cursor.getInt(0));
                factura.setCantitate_facturata(cursor.getDouble(1));
                factura.setData_factura(cursor.getString(2));
                factura.setReceptie(selectReceptie(cursor.getInt(3)));

                listaFacturi.add(factura);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return listaFacturi;
    }
    public Factura selectFactura(int cod_factura) throws ParseException {
        Factura factura = new Factura();
        SQLiteDatabase db = this.getReadableDatabase();
        String query =  "SELECT "
                +COLUMN_COD_FACTURA+SPACE+","
                +COLUMN_CANTITATE_FACTURATA+SPACE+","
                +COLUMN_DATA_FACTURA+SPACE+","
                +COLUMN_COD_RECEPTIE+SPACE+""+
                " FROM " + TABLE_FACTURI+SPACE+
                "ORDER BY "+COLUMN_COD_FACTURA;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                factura.setCod_factura(cursor.getInt(0));
                factura.setCantitate_facturata(cursor.getDouble(1));
                factura.setData_factura(cursor.getString(2));
                factura.setReceptie(selectReceptie(cursor.getInt(3)));


            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return factura;
    }


    public ArrayList<Plata> selectPlati() throws ParseException {
        ArrayList<Plata> listaPlati = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query =  "SELECT "
                +COLUMN_COD_PLATA+SPACE+","
                +COLUMN_SUMA_PLATITA+SPACE+","
                +COLUMN_DATA_PLATA+SPACE+","
                +COLUMN_COD_FACTURA+SPACE+""+
                " FROM " + TABLE_PLATI+SPACE+
                "ORDER BY "+COLUMN_COD_PLATA;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Plata plata = new Plata();
                plata.setCod_plata(cursor.getInt(0));
                plata.setSuma_platita(cursor.getDouble(1));
                plata.setData_plata(cursor.getString(2));
                plata.setFactura(selectFactura(cursor.getInt(3)));

                listaPlati.add(plata);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return listaPlati;
    }

}
