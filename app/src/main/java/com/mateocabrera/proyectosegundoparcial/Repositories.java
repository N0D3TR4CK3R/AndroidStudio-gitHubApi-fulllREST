package com.mateocabrera.proyectosegundoparcial;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import adapters.ReposAdapter;
import models.GitHubRepo;
import retrofitclient.APIClient;
import retrofitclient.GitHubRepoEndPoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repositories extends AppCompatActivity {

    String receivedUserName; // Nombre de usuario recibido de la actividad anterior
    RecyclerView mRecyclerView; // Vista para mostrar la lista de repositorios
    List<GitHubRepo> myDataSource = new ArrayList<>(); // Fuente de datos para los repositorios
    RecyclerView.Adapter myAdapter; // Adaptador para conectar los datos con la vista

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repositories);

        // Obtiene el nombre de usuario recibido de la actividad anterior
        Bundle extras = getIntent().getExtras();
        receivedUserName = extras.getString("username");

        // Configura el RecyclerView y el adaptador
        mRecyclerView = findViewById(R.id.repos_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        myAdapter = new ReposAdapter(myDataSource, R.layout.repo_item, getApplicationContext());
        mRecyclerView.setAdapter(myAdapter);

        // Carga los repositorios del usuario
        loadRepositories();
    }

    // Método para cargar los repositorios del usuario desde la API de GitHub
    public void loadRepositories() {
        GitHubRepoEndPoint apiService = APIClient.getClient().create(GitHubRepoEndPoint.class);

        // Realiza la llamada a la API para obtener la lista de repositorios
        Call<List<GitHubRepo>> call = apiService.getRepo(receivedUserName);
        call.enqueue(new Callback<List<GitHubRepo>>() {
            @Override
            public void onResponse(Call<List<GitHubRepo>> call, Response<List<GitHubRepo>> response) {
                myDataSource.clear(); // Limpia la lista de repositorios
                myDataSource.addAll(response.body()); // Agrega los repositorios obtenidos de la respuesta
                myAdapter.notifyDataSetChanged(); // Notifica al adaptador sobre el cambio en los datos
            }

            @Override
            public void onFailure(Call<List<GitHubRepo>> call, Throwable t) {
                // Registra un error si la solicitud falla
                Log.e("Repos", t.toString());
            }
        });
    }
}
