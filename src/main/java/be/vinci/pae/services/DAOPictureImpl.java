package be.vinci.pae.services;

import be.vinci.pae.domain.PictureDTO;
import be.vinci.pae.domain.PictureFactory;
import be.vinci.pae.utils.FatalException;
import jakarta.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DAOPictureImpl implements DAOPicture {

  private PreparedStatement selectAllPictures;
  private String querySelectAllPictures;

  @Inject
  private PictureFactory pictureFactory;
  @Inject
  private DalBackendServices dalBackendServices;


  public DAOPictureImpl() {
    querySelectAllPictures = "SELECT p.picture_id, p.name, p.visible_for_everyone, p.furniture, "
        + "p.scrolling_picture FROM project.pictures p";
  }

  @Override
  public List<PictureDTO> selectAllPictures() {
    List<PictureDTO> listPictures = new ArrayList<PictureDTO>();
    try {
      selectAllPictures = dalBackendServices.getPreparedStatement(querySelectAllPictures);
      ResultSet rs = selectAllPictures.executeQuery();
      while (rs.next()) {
        PictureDTO picture = pictureFactory.getPicture();
        listPictures.add(picture);
      }
      return listPictures;
    } catch (Exception e) {
      e.printStackTrace();
      throw new FatalException("Database error : selectAllPictures");
    }
  }

  @Override
  public List<PictureDTO> selectPicturesByType() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public int addPicture(PictureDTO picture) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public PictureDTO selectPictureByID(int id) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public PictureDTO selectPictureByID(String extention) {
    // TODO Auto-generated method stub
    return null;
  }

}
