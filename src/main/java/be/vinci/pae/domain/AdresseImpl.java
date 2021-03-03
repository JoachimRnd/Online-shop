package be.vinci.pae.domain;

public class AdresseImpl implements Adresse {

  private String rue;
  private String numero;
  private String boite;
  private String code_postal;
  private String commune;
  private String pays;

  @Override
  public String getRue() {
    return rue;
  }

  @Override
  public void setRue(String rue) {
    this.rue = rue;
  }

  @Override
  public String getNumero() {
    return numero;
  }

  @Override
  public void setNumero(String numero) {
    this.numero = numero;
  }

  @Override
  public String getBoite() {
    return boite;
  }

  @Override
  public void setBoite(String boite) {
    this.boite = boite;
  }

  @Override
  public String getCode_postal() {
    return code_postal;
  }

  @Override
  public void setCode_postal(String code_postal) {
    this.code_postal = code_postal;
  }

  @Override
  public String getCommune() {
    return commune;
  }

  @Override
  public void setCommune(String commune) {
    this.commune = commune;
  }

  @Override
  public String getPays() {
    return pays;
  }

  @Override
  public void setPays(String pays) {
    this.pays = pays;
  }

  @Override
  public String toString() {
    return "Adresse{" +
        "rue='" + rue + '\'' +
        ", numero='" + numero + '\'' +
        ", boite='" + boite + '\'' +
        ", code_postal='" + code_postal + '\'' +
        ", commune='" + commune + '\'' +
        ", pays='" + pays + '\'' +
        '}';
  }
}
