package be.vinci.pae.domain;

import java.time.LocalDate;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = OptionImpl.class)
public interface OptionDTO {

  int getId();

  void setId(int id);

  int getBuyerId();

  void setBuyerId(int id);

  int getFurnitureId();

  void setFurnitureId(int id);

  int getDuration();

  void setDuration(int duration);

  LocalDate getDate();

  void setDate(LocalDate date);

  int getStatus();

  void setStatus(int status);

}
