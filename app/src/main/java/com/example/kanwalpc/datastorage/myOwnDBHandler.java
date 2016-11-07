package com.example.kanwalpc.datastorage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Kanwal PC on 11/7/2016.
 */
public class myOwnDBHandler extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME="season_record.db";
    private static final int DATABASE_VERSION=1;
    private static final String TABLE_NAME="season_record";
    private static final String COLUMN_ID="_id";
    private static final String COLUMN_SEASON_NAME="season_name";
    private static final String COLUMN_SEASON_RATE="season_rate";

    private static final String CREATE_TABLE="CREATE TABLE "+ TABLE_NAME + "("+
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , "+
            COLUMN_SEASON_NAME + " VARCHAR(255), "+
            COLUMN_SEASON_RATE + " FLOAT);";

    private static final String DROP_TABLE="DROP TABLE IF EXISTS "+ TABLE_NAME;



    public myOwnDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {
            sqLiteDatabase.execSQL(CREATE_TABLE);
        }catch (SQLException e){
         Message.message(context, " "+e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        try {
            sqLiteDatabase.execSQL(DROP_TABLE);
            onCreate(sqLiteDatabase);
        }catch (SQLException e){
            Message.message(context," "+e);
        }
    }

    public long insertInToTable(String seasonName, Float seasonRate){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(COLUMN_SEASON_NAME, seasonName);
        values.put(COLUMN_SEASON_RATE, seasonRate);
        long id=db.insert(TABLE_NAME, null,values);
        return id;
    }

    public String printDatabase(){
     SQLiteDatabase db=getWritableDatabase();
        String[] columns={COLUMN_ID, COLUMN_SEASON_NAME, COLUMN_SEASON_RATE};

        Cursor cursor=db.query(TABLE_NAME, columns, null, null, null,null,null);
        StringBuffer buffer=new StringBuffer();
        while(cursor.moveToNext()){
            int index1=cursor.getColumnIndex(COLUMN_ID);
            int column_id=cursor.getInt(index1);

            int index2=cursor.getColumnIndex(COLUMN_SEASON_NAME);
            String column_season_name=cursor.getString(index2);

            int index3=cursor.getColumnIndex(COLUMN_SEASON_RATE);
            Float column_season_rate=cursor.getFloat(index3);

            buffer.append(column_id+" "+column_season_name+" "+column_season_rate+"\n");
        }
        return buffer.toString();
    }

    public String searchSeasonName(String seasonName){
        SQLiteDatabase db=getWritableDatabase();
        String[] columns={COLUMN_ID, COLUMN_SEASON_NAME, COLUMN_SEASON_RATE};

        Cursor cursor=db.query(TABLE_NAME, columns, COLUMN_SEASON_NAME+ "='" +seasonName+ "'", null,null,null,null);
        StringBuffer buffer=new StringBuffer();
        while(cursor.moveToNext()){
            int index1=cursor.getColumnIndex(COLUMN_ID);
            int column_id=cursor.getInt(index1);

            int index2=cursor.getColumnIndex(COLUMN_SEASON_NAME);
            String column_season_name=cursor.getString(index2);

            int index3=cursor.getColumnIndex(COLUMN_SEASON_RATE);
            Float column_season_rate=cursor.getFloat(index3);

            buffer.append(column_id+" "+column_season_name+" "+column_season_rate+"\n");
        }
        return buffer.toString();
    }

    public int setUpdatedRecord(String oldName, String newName, Float rating){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(COLUMN_SEASON_NAME, newName);
        values.put(COLUMN_SEASON_RATE, rating);
        String[] whereArgs={oldName};
        int count=db.update(TABLE_NAME,values,COLUMN_SEASON_NAME +"=?",whereArgs);
        return count;
    }

    public int deleteItem(String seasonName){
        SQLiteDatabase db=getWritableDatabase();
        String[] whereArgs={seasonName};
        int count=db.delete(TABLE_NAME, COLUMN_SEASON_NAME +"=?", whereArgs);
        return count;
    }
}
