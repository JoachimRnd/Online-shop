package be.vinci.pae.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = PictureImpl.class)
public interface PictureDTO {

  int getId();

  void setId(int id);

  String getName();

  void setName(String name);

  boolean isVisibleForEveryone();

  void setVisibleForEveryone(boolean isVisibleForEveryone);

  Furniture getFurniture();

  void setFurniture(Furniture furniture);

  boolean isAScrollingPicture();

  void setAScrollingPicture(boolean isAScrollingPicture);

}
