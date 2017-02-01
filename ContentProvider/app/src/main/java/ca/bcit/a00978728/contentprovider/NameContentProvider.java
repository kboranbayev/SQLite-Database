package ca.bcit.a00978728.contentprovider;


import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;


public class NameContentProvider extends ContentProvider{

    private static final UriMatcher uriMatcher;
    private static final int NAMES_URI = 1;
    private NamesOpenHelper namesOpenHelper;
    public static final Uri CONTENT_URI;

    static
    {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI("ca.bcit.a00978728.contentprovider","names",NAMES_URI);
    }

    static
    {
        CONTENT_URI = Uri.parse("content://ca.bcit.a00978728.contentprovider/names");
    }


    @Override
    public boolean onCreate()
    {
        namesOpenHelper = NamesOpenHelper.getInstance(getContext());
        return true;
    }

    @Override
    public Cursor query(final Uri uri,
                        final String[] projection,
                        final String selection,
                        final String[] selectionArgs,
                        final String sortOrder)
    {
        final Cursor cursor;

        switch(uriMatcher.match(uri)){
            case NAMES_URI:
            {
                final SQLiteDatabase db;

                db = namesOpenHelper.getWritableDatabase();
                cursor = namesOpenHelper.getAllNames(getContext(), db);
                break;
            }
            default:
            {
                throw new IllegalArgumentException("Unsupported URI: " + uri);
            }
        }
        return (cursor);
    }

    @Override
    public String getType(final Uri uri)
    {
        final String type;

        switch (uriMatcher.match(uri))
        {
            case NAMES_URI:
                type = "vnd.android.cursor.dir/vnd.ca.bcit.a00978728.contentprovider.names";
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        return (type);
    }

    @Override
    public int delete(final Uri uri, final String selection, final String[] selectionArgs)
    {
        //throw new UnsupportedOperationException("Not yet implemented (delete)");
        SQLiteDatabase sqlDB = namesOpenHelper.getWritableDatabase();
        int rowsDeleted = 0;
        switch (uriMatcher.match(uri)) {
            case NAMES_URI:
                rowsDeleted = sqlDB.delete(NamesOpenHelper.DB_NAME, selection,
                        selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public Uri insert(final Uri uri, final ContentValues values)
    {
        //throw new UnsupportedOperationException("Not yet implemented (insert)");
        int uriType = uriMatcher.match(uri);
        SQLiteDatabase sqlDB = namesOpenHelper.getWritableDatabase();
        long id = 0;
        switch(uriType)
        {
            case NAMES_URI:
                id = sqlDB.insert(NamesOpenHelper.DB_NAME, null, values);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(NamesOpenHelper.NAME_TABLE_NAME + "/" + id);

    }

    @Override
    public int update(final Uri uri, final ContentValues values, final String selection, final String[] selectionArgs)
    {
        //throw new UnsupportedOperationException("Not yet implemented (update)");
        int uriType = uriMatcher.match(uri);
        SQLiteDatabase sqlDB = namesOpenHelper.getWritableDatabase();
        int rowsUpdated = 0;
        switch (uriType) {
            case NAMES_URI:
                rowsUpdated = sqlDB.update(NamesOpenHelper.DB_NAME,
                        values,
                        selection,
                        selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;
    }
}
