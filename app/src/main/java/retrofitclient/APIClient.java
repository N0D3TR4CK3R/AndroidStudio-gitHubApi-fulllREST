package retrofitclient;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class APIClient {

    // URL base para la API de GitHub
    public static final String BASE_URL = "https://api.github.com/";

    // Objeto Retrofit para realizar llamadas a la API
    private static Retrofit retrofit = null;

    // Método para obtener una instancia del cliente Retrofit
    public static Retrofit getClient() {
        if (retrofit == null) {
            // Configuración de interceptores para permitir logs de las solicitudes y respuestas
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.addInterceptor(loggingInterceptor);

            // Configuración del cliente Retrofit con la URL base, el cliente HTTP y el convertidor Gson
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL) // Establece la URL base para las solicitudes
                    .client(httpClient.build()) // Configura el cliente HTTP
                    .addConverterFactory(GsonConverterFactory.create()) // Usa Gson para convertir respuestas a objetos Java
                    .build();
        }

        return retrofit;
    }
}
