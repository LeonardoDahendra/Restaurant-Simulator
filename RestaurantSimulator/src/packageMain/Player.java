package packageMain;

public class Player {
	
	private String username;
	private String password;
	private int score;

	public Player(String username, String password, int score) {
		super();
		this.username = username;
		this.password = password;
		this.score = score;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public int getScore() {
		return score;
	}
	
	public void addScore(int add) {
		score += add;
	}

}
