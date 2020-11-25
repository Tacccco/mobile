package com.example.android.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

public class MyContentProvider extends ContentProvider {
    private DatabaseHelper db;
    private static final UriMatcher um = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        um.addURI("MyAccounts","user_info",1);
    }
    public MyContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        int match = um.match(uri);
        switch (match){
            case 1:
                return "com.onon.own.Provider/user_info";
                default: return null;
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        if(um.match(uri) == 1){db.getWritableDatabase().insert(db.TABLE_USER,null, values);
        return uri;}
        return null;
    }

    @Override
    public boolean onCreate() {
        db = new DatabaseHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        if(um.match(uri) == 1)return db.getWritableDatabase().query(db.TABLE_USER, projection, selection, selectionArgs, null,null, sortOrder);
        return null;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        if(um.match(uri) == 1) return  db.getWritableDatabase().update(db.TABLE_USER,values,selection,selectionArgs);
        return 0;
    }
}
