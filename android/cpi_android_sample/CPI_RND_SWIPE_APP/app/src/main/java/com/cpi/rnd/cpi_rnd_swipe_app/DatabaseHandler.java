package com.cpi.rnd.cpi_rnd_swipe_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "rnd_cpi_test";

    private static final String TABLE_NAME="file_swipe_row";

    private static final String ROW_Key = "row_id";
    private static final String ROW_Value= "result";

    public DatabaseHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){

        String CREATE_TABLE = String.format("CREATE TABLE %s(id INTEGER primary key, %s INTEGER,%s TEXT);",TABLE_NAME, ROW_Key, ROW_Value);
        db.execSQL(CREATE_TABLE);
//        onUpgrade(db, 1,1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL(String.format("DROP TABLE IF EXISTS %s",TABLE_NAME));
        onCreate(db);
    }



    void addRowResult(int row_id, String result){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ROW_Key, row_id);
        values.put(ROW_Value, result);

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    String getRowResult(int row_id){

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, ROW_Key +"=?", new String[]{String.valueOf(row_id)}, null, null,null,null);

        if (cursor != null){
            cursor.moveToFirst();

        }
        return cursor.getString(2);
    }

    Boolean rowIsPresent(int row_id){

        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.query(TABLE_NAME, null, ROW_Key +"=?", new String[]{String.valueOf(row_id)}, null, null,null,null);
        String countQuery = String.format("Select * from %s where row_id=%d;", TABLE_NAME, row_id);
        Cursor cursor = db.rawQuery(countQuery, null);

        if (cursor != null) {
            if(cursor.getCount()==0){
                return false;
            }

        }
        return true;
    }


    int updateRowResult(int row_id, String result){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ROW_Value, result);

        return db.update(TABLE_NAME, values, ROW_Key + "=?", new String[]{String.valueOf(row_id)});

    }

    public int getTableRowCount(){

        String countQuery = String.format("Select * from %s", TABLE_NAME);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public int getTableRowCountByResult(String result){
//          String[] columns ={"result"};

        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.query(TABLE_NAME, columns, "result=?", new String[]{result},null,null,null);
        String resultCount = String.format("Select * from %s where result=\"%s\";", TABLE_NAME, result);
        Cursor cursor = db.rawQuery(resultCount, null);
        int count = cursor.getCount();
//        Log.d("DB_row_by_filer", String.valueOf(count));
        cursor.close();
        return count;
    }

    public List<String> getRowListByResult(String result){
        List<String> rowListByResult = new ArrayList<String>();
        String[] columns ={"row_id","result"};

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME,columns,  "result=?", new String[]{result},null,null,null);

        if(cursor.moveToFirst()){
            do{
//                Log.d("DB_row_by_filer_cnt", String.valueOf(cursor.getCount()));
                rowListByResult.add(String.format("row_id %d",cursor.getInt(0)));
            }while(cursor.moveToNext());
        }
        return rowListByResult;
    }
}
