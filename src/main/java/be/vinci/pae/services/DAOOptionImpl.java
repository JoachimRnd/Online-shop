package be.vinci.pae.services;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import be.vinci.pae.domain.FurnitureFactory;
import be.vinci.pae.domain.OptionDTO;
import be.vinci.pae.domain.OptionFactory;
import be.vinci.pae.domain.UserFactory;
import be.vinci.pae.utils.FatalException;
import jakarta.inject.Inject;

public class DAOOptionImpl implements DAOOption {

  private PreparedStatement selectAllOptions;
  private PreparedStatement selectUserId;
  private PreparedStatement selectFurnitureId;
  private String querySelectAllOptions;
  private String queryAddOption;
  private String querySelectUserId;
  private String querySelectFurnitureId;

  @Inject
  private FurnitureFactory furnitureFactory;
  @Inject
  private UserFactory userFactory;
  @Inject
  private OptionFactory optionFactory;
  @Inject
  private DalServices dalServices;


  public DAOOptionImpl() {
    querySelectAllOptions = "SELECT o.option_id, o.buyer, o.furniture, o.duration, o.date"
        + "o.status FROM project.options o;";
    queryAddOption = "INSERT INTO project.options (option_id, buyer, furniture, duration, date"
        + "status VALUES (DEFAULT, ?, ?, ?, ?, ?);";
    querySelectUserId = "TODO";
    querySelectFurnitureId = "TODO";
  }

  @Override
  public List<OptionDTO> selectAllOptions() {
    List<OptionDTO> listOptions = new ArrayList<OptionDTO>();
    try {
      selectAllOptions = dalServices.getPreparedStatement(querySelectAllOptions);
      ResultSet rs = selectAllOptions.executeQuery();
      while (rs.next()) {
        OptionDTO option = optionFactory.getOption();
        listOptions.add(option);
      }
      return listOptions;
    } catch (Exception e) {
      e.printStackTrace();
      throw new FatalException("Database error : selectAllOptions");
    }
  }

  public int addOption(OptionDTO option) {
    // TODO
    int optionId = -1;
    try {
      PreparedStatement addOption = this.dalServices.getPreparedStatement(queryAddOption);
      if (selectUserId == null && selectFurnitureId == null) {
        selectFurnitureId = this.dalServices.getPreparedStatement(querySelectFurnitureId);
        selectUserId = this.dalServices.getPreparedStatement(querySelectUserId);
      }
      selectFurnitureId.setInt(1, option.getFurnitureId());
      selectUserId.setInt(1, option.getBuyerId());
      ResultSet rsFurniture = selectFurnitureId.executeQuery();
      ResultSet rsUser = selectUserId.executeQuery();
      if (rsFurniture != null && rsUser != null) {
        addOption.setInt(2, option.getBuyerId());
        addOption.setInt(3, option.getFurnitureId());
        addOption.setInt(4, option.getDuration());
        // TODO setDate to LocalDate --> is that correct ?
        addOption.setDate(5, Date.valueOf(option.getDate()));
        addOption.setInt(6, option.getStatus());
      }
      try (ResultSet rs = addOption.getGeneratedKeys()) {
        if (rs.next()) {
          optionId = rs.getInt(1);
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
      System.out.println("Database error : addOption");
    }
    return optionId;
  }

  public OptionDTO selectOptionByID(int id) {
    // TODO
    return null;
  }

}
