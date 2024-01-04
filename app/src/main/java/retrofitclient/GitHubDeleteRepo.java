package retrofitclient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.DELETE;
import retrofit2.http.Header;
import retrofit2.http.Path;

public class GitHubDeleteRepo {

    // URL base de la API de GitHub y tu token de acceso personal
    private static final String BASE_URL = "https://api.github.com/";
    private static final String TOKEN = "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"; // Aquí debes colocar tu token de acceso personal
    private final GitHubApiEndpoint apiEndpoint;

    public GitHubDeleteRepo() {
        // Configuración de Retrofit para realizar llamadas a la API de GitHub
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Creación de una instancia de la interfaz para realizar llamadas a la API
        apiEndpoint = retrofit.create(GitHubApiEndpoint.class);
    }

    // Método para eliminar un repositorio en GitHub
    public void deleteRepository(String repoOwner, String repoName, final GitHubApiResponse responseCallback) {
        // Lógica para eliminar el repositorio en GitHub
        // Utiliza el token de acceso personal para autenticar la solicitud

        String authorizationHeader = "token " + TOKEN; // Formato de token de acceso para enviar en el encabezado de autorización

        // Crea una llamada para la eliminación del repositorio utilizando la interfaz definida
        Call<Void> call = apiEndpoint.deleteRepository(authorizationHeader, repoOwner, repoName);

        // Manejo de la respuesta de la solicitud
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    responseCallback.onSuccess(); // Notifica éxito al callback
                } else {
                    // Notifica un error al callback si la respuesta no fue exitosa
                    responseCallback.onFailure("Error al eliminar el repositorio. Código de respuesta: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Notifica un error al callback si la solicitud falla
                responseCallback.onFailure("Error al eliminar el repositorio: " + t.getMessage());
            }
        });
    }

    // Interfaz que define los métodos para las llamadas a la API de GitHub
    public interface GitHubApiEndpoint {
        @DELETE("repos/{owner}/{repo}")
        Call<Void> deleteRepository(@Header("Authorization") String authorizationHeader, @Path("owner") String owner, @Path("repo") String repo);
    }

    // Interfaz para manejar las respuestas de la API de GitHub
    public interface GitHubApiResponse {
        void onSuccess();
        void onFailure(String errorMessage);
    }
}
