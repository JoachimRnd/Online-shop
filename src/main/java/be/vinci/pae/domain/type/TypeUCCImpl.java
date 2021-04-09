package be.vinci.pae.domain.type;

import java.util.List;
import be.vinci.pae.services.DalServices;
import be.vinci.pae.services.type.DAOType;
import jakarta.inject.Inject;

public class TypeUCCImpl implements TypeUCC {

  @Inject
  private DAOType daoType;

  @Inject
  private DalServices dalServices;

  @Override
  public List<TypeDTO> getFurnitureTypes() {
    try {
      List<TypeDTO> listTypes = this.daoType.selectFurnitureTypes();
      return listTypes;
    } finally {
      this.dalServices.closeConnection();
    }
  }

}
