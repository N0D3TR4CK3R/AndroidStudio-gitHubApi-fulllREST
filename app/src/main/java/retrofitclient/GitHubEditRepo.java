package retrofitclient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.Path;

public class GitHubEditRepo {

    // URL base de la API de GitHub y tu token de acceso personal
    private static final String BASE_URL = "https://api.github.com/";
    private static final String TOKEN = "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";

    private final GitHubApiEndpoint apiEndpoint;

    public GitHubEditRepo() {
        // Configuración de Retrofit para realizar llamadas a la API de GitHub
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Creación de una instancia de la interfaz para realizar llamadas a la API
        apiEndpoint = retrofit.create(GitHubApiEndpoint.class);
    }

    // Método para editar un repositorio en GitHub
    public void editRepository(String repoOwner, String repoName, String newDescription, final GitHubApiResponse responseCallback) {
        String authorizationHeader = "token " + TOKEN;

        // Objeto RepositoryData con los datos a modificar del repositorio
        RepositoryData repositoryData = new RepositoryData();
        repositoryData.setName(repoName);
        repositoryData.setDescription(newDescription);

        // Llamada para editar el repositorio utilizando la interfaz definida
        Call<RepositoryResponse> call = apiEndpoint.editRepository(authorizationHeader, repoOwner, repoName, repositoryData);

        // Manejo de la respuesta de la solicitud
        call.enqueue(new Callback<RepositoryResponse>() {
            @Override
            public void onResponse(Call<RepositoryResponse> call, Response<RepositoryResponse> response) {
                if (response.isSuccessful()) {
                    responseCallback.onSuccess(); // Notifica éxito al callback
                } else {
                    // Notifica un error al callback si la respuesta no fue exitosa
                    responseCallback.onFailure("Error al editar el repositorio. Código de respuesta: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<RepositoryResponse> call, Throwable t) {
                // Notifica un error al callback si la solicitud falla
                responseCallback.onFailure("Error al editar el repositorio: " + t.getMessage());
            }
        });
    }

    // Interfaz que define los métodos para las llamadas a la API de GitHub
    public interface GitHubApiEndpoint {
        @PATCH("repos/{owner}/{repo}")
        Call<RepositoryResponse> editRepository(@Header("Authorization") String authorizationHeader,
                                                @Path("owner") String owner,
                                                @Path("repo") String repo,
                                                @Body RepositoryData repositoryData);
    }

    // Interfaz para manejar las respuestas de la API de GitHub
    public interface GitHubApiResponse {
        void onSuccess();
        void onFailure(String errorMessage);
    }

    // Clase para los datos del repositorio a editar
    public static class RepositoryData {
        private String name;
        private String description;

        public void setName(String name) {
            this.name = name;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

    // Clase para la respuesta de la edición del repositorio (puedes definir los campos relevantes aquí)
    public static class RepositoryResponse {
        // Puedes definir aquí los campos relevantes de la respuesta
    }
}
