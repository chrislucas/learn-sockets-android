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
 * Exemplo site :
 *
 * http://androidexample.com/Accelerometer_Basic_Example_-_Detect_Phone_Shake_Motion/index.php?view=article_discription&aid=109&aaid=131
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
        configureSupportedAccelerometer();
        configureSensorEventListener();
    }

    private Sensor getSensor() {
        return sensor;
    }

    private Context getContext() {
        return context;
    }

    private SensorManager getSensorManager() {
        return sensorManager;
    }

    private AccelerometerListener getAccListener() {
        return accListener;
    }

    public boolean isSupportAccelerometer() {
        return supportAccelerometer;
    }

    public boolean isRunning() {
        return running;
    }

    private SensorEventListener getSensorEventListener() {
        return sensorEventListener;
    }

    public void stopListener() {
        if(isRunning()) {
           try {
                if(getSensorManager() != null && getSensorEventListener() != null) {
                    getSensorManager().unregisterListener(getSensorEventListener());
                }
           } catch(Exception e) {
               Log.e("EXCEPTION_SENSOR", e.getMessage());
           }
        }
        return;
    }

    // retorna TRUE se ao menos 1 acelerometro estiver habilitado no dispositivo
    private void configureSupportedAccelerometer() {
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
                    this.running = getSensorManager().registerListener(getSensorEventListener(),
                            sensor, SensorManager.SENSOR_DELAY_GAME);
                    this.accListener = accListener;
                }
            }
        }
    }

    public void registerListener(AccelerometerListener accListener, float threshold, int interval) {
        reconfigureParametersAcc(threshold, interval);
        registerListener(accListener);
    }

    private void configureSensorEventListener() {
        this.sensorEventListener = new SensorEventListener() {
            private long now        = 0;
            private long timeDiff   = 0;
            private long lastUpdate = 0;
            private long lastShake  = 0;

            private float x = 0, y = 0, z = 0,
                    lastX = 0, lastY = 0, lastZ = 0, force = 0;

            @Override
            public void onSensorChanged(SensorEvent event) {
                this.now = event.timestamp;
                x = event.values[0];
                y = event.values[1];
                z = event.values[2];
                if(lastUpdate == 0) {
                    lastUpdate = now;
                    lastShake = now;
                    lastX = x;
                    lastY = y;
                    lastZ = z;
                } else {
                    timeDiff = now - lastUpdate;
                    if(timeDiff > 0) {
                        force = Math.abs(x+y+z-lastX-lastY-lastZ);
                        if(Float.compare(force, threshold) > 0) {
                            if(now - lastUpdate >= interval)
                                getAccListener().onShake(force);
                            lastShake = now;
                        }
                        lastX = x;
                        lastY = y;
                        lastZ = z;
                        lastUpdate = now;
                    }
                }
                getAccListener().onAccelerationChange(x, y, z);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        return;
    }
/*
    private int compareNumbers(double a, double b) {
        double threshold = 1E-9;
        if( (a - b) > 0.0 )
            return 1;
        else if(  (a-b) < threshold)
            return 0;
        else if( (a-b) < 0.0)
            return -1;
    }
    */
}
