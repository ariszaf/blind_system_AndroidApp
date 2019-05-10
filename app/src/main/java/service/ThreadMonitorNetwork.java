package service;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public class ThreadMonitorNetwork extends Thread {
    private final static String Tag = ThreadAlarm.class.getName();
    public static Handler mainActivityHandler = null;
    private final Context context;
    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Log.w("HANDLER", msg.toString());
            switch (msg.arg1) {
                case 11:
                    Toast.makeText(context, "AUTO: Online => Offline", Toast.LENGTH_LONG).show();
                    break;
                case 13:
                    Toast.makeText(context, "AUTO: Offline => Online", Toast.LENGTH_LONG).show();
                    break;
                case 15:
                    Toast.makeText(context, "MANUAL: Online => Offline", Toast.LENGTH_LONG).show();
                    break;
                case 17:
                    Toast.makeText(context, "MANUAL: Offline => Online", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };

    public ThreadMonitorNetwork(Context context) {
        this.context = context;
    }

    @Override
    public void run() {
        super.run();

        boolean prevConnectedState = false;
        boolean currentConnectedState;

        try {
            while (!isInterrupted()) {
                Log.w(Tag, "Tick from Monitor thread");
                Thread.sleep(2000);

                // -------------------- get network state ---------------------
                ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();


                // --------------------   state booleans   --------------------
                currentConnectedState = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

                if (BackgroundService.automaticModeEnabled) { // AUTO
                    if (prevConnectedState && currentConnectedState) { // online -> online
                        BackgroundService.onlineModeEnabled = true;
                    }

                    if (!prevConnectedState && !currentConnectedState) { // offline -> offline
                        BackgroundService.onlineModeEnabled = false;
                    }

                    if (!prevConnectedState && currentConnectedState) { // offline -> online
                        BackgroundService.onlineModeEnabled = true;
                        //__________________________________________________________________________________
                        Message message = new Message();
                        message.arg1 = 13;
                        message.obj = null;
                        handler.sendMessage(message);

                        message = new Message();
                        message.arg1 = 13;
                        message.obj = null;
                        mainActivityHandler.sendMessage(message);
                    }

                    if (prevConnectedState && !currentConnectedState) { // online -> offline
                        BackgroundService.onlineModeEnabled = false;
                        //__________________________________________________________________________________
                        Message message = new Message();
                        message.arg1 = 11;
                        message.obj = null;
                        handler.sendMessage(message);

                        message = new Message();
                        message.arg1 = 11;
                        message.obj = null;
                        mainActivityHandler.sendMessage(message);
                    }
                } else { // MANUAL
                    if (prevConnectedState && currentConnectedState) { // online -> online

                    }

                    if (!prevConnectedState && !currentConnectedState) { // offline -> offline
                        BackgroundService.onlineModeEnabled = false;
                    }

                    if (!prevConnectedState && currentConnectedState) { // offline -> online
                        //__________________________________________________________________________________
                        Message message = new Message();
                        message.arg1 = 17;
                        message.obj = null;
                        handler.sendMessage(message);

                        message = new Message();
                        message.arg1 = 17;
                        message.obj = null;
                        mainActivityHandler.sendMessage(message);
                    }

                    if (prevConnectedState && !currentConnectedState) { // online -> offline
                        BackgroundService.onlineModeEnabled = false;
                        //__________________________________________________________________________________
                        Message message = new Message();
                        message.arg1 = 15;
                        message.obj = null;
                        handler.sendMessage(message);

                        message = new Message();
                        message.arg1 = 15;
                        message.obj = null;
                        mainActivityHandler.sendMessage(message);
                    }
                }

                prevConnectedState = currentConnectedState;
            }
        } catch (InterruptedException e) {
            Log.i(Tag, "InterruptedException");
        }
    }
}
