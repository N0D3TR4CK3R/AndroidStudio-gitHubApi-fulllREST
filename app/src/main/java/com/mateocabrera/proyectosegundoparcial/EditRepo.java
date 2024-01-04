package com.mateocabrera.proyectosegundoparcial;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import retrofitclient.GitHubEditRepo;

public class EditRepo extends AppCompatActivity {

    // Campos para la interfaz de usuario
    private EditText editTextRepoName;
    private EditText editTextRepoDescription;
    private GitHubEditRepo gitHubEditRepo; // Instancia del servicio de GitHub para editar repositorios

    // Método llamado cuando se crea la actividad
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        // Asignación de campos de la interfaz a variables Java
        editTextRepoName = findViewById(R.id.editTextRepoName);
        editTextRepoDescription = findViewById(R.id.editTextRepoDescription);
        gitHubEditRepo = new GitHubEditRepo(); // Instancia del servicio de GitHub
    }

    // Método llamado al hacer clic en el botón para editar un repositorio
    public void onEditRepoButtonClick(View view) {
        String repoOwner = "N0D3TR4CK3R"; // Propietario del repositorio (puede ser una variable dinámica)
        String repoName = editTextRepoName.getText().toString(); // Obtiene el nombre del repositorio desde el campo
        String newDescription = editTextRepoDescription.getText().toString(); // Obtiene la nueva descripción del repositorio

        // Verifica si los campos necesarios están completos para editar el repositorio
        if (!repoName.isEmpty() && !newDescription.isEmpty()) {
            // Llamada al método para editar un repositorio utilizando el servicio de GitHub
            gitHubEditRepo.editRepository(repoOwner, repoName, newDescription, new GitHubEditRepo.GitHubApiResponse() {
                @Override
                public void onSuccess() {
                    // Muestra un mensaje de éxito en el hilo principal de la interfaz de usuario
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(EditRepo.this, "Successfully edited repository", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onFailure(String errorMessage) {
                    // Muestra un mensaje de error en el hilo principal de la interfaz de usuario
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(EditRepo.this, "Error editing repository", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        } else {
            // Muestra un mensaje si no se completaron todos los campos necesarios
            Toast.makeText(EditRepo.this, "Complete all fields", Toast.LENGTH_SHORT).show();
        }
    }
}
