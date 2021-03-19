package be.vinci.pae.domain;

public interface PictureDTO {
	
	public int getId();
	
	public void setId(int id);
	
	public String getName();
	
	public void setName(String name);
	
	public boolean isVisibleForEveryone();
	
	public void setVisibleForEveryone(boolean isVisibleForEveryone);
	
	//TODO replace String par Furniture
	public String getFurniture();
	
	//TODO replace String par Furniture
	public void setFurniture(String furniture);
	
	public boolean isAScrollingPicture(); 
	
	public void setAScrollingPicture(boolean isAScrollingPicture);

}
