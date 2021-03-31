package be.vinci.pae.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.sql.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class OptionImpl implements Option {

  private int id;
  private UserDTO buyer;
  private FurnitureDTO furniture;
  private int duration;
  private Date date;
  private String status;

  @Override
  public int getId() {
    return id;
  }

  @Override
  public void setId(int id) {
    this.id = id;
  }

  @Override
  public UserDTO getBuyer() {
    return buyer;
  }

  @Override
  public void setBuyer(UserDTO buyer) {
    this.buyer = buyer;
  }

  @Override
  public FurnitureDTO getFurniture() {
    return furniture;
  }

  @Override
  public void setFurniture(FurnitureDTO furniture) {
    this.furniture = furniture;
  }

  @Override
  public int getDuration() {
    return duration;
  }

  @Override
  public void setDuration(int duration) {
    this.duration = duration;
  }

  @Override
  public Date getDate() {
    return date;
  }

  @Override
  public void setDate(Date date) {
    this.date = date;
  }

  @Override
  public String getStatus() {
    return status;
  }

  @Override
  public void setStatus(String status) {
    this.status = status;
  }
}
