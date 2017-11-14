package com.romanpulov.library.dropbox;

import android.content.Context;
import android.content.SharedPreferences;

import com.dropbox.core.android.Auth;
import com.dropbox.core.v2.DbxClientV2;

/**
 * Helper class for Dropbox operation
 * Created by romanpulov on 14.11.2017.
 */
public class DropboxHelper {
    public class DBHException extends Exception {
        public DBHException() {}
    }

    public class DBHNoAccessTokenException extends DBHException {
        public DBHNoAccessTokenException() {
            super();
        }
    }

    //private constants
    private static final String SHARED_PREFERENCES_NAME = "romanpulov-library-dropbox-preferences";
    private static final String SHARED_PREFERENCES_ACCESS_TOKEN = "access-token";

    private final Context mContext;
    private final String mClientIdentifier;
    private final SharedPreferences mPrefs;
    private String mAccessToken;

    public static DropboxHelper getInstance(Context context) {
        return DropboxHelper.getInstance(context, context.getPackageName());
    }

    public static DropboxHelper getInstance(Context context, String clientIdentifier) {
        return new DropboxHelper(context, clientIdentifier);
    }

    private DropboxHelper(Context context, String clientIdentifier) {
        mContext = context;
        mClientIdentifier = clientIdentifier;
        mPrefs = mContext.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        mAccessToken = getAccessToken();
    }

    public String getAccessToken() {
        return mPrefs.getString(SHARED_PREFERENCES_ACCESS_TOKEN, null);
    }

    private void setAccessToken(String accessToken) {
        mPrefs.edit().putString(SHARED_PREFERENCES_ACCESS_TOKEN, accessToken).apply();
        mAccessToken = accessToken;
    }

    public void invokeAuthActivity(String appKey) {
        Auth.startOAuth2Authentication(mContext, appKey);
    }

    private String getAuthToken() {
        return Auth.getOAuth2Token();
    }

    public void refreshAccessToken() {
        String newAccessToken = getAuthToken();
        if (newAccessToken != null) {
            setAccessToken(newAccessToken);
        }
    }

    public DbxClientV2 getClient() {
        refreshAccessToken();
        DropboxClientFactory.init(mClientIdentifier, mAccessToken);
        return DropboxClientFactory.getClient();
    }

    public void validateDropBox() throws DBHException {
        if (getAccessToken() == null)
            throw new DBHNoAccessTokenException();
    }
}
