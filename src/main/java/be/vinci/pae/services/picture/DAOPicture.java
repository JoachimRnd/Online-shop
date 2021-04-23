package be.vinci.pae.services.picture;

import java.util.List;
import be.vinci.pae.domain.picture.PictureDTO;

public interface DAOPicture {

  int addPicture(PictureDTO picture);

  List<PictureDTO> selectPictureByFurnitureId(int idFurniture);

  boolean updateScrollingPicture(int pictureId);

  PictureDTO selectPictureById(int id);

}
