package ca.bcit.a00978728.contentprovider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class NamesOpenHelper extends SQLiteOpenHelper {

    private static final String TAG = NamesOpenHelper.class.getName();
    private static final int SCHEMA_VERSION = 1;
    protected static final String DB_NAME = "names.db";
    protected static final String NAME_TABLE_NAME = "name";
    protected static final String ID_COLUMN_NAME = "_id";
    private static final String NAME_COLUMN_NAME = "name";

    protected static final String KEY_ID = "id";
    protected static final String TABLE_NAME = "demoTable";
    protected static final String KEY_NAME = "name";
    protected static final String KEY_PHONE_NUMBER = "phone_number";
    protected static final String KEY_SUBJECT = "subject";

    private static NamesOpenHelper instance;

    public NamesOpenHelper(final Context ctx)
    {
        super(ctx, DB_NAME, null, SCHEMA_VERSION);
    }

    public synchronized static NamesOpenHelper getInstance(final Context context)
    {
        if (instance == null)
        {
            instance = new NamesOpenHelper(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(final SQLiteDatabase db)
    {
        final String CREATE_NAME_TABLE;

        /*CREATE_NAME_TABLE = "CREATE TABLE IF NOT EXISTS " + NAME_TABLE_NAME + " ( " +
                ID_COLUMN_NAME + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NAME_COLUMN_NAME + " TEXT NOT NULL)";*/
        CREATE_NAME_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ( " +
                KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_NAME + " VARCHAR, " +
                KEY_PHONE_NUMBER + " VARCHAR, " +
                KEY_SUBJECT + " VARCHAR)";
        db.execSQL(CREATE_NAME_TABLE);
    }

    @Override
    public void onUpgrade(final SQLiteDatabase db,
                          final int oldVersion,
                          final int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + NAME_TABLE_NAME);
        onCreate(db);
    }

    public long getNumberOfNames(final SQLiteDatabase db)
    {
        final long numEntries;

        numEntries = DatabaseUtils.queryNumEntries(db, NAME_TABLE_NAME);

        return (numEntries);
    }

    public void insertName(final SQLiteDatabase db,
                           final String name)
    {
        final ContentValues contentValues;

        contentValues = new ContentValues();
        contentValues.put(NAME_COLUMN_NAME, name);
        db.insert(NAME_TABLE_NAME, null, contentValues);
    }

    public int deleteName(final SQLiteDatabase db,
                          final String name)
    {
        final int rows;

        rows = db.delete(NAME_TABLE_NAME, NAME_COLUMN_NAME + " = ?",
                new String[]
                        {
                                name,
                        });
        return (rows);
    }


    public Cursor getAllNames(final Context context, final SQLiteDatabase db)
    {
        final Cursor cursor;

        cursor = db.query(NAME_TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null,
                null);

        cursor.setNotificationUri(context.getContentResolver(), NameContentProvider.CONTENT_URI);
        return (cursor);
    }
}
