package wiselabs.com.br.rest.utils.device;

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
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by christoffer on 8/4/15.
 */
public class UtilsNetworking {


    public static final String DEVICE = Build.DEVICE;
    public static final String MANUFACTURE = Build.MANUFACTURER;

    private static final Map<String, String>  settings = new HashMap<>();

    static {
        settings.put(Settings.ACTION_WIFI_SETTINGS, "Wifi está desabilitado, deseja habilitar ?");
        settings.put(Settings.ACTION_LOCATION_SOURCE_SETTINGS, "GPS está desabilitado, deseja habilitar ?");
    }

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


    public static void openSettings(final Context context, String intent) {
        //Activity activity = getActivity();
        if(context != null) {
            // if(activity == null) { return; }
            // ACTION_WIFI_SETTINGS, ACTION_LOCATION_SOURCE_SETTINGS
            final String message = settings.get(intent);
            final Activity activity = (Activity) context;

            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(message == null) {
                        AlertDialog.Builder alert = new AlertDialog.Builder(context);
                        alert.setTitle("Erro");
                        alert.setMessage("Nao ao configuração para essa Atividade");
                        alert.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create().show();

                    } else {
                        AlertDialog.Builder alert = new AlertDialog.Builder(context);
                        alert.setMessage(message)
                                .setCancelable(false)
                                .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent settings = new Intent(Settings.ACTION_WIFI_SETTINGS);
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
                } // fim run()
            }); // fim runOnUIThread (new Runnable(){})
        } // fim if
        return;
    }


    public static boolean testConnection(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_ETHERNET);
        boolean connectivity = false;
        if(netInfo != null) {
            connectivity = netInfo.isConnectedOrConnecting();
        }

        netInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);      // 3g
        if(netInfo != null) {
            connectivity = netInfo.isConnectedOrConnecting();
        }

        netInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if(netInfo != null)
            connectivity = netInfo.isConnectedOrConnecting();

        return connectivity;
    }

    public static TelephonyManager getPhoneManager(Context context) {
        TelephonyManager managerPhone = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String imei = managerPhone.getDeviceId();
        Toast.makeText(context, imei, Toast.LENGTH_LONG).show();
        return managerPhone;
    }

    public static String getDeviceName() {
        return "";
    }
}
