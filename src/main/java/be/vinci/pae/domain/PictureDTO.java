package be.vinci.pae.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = PictureImpl.class)
public interface PictureDTO {

  public int getId();

  public void setId(int id);

  public String getName();

  public void setName(String name);

  public boolean isVisibleForEveryone();

  public void setVisibleForEveryone(boolean isVisibleForEveryone);

  public Furniture getFurniture();

  public void setFurniture(Furniture furniture);

  public boolean isAScrollingPicture();

  public void setAScrollingPicture(boolean isAScrollingPicture);

}
