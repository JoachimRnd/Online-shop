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

  public int getType_utilisateur();

  public Adresse getAdresse();

  public LocalDateTime getDate_inscription();

  public boolean isInscription_valide();

  public String getMot_de_passe();

  public void setId(int id);

  public void setPseudo(String pseudo);

  public void setNom(String nom);

  public void setPrenom(String prenom);

  public void setEmail(String email);

  public void setType_utilisateur(int type_utilisateur);

  public void setAdresse(Adresse adresse);

  public void setDate_inscription(LocalDateTime date_inscription);

  public void setInscription_valide(boolean inscription_valide);

  public void setMot_de_passe(String mot_de_passe);

  @Override
  public String toString();

}
