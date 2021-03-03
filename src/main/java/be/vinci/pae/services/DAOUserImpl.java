package be.vinci.pae.services;

import be.vinci.pae.domain.Adresse;
import be.vinci.pae.domain.AdresseFactory;
import be.vinci.pae.domain.User;
import be.vinci.pae.domain.UserFactory;
import be.vinci.pae.utils.Config;
import jakarta.inject.Inject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAOUserImpl implements DAOUser {

  private static final String DB_STRING_CONNECTION = Config.getProperty("DatabaseStringConnection");
  private Connection conn;
  private PreparedStatement selectUserByPseudo;
  private PreparedStatement selectUserById;

  @Inject
  private UserFactory userFactory;

  @Inject
  private AdresseFactory adresseFactory;

  /**
   * Description.
   *
   * @TODO Javadoc
   */
  public DAOUserImpl() {
    try {
      conn = DriverManager.getConnection(DB_STRING_CONNECTION);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    try {
      selectUserByPseudo = conn.prepareStatement(
          "SELECT u.id_utilisateur, u.pseudo, u.mot_de_passe, u.nom, u.prenom, a.rue, a.numero,"
              + " a.boite, a.code_postal, a.commune, a.pays, u.email, u.date_inscription,"
              + " u.inscription_valide, u.type_utilisateur "
              + "FROM projet.adresses a, projet.utilisateurs u "
              + "WHERE u.pseudo = ? AND u.adresse = a.id_adresse");
      selectUserById = conn.prepareStatement(
          "SELECT u.id_utilisateur, u.pseudo, u.mot_de_passe, u.nom, u.prenom, a.rue, a.numero,"
              + " a.boite, a.code_postal, a.commune, a.pays, u.email, u.date_inscription,"
              + " u.inscription_valide, u.type_utilisateur "
              + "FROM projet.adresses a, projet.utilisateurs u "
              + "WHERE u.id_utilisateur = ? AND u.adresse = a.id_adresse");
    } catch (SQLException e) {
      e.printStackTrace();
    }

  }

  @Override
  public User getUser(String login) {
    try {
      selectUserByPseudo.setString(1, login);
      try (ResultSet rs = selectUserByPseudo.executeQuery()) {
        User user = createUser(rs);
        return user;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public User getUser(int id) {
    try {
      selectUserById.setInt(1, id);
      try (ResultSet rs = selectUserById.executeQuery()) {
        User user = createUser(rs);
        return user;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  private User createUser(ResultSet rs) throws SQLException {
    User user = null;
    while (rs.next()) {
      user = this.userFactory.getUser();
      user.setId(rs.getInt("id_utilisateur"));
      user.setPseudo(rs.getString("pseudo"));
      user.setMotDePasse(rs.getString("mot_de_passe"));
      user.setNom(rs.getString("nom"));
      user.setPrenom(rs.getString("prenom"));
      Adresse adresse = this.adresseFactory.getAdresse();
      adresse.setRue(rs.getString("rue"));
      adresse.setNumero(rs.getString("numero"));
      adresse.setBoite(rs.getString("boite"));
      adresse.setCodePostal(rs.getString("code_postal"));
      adresse.setCommune(rs.getString("commune"));
      adresse.setPays(rs.getString("pays"));
      user.setAdresse(adresse);
      user.setEmail(rs.getString("email"));
      user.setDateInscription(rs.getTimestamp("date_inscription").toLocalDateTime());
      user.setInscriptionValide(rs.getBoolean("inscription_valide"));
      user.setTypeUtilisateur(rs.getInt("type_utilisateur"));
    }
    return user;
  }

  @Override
  public void addUser(User user) {
  }
}
