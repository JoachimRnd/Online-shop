package be.vinci.pae.services;

import be.vinci.pae.domain.AddressDTO;
import be.vinci.pae.domain.FurnitureDTO;
import be.vinci.pae.domain.VisitRequestDTO;

public class DAOVisitRequestImpl implements DAOVisitRequest {

  //private String querySelectAdressUser;
  //private String querySelectTypesFurniture;

  /**
   * constructor of DAOVisitRequestImpl. contains queries.
   */
  public DAOVisitRequestImpl() {
    /*String querySelectAdressUser =
        "SELECT a.address_id, a.street, a.building_number, a.unit_number, a.postcode, a.commune,"
            + "a.country FROM project.addresses a, project.users u WHERE u.user_id = ? "
            + "AND u.address = a.address_id;";
    String querySelectTypesFurniture = "SELECT ft.name FROM furniture_types ft;";*/
  }

  @Override
  public AddressDTO getAddressByUserId(int id) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String[] getTypesOfFurniture() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public int addPicture(String path) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public int addFurniture(FurnitureDTO furniture) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public int addVisitRequest(VisitRequestDTO visitRequest) {
    // TODO Auto-generated method stub
    return 0;
  }


}
