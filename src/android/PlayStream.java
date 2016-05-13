import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaInterface;
import android.util.Log;
import android.provider.Settings;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.media.AudioTrack;
import com.spoledge.aacdecoder.MultiPlayer;
import com.spoledge.aacdecoder.PlayerCallback;

import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PlayStream extends CordovaPlugin {
	
	public static final String TAG = "CheckStream";
	private MultiPlayer multiPlayer = null;
	private PlayerCallback player = null;
	private String url = null;
	
	public static final int MEDIA_NONE 	   = 0;
    public static final int MEDIA_STARTING = 1;
	public static final int MEDIA_RUNNING  = 2;
	public static final int MEDIA_PAUSED   = 3;
	public static final int MEDIA_STOPPED  = 4;
	public static int STATUS 		   	   = 0;
	
	private CallbackContext messageChannel;
	
	/**
	* Constructor.
	*/
	public PlayStream(){
		
	}
	
	/**
	* Sets the context of the Command. This can then be used to do things like
	* get file paths associated with the Activity.
	*
	* @param cordova The context of the main Activity.
	* @param webView The CordovaWebView Cordova is running in.
	*/
	public void initialize(CordovaInterface cordova, CordovaWebView webView) {
		super.initialize(cordova, webView);
		Log.i(TAG,"Init PlayStream");
	}
	
	public boolean execute(final String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		
		if (action.equals("startPlayingAudio")) {
			this.play();
        }
        else if (action.equals("stopPlayingAudio")) {
			this.stop();
        } 
        else if (action.equals("create")) {
			this.url = args.getString(0);
            if(preparePlayer(callbackContext)){
				createPlayer();
			}
        }else if (action.equals("release")) {
			this.release();
        }
        else { // Unrecognized action.
            return false;
        }

		return true;
	}
	
	private void play(){
		if(PlayStream.STATUS != PlayStream.MEDIA_STARTING && PlayStream.STATUS != PlayStream.MEDIA_RUNNING)
			multiPlayer.playAsync(this.url);
	}
	
	private void stop(){
		if(PlayStream.STATUS != PlayStream.MEDIA_NONE && PlayStream.STATUS != PlayStream.MEDIA_STOPPED)
			multiPlayer.stop();
	}
	
	private void release(){
		if(player != null){
			this.stop();
			multiPlayer = null;
			player = null;
		}
	}
	
	private boolean createPlayer(){
		multiPlayer = new MultiPlayer(player);
		return true;
	}
	
	private boolean preparePlayer(final CallbackContext callbackContext){
		Log.i(TAG,"playMuisic enter");
		
		if(player != null)
			return false;
		
		player = new PlayerCallback() {
            @Override
            public void playerStarted() {
				PlayStream.STATUS = PlayStream.MEDIA_STARTING;
                Log.i(TAG,"started");
				
				PluginResult result = new PluginResult(PluginResult.Status.OK,PlayStream.MEDIA_STARTING); 
				result.setKeepCallback(true); 
				callbackContext.sendPluginResult(result); 
            }

            @Override
            public void playerPCMFeedBuffer(boolean b, int i, int i2) {
				if(PlayStream.STATUS != PlayStream.MEDIA_RUNNING){
					PlayStream.STATUS = PlayStream.MEDIA_RUNNING;
					
					PluginResult result = new PluginResult(PluginResult.Status.OK,PlayStream.MEDIA_RUNNING); 
					result.setKeepCallback(true); 
					callbackContext.sendPluginResult(result); 
				}
            }

            @Override
            public void playerStopped(int i) {
				PlayStream.STATUS = PlayStream.MEDIA_STOPPED;
                Log.i(TAG,"stoped");
				
				PluginResult result = new PluginResult(PluginResult.Status.OK,PlayStream.MEDIA_STOPPED); 
				result.setKeepCallback(true); 
				callbackContext.sendPluginResult(result); 
            }

            @Override
            public void playerException(Throwable throwable) {
                Log.i(TAG,throwable.getMessage());
				
				PlayStream.STATUS = PlayStream.MEDIA_NONE;
				PluginResult result = new PluginResult(PluginResult.Status.ERROR,throwable.getMessage()); 
				result.setKeepCallback(true); 
				callbackContext.sendPluginResult(result); 
            }

            @Override
            public void playerMetadata(String s, String s2) {

            }

            @Override
            public void playerAudioTrackCreated(AudioTrack audioTrack) {

            }
        };
		
		return true;
        
	}
	
}