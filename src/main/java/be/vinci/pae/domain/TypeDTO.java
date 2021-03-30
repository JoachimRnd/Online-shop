package be.vinci.pae.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = TypeImpl.class)
public interface TypeDTO {
  // TODO for later

  int getId();

  void setId(int id);

  String getName();

  void setName(String name);

}
