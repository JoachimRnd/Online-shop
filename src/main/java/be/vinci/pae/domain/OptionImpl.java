package be.vinci.pae.domain;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class OptionImpl implements Option {

  private int id;
  private int buyerId;
  private int furnitureId;
  private int duration;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getBuyerId() {
    return buyerId;
  }

  public void setBuyerId(int buyerId) {
    this.buyerId = buyerId;
  }

  public int getFurnitureId() {
    return furnitureId;
  }

  public void setFurnitureId(int furnitureId) {
    this.furnitureId = furnitureId;
  }

  public int getDuration() {
    return duration;
  }

  public void setDuration(int duration) {
    this.duration = duration;
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  private LocalDate date;
  private int status;

}
