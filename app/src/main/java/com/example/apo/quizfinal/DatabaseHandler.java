package com.example.apo.quizfinal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by apo on 10/12/2016.
 */

public class DatabaseHandler extends SQLiteOpenHelper {
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "userManager";

    // Contacts table name
    private static final String TABLE_USER = "user";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PASS = "password";
    private static final String SCORE_TABLE = "scorer" ;
    private static final String KEY_ID_SCORE = "id_user";
    private static final String KEY_SCORE = "score";
    private static final String KEY_TIME = "created_at";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating User Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_PASS + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);

        String CREATE_SCORE_TABLE = "CREATE TABLE " + SCORE_TABLE + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_ID_SCORE + " INTEGER,"
                + KEY_SCORE + " INTEGER," + KEY_TIME + " DATETIME DEFAULT CURRENT_TIMESTAMP)";
        db.execSQL(CREATE_SCORE_TABLE);
    }
//score table--------------------------------------------------------------------------


    //inputing score
    void addScore(User user,int score) {
        SQLiteDatabase scoredb = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID_SCORE, user.getID()); // User Name
        values.put(KEY_SCORE, score); // User com.example.apo.quizfinal.Score
        values.put(KEY_TIME,getDateTime());


        // Inserting Row

        scoredb.insert(SCORE_TABLE, null, values);
        scoredb.close(); // Closing database connection
    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    // Getting single score
    List<Score> getScore(int user_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        List<Score> scoresforplayer=new ArrayList<>();
        Cursor cursor = db.query(SCORE_TABLE, new String[] { KEY_SCORE,
                         KEY_TIME }, KEY_ID_SCORE + "=?",
                new String[] { String.valueOf(user_id) }, null, null,KEY_SCORE+" DESC"  , null);

        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast() == false) {
                int score=cursor.getInt(0);
                String datetime= cursor.getString(1);
                Score tempscore=new Score(score,datetime);
                scoresforplayer.add(tempscore);
                cursor.moveToNext();
            }
        }

        return scoresforplayer;
    }

    // Getting All Scores

    public List<Score> getAllScores() {

        // Select All Query


        SQLiteDatabase db = this.getReadableDatabase();
        List<Score> scorelist=new ArrayList<>();


        Cursor cursor = db.rawQuery("SELECT " + KEY_SCORE +" FROM " + SCORE_TABLE + " ORDER BY " + KEY_SCORE + " DESC", null);


        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                int score=cursor.getInt(0);
                String datetime= cursor.getString(1);
                Score tempscore=new Score(score,datetime);
                scorelist.add(tempscore);

            } while (cursor.moveToNext());
         }
        return scorelist;
    }


//---------------------------------------------------------------------------------------------------
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new contact
    boolean addContact(User user) {
        boolean contactadded = false;
        SQLiteDatabase db = this.getWritableDatabase();


        String usersSelectQuery = String.format("SELECT * FROM %s WHERE %s = ? LIMIT 1", TABLE_USER , KEY_NAME);

        Cursor cursor = db.rawQuery(usersSelectQuery, new String[]{(user.getName())});

        try
        {
            if (cursor.moveToFirst())
            {
                contactadded = false;
            }
            else
            {
                ContentValues values = new ContentValues();
                values.put(KEY_NAME, user.getName()); // User Name
                values.put(KEY_PASS, md5(user.getPassword())); // User Password

                // Inserting Row
                db.insert(TABLE_USER, null, values);
                db.close(); // Closing database connection
                contactadded = true;
            }
        } finally
        {
            if (cursor != null && !cursor.isClosed())
            {
                cursor.close();
            }
        }
        return contactadded;
    }

    // Getting single contact
    User getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USER, new String[] { KEY_ID,
                        KEY_NAME, KEY_PASS }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        User user = new User(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        // return contact
        return user;
    }


    //logging in
    public User login (String name, String pass)
    {
        User user = null;
        SQLiteDatabase db = this.getWritableDatabase();


        String usersSelectQuery = String.format("SELECT * FROM %s WHERE %s = ? LIMIT 1", TABLE_USER , KEY_NAME);

        Cursor cursor = db.rawQuery(usersSelectQuery, new String[]{(name)});

        try
        {
            if (cursor.moveToFirst())
            {	int id = cursor.getInt(0);
                String username = cursor.getString(1);
                String userpass = cursor.getString(2);

                if (userpass.equals(md5(pass)))
                {
                    user = new User(id, username, userpass);
                }
            }

        } finally
        {
            if (cursor != null && !cursor.isClosed())
            {
                cursor.close();
            }
        }
        return user;
    }





    // Getting All Contacts
    public List<User> getAllContacts() {
        List<User> contactList = new ArrayList<User>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_USER;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setID(Integer.parseInt(cursor.getString(0)));
                user.setName(cursor.getString(1));
                user.setPassword(cursor.getString(2));
                // Adding user to list
                contactList.add(user);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }

    // Updating single contact
    public int updateContact(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, user.getName());
        values.put(KEY_PASS, user.getPassword());

        // updating row
        return db.update(TABLE_USER, values, KEY_ID + " = ?",
                new String[] { String.valueOf(user.getID()) });
    }

    // Deleting single contact
    public void deleteContact(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USER, KEY_ID + " = ?",
                new String[] { String.valueOf(user.getID()) });
        db.close();
    }


    // Getting contacts Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_USER;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

    private String md5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i=0; i<messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

}

