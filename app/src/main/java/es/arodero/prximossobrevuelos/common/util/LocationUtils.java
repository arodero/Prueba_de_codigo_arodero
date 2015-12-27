package es.arodero.prximossobrevuelos.common.util;

import android.location.Criteria;
import android.location.LocationManager;
import android.util.Log;

import java.util.List;

/**
 * Created by arodero on 26/12/2015.
 */
public final class LocationUtils {

    private LocationUtils() {}

    /**
     * Obtiene el mejor proveedor de localización en el momento,
     * para utilizarlo en el Location Manager
     * @param lm - El location manager
     * @param speedRequired - Propiedad para indicar si se quiere una localización rápida o no
     * @return el nombre del mejor proveedor de localización en ese momento
     */
    public static String getBestProvider(LocationManager lm, Boolean speedRequired) {
        Criteria c = new Criteria();
        c.setAccuracy(Criteria.ACCURACY_COARSE);
        c.setAltitudeRequired(false);
        c.setBearingRequired(false);
        c.setCostAllowed(false);
        c.setPowerRequirement(Criteria.NO_REQUIREMENT);
        c.setSpeedRequired(speedRequired);
        String provider = lm.getBestProvider(c, true);

        return provider;
    }
}