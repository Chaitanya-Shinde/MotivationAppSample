package com.example.motivationappsample.Utils;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.motivationappsample.Model.ReminderModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int VERSION =1;
    private static final String NAME = "ReminderDatabase";
    private static final String REM_TABLE = "reminder";
    private static final String ID = "id";
    private static final String TASK_TITLE="task_title";
    private static final String TASK_DESC = "task_desc";
    private static final String TASK_DATE = "task_date";
    private static final String TASK_TIME = "task_time";
    private static final String STATUS = "status";
    private static final String CREATE_REM_TABLE = "CREATE TABLE " + REM_TABLE + "(" + ID + "INTEGER PRIMARY KEY AUTOINCREMENT," +
            TASK_TITLE + "TEXT," + TASK_DESC + "TEXT," + TASK_DATE + "TEXT,"+ TASK_TIME +"TEXT," + STATUS + "INTEGER)";

    private SQLiteDatabase db;

        private DatabaseHandler(Context context){
            super(context,NAME,null,VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db){
            db.execSQL(CREATE_REM_TABLE);
        }



    @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
            //Drop older tables
            db.execSQL("DROP TABLE IF EXISTS " + REM_TABLE);
            //Create table again
            onCreate(db);
        }

        public void openDatabase(){
            db=this.getWritableDatabase();
        }

        public void insertTask(ReminderModel task){
            ContentValues cv = new ContentValues();
            cv.put(TASK_TITLE, task.getTaskTitle());
//            cv.put(TASK_DESC, task.getTaskDesc());
//            cv.put(TASK_TIME, task.getTaskTime());
//            cv.put(TASK_DATE,task.getTaskDate());
            cv.put(STATUS,0);
            db.insert(REM_TABLE, null,cv);
        }



        public List<ReminderModel> getAllTasks(){
            List<ReminderModel> dbList = new ArrayList<>();
            Cursor cur = null;
            db.beginTransaction();
            try{
                cur =db.query(REM_TABLE, null,null, null, null, null, null, null);
                if(cur != null){
                    if(cur.moveToFirst()){
                        do{
                            ReminderModel task = new ReminderModel();
                            task.setId(cur.getInt(cur.getColumnIndexOrThrow(ID)));
                            task.setTaskTitle(cur.getString(cur.getColumnIndexOrThrow(TASK_TITLE)));
//                            task.setTaskDesc(cur.getString(cur.getColumnIndex(TASK_DESC)));
//                            task.setTaskDate(cur.getString(cur.getColumnIndex(TASK_DATE)));
//                            task.setTaskTime(cur.getString(cur.getColumnIndex(TASK_TIME)));
                            task.setStatus(cur.getInt(cur.getColumnIndexOrThrow(STATUS)));
                            dbList.add(task);

                        }while (cur.moveToNext());
                    }
                }
            }finally {

                db.endTransaction();
                assert cur != null;
                cur.close();

            }
            return dbList;
        }

        public void updateStatus(int id, int status){
            ContentValues cv = new ContentValues();
            cv.put(STATUS, status);
            db.update(REM_TABLE, cv, ID + "=?" , new String[]{String.valueOf(id)});
        }

        public void updateTaskTitle(int id, String task_title){
            ContentValues cv = new ContentValues();
            cv.put(TASK_TITLE, task_title);
            db.update(REM_TABLE, cv , ID + "+?" , new String[]{String.valueOf(id)});
        }

        public  void  deleteTask(int id){
            db.delete(REM_TABLE, ID + "+?" , new String[]{String.valueOf(id)});
        }

    }


