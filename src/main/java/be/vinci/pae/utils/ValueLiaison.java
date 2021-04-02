package be.vinci.pae.utils;

public class ValueLiaison {

  //TODO passer en Enum (Avec interface)

  public static final String ADMIN_STRING = "admin";
  public static final String CLIENT_STRING = "client";
  public static final String ANTIQUAIRE_STRING = "antiquaire";
  public static final int ADMIN_INT = 2;
  public static final int CLIENT_INT = 0;
  public static final int ANTIQUAIRE_INT = 1;
  public static final String CANCELED_OPTION_STRING = "annulee";
  public static final String FINISHED_OPTION_STRING = "finie";
  public static final String RUNNING_OPTION_STRING = "en cours";
  public static final int CANCELED_OPTION_INT = 0;
  public static final int FINISHED_OPTION_INT = 2;
  public static final int RUNNING_OPTION_INT = 1;
  public static final int IN_RESTORATION = 0;
  public static final int IN_STORE = 1;
  public static final int ON_SALE = 2;
  public static final int REMOVED_FROM_SALE = 3;
  public static final int BOUGHT = 4;
  public static final int NOT_SUITABLE = 5;
  public static final String ON_SALE_STRING = "en_vente";
  public static final String IN_RESTORATION_STRING = "en_restauration";
  public static final String IN_STORE_STRING = "en_magasin";
  public static final String REMOVED_FROM_SALE_STRING = "retire_de_vente";
  public static final String BOUGHT_STRING = "achete";
  public static final String NOT_SUITABLE_STRING = "ne_convient_pas";


  /**
   * Change the user type form string into a integer.
   *
   * @param type type of user as string
   * @return integer of the user type
   */
  public static int stringToIntUserType(String type) {
    switch (type.toLowerCase()) {
      case CLIENT_STRING:
        return CLIENT_INT;
      case ANTIQUAIRE_STRING:
        return ANTIQUAIRE_INT;
      case ADMIN_STRING:
        return ADMIN_INT;
      default:
        throw new BusinessException("Le type n'existe pas");
    }
  }

  /**
   * Change the user type form integer into a string.
   *
   * @param type type of user as integer
   * @return string of the user type
   */
  public static String intToStringUserType(int type) {
    switch (type) {
      case CLIENT_INT:
        return CLIENT_STRING;
      case ANTIQUAIRE_INT:
        return ANTIQUAIRE_STRING;
      case ADMIN_INT:
        return ADMIN_STRING;
      default:
        throw new BusinessException("Le type n'existe pas");
    }
  }

  /**
   * Valid the string of the user type.
   *
   * @param type type of user as string
   * @return boolean of valid type
   */
  public static boolean isValidUserType(String type) {
    switch (type.toLowerCase()) {
      case CLIENT_STRING:
      case ANTIQUAIRE_STRING:
      case ADMIN_STRING:
        return true;
      default:
        return false;
    }
  }

  /**
   * Valid the integer of the user type.
   *
   * @param type type of user as integer
   * @return boolean of valid type
   */
  public static boolean isValidUserType(int type) {
    switch (type) {
      case CLIENT_INT:
      case ANTIQUAIRE_INT:
      case ADMIN_INT:
        return true;
      default:
        return false;
    }
  }

  /**
   * Change the option type form string into a integer.
   *
   * @param type type of option as string
   * @return integer of the option type
   */
  public static int stringToIntOption(String type) {
    switch (type.toLowerCase()) {
      case RUNNING_OPTION_STRING:
        return RUNNING_OPTION_INT;
      case FINISHED_OPTION_STRING:
        return FINISHED_OPTION_INT;
      case CANCELED_OPTION_STRING:
        return CANCELED_OPTION_INT;
      default:
        throw new BusinessException("Le type n'existe pas");
    }
  }

  /**
   * Change the option type form integer into a string.
   *
   * @param type type of option as integer
   * @return string of the option type
   */
  public static String intToStringOption(int type) {
    switch (type) {
      case RUNNING_OPTION_INT:
        return RUNNING_OPTION_STRING;
      case FINISHED_OPTION_INT:
        return FINISHED_OPTION_STRING;
      case CANCELED_OPTION_INT:
        return CANCELED_OPTION_STRING;
      default:
        throw new BusinessException("Le type n'existe pas");
    }
  }

  /**
   * Valid the string of the option type.
   *
   * @param type type of option as string
   * @return boolean of valid type
   */
  public static boolean isValidOption(String type) {
    switch (type.toLowerCase()) {
      case RUNNING_OPTION_STRING:
      case FINISHED_OPTION_STRING:
      case CANCELED_OPTION_STRING:
        return true;
      default:
        return false;
    }
  }

  /**
   * Valid the integer of the option type.
   *
   * @param type type of option as integer
   * @return boolean of valid type
   */
  public static boolean isValidOption(int type) {
    switch (type) {
      case RUNNING_OPTION_INT:
      case FINISHED_OPTION_INT:
      case CANCELED_OPTION_INT:
        return true;
      default:
        return false;
    }
  }

  /**
   * Change the condition form integer into a string.
   *
   * @param condition condition of user as integer
   * @return string of the user condition
   */
  public static String intToStringCondition(int condition) {
    switch (condition) {
      case IN_RESTORATION:
        return IN_RESTORATION_STRING;
      case IN_STORE:
        return IN_STORE_STRING;
      case ON_SALE:
        return ON_SALE_STRING;
      case REMOVED_FROM_SALE:
        return REMOVED_FROM_SALE_STRING;
      case BOUGHT:
        return BOUGHT_STRING;
      case NOT_SUITABLE:
        return NOT_SUITABLE_STRING;
      default:
        throw new BusinessException("L'état n'existe pas");
    }
  }

  /**
   * Change the condition from string into a integer.
   *
   * @param condition condition of user as string
   * @return integer of the condition user
   */
  public static int stringToIntCondition(String condition) {
    switch (condition.toLowerCase()) {
      case IN_RESTORATION_STRING:
        return IN_RESTORATION;
      case IN_STORE_STRING:
        return IN_STORE;
      case ON_SALE_STRING:
        return ON_SALE;
      case REMOVED_FROM_SALE_STRING:
        return REMOVED_FROM_SALE;
      case BOUGHT_STRING:
        return BOUGHT;
      case NOT_SUITABLE_STRING:
        return NOT_SUITABLE;
      default:
        throw new BusinessException("L'état n'existe pas");
    }
  }

}
