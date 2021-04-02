package be.vinci.pae.domain.picture;

import java.util.List;

public interface PictureUCC {

  List<PictureDTO> getCarouselPictures();

  PictureDTO addPicture(PictureDTO picture);

}
