package es.arodero.prximossobrevuelos.presentation.views;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;

import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import es.arodero.prximossobrevuelos.R;
import es.arodero.prximossobrevuelos.common.constans.Constans;
import es.arodero.prximossobrevuelos.common.util.LocationUtils;
import es.arodero.prximossobrevuelos.presentation.adapters.PassTimeAdapter;
import es.arodero.prximossobrevuelos.model.entities.PassTimeEntity;
import es.arodero.prximossobrevuelos.service.PassTimeService;
import es.arodero.prximossobrevuelos.service.impl.PassTimeServiceImpl;

public class MainActivity extends AppCompatActivity {
    /**
     * Método que inicializa la vista
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getPositionAndLoadData();
        initListItemClick();
    }

    /**
     * Obtiene el servicio de posicionamiento, solicita una actualización simple.
     * Cuando se produce la actualización se obtiene la geolocalización inversa y se llama al
     * servicio de ISS Pass Times con las coordenadas obtenidas
     */
    private void getPositionAndLoadData() {
        //Obtenermos el servicio de localización del sistema
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        //Comprobamos permisos y si no tenemos permisos indicamos el error
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            String position = "No se ha podido obtener la posición";
            TextView textView = (TextView) findViewById(R.id.textLocation);
            textView.setText(position);
        } else {
            //Solicitamos un refresco único del posicionamiento y tratamos el evento con un LocationListener

            locationManager.requestSingleUpdate(LocationUtils.getBestProvider(locationManager, true),
                    new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {
                            //Cuando se refresca la posición, obtenemos la geoposición inversa
                            Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
                            List<Address> addresses;
                            try {
                                addresses = gcd.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                if (addresses.size() > 0) {
                                    Address returnedAddress = addresses.get(0);
                                    StringBuilder strReturnedAddress = new StringBuilder("");
                                    String sep = "";
                                    for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
                                        strReturnedAddress.append(sep).append(returnedAddress.getAddressLine(i));
                                        sep = "\n";
                                    }

                                    //Ahora que ya tenemos la posición y la dirección, actualizamos la etiqueta con la dirección
                                    //e iniciamos la tarea en segundo plano que llama al servicio para refrescar el listado
                                    TextView textView = (TextView) findViewById(R.id.textLocation);
                                    textView.setText(strReturnedAddress.toString());
                                    new PopulatePassTimesTask().execute(location);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        @Override
                        public void onStatusChanged(String provider, int status, Bundle extras) {}
                        @Override
                        public void onProviderEnabled(String provider) {}
                        @Override
                        public void onProviderDisabled(String provider) {}
                    }, Looper.myLooper());
        }
    }

    /**
     * Método que rellena el list View
     */
    private void populatePassTimes(List<PassTimeEntity> lstPassTimes) {
        PassTimeAdapter adapter = new PassTimeAdapter(this, (ArrayList<PassTimeEntity>) lstPassTimes);
        ListView listView = (ListView) findViewById(R.id.lvPassTimes);
        listView.setAdapter(adapter);

    }

    /**
     * Método para iniciar el comportamiento en el click de un item del listado
     */
    private void initListItemClick() {
        ListView listView = (ListView) findViewById(R.id.lvPassTimes);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PassTimeEntity item = (PassTimeEntity) parent.getItemAtPosition(position);

                Intent intent = new Intent(view.getContext(), DetailActivity.class);
                intent.putExtra(Constans.PARAM_ITEM, item);
                intent.putExtra(Constans.PARAM_ADDRESS, TextView.class.cast(findViewById(R.id.textLocation)).getText());
                startActivity(intent);
            }
        });
    }


    /**
     * Tarea Asyncrona para la solicitud del servicio que conectará con la API
     * para consultar los sobrevuelos
     *
     * NOTA: Primero implementé un Callback de Retrofit que parecia mejor para esperar la respuesta,
     *       pero me pareció que crear un Callback en el Activity y pasarlo a otras capas generaba
     *       algo de acoplamiento entre las capas, por ese motivo finalmente tomé esta opción
     */
    private class PopulatePassTimesTask extends AsyncTask<Location, Void, List<PassTimeEntity>> {
        @Override
        protected void onPostExecute(List<PassTimeEntity> passTimeEntities) {
            super.onPostExecute(passTimeEntities);
            populatePassTimes(passTimeEntities);
        }
        @Override
        protected List<PassTimeEntity> doInBackground(Location... params) {
            PassTimeService service = PassTimeServiceImpl.getInstance();
            Location location = params[0];
            return service.getPassTimes(location.getLatitude(), location.getLongitude());
        }
    }
}

