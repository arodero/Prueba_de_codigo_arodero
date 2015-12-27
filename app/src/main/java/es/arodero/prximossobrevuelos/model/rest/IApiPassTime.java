package es.arodero.prximossobrevuelos.model.rest;

import java.util.List;

import es.arodero.prximossobrevuelos.model.entities.PassTimeEntity;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by arodero on 26/12/2015.
 */
public interface IApiPassTime {
    /** URL BASE de la API */
    public final static String BASE_URL = "http://api.open-notify.org";

    /**
     * Método para obtener los próximos sobrevuelos
     * @param lat - Latitud
     * @param lon - Longitud
     * @return response - Un objeto que contiene un listado con los próximos sobrevuelos
     */
    @GET("/iss-pass.json")
    IApiPassTime.Response getPassTimes(
            @Query("lat") Double lat,
            @Query("lon") Double lon
    );

    /**
     * Pojo interno de la API para mapear el atributo "response" del JSON que responde la API,
     * en este atributo está la respuesta real que es un array de Sobrevuelos
     */
    public class Response {
        private List<PassTimeEntity> response;

        public List<PassTimeEntity> getResponse() {
            return response;
        }

        public void setResponse(List<PassTimeEntity> response) {
            this.response = response;
        }
    }
}
