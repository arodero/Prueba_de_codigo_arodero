package es.arodero.prximossobrevuelos.presentation.views;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;


import es.arodero.prximossobrevuelos.R;
import es.arodero.prximossobrevuelos.common.constans.Constans;
import es.arodero.prximossobrevuelos.model.entities.PassTimeEntity;
import es.arodero.prximossobrevuelos.service.NumbersService;

import es.arodero.prximossobrevuelos.service.impl.NumbersServiceImpl;


public class DetailActivity extends AppCompatActivity {

    /**
     * Método que inicializa la vista
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        populateDetailInfo();

        //String numberMessage = "614 son los segundos que durará el sobrevuelo y además, 614 is the smallest number that can be written as the sum of 3 squares in 9 ways";
        //showNumberFact(numberMessage);
        PassTimeEntity passTime = (PassTimeEntity) getIntent().getExtras().getSerializable(Constans.PARAM_ITEM);
        new LoadNumberFactTask().execute(passTime.getDuration());
    }

    /**
     * Método que crea un SnackBar con la información de NumbersAPI
     * @param numberMessage
     */
    private void showNumberFact(String numberMessage) {
        PassTimeEntity passTime = (PassTimeEntity) getIntent().getExtras().getSerializable(Constans.PARAM_ITEM);
        numberMessage = String.format(
                getResources().getString(R.string.numbers_message),
                passTime.getDuration(), numberMessage);

        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),
                numberMessage, Snackbar.LENGTH_INDEFINITE);

        View snackbarView = snackbar.getView();
        TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        textView.setBackgroundColor(Color.parseColor(Constans.SNACKBAR_COLOR));
        snackbarView.setBackgroundColor(Color.parseColor(Constans.SNACKBAR_COLOR));
        textView.setMaxLines(5);  // show multiple line
        snackbar.show();
    }

    /**
     * Método que prepara la información necesaria para mostrar el detalle
     * y la establece en los distintos textView
     */
    private void populateDetailInfo() {
        String address = getIntent().getExtras().getString(Constans.PARAM_ADDRESS);
        TextView.class.cast(findViewById(R.id.textLocation)).setText(address);


        PassTimeEntity passTime = (PassTimeEntity) getIntent().getExtras().getSerializable(Constans.PARAM_ITEM);

        Long secondsToRisetime = passTime.getRisetime() - (System.currentTimeMillis() / 1000);
        TextView.class.cast(findViewById(R.id.tvDetail2)).setText(secondsToRisetime.toString());

        Integer minutes = passTime.getDuration() / 60;
        Integer seconds = passTime.getDuration() % 60;
        String duration = String.format(
                getResources().getString(R.string.detail4),
                minutes, seconds);
        ;
        TextView.class.cast(findViewById(R.id.tvDetail4)).setText(duration);
    }

    /**
     * Tarea Asyncrona para la solicitud del servicio que conectará con la API
     * para consultar información relativa a un número
     *
     * NOTA: Primero implementé un Callback de Retrofit que parecia mejor para esperar la respuesta,
     *       pero me pareció que crear un Callback en el Activity y pasarlo a otras capas generaba
     *       algo de acoplamiento entre las capas, por ese motivo finalmente tomé esta opción
     */
    private class LoadNumberFactTask extends AsyncTask<Integer, Void, String> {
        @Override
        protected void onPostExecute(String numberFact) {
            super.onPostExecute(numberFact);
            showNumberFact(numberFact);
        }
        @Override
        protected String doInBackground(Integer... params) {
            NumbersService service = NumbersServiceImpl.getInstance();
            Integer number = params[0];
            return service.getNumberFact(number);
        }
    }
}
