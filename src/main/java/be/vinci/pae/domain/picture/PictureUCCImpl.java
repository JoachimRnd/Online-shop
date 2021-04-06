package be.vinci.pae.domain.picture;

import java.util.List;
import be.vinci.pae.services.DalServices;
import jakarta.inject.Inject;

public class PictureUCCImpl implements PictureUCC {

  @Inject
  private DalServices dalServices;

  @Override
  public List<PictureDTO> getCarouselPictures() {
    try {
      // TODO Auto-generated method stub
      return null;
    } finally {
      this.dalServices.closeConnection();
    }
  }

  @Override
  public PictureDTO addPicture(PictureDTO newPicture) {
    try {
      // TODO Auto-generated method stub
      return null;
    } finally {
      this.dalServices.closeConnection();
    }
  }

}
