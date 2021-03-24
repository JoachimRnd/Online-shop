package be.vinci.pae.domain;

import java.time.LocalDate;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = OptionImpl.class)
public interface OptionDTO {

  public int getId();

  public void setId(int id);

  public int getBuyerId();

  public void setBuyerId(int id);

  public int getFurnitureId();

  public void setFurnitureId(int id);

  public int getDuration();

  public void setDuration(int duration);

  public LocalDate getDate();

  public void setDate(LocalDate date);

  public int getStatus();

  public void setStatus(int status);

}
