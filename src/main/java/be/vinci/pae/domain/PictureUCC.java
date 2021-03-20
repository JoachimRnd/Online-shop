package be.vinci.pae.domain;

import java.util.List;

public interface PictureUCC {
	
	List<PictureDTO> getAllPictures();
	
	List<PictureDTO> getCarouselPictures();
	
	PictureDTO addPicture(int id, String name, boolean isVisible, FurnitureDTO furniture, boolean isScrolling);
	
	

}
