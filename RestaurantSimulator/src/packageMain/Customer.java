package packageMain;

public class Customer {
	
	private Edible food;
	private Edible drink;
	private int patience;
	private boolean foodDone;
	private boolean drinkDone;

	public Customer(Edible food, Edible drink) {
		super();
		this.food = food;
		this.drink = drink;
		this.patience = 10;
		this.foodDone = false;
		this.drinkDone = false;
	}

	public boolean isFoodDone() {
		return foodDone;
	}

	public void setFoodDone(boolean foodDone) {
		this.foodDone = foodDone;
	}
	
	public String getFoodDone() {
		return foodDone ? "v" : " ";
	}

	public boolean isDrinkDone() {
		return drinkDone;
	}

	public void setDrinkDone(boolean drinkDone) {
		this.drinkDone = drinkDone;
	}
	
	public String getDrinkDone() {
		return drinkDone ? "v" : " ";
	}

	public Edible getFood() {
		return food;
	}

	public Edible getDrink() {
		return drink;
	}

	public int getPatience() {
		return patience;
	}
	
	public void decreasePatience() {
		patience--;
	}
}
