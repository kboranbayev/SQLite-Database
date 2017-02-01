package ca.bcit.a00978728.contentprovider;


import android.app.Activity;
import android.app.ListActivity;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

/*
public class MainActivity extends ListActivity {

    private static final String TAG = MainActivity.class.getName();
    private NamesOpenHelper namesOpenHelper;
    private SimpleCursorAdapter adapter;

    protected EditText GetName, GetPhoneNumber, GetSubject;
    protected Button Submit, EditData, DisplayData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final LoaderManager manager;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        namesOpenHelper = NamesOpenHelper.getInstance(getApplicationContext());
        adapter = new SimpleCursorAdapter(getBaseContext(),
                android.R.layout.simple_list_item_1,
                null,
                new String[]
                        {
                                "name",
                        },
                new int[]
                        {
                        android.R.id.text1,
                        },
                0);
        setListAdapter(adapter);
        manager = getLoaderManager();
        manager.initLoader(0, null, new NameLoaderCallbacks());
        init();
    }

    private void init(){
        final SQLiteDatabase db;
        final long numEntries;

        db = namesOpenHelper.getWritableDatabase();
        numEntries = namesOpenHelper.getNumberOfNames(db);

        if (numEntries == 0)
        {
            db.beginTransaction();
            try
            {
                namesOpenHelper.insertName(db, "D'Arcy");
                namesOpenHelper.insertName(db, "Medhat");
                namesOpenHelper.insertName(db, "Albert");
                namesOpenHelper.insertName(db, "Jason");
            }
            finally {
                db.endTransaction();
            }
        }

        db.close();
    }

    private class NameLoaderCallbacks implements LoaderCallbacks<Cursor> {
        @Override
        public Loader<Cursor> onCreateLoader(final int id, final Bundle args)
        {
            final Uri uri;
            final CursorLoader loader;

            uri = NameContentProvider.CONTENT_URI;
            loader = new CursorLoader(MainActivity.this, uri, null, null, null, null);

            return (loader);
        }

        @Override
        public void onLoadFinished(final Loader<Cursor> loader, final Cursor data)
        {
            adapter.swapCursor(data);
        }

        @Override
        public void onLoaderReset(final Loader<Cursor> loader)
        {
            adapter.swapCursor(null);
        }
    }
}
*/

public class MainActivity extends Activity {

    private static final String TAG = MainActivity.class.getName();
    private NamesOpenHelper namesOpenHelper;
    private SimpleCursorAdapter adapter;

    protected EditText GetName, GetPhoneNumber, GetSubject;
    protected Button Submit, EditData, DisplayData;
    protected SQLiteDatabase SQLITEDATABASE;
    protected String Name, PhoneNumber, Subject;
    protected Boolean CheckEditTextEmpty;
    protected String SQLiteQuery;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final LoaderManager manager;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GetName = (EditText)findViewById(R.id.editText1);

        GetPhoneNumber = (EditText)findViewById(R.id.editText2);

        GetSubject = (EditText)findViewById(R.id.editText3);

        Submit = (Button)findViewById(R.id.button1);

        EditData = (Button)findViewById(R.id.button2);

        DisplayData = (Button)findViewById(R.id.button3);

        Submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                DBCreate();

                SubmitData2SQLiteDB();

            }
        });

        EditData.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Intent intent = new Intent(MainActivity.this, EditDataActivity.class);
                startActivity(intent);

            }
        });

        DisplayData.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Intent intent = new Intent(MainActivity.this, ListViewActivity.class);
                startActivity(intent);

            }
        });
    }

    public void DBCreate()
    {
        SQLITEDATABASE = openOrCreateDatabase("DemoDataBase", Context.MODE_PRIVATE, null);
        SQLITEDATABASE.execSQL("CREATE TABLE IF NOT EXISTS demoTable(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, name VARCHAR, phone_number VARCHAR, subject VARCHAR);");
    }

    public void SubmitData2SQLiteDB(){

        Name = GetName.getText().toString();

        PhoneNumber = GetPhoneNumber.getText().toString();

        Subject = GetSubject.getText().toString();

        CheckEditTextIsEmptyOrNot( Name,PhoneNumber, Subject);

        if(CheckEditTextEmpty == true)
        {

            SQLiteQuery = "INSERT INTO demoTable (name,phone_number,subject) VALUES('"+Name+"', '"+PhoneNumber+"', '"+Subject+"');";

            SQLITEDATABASE.execSQL(SQLiteQuery);

            Toast.makeText(MainActivity.this,"Data Submit Successfully", Toast.LENGTH_LONG).show();

            ClearEditTextAfterDoneTask();

        }
        else {

            Toast.makeText(MainActivity.this,"Please Fill All the Fields", Toast.LENGTH_LONG).show();
        }
    }

    public void CheckEditTextIsEmptyOrNot(String Name,String PhoneNumber, String subject ){

        if(TextUtils.isEmpty(Name) || TextUtils.isEmpty(PhoneNumber) || TextUtils.isEmpty(Subject)){

            CheckEditTextEmpty = false ;

        }
        else {
            CheckEditTextEmpty = true ;
        }
    }

    public void ClearEditTextAfterDoneTask(){

        GetName.getText().clear();
        GetPhoneNumber.getText().clear();
        GetSubject.getText().clear();

    }
}
