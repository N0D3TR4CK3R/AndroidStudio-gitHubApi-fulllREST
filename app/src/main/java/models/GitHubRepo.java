package models;

import android.widget.EditText;
import com.google.gson.annotations.SerializedName;

public class GitHubRepo {

    @SerializedName("name")
    private String name; // Nombre del repositorio

    @SerializedName("description")
    private String description; // Descripción del repositorio

    @SerializedName("language")
    private String language; // Lenguaje utilizado en el repositorio

    // Constructor para inicializar un objeto GitHubRepo con datos
    public GitHubRepo(String language, String description, String name) {
        this.setLanguage(language);
        this.setDescription(description);
        this.setName(name);
    }

    // Constructor vacío (¿Este constructor está incompleto?)
    public GitHubRepo(String language, EditText repoDescription, EditText repoName) {
        // Aquí parece que este constructor está vacío y no se utiliza para asignar valores
        // Puede que se necesite completar o revisar su implementación
    }

    // Métodos getters y setters para acceder y modificar los atributos del repositorio
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public String getLanguage() {
        return language;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setLanguage(String language) {
        this.language = language;
    }
}
