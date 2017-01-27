package com.example.android.moviefinder.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.example.android.moviefinder.R;

/**
 * Created by tiago.carvalho on 01/25/17.
 */

public class MovieProvider extends ContentProvider {

    public static final int FAVORITES_CODE = 100;
    public static final int FAVORITES_CODE_WITH_TMDBID = 101;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    private MovieFinderDBHelper mOpenHelper;

    @Override
    public boolean onCreate() {
        mOpenHelper = new MovieFinderDBHelper(getContext());

        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        final SQLiteDatabase db = mOpenHelper.getReadableDatabase();

        int matcher = sUriMatcher.match(uri);

        Cursor cursor = null;

        switch (matcher) {
            case FAVORITES_CODE:
                cursor = db.query(MovieFinderContract.MovieFinderEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case FAVORITES_CODE_WITH_TMDBID:
                String movieId = uri.getLastPathSegment();
                String[] selectionArguments = new String[]{movieId};
                cursor = db.query(MovieFinderContract.MovieFinderEntry.TABLE_NAME, projection, MovieFinderContract.MovieFinderEntry.COLUMN_TMDBID + " =? ", selectionArguments, null, null, null);
                break;
            default:
                throw new UnsupportedOperationException(getContext().getString(R.string.not_supported_operation));
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        int match = sUriMatcher.match(uri);

        switch (match) {
            case FAVORITES_CODE:
                return "vnd.android.cursor.dir" + "/" + MovieFinderContract.CONTENT_AUTHORITY + "/" + MovieFinderContract.PATH_FAVORITES;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();

        int matcher = sUriMatcher.match(uri);
        Uri returnUri;
        Cursor cursor = null;

        switch (matcher) {
            case FAVORITES_CODE:
                long id = db.insert(MovieFinderContract.MovieFinderEntry.TABLE_NAME, null, values);
                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(MovieFinderContract.MovieFinderEntry.CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException(getContext().getString(R.string.not_supported_operation));
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();

        int matcher = sUriMatcher.match(uri);
        int rowsDeleted;
        if (null == selection) {
            selection = "1";
        }

        switch (matcher) {
            case FAVORITES_CODE_WITH_TMDBID:
                String id = uri.getPathSegments().get(1);
                rowsDeleted = db.delete(MovieFinderContract.MovieFinderEntry.TABLE_NAME, " _id=? ", new String[]{id});
                break;
            default:
                throw new UnsupportedOperationException(getContext().getString(R.string.not_supported_operation));
        }
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException(getContext().getString(R.string.not_supported_operation));
    }

    public static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = MovieFinderContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, MovieFinderContract.PATH_FAVORITES, FAVORITES_CODE);
        matcher.addURI(authority, MovieFinderContract.PATH_FAVORITES + "/#", FAVORITES_CODE_WITH_TMDBID);

        return matcher;
    }
}