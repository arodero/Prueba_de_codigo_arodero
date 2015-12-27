package es.arodero.prximossobrevuelos.model.repository.impl;

import es.arodero.prximossobrevuelos.model.repository.NumbersRepository;

import es.arodero.prximossobrevuelos.model.rest.IApiNumbers;
import retrofit.RestAdapter;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;


/**
 * Created by arodero on 26/12/2015.
 */
public class NumbersRepositoryImpl implements NumbersRepository {
    private static NumbersRepositoryImpl ourInstance = new NumbersRepositoryImpl();

    public static NumbersRepositoryImpl getInstance() {
        return ourInstance;
    }

    /**
     * API generada con Retrofit,
     * se inicializa en el constructor al generar la instancia del singleton
     */
    IApiNumbers apiNumbers;

    /**
     * Inicializamos el servicio REST en el constructor del repositorio
     */
    private NumbersRepositoryImpl() {
        RestAdapter restAdapter;
        restAdapter = new RestAdapter.Builder()
                .setEndpoint(IApiNumbers.BASE_URL)
                .build();

        apiNumbers = restAdapter.create(IApiNumbers.class);
    }

    /**
     * Llamada a la API para solicitar la información del número
     * Como la API nos devuelve una cadena sencilla en lugar de JSON, obtenemos directamente
     * la respuesta y la transformamos
     * @param number
     * @return
     */
    @Override
    public String getNumberFact(Integer number) {
        Response retrofitResponse = apiNumbers.getNumberFact(number);
        return new String(((TypedByteArray) retrofitResponse.getBody()).getBytes());
    }

}
