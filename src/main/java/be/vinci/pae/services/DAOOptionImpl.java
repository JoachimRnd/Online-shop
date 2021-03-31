package be.vinci.pae.services;

import be.vinci.pae.domain.OptionDTO;
import be.vinci.pae.domain.OptionFactory;
import be.vinci.pae.utils.FatalException;
import be.vinci.pae.utils.ValueLiaison;
import jakarta.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class DAOOptionImpl implements DAOOption {

  private String querySelectOptionsOfFurniture;
  private String querySelectAllOptions;
  private String querySelectOptionById;
  private String querySelectOptionsOfBuyer;
  private String querySelectOptionsOfBuyerFromFurniture;
  private String queryAddOption;
  private String queryChangeStatusOption;
  private String queryGetLastOptionOfFurniture;

  @Inject
  private OptionFactory optionFactory;
  @Inject
  private DalBackendServices dalBackendServices;
  @Inject
  private DAOFurniture daoFurniture;
  @Inject
  private DAOUser daoUser;

  /**
   * constructor of DAOOptionImpl. contains queries.
   */
  public DAOOptionImpl() {
    querySelectAllOptions = "SELECT option_id, buyer, furniture, duration, date, "
        + "status FROM project.options";
    querySelectOptionById = "SELECT option_id, buyer, furniture, duration, date, "
        + "status FROM project.options WHERE option_id = ?";
    querySelectOptionsOfFurniture = "SELECT option_id, buyer, furniture, duration, date, "
        + "status FROM project.options WHERE furniture = ?";
    querySelectOptionsOfBuyer = "SELECT option_id, buyer, furniture, duration, date, "
        + "status FROM project.options WHERE buyer = ?";
    querySelectOptionsOfBuyerFromFurniture =
        "SELECT option_id, buyer, furniture, duration, date, "
            + "status FROM project.options WHERE buyer = ? AND furniture = ?";
    queryAddOption = "INSERT INTO project.options (option_id, buyer, furniture, duration, date, "
        + "status) VALUES (DEFAULT, ?, ?, ?, ?, ?)";
    queryChangeStatusOption = "UPDATE project.options SET status = ? WHERE option_id = ?";
    queryGetLastOptionOfFurniture = "SELECT option_id, buyer, furniture, duration, date, status "
        + "FROM project.options WHERE furniture = ? AND date = "
        + "(SELECT MAX(date) FROM project.options)";
  }

  @Override
  public List<OptionDTO> selectAllOptions() {
    try {
      PreparedStatement selectAllOptions = dalBackendServices
          .getPreparedStatement(querySelectAllOptions);
      try (ResultSet rs = selectAllOptions.executeQuery()) {
        List<OptionDTO> listOptions = new ArrayList<OptionDTO>();
        OptionDTO option;
        do {
          option = createOption(rs);
          listOptions.add(option);
        } while (option != null);
        listOptions.remove(listOptions.size() - 1);
        return listOptions;
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw new FatalException("Database error : selectAllOptions");
    }
  }

  @Override
  public int addOption(OptionDTO option) {
    try {
      PreparedStatement addOption = dalBackendServices.getPreparedStatementAdd(queryAddOption);
      addOption.setInt(1, option.getBuyer().getId());
      addOption.setInt(2, option.getFurniture().getId());
      addOption.setInt(3, option.getDuration());
      addOption.setTimestamp(4, Timestamp.from(option.getDate().toInstant()));
      addOption.setInt(5, ValueLiaison.stringToIntOption(option.getStatus()));
      addOption.executeUpdate();
      try (ResultSet rs = addOption.getGeneratedKeys()) {
        int optionId = -1;
        if (rs.next()) {
          optionId = rs.getInt(1);
        }
        return optionId;
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw new FatalException("Database error : addOption");
    }
  }

  @Override
  public OptionDTO selectOptionByID(int id) {
    try {
      PreparedStatement selectOptionById = dalBackendServices
          .getPreparedStatement(querySelectOptionById);
      selectOptionById.setInt(1, id);
      try (ResultSet rs = selectOptionById.executeQuery()) {
        OptionDTO option = createOption(rs);
        return option;
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw new FatalException("Database error : selectOptionByID");
    }
  }

  @Override
  public List<OptionDTO> selectOptionsOfFurniture(int idFurniture) {
    try {
      PreparedStatement selectOptionsOfFurniture = dalBackendServices
          .getPreparedStatement(querySelectOptionsOfFurniture);
      selectOptionsOfFurniture.setInt(1, idFurniture);
      try (ResultSet rs = selectOptionsOfFurniture.executeQuery()) {
        List<OptionDTO> listOptions = new ArrayList<OptionDTO>();
        OptionDTO option;
        do {
          option = createOption(rs);
          listOptions.add(option);
        } while (option != null);
        listOptions.remove(listOptions.size() - 1);
        return listOptions;
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw new FatalException("Database error : selectOptionsOfFurniture");
    }
  }

  @Override
  public List<OptionDTO> selectOptionsOfBuyer(int idBuyer) {
    try {
      PreparedStatement selectOptionsOfBuyer = dalBackendServices
          .getPreparedStatement(querySelectOptionsOfBuyer);
      selectOptionsOfBuyer.setInt(1, idBuyer);
      try (ResultSet rs = selectOptionsOfBuyer.executeQuery()) {
        List<OptionDTO> listOptions = new ArrayList<OptionDTO>();
        OptionDTO option;
        do {
          option = createOption(rs);
          listOptions.add(option);
        } while (option != null);
        listOptions.remove(listOptions.size() - 1);
        return listOptions;
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw new FatalException("Database error : selectOptionsOfBuyer");
    }
  }

  @Override
  public OptionDTO selectOptionsOfBuyerFromFurniture(int idBuyer, int idFurniture) {
    try {
      PreparedStatement selectOptionsOfBuyerFromFurniture = dalBackendServices
          .getPreparedStatement(querySelectOptionsOfBuyerFromFurniture);
      selectOptionsOfBuyerFromFurniture.setInt(1, idBuyer);
      selectOptionsOfBuyerFromFurniture.setInt(2, idFurniture);
      try (ResultSet rs = selectOptionsOfBuyerFromFurniture.executeQuery()) {
        OptionDTO option = createOption(rs);
        return option;
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw new FatalException("Database error : selectOptionsOfBuyerFromFurniture");
    }
  }

  @Override
  public boolean finishOption(int id) {
    try {
      PreparedStatement finishOption = dalBackendServices
          .getPreparedStatement(queryChangeStatusOption);
      finishOption.setInt(1, ValueLiaison.FINISHED_OPTION_INT);
      finishOption.setInt(2, id);
      finishOption.executeUpdate();
      return true;
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }

  @Override
  public boolean cancelOption(OptionDTO optionToCancel) {
    try {
      PreparedStatement finishOption = dalBackendServices
          .getPreparedStatement(queryChangeStatusOption);
      finishOption.setInt(1, ValueLiaison.CANCELED_OPTION_INT);
      finishOption.setInt(2, optionToCancel.getId());
      int affectedRows = finishOption.executeUpdate();
      if (affectedRows == 1) {
        return true;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new FatalException("Database error : cancelOption");
    }
    return false;
  }

  @Override
  public OptionDTO getLastOptionOfFurniture(int idFurniture) {
    try {
      PreparedStatement getLastOptionOfFurniture = dalBackendServices
          .getPreparedStatement(queryGetLastOptionOfFurniture);
      getLastOptionOfFurniture.setInt(1, idFurniture);
      try (ResultSet rs = getLastOptionOfFurniture.executeQuery()) {
        OptionDTO option = createOption(rs);
        return option;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new FatalException("Database error : getLastOptionOfFurniture");
    }
  }

  private OptionDTO createOption(ResultSet rs) throws SQLException {
    OptionDTO option = null;
    if (rs.next()) {
      option = optionFactory.getOption();
      option.setId(rs.getInt("option_id"));
      option.setDate(rs.getDate("date"));
      option.setDuration(rs.getInt("duration"));
      option.setStatus(ValueLiaison.intToStringOption(rs.getInt("status")));
      option.setFurniture(daoFurniture.selectFurnitureById(rs.getInt("furniture")));
      option.setBuyer(daoUser.getUserById(rs.getInt("buyer")));
    }
    return option;
  }

}
