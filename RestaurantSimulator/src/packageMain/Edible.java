package packageMain;

public class Edible {
	
	private String name;
	private int randNum;
	private int price;
	public Edible(String name, int randNum, int price) {
		super();
		this.name = name;
		this.randNum = randNum;
		this.price = price;
	}
	public String getName() {
		return name;
	}
	public int getPrice() {
		return price;
	}

}
