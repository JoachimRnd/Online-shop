package be.vinci.pae.domain;

import java.util.List;

public interface PictureUCC {
<<<<<<< HEAD
	
	List<PictureDTO> getAllPictures();
	
	List<PictureDTO> getCarouselPictures();
	
	PictureDTO addPicture(int id, String name, boolean isVisible, FurnitureDTO furniture, boolean isScrolling);
	
	
=======

  List<PictureDTO> getAllPictures();

  List<PictureDTO> getCarouselPictures();

  PictureDTO addPicture(int id, String name, boolean isVisible, Furniture furniture,
      boolean isScrolling);

>>>>>>> branch 'picture-classes' of https://gitlab.vinci.be/6i2-cae/2020-2021/projet-ae-groupe-15.git

}
