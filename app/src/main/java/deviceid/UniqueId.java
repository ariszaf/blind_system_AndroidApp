package deviceid;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import java.util.UUID;


//http://stackoverflow.com/questions/2785485/is-there-a-unique-android-device-id
//http://stackoverflow.com/questions/11705906/programmatically-getting-the-mac-of-an-android-device
public class UniqueId {

    private static final String Tag = UniqueId.class.getName();
    private  TelephonyManager telephonyManager = null;

    private Context context = null;

    public UniqueId(Context context) {
        this.context = context;
        telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
    }

   public String UniqueIdName(){

       try {
           String tmDevice;
           String tmSerial;
           String androidId;
           String deviceId;

           tmDevice = "" + telephonyManager.getDeviceId();
           tmSerial = "" + telephonyManager.getSimSerialNumber();
           androidId = "" + android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
           UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());

           deviceId = deviceUuid.toString();

           Log.w(Tag + " tmDevice", tmDevice);
           Log.w(Tag + " tmSerial", tmSerial);
           Log.w(Tag + " androidId", androidId);
           Log.w(Tag + " deviceId ", deviceId);

           return deviceId;
       } catch (Exception e) {
           WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
           WifiInfo info = manager.getConnectionInfo();
           String address = info.getMacAddress();
           if(address == null){
               Toast.makeText(context,"Device don't have mac address or wi-fi is disabled",Toast.LENGTH_LONG).show();
           }
           int address_nr = info.getMacAddress().hashCode();
           return Integer.toString(address_nr);
       }
   }
}
