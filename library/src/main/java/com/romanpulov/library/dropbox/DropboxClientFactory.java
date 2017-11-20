package com.romanpulov.library.dropbox;

import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.http.OkHttp3Requestor;
import com.dropbox.core.v2.DbxClientV2;

import java.util.Locale;

/**
 * Singleton instance of and friends
 */

public class DropboxClientFactory {

    private static DbxClientV2 sDbxClient;

    public static void init(String clientIdentifier, String accessToken) {
        if (sDbxClient == null) {
            String userLocale = Locale.getDefault().toString();
            DbxRequestConfig requestConfig = DbxRequestConfig.newBuilder(clientIdentifier)
                    .withHttpRequestor(new OkHttp3Requestor(OkHttp3Requestor.defaultOkHttpClient()))
                    .build();

            sDbxClient = new DbxClientV2(requestConfig, accessToken);
        }
    }

    public static DbxClientV2 getClient() {
        if (sDbxClient == null) {
            throw new IllegalStateException("Client not initialized.");
        }
        return sDbxClient;
    }
}
