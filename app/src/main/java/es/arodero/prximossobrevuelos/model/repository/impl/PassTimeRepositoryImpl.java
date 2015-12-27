package es.arodero.prximossobrevuelos.model.repository.impl;

import java.util.List;

import es.arodero.prximossobrevuelos.model.entities.PassTimeEntity;
import es.arodero.prximossobrevuelos.model.repository.PassTimeRepository;
import es.arodero.prximossobrevuelos.model.rest.IApiPassTime;
import retrofit.RestAdapter;

/**
 * Created by arodero on 26/12/2015.
 */
public class PassTimeRepositoryImpl implements PassTimeRepository {
    private static PassTimeRepositoryImpl ourInstance = new PassTimeRepositoryImpl();

    public static PassTimeRepositoryImpl getInstance() {
        return ourInstance;
    }

    /**
     * API generada con Retrofit,
     * se inicializa en el constructor al generar la instancia del singleton
     */
    IApiPassTime apiPassTime;

    /**
     * Inicializamos el servicio REST en el constructor del repositorio
     */
    private PassTimeRepositoryImpl() {
        RestAdapter restAdapter;
        restAdapter = new RestAdapter.Builder()
                .setEndpoint(IApiPassTime.BASE_URL)
                .build();

        apiPassTime = restAdapter.create(IApiPassTime.class);
    }

    /**
     * Llamada a la API para obtener los proximos sobrebuelos
     * @param lat - Latitud
     * @param lon - Longitud
     * @return
     */
    @Override
    public List<PassTimeEntity> getPassTimes(Double lat, Double lon) {
        return apiPassTime.getPassTimes(lat, lon).getResponse();
    }
}
