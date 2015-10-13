package project.com.serrat.classes;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;
import android.telephony.CellInfo;
import android.telephony.TelephonyManager;

import java.util.List;

/**
 * Created by christoffer on 8/4/15.
 */
public class UtilsNetworking {


    public static final String DEVICE = Build.DEVICE;
    public static final String MANUFACTURE = Build.MANUFACTURER;

    public UtilsNetworking(){}
    /**
     * precisa da permissao android.chp.chp.rest.permission.READ_PHONE_STATE
     * */
    public static String getIDDevice(Context context) {
        TelephonyManager mng = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
        return mng.getDeviceId();
    }

    public static String getDeviceSW(Context context) {
        TelephonyManager mng =  (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
        return mng.getDeviceSoftwareVersion();
    }

    public static List<CellInfo> getCellInfo(Context context) {
        TelephonyManager mng = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);;
        List<CellInfo> list = mng.getAllCellInfo();
        return list;
    }


    public static void openSettingsLocation(final Context context) {
        //Activity activity = getActivity();
        if(context != null) {
            // if(activity == null) { return; }
            AlertDialog.Builder alert = new AlertDialog.Builder(context);
            alert.setMessage("GPS está desabilitado, deseja habilitar ?")
                    .setCancelable(true)
                    .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent settings = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            //getActivity().startActivity(settings);
                            context.startActivity(settings);
                        }
                    }).setNegativeButton("NAO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog dialog = alert.create();
            dialog.show();
        }
        return;
    }

    public static void openSettingNetwork(final Context context) {
        if(context != null) {

        }
        return;
    }

    public static boolean testConnection(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean connectivity = manager.getNetworkInfo(ConnectivityManager.TYPE_ETHERNET).isConnected();
        if(!connectivity) {
            connectivity = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
            if(!connectivity) {
                connectivity = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
            }
        }
        return connectivity;
    }

    public static String getDeviceName() {
        return "";
    }
}
