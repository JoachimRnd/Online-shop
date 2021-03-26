package be.vinci.pae.domain;

import java.util.List;

public interface FurnitureUCC {
  
  List<FurnitureDTO> getAllFurniture();

  FurnitureDTO addFurniture(FurnitureDTO furniture);
}
