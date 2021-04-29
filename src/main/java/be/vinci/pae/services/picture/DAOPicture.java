package be.vinci.pae.services.picture;

import be.vinci.pae.domain.picture.PictureDTO;
import java.util.List;

public interface DAOPicture {

  int addPicture(PictureDTO picture);

  boolean updateScrollingPicture(int pictureId);

  PictureDTO selectPictureById(int id);

  boolean deletePicture(int pictureId);

  List<PictureDTO> getCarouselPictures();
}
