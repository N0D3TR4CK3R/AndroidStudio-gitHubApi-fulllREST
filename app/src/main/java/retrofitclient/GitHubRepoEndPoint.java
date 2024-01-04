package retrofitclient;

import java.util.List;
import java.util.Map;

import models.GitHubRepo;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GitHubRepoEndPoint {
    @GET("/users/{user}/repos")
    Call<List<GitHubRepo>> getRepo(@Path("user") String name);
    Call<Object> createRepo(@Query("owner") String owner, @Body Map<String, Object> body, String accessToken);
}
