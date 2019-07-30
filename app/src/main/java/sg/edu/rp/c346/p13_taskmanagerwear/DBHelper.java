package sg.edu.rp.c346.p13_taskmanagerwear;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "task.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_TASK = "task";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TASK_TITLE = "task_title";
    private static final String COLUMN_TASK_DESCRIPTION = "task_description";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTaskTableSql = "CREATE TABLE " + TABLE_TASK + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TASK_TITLE + " TEXT,"
                + COLUMN_TASK_DESCRIPTION + " TEXT )";
        db.execSQL(createTaskTableSql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASK);
        onCreate(db);
    }

    public long insertTask(String task_title, String task_description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_TASK_TITLE, task_title);
        values.put(COLUMN_TASK_DESCRIPTION, task_description);

        long result = db.insert(TABLE_TASK, null, values);
        db.close();
        Log.d("SQL Insert ",""+ result); //id returned, shouldn’t be -1
        return result;
    }

    public ArrayList<String> getAllTasks() {
        ArrayList<String> tasks = new ArrayList<String>();

        String selectQuery = "SELECT " + COLUMN_ID + ","
                + COLUMN_TASK_TITLE + "," + COLUMN_TASK_DESCRIPTION + " FROM " + TABLE_TASK;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String description = cursor.getString(2);

                tasks.add(id + " " + title + "\n" + description);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return tasks;
    }

//    public int updateNote(Note data){
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(COLUMN_NOTE_CONTENT, data.getNoteContent());
//        String condition = COLUMN_ID + "= ?";
//        String[] args = {String.valueOf(data.getId())};
//        int result = db.update(TABLE_NOTE, values, condition, args);
//        db.close();
//        return result;
//    }
//
//    public int deleteNote(int id){
//        SQLiteDatabase db = this.getWritableDatabase();
//        String condition = COLUMN_ID + "= ?";
//        String[] args = {String.valueOf(id)};
//        int result = db.delete(TABLE_NOTE, condition, args);
//        db.close();
//        return result;
//    }


}
