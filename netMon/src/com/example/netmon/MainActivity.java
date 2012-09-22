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
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {

	public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
	EditText progress;
	EditText currentStatus;
	TelephonyManager tel;
	private Handler mHandler;
    private PhoneStateListener phoneStateListener;
	int signal=0;
	Location currentBestLocation;
	LocationManager locationManager;
	LocationListener locationListener;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progress = (EditText) findViewById(R.id.progress);
        currentStatus = (EditText) findViewById(R.id.currentStatus);
        mHandler = new Handler();
        phoneStateListener = new PhoneStateListener() {
        	@Override
        	public void onSignalStrengthsChanged (SignalStrength signalStrength){
        		signal = signalStrength.getGsmSignalStrength();
            }
        };
        tel = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        tel.listen(phoneStateListener,PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
    	locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
    	// Define a listener that responds to location updates
    	locationListener = new LocationListener() {
    	    public void onLocationChanged(Location location) {
    	      if (isBetterLocation(location, currentBestLocation))
    	    	  currentBestLocation = location;
    	    }

			@Override
			public void onProviderDisabled(String provider) {
			}

			@Override
			public void onProviderEnabled(String provider) {
			}

			@Override
			public void onStatusChanged(String provider, int status,
					Bundle extras) {
			}
    	  };
    	  locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
    	  currentBestLocation= locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
    }

    private static final int TWO_MINUTES = 1000 * 60 * 2;
    
    protected boolean isBetterLocation(Location location, Location currentBestLocation) {
        if (currentBestLocation == null) {
            // A new location is always better than no location
            return true;
        }

        // Check whether the new location fix is newer or older
        long timeDelta = location.getTime() - currentBestLocation.getTime();
        boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
        boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
        boolean isNewer = timeDelta > 0;

        // If it's been more than two minutes since the current location, use the new location
        // because the user has likely moved
        if (isSignificantlyNewer) {
            return true;
        // If the new location is more than two minutes older, it must be worse
        } else if (isSignificantlyOlder) {
            return false;
        }

        // Check whether the new location fix is more or less accurate
        int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
        boolean isLessAccurate = accuracyDelta > 0;
        boolean isMoreAccurate = accuracyDelta < 0;
        boolean isSignificantlyLessAccurate = accuracyDelta > 200;

        // Check if the old and new location are from the same provider
        boolean isFromSameProvider = isSameProvider(location.getProvider(),
                currentBestLocation.getProvider());

        // Determine location quality using a combination of timeliness and accuracy
        if (isMoreAccurate) {
            return true;
        } else if (isNewer && !isLessAccurate) {
            return true;
        } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
            return true;
        }
        return false;
    }    
    
    /** Checks whether two providers are the same */
    private boolean isSameProvider(String provider1, String provider2) {
        if (provider1 == null) {
          return provider2 == null;
        }
        return provider1.equals(provider2);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    /* esto era llamado por un boton lo voy a dejar por las dudas que no sepa como hacerlo despues
    
    
    public void sendMessage(View view) {
    	Intent intent = new Intent(this, DisplayMessageActivity.class);
    	EditText editText = (EditText) findViewById(R.id.edit_message);
    	String message = editText.getText().toString();
    	intent.putExtra(EXTRA_MESSAGE, message);
    	startActivity(intent);
    }
	*/

    boolean iniciado = false;
    public void connectToURL(View view) {
    	
    	if (iniciado){
    		stopMonitoring();
    		iniciado=false;
    	} else {
    		startMonitoring();
    		iniciado=true;
    	}    	
    }

    Hilo t = new Hilo();
    private void startMonitoring(){
    	currentStatus.setText("Analizando...");
    	progress.setText("Connecting to google...");
    	t.fin=false;
        mHandler.post(t);
    	currentStatus.setText("Esperando...");
    	
    }
    private void stopMonitoring(){
    	t.fin=true;
    	currentStatus.setText(currentStatus.getText()+"\nDetenido");
    	progress.setText("Detenido");
    }

    int contador=0;
    
    private class Hilo extends Thread {
    	public boolean fin = false;
    	public void run(){
    		String result[] = networkMonitor();
    		postEvent(result[1]);
    		contador++;   
    		if (contador>6){
    			currentStatus.setText((String)result[0]);
    			contador=0;
    		} else {
    			currentStatus.setText(currentStatus.getText()+"\n"+result);
    		}
		   	progress.setText("Fin :"+contador);
		   	if (!fin){
		   		mHandler.postDelayed(this, 60000*15);
		   	}
    	}
    }
   
    public String[] networkMonitor(){
    
	    ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

	    String lat=""+currentBestLocation.getLatitude();
	    String lon=""+currentBestLocation.getLongitude();
	    String emai=tel.getDeviceId();
	    String carrier=tel.getNetworkOperatorName();
	    	
	    String networkType;
	    String networkSubType;
	    
	    String pingTime="";
	    
	    if (networkInfo != null && networkInfo.isConnected()) {
	    	
	    	networkType = networkInfo.getTypeName();
	    	networkSubType = networkInfo.getSubtypeName();
	    	
	    	try {
	    		long millis1 = System.currentTimeMillis();
				downloadUrl("http://www.google.com");
				long millis2 = System.currentTimeMillis();	
				pingTime = ""+(millis2-millis1);
			} catch (IOException e) {
				pingTime = "Error";
			}
	    } else {
	    	networkType = "Not Network";
	    	networkSubType = "Not Sub Type";
	    }

	    String[] result = new String[2];
	    
	    result[0] = "Lat: "+lat+", Long: "+lon+", Device:"+emai+", Carrier: "+carrier+", Network: "+networkType+", Type: "+networkSubType+", Signal: "+signal+", ping: "+pingTime;
	    result[1] = "emai="+emai+"&lat="+lat+"&lon="+lon+"&signal="+signal+"&carrier="+carrier+"&type="+networkType+"&protocol="+networkSubType+"&sig="+signal+"&ms="+pingTime;
	    
		return result;
    	
    }

    /***************************
    //Funciones para bajar páginas
    ***************************/
    
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
	        //int response = conn.getResponseCode();
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
	
	private void postEvent(String datos){
		try {
			String result = downloadUrl("http://ec2-174-129-96-72.compute-1.amazonaws.com/webNetMon-0.1/event/evento/1?"+datos);
		} catch (Exception e){
			
		}
	}

}
