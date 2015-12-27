package es.arodero.prximossobrevuelos.model.repository;

import java.util.List;

import es.arodero.prximossobrevuelos.model.entities.PassTimeEntity;

/**
 * Created by arodero on 26/12/2015.
 */
public interface PassTimeRepository {
    /**
     * Método para obtener los próximos sobrevuelos de la ISS
     * @param lat - Latitud
     * @param lon - Longitud
     * @return listado con los próximos sobrevuelos
     */
    List<PassTimeEntity> getPassTimes(Double lat, Double lon);
}
