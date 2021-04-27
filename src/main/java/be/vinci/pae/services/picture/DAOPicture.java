package be.vinci.pae.services.picture;

import be.vinci.pae.domain.picture.PictureDTO;
import java.util.List;

public interface DAOPicture {
  //@TODO Methodes non utilisée => Supprimer ?

  int addPicture(PictureDTO picture);

  List<PictureDTO> selectPictureByFurnitureId(int idFurniture);

  boolean updateScrollingPicture(int pictureId);

  PictureDTO selectPictureById(int id);

  boolean deletePicture(int pictureId);

}
