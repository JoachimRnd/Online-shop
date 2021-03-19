package be.vinci.pae.services;

import java.sql.PreparedStatement;
import java.util.List;

import be.vinci.pae.domain.PictureDTO;
import be.vinci.pae.domain.PictureFactory;
import be.vinci.pae.domain.UserFactory;
import jakarta.inject.Inject;

public class DAOPictureImpl implements DAOPicture{
	
	private PreparedStatement selectAllPictures;
	private String querySelectAllPictures;
	
	 @Inject
	  private PictureFactory pictureFactory;
	 @Inject
	  private DalServices dalServices;
	 
	 
	 public DAOPictureImpl() {
		 querySelectAllPictures = 
				 "SELECT p.picture_id, p.name, p.";
	 }

	@Override
	public List<PictureDTO>selectAllPictures() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PictureDTO> selectPicturesByType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int addPicture(PictureDTO picture) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public PictureDTO selectPictureByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PictureDTO selectPictureByID(String extention) {
		// TODO Auto-generated method stub
		return null;
	}

}
