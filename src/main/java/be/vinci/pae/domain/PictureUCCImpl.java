package be.vinci.pae.domain;

import java.util.List;
import be.vinci.pae.services.DAOFurniture;
import be.vinci.pae.services.DAOPicture;
import be.vinci.pae.utils.BusinessException;
import jakarta.inject.Inject;

public class PictureUCCImpl implements PictureUCC {

  @Inject
  private DAOPicture daoPicture;
  @Inject
  private DAOFurniture daoFurniture;

  @Override
  public List<PictureDTO> getAllPictures() {
    List<PictureDTO> liste = daoPicture.selectAllPictures();
    return liste;
  }
  
  @Override
  public List<PictureDTO> getAllPicturesByFurnitureType(String type) {
    //TODO get all listFurniture via type String then foreach furniture get listPicture
    daoFurniture = null;//just to use that later (not a statement)
    List<PictureDTO> liste = daoPicture.selectAllPictures();
    return liste;
  }

  @Override
  public List<PictureDTO> getCarouselPictures() {
    // TODO 
    return null;
  }

  @Override
  public PictureDTO addPicture(int id, String name, boolean isVisible, FurnitureDTO furniture,
      boolean isScrolling) {
    // TODO 
    //peut-etre totalement inutile car pas de setters dans DAO... IDK...
    return null;
  }

  @Override
  public PictureDTO addPicture(PictureDTO newPicture) {
    // TODO
    Picture picture = (Picture) daoPicture.selectPictureByID(newPicture.getId());
    if (picture == null) {
      throw new BusinessException("il n'y a pas de photo ayant cet ID");
    }
    picture = (Picture) newPicture;
    picture.setName(picture.getName());
    picture.setFurniture(picture.getFurniture());
    picture.setAScrollingPicture(picture.isAScrollingPicture());
    picture.setVisibleForEveryone(picture.isVisibleForEveryone());

    int id = daoPicture.addPicture(newPicture);
    picture.setId(id); // ou alors a la place de id : picture.getId() ?
    
    return picture;
  }

}
