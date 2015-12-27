package es.arodero.prximossobrevuelos.model.repository;

import java.util.List;

import es.arodero.prximossobrevuelos.model.entities.PassTimeEntity;

/**
 * Created by arodero on 26/12/2015.
 */
public interface NumbersRepository {
    /**
     * Método que retorna una frase a partir de un número de la API de numbers
     * @param number
     * @return
     */
    String getNumberFact(Integer number);
}
