package be.vinci.pae.services.picture;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import be.vinci.pae.domain.picture.PictureDTO;
import be.vinci.pae.domain.picture.PictureFactory;
import be.vinci.pae.services.DalBackendServices;
import be.vinci.pae.services.furniture.DAOFurniture;
import be.vinci.pae.utils.FatalException;
import jakarta.inject.Inject;

public class DAOPictureImpl implements DAOPicture {

  private String queryInsertPicture;
  private String queryUpdateScrollingPicture;
  private String querySelectPictureById;
  private String queryDeletePictureById;
  private String querySelectPicturesByFurnitureId;

  @Inject
  private DalBackendServices dalServices;

  @Inject
  private DAOFurniture daoFurniture;

  @Inject
  private PictureFactory pictureFactory;

  /**
   * Contructor of DAOPictureImpl. Contain queries.
   */
  public DAOPictureImpl() {
    queryInsertPicture = "INSERT INTO project.pictures (picture_id,name,visible_for_everyone,"
        + "furniture,scrolling_picture) VALUES (DEFAULT,?,?,?,?)";
    queryUpdateScrollingPicture =
        "UPDATE project.pictures SET scrolling_picture = ? WHERE picture_id = ?";
    querySelectPictureById =
        "SELECT p.picture_id, p.name, p.visible_for_everyone, p.furniture, p.scrolling_picture "
            + "FROM project.pictures p WHERE p.picture_id = ?";
    queryDeletePictureById = "DELETE FROM project.pictures WHERE picture_id = ?";
    querySelectPicturesByFurnitureId =
        "SELECT p.picture_id, p.name, p.visible_for_everyone, p.furniture, p.scrolling_picture "
            + "FROM project.pictures p WHERE p.furniture = ?";
  }

  @Override
  public PictureDTO selectPictureById(int id) {
    try {
      PreparedStatement selectPictureById =
          dalServices.getPreparedStatement(querySelectPictureById);
      selectPictureById.setInt(1, id);
      try (ResultSet rs = selectPictureById.executeQuery()) {
        return createPicture(rs);
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw new FatalException("Data error : selectPictureById");
    }
  }

  @Override
  public List<PictureDTO> selectPicturesByFurnitureId(int furnitureId) {
    try {
      PreparedStatement selectPicturesByFurnitureId =
          dalServices.getPreparedStatement(querySelectPicturesByFurnitureId);
      selectPicturesByFurnitureId.setInt(1, furnitureId);
      try (ResultSet rs = selectPicturesByFurnitureId.executeQuery()) {
        List<PictureDTO> listPicture = new ArrayList<>();
        PictureDTO picture;
        do {
          picture = createPicture(rs);
          listPicture.add(picture);
        } while (picture != null);
        listPicture.remove(listPicture.size() - 1);
        return listPicture;
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw new FatalException("Data error : selectPicturesByFurnitureId");
    }
  }


  private PictureDTO createPicture(ResultSet rs) throws SQLException {
    PictureDTO picture = null;
    if (rs.next()) {
      picture = this.pictureFactory.getPicture();
      picture.setId(rs.getInt("picture_id"));
      picture.setName(rs.getString("name"));
      picture.setVisibleForEveryone(rs.getBoolean("visible_for_everyone"));
      picture.setFurniture(this.daoFurniture.selectFurnitureById(rs.getInt("furniture")));
      picture.setAScrollingPicture(rs.getBoolean("scrolling_picture"));
    }
    return picture;
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
  public boolean deletePicture(int pictureId) {
    try {
      PreparedStatement deletePictureById =
          this.dalServices.getPreparedStatement(queryDeletePictureById);
      deletePictureById.setInt(1, pictureId);
      deletePictureById.execute();
      return true;
    } catch (SQLException e) {
      e.printStackTrace();
      throw new FatalException("Data error : deletePictureById");
    }
  }


  @Override
  public boolean updateScrollingPicture(int pictureId) {
    try {
      PreparedStatement updateScrollingPicture =
          this.dalServices.getPreparedStatement(queryUpdateScrollingPicture);
      PictureDTO pictureDTO = selectPictureById(pictureId);
      updateScrollingPicture.setBoolean(1, !pictureDTO.isAScrollingPicture());
      updateScrollingPicture.setInt(2, pictureId);
      return updateScrollingPicture.executeUpdate() == 1;
    } catch (Exception e) {
      e.printStackTrace();
      throw new FatalException("Data error : updateScrollingPicture");
    }
  }



}
