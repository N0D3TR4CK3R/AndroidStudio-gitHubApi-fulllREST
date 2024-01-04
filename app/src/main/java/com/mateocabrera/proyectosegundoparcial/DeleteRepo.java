package com.mateocabrera.proyectosegundoparcial;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import retrofitclient.GitHubDeleteRepo;

public class DeleteRepo extends AppCompatActivity {

    // Campos para la interfaz de usuario
    private EditText editTextRepoName;
    private EditText editTextRepoOwner; // Campo para el propietario del repositorio
    private GitHubDeleteRepo gitHubDeleteRepo; // Instancia del servicio de GitHub

    // Método llamado cuando se crea la actividad
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        // Asignación de campos de la interfaz a variables Java
        editTextRepoOwner = findViewById(R.id.editTextRepoOwner); // Campo para el propietario del repositorio desde el layout
        editTextRepoName = findViewById(R.id.editTextRepoName);
        gitHubDeleteRepo = new GitHubDeleteRepo(); // Instancia del servicio de GitHub
    }

    // Método llamado al hacer clic en el botón para eliminar un repositorio
    public void onDeleteRepoButtonClick(View view) {
        String repoOwner = editTextRepoOwner.getText().toString(); // Obtiene el propietario del repositorio desde el campo
        String repoName = editTextRepoName.getText().toString(); // Obtiene el nombre del repositorio desde el campo

        if (!repoOwner.isEmpty() && !repoName.isEmpty()) {
            // Llamada al método para eliminar un repositorio utilizando el servicio de GitHub
            gitHubDeleteRepo.deleteRepository(repoOwner, repoName, new GitHubDeleteRepo.GitHubApiResponse() {
                @Override
                public void onSuccess() {
                    // Ejecuta el mensaje de éxito en el hilo principal de la interfaz de usuario
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(DeleteRepo.this, "Successfully deleted repository", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onFailure(String errorMessage) {
                    // Ejecuta el mensaje de error en el hilo principal de la interfaz de usuario
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(DeleteRepo.this, "Error deleting repository", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        } else {
            // Muestra un mensaje si no se completaron todos los campos necesarios
            Toast.makeText(DeleteRepo.this, "Complete all fields", Toast.LENGTH_SHORT).show();
        }
    }
}
