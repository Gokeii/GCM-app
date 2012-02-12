/* Receives messages from the cloud via push notifications
 * (Google's C2DM)
 * 
 * When it gets the message, it fires an intent that contains the message
 * payload.  The intent calls UsbFromC2DMService's onStartCommand() function
 * which then interprets the payload and sends a USB command to the robot
 */

package org.abarry.telo;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class C2DMMessageReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		Log.w("C2DM", "Message Receiver called");
		if ("com.google.android.c2dm.intent.RECEIVE".equals(action)) {
			Log.w("C2DM", "Received message");
			final String payload = intent.getStringExtra("payload");
			Log.d("C2DM", "dmControl: payload = " + payload);
			
			// call our function which will fire off the USB intent
			doUsb(context, payload);

		}
	}
	
	// helper function that creates an intent for UsbFromC2DmService	
	private void doUsb(Context context, String payload)
	{
		Log.d("C2DM", "in doUsb");
		
		// fire an intent
		Intent i = new Intent(context, UsbFromC2DMService.class);
		i.putExtra("payload", payload);
		context.startService(i);
		
	}

}
