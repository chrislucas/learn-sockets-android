package wiselabs.com.br.rest.sensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by christoffer on 10/15/15.
 * Exemplo site : http://androidexample.com/Accelerometer_Basic_Example_-_Detect_Phone_Shake_Motion/index.php?view=article_discription&aid=109&aaid=131
 *
 */
public class AccelerometerManager {

    private Context context;
    private Sensor sensor;
    private SensorManager sensorManager;
    private AccelerometerListener accListener;
    private boolean supportAccelerometer;
    private boolean running;
    private SensorEventListener sensorEventListener;

    private static float threshold = 15.0F;
    private static int interval    = 200;

    public AccelerometerManager(Context context) {
        this.context = context;
        this.sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        setSupportedAccelerometer();
    }

    public Sensor getSensor() {
        return sensor;
    }

    public Context getContext() {
        return context;
    }

    public SensorManager getSensorManager() {
        return sensorManager;
    }

    public AccelerometerListener getAccListener() {
        return accListener;
    }

    public boolean isSupportAccelerometer() {
        return supportAccelerometer;
    }

    public boolean isRunning() {
        return running;
    }

    public SensorEventListener getSensorEventListener() {
        return sensorEventListener;
    }

    public void stopListener() {
        if(isRunning()) {
           try {
                if(getSensorManager() != null && getSensorEventListener() != null) {
                    getSensorManager().unregisterListener(getSensorEventListener());
                }
           } catch(Exception e) {
               Log.e("", "");
           }
        }
        return;
    }

    // retorna TRUE se ao menos 1 acelerometro estiver habilitado no dispositivo
    public void setSupportedAccelerometer() {
        List<Sensor> sensors = new ArrayList<>();
        if(getSensorManager() != null)
            sensors.addAll(getSensorManager().getSensorList(Sensor.TYPE_ACCELEROMETER));
        this.supportAccelerometer = sensors.size() > 0 ? true : false;
        return;
    }

    // redefinindo parametros de membros da classe
    public void reconfigureParametersAcc(float threshold, int interval) {
        AccelerometerManager.interval = interval;
        AccelerometerManager.threshold = threshold;
        return;
    }

    public void registerListener(AccelerometerListener accListener) {
        if(getSensorManager() != null) {
            List<Sensor> sensors = new ArrayList<>();
            sensors.addAll(getSensorManager().getSensorList(Sensor.TYPE_ACCELEROMETER));
            if(sensors.size() > 0) {
                Sensor sensor = sensors.get(0);
                if(getSensorEventListener() != null) {
                    this.running = getSensorManager().registerListener(getSensorEventListener(), sensor, SensorManager.SENSOR_DELAY_GAME);
                    this.accListener = accListener;
                }
            }
        }
    }

    private void configureSensorEventListener() {
        this.sensorEventListener = new SensorEventListener() {
            private long now        = 0;
            private long timeDiff   = 0;
            private long lastUpdate = 0;
            private long lastShake  = 0;
            @Override
            public void onSensorChanged(SensorEvent event) {

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        return;
    }
}
