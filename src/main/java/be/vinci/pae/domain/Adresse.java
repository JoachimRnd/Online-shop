package be.vinci.pae.domain;

public interface Adresse {

  String getRue();

  void setRue(String rue);

  String getNumero();

  void setNumero(String numero);

  String getBoite();

  void setBoite(String boite);

  String getCodePostal();

  void setCodePostal(String codePostal);

  String getCommune();

  void setCommune(String commune);

  String getPays();

  void setPays(String pays);

  @Override
  String toString();
}
