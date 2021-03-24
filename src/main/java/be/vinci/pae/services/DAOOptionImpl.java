package be.vinci.pae.services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import be.vinci.pae.domain.OptionDTO;
import be.vinci.pae.domain.OptionFactory;
import be.vinci.pae.utils.FatalException;
import jakarta.inject.Inject;

public class DAOOptionImpl implements DAOOption {

  private PreparedStatement selectAllOptions;
  private String querySelectAllOptions;

  @Inject
  private OptionFactory optionFactory;
  @Inject
  private DalServices dalServices;


  public DAOOptionImpl() {
    querySelectAllOptions = "SELECT o.option_id, o.buyer, o.furniture, o.duration, o.date"
        + "o.status FROM project.options o";
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

  public int addOption(OptionDTO picture) {
    // TODO
    return 42;
  }

  public OptionDTO selectOptionByID(int id) {
    // TODO
    return null;
  }

}
