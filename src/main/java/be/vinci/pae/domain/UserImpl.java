package be.vinci.pae.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import java.time.LocalDateTime;
import org.mindrot.jbcrypt.BCrypt;
import views.Views;

// ignore all null fields in order to avoid sending props not linked to a JSON view
@JsonInclude(JsonInclude.Include.NON_NULL)
class UserImpl implements User {

  @JsonView(Views.Public.class)
  private int id;
  @JsonView(Views.Public.class)
  private String pseudo;
  @JsonView(Views.Public.class)
  private String nom;
  @JsonView(Views.Public.class)
  private String prenom;
  @JsonView(Views.Public.class)
  private String email;
  @JsonView(Views.Public.class)
  private int type_utilisateur;

  //@TODO Bug avec l'adresse (Objet dans objet JSON Vue publique)
  @JsonView(Views.Todo.class)
  private Adresse adresse;

  @JsonView(Views.Internal.class)
  private LocalDateTime date_inscription;
  @JsonView(Views.Internal.class)
  private boolean inscription_valide;
  @JsonView(Views.Internal.class)
  private String mot_de_passe;

  @Override
  public boolean checkPassword(String password) {
    return BCrypt.checkpw(password, this.mot_de_passe);
  }

  @Override
  public String hashPassword(String password) {
    return BCrypt.hashpw(password, BCrypt.gensalt());
  }

  public int getId() {
    return id;
  }

  @Override
  public String getPseudo() {
    return pseudo;
  }

  public String getNom() {
    return nom;
  }

  public String getPrenom() {
    return prenom;
  }

  public String getEmail() {
    return email;
  }

  public int getType_utilisateur() {
    return type_utilisateur;
  }

  public Adresse getAdresse() {
    return adresse;
  }

  public LocalDateTime getDate_inscription() {
    return date_inscription;
  }

  public boolean isInscription_valide() {
    return inscription_valide;
  }

  public String getMot_de_passe() {
    return mot_de_passe;
  }

  public void setId(int id) {
    this.id = id;
  }

  @Override
  public void setPseudo(String pseudo) {
    this.pseudo = pseudo;
  }

  public void setNom(String nom) {
    this.nom = nom;
  }

  public void setPrenom(String prenom) {
    this.prenom = prenom;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setType_utilisateur(int type_utilisateur) {
    this.type_utilisateur = type_utilisateur;
  }

  public void setAdresse(Adresse adresse) {
    this.adresse = adresse;
  }

  public void setDate_inscription(LocalDateTime date_inscription) {
    this.date_inscription = date_inscription;
  }

  public void setInscription_valide(boolean inscription_valide) {
    this.inscription_valide = inscription_valide;
  }

  public void setMot_de_passe(String mot_de_passe) {
    this.mot_de_passe = mot_de_passe;
  }

  @Override
  public String toString() {
    return "UserImpl{" +
        "id=" + id +
        ", pseudo='" + pseudo + '\'' +
        ", nom='" + nom + '\'' +
        ", prenom='" + prenom + '\'' +
        ", email='" + email + '\'' +
        ", type_utilisateur=" + type_utilisateur +
        ", adresse=" + adresse +
        ", date_inscription=" + date_inscription +
        ", inscription_valide=" + inscription_valide +
        ", mot_de_passe='" + mot_de_passe + '\'' +
        '}';
  }
}
