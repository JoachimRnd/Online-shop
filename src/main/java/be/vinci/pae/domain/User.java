package be.vinci.pae.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.time.LocalDateTime;

@JsonDeserialize(as = UserImpl.class)
public interface User {

  boolean checkPassword(String password);

  String hashPassword(String password);

  int getId();

  String getPseudo();

  String getNom();

  String getPrenom();

  String getEmail();

  int getTypeUtilisateur();

  Adresse getAdresse();

  LocalDateTime getDateInscription();

  boolean isInscriptionValide();

  String getMotDePasse();

  void setId(int id);

  void setPseudo(String pseudo);

  void setNom(String nom);

  void setPrenom(String prenom);

  void setEmail(String email);

  void setTypeUtilisateur(int typeUtilisateur);

  void setAdresse(Adresse adresse);

  void setDateInscription(LocalDateTime dateInscription);

  void setInscriptionValide(boolean inscriptionValide);

  void setMotDePasse(String motDePasse);

  @Override
  String toString();

}
