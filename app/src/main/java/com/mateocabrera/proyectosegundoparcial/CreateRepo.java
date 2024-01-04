package com.mateocabrera.proyectosegundoparcial;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import android.view.View;
import android.widget.EditText;

import retrofitclient.GitHubCreateRepo;

public class CreateRepo extends AppCompatActivity {

    private EditText editTextRepoName;
    private EditText editTextDescription;
    private GitHubCreateRepo gitHubCreateRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        // Inicialización de los elementos de la interfaz
        editTextRepoName = findViewById(R.id.editTextRepoName);
        editTextDescription = findViewById(R.id.editTextDescription);

        // Inicialización de tu servicio de GitHubCreateRepo
        gitHubCreateRepo = new GitHubCreateRepo();
    }

    public void onCreateRepoButtonClick(View view) {
        String repoName = editTextRepoName.getText().toString();
        String description = editTextDescription.getText().toString();

        // Verificación de datos antes de enviar la solicitud a GitHub
        if (!repoName.isEmpty() && !description.isEmpty()) {
            // llamamos al método correspondiente en tu GitHubApiService
            // Por ejemplo: gitHubCreateRepo.createNewRepository(repoName, description);
            gitHubCreateRepo.createNewRepository(repoName, description, new GitHubCreateRepo.GitHubApiResponse() {
                @Override
                public void onSuccess() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // Éxito: muestra un mensaje al usuario indicando que el repositorio se creó correctamente
                            Toast.makeText(CreateRepo.this, "Successfully created repository", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                @Override
                public void onFailure(String errorMessage) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // Error: muestra un mensaje al usuario indicando que hubo un problema al crear el repositorio
                            Toast.makeText(CreateRepo.this, "Error creating repository", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        } else {
            // Manejo de campos vacios, puedes mostrar un mensaje al usuario para que complete los campos
            Toast.makeText(CreateRepo.this, "Complete all fields", Toast.LENGTH_SHORT).show();
        }
    }
}