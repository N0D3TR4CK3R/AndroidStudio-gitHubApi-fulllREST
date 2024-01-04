package retrofitclient;

import java.io.IOException;
import okhttp3.*;

public class GitHubCreateRepo {

    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private static final String BASE_URL = "https://api.github.com";
    private static final String TOKEN = "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"; // Aquí debes colocar tu token de acceso personal

    OkHttpClient client = new OkHttpClient();

    // Método para autenticarse utilizando el token de acceso personal
    private String getAuthorizationHeader() {
        return "token " + TOKEN;
    }

    public interface GitHubApiResponse {
        void onSuccess();
        void onFailure(String errorMessage);
    }

    // Método para crear un nuevo repositorio en GitHub
    public void createNewRepository(String repoName, String description, GitHubApiResponse responseCallback) {
        String url = BASE_URL + "/user/repos";
        String json = "{\"name\": \"" + repoName + "\", \"description\": \"" + description + "\"}";

        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .header("Authorization", getAuthorizationHeader()) // Añade el encabezado de autorización con el token
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // Manejo de errores en la solicitud HTTP
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    // Manejo de respuestas fallidas
                    throw new IOException("Unexpected code " + response);
                }
            }
        });
    }
}

