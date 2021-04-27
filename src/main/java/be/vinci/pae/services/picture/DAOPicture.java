package be.vinci.pae.services.picture;

import java.util.List;
import be.vinci.pae.domain.picture.PictureDTO;

public interface DAOPicture {
  // @TODO Methodes non utilisée => Supprimer ?
  // yep

  int addPicture(PictureDTO picture);

  List<PictureDTO> selectPictureByFurnitureId(int idFurniture);

  boolean updateScrollingPicture(int pictureId);

  PictureDTO selectPictureById(int id);

  boolean deletePicture(int pictureId);

}
