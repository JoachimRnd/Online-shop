package be.vinci.pae.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.time.LocalDateTime;

@JsonDeserialize(as = UserImpl.class)
public interface User {

  public boolean checkPassword(String password);

  public String hashPassword(String password);

  public int getId();

  public String getPseudo();

  public String getNom();

  public String getPrenom();

  public String getEmail();

  public int getTypeUtilisateur();

  public Adresse getAdresse();

  public LocalDateTime getDateInscription();

  public boolean isInscriptionValide();

  public String getMotDePasse();

  public void setId(int id);

  public void setPseudo(String pseudo);

  public void setNom(String nom);

  public void setPrenom(String prenom);

  public void setEmail(String email);

  public void setTypeUtilisateur(int type_utilisateur);

  public void setAdresse(Adresse adresse);

  public void setDateInscription(LocalDateTime date_inscription);

  public void setInscriptionValide(boolean inscription_valide);

  public void setMotDePasse(String mot_de_passe);

  @Override
  public String toString();

}
