package wiselabs.com.br.rest.sensor;

/**
 * Created by christoffer on 10/15/15.
 */
public interface AccelerometerListener {
    public void onAccelerationChange(float x, float y, float z);
    public void onShake(float force);
}
