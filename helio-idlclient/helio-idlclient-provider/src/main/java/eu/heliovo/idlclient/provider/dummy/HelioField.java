package eu.heliovo.idlclient.provider.dummy;


public class HelioField {

	private String name;
	
	private String description;

	private float MyFloat;
	
	private float MyArray[];
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public float getMyFloat() {
		return MyFloat;
	}

	public void setMyFloat(float myFloat) {
		MyFloat = myFloat;
	}

	public void setMyArray(float myArray[]) {
		MyArray = myArray;
	}

	public float[] getMyArray() {
		return MyArray;
	}
}