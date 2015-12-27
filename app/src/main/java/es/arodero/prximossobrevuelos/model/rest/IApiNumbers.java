package es.arodero.prximossobrevuelos.model.rest;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by arodero on 27/12/2015.
 */
public interface IApiNumbers {
        /** URL BASE de la API */
        public static final String BASE_URL = "http://numbersapi.com/";

        @GET("/{number}")
        Response getNumberFact(@Path("number") Integer number);

}
