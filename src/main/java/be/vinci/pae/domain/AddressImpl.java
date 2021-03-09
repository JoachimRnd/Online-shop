package be.vinci.pae.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddressImpl implements Address {

  private String street;
  private String buildingNumber;
  private String UnitNumber;
  private String postcode;
  private String commune;
  private String country;

  @Override
  public String getStreet() {
    return street;
  }

  @Override
  public void setStreet(String street) {
    this.street = street;
  }

  @Override
  public String getBuildingNumber() {
    return buildingNumber;
  }

  @Override
  public void setBuildingNumber(String buildingNumber) {
    this.buildingNumber = buildingNumber;
  }

  @Override
  public String getUnitNumber() {
    return UnitNumber;
  }

  @Override
  public void setUnitNumber(String unitNumber) {
    this.UnitNumber = unitNumber;
  }

  @Override
  public String getPostcode() {
    return postcode;
  }

  @Override
  public void setPostcode(String postcode) {
    this.postcode = postcode;
  }

  @Override
  public String getCommune() {
    return commune;
  }

  @Override
  public void setCommune(String commune) {
    this.commune = commune;
  }

  @Override
  public String getCountry() {
    return country;
  }

  @Override
  public void setCountry(String country) {
    this.country = country;
  }

  @Override
  public String toString() {
    return "AddressImpl [street=" + street + ", buildingNumber=" + buildingNumber + ", UnitNumber="
        + UnitNumber + ", postcode=" + postcode + ", commune=" + commune + ", country=" + country
        + "]";
  }



}
