package com.ccy.janurarychat.server.network.http;

import android.util.Log;


import org.apache.http.HttpResponse;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.protocol.HttpContext;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.UnknownHostException;

class AsyncHttpRequest implements Runnable {

    private final String tag = AsyncHttpRequest.class.getSimpleName();

    private final AbstractHttpClient client;
    private final HttpContext context;
    private final HttpUriRequest request;
    private final ResponseHandlerInterface responseHandler;
    private int executionCount;

    public AsyncHttpRequest(AbstractHttpClient client, HttpContext context, HttpUriRequest request, ResponseHandlerInterface responseHandler) {
        this.client = client;
        this.context = context;
        this.request = request;
        this.responseHandler = responseHandler;

        //断点续传处理
        if (this.responseHandler instanceof BreakpointHttpResponseHandler) {
            BreakpointHttpResponseHandler breakpointHandler = (BreakpointHttpResponseHandler)this.responseHandler;
            File tempFile = breakpointHandler.getTempFile();
            if (tempFile.exists()) {
                long previousFileSize = tempFile.length();
                Log.e(tag, "previousFileSized: " + previousFileSize);
                this.request.setHeader("RANGE", "bytes=" + previousFileSize + "-");
            }
        }
    }

    @Override
    public void run() {
        if (responseHandler != null) {
            responseHandler.sendStartMessage();
        }

        try {
            makeRequestWithRetries();
        } catch (IOException e) {
            if (responseHandler != null) {
                responseHandler.sendFailureMessage(0, null, null, e);
            }
        }

        if (responseHandler != null) {
            responseHandler.sendFinishMessage();
        }
    }

    private void makeRequest() throws IOException {
        if (!Thread.currentThread().isInterrupted()) {
            // Fixes #115
            if (request.getURI().getScheme() == null) {
                // subclass of IOException so processed in the caller
                throw new MalformedURLException("No valid URI scheme was provided");
            }

            HttpResponse response = client.execute(request, context);

            if (!Thread.currentThread().isInterrupted()) {
                if (responseHandler != null) {
                    responseHandler.sendResponseMessage(response);
                }
            }
        }
    }

    private void makeRequestWithRetries() throws IOException {
        boolean retry = true;
        IOException cause = null;
        HttpRequestRetryHandler retryHandler = client.getHttpRequestRetryHandler();
        try {
            while (retry) {
                try {
                    makeRequest();
                    return;
                } catch (UnknownHostException e) {
                    // switching between WI-FI and mobile data networks can cause a retry which then results in an UnknownHostException
                    // while the WI-FI is initialising. The retry logic will be invoked here, if this is NOT the first retry
                    // (to assist in genuine cases of unknown host) which seems better than outright failure
                    cause = new IOException("UnknownHostException exception: " + e.getMessage());
                    retry = (executionCount > 0) && retryHandler.retryRequest(cause, ++executionCount, context);
                } catch (NullPointerException e) {
                    // there's a bug in HttpClient 4.0.x that on some occasions causes
                    // DefaultRequestExecutor to throw an NPE, see
                    // http://code.google.com/p/android/issues/detail?id=5255
                    cause = new IOException("NPE in HttpClient: " + e.getMessage());
                    retry = retryHandler.retryRequest(cause, ++executionCount, context);
                } catch (IOException e) {
                    cause = e;
                    retry = retryHandler.retryRequest(cause, ++executionCount, context);
                }
                if (retry && (responseHandler != null)) {
                    responseHandler.sendRetryMessage();
                }
            }
        } catch (Exception e) {
            // catch anything else to ensure failure message is propagated
            Log.e("AsyncHttpRequest", "Unhandled exception origin cause", e);
            cause = new IOException("Unhandled exception: " + e.getMessage());
        }

        // cleaned up to throw IOException
        throw (cause);
    }
}
