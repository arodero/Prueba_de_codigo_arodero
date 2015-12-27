package es.arodero.prximossobrevuelos.service.impl;

import java.util.List;

import es.arodero.prximossobrevuelos.model.entities.PassTimeEntity;
import es.arodero.prximossobrevuelos.model.repository.NumbersRepository;
import es.arodero.prximossobrevuelos.model.repository.PassTimeRepository;
import es.arodero.prximossobrevuelos.model.repository.impl.NumbersRepositoryImpl;
import es.arodero.prximossobrevuelos.model.repository.impl.PassTimeRepositoryImpl;
import es.arodero.prximossobrevuelos.service.NumbersService;
import es.arodero.prximossobrevuelos.service.PassTimeService;

/**
 * Created by arodero on 26/12/2015.
 */
public class NumbersServiceImpl implements NumbersService {
    private static NumbersServiceImpl ourInstance = new NumbersServiceImpl();

    public static NumbersServiceImpl getInstance() {
        return ourInstance;
    }

    private NumbersServiceImpl() {
    }

    /**
     * Inyectamos el repositorio que utilizará este servicio
     */
    private NumbersRepository repository = NumbersRepositoryImpl.getInstance();

    /**
     * Método que retorna una frase a partir de un número de la API de numbers
     * @param number
     * @return
     */
    public String getNumberFact(Integer number) {
        return repository.getNumberFact(number);
    }
}
