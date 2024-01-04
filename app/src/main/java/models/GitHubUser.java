package models;

import com.google.gson.annotations.SerializedName;

public class GitHubUser {
    // Atributos del usuario de GitHub con anotaciones para el mapeo JSON
    @SerializedName("login")
    private String login; // Nombre de usuario

    @SerializedName("name")
    private String name; // Nombre completo del usuario

    @SerializedName("followers")
    private String followers; // Cantidad de seguidores

    @SerializedName("following")
    private String following; // Cantidad de personas a las que sigue

    @SerializedName("avatar_url")
    private String avatar; // URL del avatar del usuario

    @SerializedName("email")
    private String email; // Dirección de correo electrónico del usuario

    // Constructor para inicializar un objeto GitHubUser con datos
    public GitHubUser(String email, String avatar, String following, String followers, String name, String login) {
        this.setEmail(email);
        this.setAvatar(avatar);
        this.setFollowing(following);
        this.setFollowers(followers);
        this.setName(name);
        this.setLogin(login);
    }

    // Métodos getters y setters para acceder y modificar los atributos del usuario
    public String getEmail() {return email;}
    public String getLogin() {return login;}
    public String getName() {return name;}
    public String getFollowers() {return followers;}
    public String getFollowing() {return following;}
    public String getAvatar() {return avatar;}
    public void setEmail(String email) {this.email = email;}
    public void setLogin(String login) {this.login = login;}
    public void setName(String name) {this.name = name;}
    public void setFollowers(String followers) {this.followers = followers;}
    public void setFollowing(String following) {this.following = following;}
    public void setAvatar(String avatar) {this.avatar = avatar;}
}
