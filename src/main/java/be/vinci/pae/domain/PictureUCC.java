package be.vinci.pae.domain;

import java.util.List;

public interface PictureUCC {
	
	List<PictureDTO> getAllPictures();
	
	List<PictureDTO> getCarouselPictures();
	
	//TODO change String to Furniture
	PictureDTO addPicture(int id, String name, boolean isVisible, String furniture, boolean isScrolling);
	
	

}
