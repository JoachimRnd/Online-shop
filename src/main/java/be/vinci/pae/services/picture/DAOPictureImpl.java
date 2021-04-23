package be.vinci.pae.services.picture;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import be.vinci.pae.domain.picture.PictureDTO;
import be.vinci.pae.services.DalBackendServices;
import be.vinci.pae.utils.FatalException;
import jakarta.inject.Inject;

public class DAOPictureImpl implements DAOPicture {

  private String queryInsertPicture;

  @Inject
  private DalBackendServices dalServices;

  public DAOPictureImpl() {
    queryInsertPicture = "INSERT INTO project.pictures (picture_id,name,visible_for_everyone,"
        + "furniture,scrolling_picture) VALUES (DEFAULT,?,?,?,?)";
  }

  @Override
  public int addPicture(PictureDTO picture) {
    int pictureId = -1;
    try {
      PreparedStatement insertPicture =
          this.dalServices.getPreparedStatementAdd(queryInsertPicture);

      insertPicture.setString(1, picture.getName());
      insertPicture.setBoolean(2, picture.isVisibleForEveryone());
      insertPicture.setInt(3, picture.getFurniture().getId());
      insertPicture.setBoolean(4, picture.isAScrollingPicture());
      insertPicture.execute();
      ResultSet rs = insertPicture.getGeneratedKeys();
      if (rs.next()) {
        pictureId = rs.getInt(1);
      }
      return pictureId;
    } catch (SQLException e) {
      e.printStackTrace();
      throw new FatalException("Data error : insertPicture");
    }
  }

  @Override
  public List<PictureDTO> selectPictureByFurnitureId(int idFurniture) {
    // TODO Auto-generated method stub
    return null;
  }

}
