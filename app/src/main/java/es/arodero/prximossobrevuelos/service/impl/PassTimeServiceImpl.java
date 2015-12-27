package es.arodero.prximossobrevuelos.service.impl;

import java.util.List;

import es.arodero.prximossobrevuelos.model.entities.PassTimeEntity;
import es.arodero.prximossobrevuelos.model.repository.PassTimeRepository;
import es.arodero.prximossobrevuelos.model.repository.impl.PassTimeRepositoryImpl;
import es.arodero.prximossobrevuelos.service.PassTimeService;

/**
 * Created by arodero on 26/12/2015.
 */
public class PassTimeServiceImpl implements PassTimeService {
    private static PassTimeServiceImpl ourInstance = new PassTimeServiceImpl();

    public static PassTimeServiceImpl getInstance() {
        return ourInstance;
    }

    private PassTimeServiceImpl() {
    }

    /**
     * Inyectamos el repositorio que utilizará este servicio
     */
    private PassTimeRepository repository = PassTimeRepositoryImpl.getInstance();

    /**
     * Método para obtener los proximos sobrevuelos de la ISS.
     * Simplemente llama al repositorio, si fuese necesaria alguna operación
     * o transformación se realizaria en esta capa
     *
     * @param lat - Latitud
     * @param lon - Longitud
     * @return listado con los próximos sobrevuelos
     */
    public List<PassTimeEntity> getPassTimes(Double lat, Double lon) {
        return repository.getPassTimes(lat, lon);
    }
}
