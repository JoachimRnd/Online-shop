package be.vinci.pae.domain;

import java.util.List;

import be.vinci.pae.services.DAOPicture;
import be.vinci.pae.utils.BusinessException;
import jakarta.inject.Inject;

public class PictureUCCImpl implements PictureUCC {
	
	@Inject
	private DAOPicture daoPicture;

	@Override
	public List<PictureDTO> getAllPictures() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PictureDTO> getCarouselPictures() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PictureDTO addPicture(int id, String name, boolean isVisible, FurnitureDTO furniture, boolean isScrolling) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PictureDTO addPicture(PictureDTO newPicture) {
		// TODO
		Picture picture = (Picture) daoPicture.selectPictureByID(newPicture.getId());
		if(picture == null) {
			throw new BusinessException("il n'y a pas de photo ayant cet ID");
		}
		picture = (Picture) newPicture;
		picture.setName(picture.getName());
		return picture;
	}

}
