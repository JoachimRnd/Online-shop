package be.vinci.pae.domain.picture;

import be.vinci.pae.domain.furniture.FurnitureDTO;
import be.vinci.pae.services.DalServices;
import be.vinci.pae.services.furniture.DAOFurniture;
import be.vinci.pae.services.picture.DAOPicture;
import be.vinci.pae.utils.BusinessException;
import be.vinci.pae.utils.Upload;
import jakarta.inject.Inject;
import java.io.InputStream;
import java.util.List;

public class PictureUCCImpl implements PictureUCC {

  @Inject
  private DalServices dalServices;

  @Inject
  private DAOFurniture daoFurniture;

  @Inject
  private DAOPicture daoPicture;

  @Override
  public List<String> getCarouselPictures() {
    try {
      // TODO Auto-generated method stub
      return null;
    } finally {
      this.dalServices.closeConnection();
    }
  }

  @Override
  public PictureDTO addPicture(int furnitureId, PictureDTO newPicture,
      InputStream uploadedInputStream, String pictureType) {
    try {
      this.dalServices.startTransaction();
      FurnitureDTO furniture = this.daoFurniture.selectFurnitureById(furnitureId);
      if (furniture == null) {
        throw new BusinessException("Le meuble n'existe pas");
      }
      int id = this.daoPicture.addPicture(newPicture);
      if (id == -1) {
        this.dalServices.rollbackTransaction();
        return null;
      } else {
        String uploadedFileLocation = ".\\images\\" + id + "." + pictureType;
        if (Upload.saveToFile(uploadedInputStream, uploadedFileLocation)) {
          this.dalServices.commitTransaction();
          newPicture.setId(id);
          return newPicture;
        } else {
          this.dalServices.rollbackTransaction();
        }
      }
      return null;
    } finally {
      this.dalServices.closeConnection();
    }
  }

  @Override
  public boolean modifyScrollingPicture(int pictureId) {
    try {
      this.dalServices.startTransaction();
      if (!this.daoPicture.updateScrollingPicture(pictureId)) {
        this.dalServices.rollbackTransaction();
        throw new BusinessException("Error modify scrolling picture");
      }
      this.dalServices.commitTransaction();
      return true;
    } finally {
      this.dalServices.closeConnection();
    }
  }

  @Override
  public boolean deletePicture(int pictureId) {
    // TODO à tester
    // Récupérer la picture et vérifier qu'elle est pas la favorite de son meuble
    PictureDTO pictureDTO = null;
    if (pictureDTO.getFurniture().getFavouritePicture().getId() != pictureId) {
      try {
        this.dalServices.startTransaction();
        if (!this.daoPicture.deletePicture(pictureId)) {
          this.dalServices.rollbackTransaction();
          throw new BusinessException("Error delete picture");
        }
        this.dalServices.commitTransaction();
        return true;
      } finally {
        this.dalServices.closeConnection();
      }

      // TODO remove in server
    } else {
      return false;
    }

  }
}
