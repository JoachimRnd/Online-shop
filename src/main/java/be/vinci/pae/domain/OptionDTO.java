package be.vinci.pae.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.Date;

@JsonDeserialize(as = OptionImpl.class)
public interface OptionDTO {

  int getId();

  void setId(int id);

  UserDTO getBuyer();

  void setBuyer(UserDTO buyer);

  FurnitureDTO getFurniture();

  void setFurniture(FurnitureDTO furniture);

  int getDuration();

  void setDuration(int duration);

  Date getDate();

  void setDate(Date date);

  String getStatus();

  void setStatus(String status);

}
