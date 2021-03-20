package be.vinci.pae.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PictureImpl implements Picture {

  private int id;
  private String name;
  private boolean visibleForEveryone;
  private Furniture furniture;
  private boolean scrollingPicture;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean isVisibleForEveryone() {
    return visibleForEveryone;
  }

  public void setVisibleForEveryone(boolean isVisibleForEveryone) {
    this.visibleForEveryone = isVisibleForEveryone;
  }

  public Furniture getFurniture() {
    return furniture;
  }

  public void setFurniture(Furniture furniture) {
    this.furniture = furniture;
  }

  public boolean isAScrollingPicture() {
    return scrollingPicture;
  }

  public void setAScrollingPicture(boolean scrollingPicture) {
    this.scrollingPicture = scrollingPicture;
  }



}
