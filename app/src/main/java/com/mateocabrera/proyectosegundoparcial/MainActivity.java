package com.mateocabrera.proyectosegundoparcial;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import models.GitHubUser;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofitclient.APIClient;
import retrofitclient.GitHubUserEndPoint;

// Clase principal que representa la actividad principal de la aplicación
public class MainActivity extends AppCompatActivity {

    // Declaración de variables para los elementos de la interfaz de usuario
    ImageView AvatarImg;
    TextView UserNameTV, FollowersTV, FollowingTV, LogIn, E_mail;
    Bitmap MyImage;
    Button BtnListRepo, BtnCreateRepo, BtnDeleteRepo, BtnEditRepo;
    Bundle Extras;
    // Cadena utilizada para la llamada a la API
    String NewString = "N0D3TR4CK3R";

    // Método llamado cuando se crea la actividad
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Vinculación de elementos de diseño con variables Java
        AvatarImg = (ImageView) findViewById(R.id.avatar);
        UserNameTV = (TextView) findViewById(R.id.username);
        FollowersTV = (TextView) findViewById(R.id.followers);
        FollowingTV = (TextView) findViewById(R.id.following);
        LogIn = (TextView) findViewById(R.id.logIn);
        E_mail = (TextView) findViewById(R.id.email);
        /* no hace falta instancial los botones ya
        BtnListRepo = (Button) findViewById(R.id.listRepo);
        BtnCreateRepo = (Button) findViewById(R.id.createRepo);
        BtnDeleteRepo = (Button) findViewById(R.id.deleteRepo);
        BtnEditRepo = (Button) findViewById(R.id.editRepo);
        */

        System.out.println(NewString);
        loadData(); //Método para cargar los datos de la API al iniciar la actividad
    }

    // Clase interna para descargar la imagen de perfil del usuario de forma asíncrona
    public class ImageDownloader extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... urls) {
            try {
                // Se descarga la imagen desde la URL proporcionada
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream inputStream = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(inputStream);
                return myBitmap;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    // Método para cargar datos del usuario desde la API de GitHub
    public void loadData() {
        final GitHubUserEndPoint apiService =
                // Creación del servicio para realizar la llamada a la API de GitHub
                APIClient.getClient().create(GitHubUserEndPoint.class);

        // Se hace una llamada a la API para obtener los datos del usuario
        Call<GitHubUser> call = apiService.getUser(NewString);
        call.enqueue(new Callback<GitHubUser>() {

            // Método llamado cuando se recibe una respuesta exitosa de la API
            @Override
            public void onResponse(Call<GitHubUser> call, Response<GitHubUser>
                    response) {
                // Se crea una instancia de ImageDownloader para descargar la imagen de perfil
                ImageDownloader task = new ImageDownloader();
                try {
                    MyImage = task.execute(response.body().getAvatar()).get();

                } catch (Exception e) {

                    e.printStackTrace();
                }
                // Actualización de elementos de la interfaz con los datos obtenidos de la API
                AvatarImg.setImageBitmap(MyImage);
                AvatarImg.getLayoutParams().height=240;
                AvatarImg.getLayoutParams().width=240;

                // Verificación y asignación de datos a los TextView correspondientes
                if(response.body().getName() == null){
                    UserNameTV.setText("No name provided");
                } else {
                    UserNameTV.setText(response.body().getName());
                }

                FollowersTV.setText(response.body().getFollowers());
                FollowingTV.setText(response.body().getFollowing());
                LogIn.setText(response.body().getLogin());

                if(response.body().getEmail() == null){
                    E_mail.setText("No email provided");
                } else{
                    E_mail.setText(response.body().getEmail());
                }
            }

            // Método llamado si la llamada a la API falla
            @Override
            public void onFailure(Call<GitHubUser> call, Throwable t) {
                System.out.println("Failed!" + t.toString());
            }
        });
    }

    // Métodos para iniciar actividades relacionadas con repositorios al hacer clic en botones
    public void listActivity (View view) {
        Intent intent = new Intent(MainActivity.this, Repositories.class);
        intent.putExtra("username", NewString);
        startActivity(intent);
    }

    public void createActivity (View view) {
        Intent intent = new Intent(MainActivity.this, CreateRepo.class);
        startActivity(intent);
    }

    public void deleteActivity (View view) {
        Intent intent = new Intent(MainActivity.this, DeleteRepo.class);
        startActivity(intent);
    }
    public void editActivity (View view) {
        Intent intent = new Intent(MainActivity.this, EditRepo.class);
        startActivity(intent);
    }
}