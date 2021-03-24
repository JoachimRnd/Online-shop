package be.vinci.pae.services;

import java.util.List;

import be.vinci.pae.domain.OptionDTO;

public interface DAOOption {

	List<OptionDTO> selectAllOptions();
	
	int addOption(OptionDTO picture);
	
	OptionDTO selectOptionByID(int id);
	
	
}