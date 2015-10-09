package wiselabs.com.br.connection.utils;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * Created by C.Lucas on 08/10/2015.
 */
public class Device {

    public static String getAddressDevice() {
        String answer = null;
        try {
            Enumeration<NetworkInterface> enumInet = NetworkInterface.getNetworkInterfaces();
            while(enumInet.hasMoreElements()) {
                NetworkInterface iNet  = enumInet.nextElement();
                Enumeration<InetAddress> enumInetAddres = iNet.getInetAddresses();
                while(enumInetAddres.hasMoreElements()) {
                    InetAddress address = enumInetAddres.nextElement();
                    if(!address.isLoopbackAddress()) {
                        answer = address.getHostAddress();
                        return answer;
                    }
                }
            }
        } catch (SocketException ex) {

        }
        return answer;
    }
}
