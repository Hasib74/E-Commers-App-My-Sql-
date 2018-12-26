package com.example.dcl.onlineshopapp.Database.SqliteDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.example.dcl.onlineshopapp.Helper.FavoriteDB;

public class FavoriteDatabaseHelper extends SQLiteOpenHelper {
    private static int DATABASE_VERSION=1;
    private static String DATABASE_NAME="favoritedb";
   private static  String FAVORITE_TABLE="favorite_table";
    //Table info
    public static  String FAVORITE_ID="id";
    public static  String FAVORITE_NAME="name";
    public static  String FAVORITE_LINK="link";
    public static  String FAVORITE_PRICE="price";
    private static  String FAVORITE_MENUID="menuId";

    Context context;



    private static  String DATABASE_TABLE = "CREATE TABLE "
            + FAVORITE_TABLE + " (" + FAVORITE_ID + " INTEGER PRIMARY KEY, " + FAVORITE_NAME
            + " TEXT," + FAVORITE_LINK + " TEXT," + FAVORITE_PRICE
            + " TEXT," +FAVORITE_MENUID+" TEXT )";



    public FavoriteDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(DATABASE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void InsetIntoDatabase(FavoriteDB favoriteDB){
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(FAVORITE_ID,favoriteDB.getId());
        values.put(FAVORITE_NAME,favoriteDB.getName());
        values.put(FAVORITE_LINK,favoriteDB.getLink());
        values.put(FAVORITE_PRICE,favoriteDB.getPrice());
        values.put(FAVORITE_MENUID,favoriteDB.getMenuId());


      long data=  db.insert(FAVORITE_TABLE,null,values);
      if (data>-1){
          Log.d("MessageDB","Insurt");
//          Toast.makeText(context,"Insert",Toast.LENGTH_SHORT).show();
      }else {
          //Toast.makeText(context,"Insert",Toast.LENGTH_SHORT).show();
      }

        db.close();

    }

   public Cursor retivePossirion(String id){
       SQLiteDatabase db=this.getReadableDatabase();
       Cursor cursor;
       String sql ="SELECT id FROM "+FAVORITE_TABLE+" WHERE id="+id;
       cursor= db.rawQuery(sql,null);

       return cursor;



      /*  SQLiteDatabase db=this.getReadableDatabase();
        String[] colum={FAVORITE_NAME};
        Cursor cursor=db.query(FAVORITE_TABLE,null,FAVORITE_ID+"='"+id+"'",null,null,null,null);
        return cursor;*/
    }
    public Cursor retiveAllData(){
        SQLiteDatabase db=this.getReadableDatabase();
       // String[] colum={FAVORITE_NAME,FAVORITE_LINK,FAVORITE_PRICE};
        Cursor cursor=db.query(FAVORITE_TABLE,null,null,null,null,null,null);
        return cursor;
    }


    public  void deleteFavoriteTable(String id){
        SQLiteDatabase db=this.getWritableDatabase();

        db.delete(FAVORITE_TABLE,FAVORITE_ID+"='"+id+"'",null);
    }





}
