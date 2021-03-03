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
  private int typeUtilisateur;

  //@TODO Bug avec l'adresse (Objet dans objet JSON Vue publique)
  @JsonView(Views.Todo.class)
  private Adresse adresse;

  @JsonView(Views.Internal.class)
  private LocalDateTime dateInscription;
  @JsonView(Views.Internal.class)
  private boolean inscriptionValide;
  @JsonView(Views.Internal.class)
  private String motDePasse;

  @Override
  public boolean checkPassword(String password) {
    return BCrypt.checkpw(password, this.motDePasse);
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

  public int getTypeUtilisateur() {
    return typeUtilisateur;
  }

  public Adresse getAdresse() {
    return adresse;
  }

  public LocalDateTime getDateInscription() {
    return dateInscription;
  }

  public boolean isInscriptionValide() {
    return inscriptionValide;
  }

  public String getMotDePasse() {
    return motDePasse;
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

  public void setTypeUtilisateur(int typeUtilisateur) {
    this.typeUtilisateur = typeUtilisateur;
  }

  public void setAdresse(Adresse adresse) {
    this.adresse = adresse;
  }

  public void setDateInscription(LocalDateTime dateInscription) {
    this.dateInscription = dateInscription;
  }

  public void setInscriptionValide(boolean inscriptionValide) {
    this.inscriptionValide = inscriptionValide;
  }

  public void setMotDePasse(String motDePasse) {
    this.motDePasse = motDePasse;
  }

  @Override
  public String toString() {
    return "UserImpl{" + "id=" + id + ", pseudo='" + pseudo + '\'' + ", nom='" + nom + '\''
        + ", prenom='" + prenom + '\'' + ", email='" + email + '\'' + ", type_utilisateur="
        + typeUtilisateur + ", adresse=" + adresse + ", date_inscription=" + dateInscription
        + ", inscription_valide=" + inscriptionValide + ", mot_de_passe='" + motDePasse + '\''
        + '}';
  }
}
