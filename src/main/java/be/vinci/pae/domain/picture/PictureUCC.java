package be.vinci.pae.domain.picture;

import java.io.InputStream;
import java.util.List;

public interface PictureUCC {

  List<PictureDTO> getCarouselPictures();

  PictureDTO addPicture(int furnitureId, PictureDTO newPicture, InputStream uploadedInputStream,
      String pictureType);

  boolean modifyScrollingPicture(int pictureId);

}
