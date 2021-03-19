package be.vinci.pae.domain;

public class PictureImpl implements Picture{
	
	private int id;
	private String name;
	private boolean isVisibleForEveryone;
	//TODO replace String par Furniture
	private String furniture;
	private boolean isAScrollingPicture;
	
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
		return isVisibleForEveryone;
	}
	public void setVisibleForEveryone(boolean isVisibleForEveryone) {
		this.isVisibleForEveryone = isVisibleForEveryone;
	}
	//TODO replace String par Furniture
	public String getFurniture() {
		return furniture;
	}
	//TODO replace String par Furniture
	public void setFurniture(String furniture) {
		this.furniture = furniture;
	}
	public boolean isAScrollingPicture() {
		return isAScrollingPicture;
	}
	public void setAScrollingPicture(boolean isAScrollingPicture) {
		this.isAScrollingPicture = isAScrollingPicture;
	}
	
	

}
