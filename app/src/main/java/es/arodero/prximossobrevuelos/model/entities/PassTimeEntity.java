package es.arodero.prximossobrevuelos.model.entities;

import java.io.Serializable;
import java.util.Date;

/**
 * Entidad que contendrá la información obtenida para cada sobrevuelo
 * a partir de la API de ISS Pass Times
 */
public class PassTimeEntity implements Serializable {

    /* Duración en segundos del sobrevuelo*/
    private Integer duration;

    /* Timestamp con la fecha a la que comenzará a ser visible el sobrevuelo*/
    private Long risetime;

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Long getRisetime() {
        return risetime;
    }

    public void setRisetime(Long risetime) {
        this.risetime = risetime;
    }
}
