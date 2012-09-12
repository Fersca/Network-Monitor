package com.example.netmon;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.TextView;

public class ConnectToURLActivity extends Activity {
    
	String message;
	TextView textView;
	String resultado;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_to_url);
        
        // Get the message from the intent
        Intent intent = getIntent();
        message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        
        textView = (TextView) findViewById(R.id.resultado);
        textView.setText("iniciando...");
        buscarPagina();
        
    }

    // When user clicks button, calls AsyncTask.
    // Before attempting to fetch the URL, makes sure that there is a network connection.
    public void buscarPagina() {
        // Gets the URL from the UI's text field.
        String stringUrl = message;
        ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
        	textView.setText("Procesando....");
        	try {
        		long millis1 = System.currentTimeMillis();
				resultado = downloadUrl(stringUrl);
				long millis2 = System.currentTimeMillis();
				textView.setText("Tiempo: "+(millis2-millis1)+"ms, resultado: "+resultado);
			} catch (IOException e) {
				textView.setText("Error: "+e.getMessage());
			}
        } else {
            textView.setText("No network connection available.");
        }
    }


        public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
            Reader reader = null;
            reader = new InputStreamReader(stream, "UTF-8");        
            char[] buffer = new char[len];
            reader.read(buffer);
            return new String(buffer);
        }
        
        private String downloadUrl(String myurl) throws IOException {
            InputStream is = null;
            // Only display the first 1000 characters of the retrieved
            // web page content.
            int len = 250;
                
            try {
                URL url = new URL(myurl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                // Starts the query
                conn.connect();
                int response = conn.getResponseCode();
                //Log.d(DEBUG_TAG, "The response is: " + response);
                is = conn.getInputStream();

                // Convert the InputStream into a string
                String contentAsString = readIt(is, len);
                return contentAsString;
                
            // Makes sure that the InputStream is closed after the app is
            // finished using it.
            } finally {
                if (is != null) {
                    is.close();
                } 
            }
        }
       
     
}
