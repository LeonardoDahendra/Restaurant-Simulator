package packageMain;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main implements Runnable{
	
	Scanner scan = new Scanner(System.in);
	Random rand = new Random();
	ArrayList<Player> players = new ArrayList<>();
	ArrayList<Edible> foods = new ArrayList<>();
	ArrayList<Customer> customers = new ArrayList<>();
	ArrayList<Integer> workers = new ArrayList<>();
	Thread thread;
	String userOrder;
	int curTime = 0;
	int foodAmount = 0;
	int curLife = 5;
	int decorLvl = 0;
	int adsLvl = 0;
	int resLvl = 0;
	int workerLvl = 0;
	int maxCus = 5;
	int cusChance = 10;
	int patChance = 10;
	int curPlayer;
	boolean finished;
	boolean ended;
	
	void merge(int start, int mid, int end)
	{
		Player leftArr[] = new Player[mid - start + 1];
		Player rightArr[] = new Player[end - mid];
		for (int i = start; i <= mid; i++) {
			leftArr[i - start] = players.get(i);
		}
		for (int i = mid + 1; i <= end; i++) {
			rightArr[i - mid - 1] = players.get(i);
		}
		int left = 0;
		int right = 0;
		while (left <= mid - start && right < end - mid) {
			if (leftArr[left].getScore() > rightArr[right].getScore()) {
				players.set(left + right + start, leftArr[left++]);
			}
			else {
				players.set(left + right + start, rightArr[right++]);
			}
		}
		while (left <= mid - start) {
			players.set(left + right + start, leftArr[left++]);
		}
		while (right < end - mid) {
			players.set(left + right + start, rightArr[right++]);
		}
	}
	
	void mergeSort(int start, int end) {
		if (start >= end) return;
		int mid = start + (end - start) / 2;
		mergeSort(start, mid);
		mergeSort(mid + 1, end);
		merge(start, mid, end);
	}
	
	void leaderBoard() {
		String curPlayerName = players.get(curPlayer).getUsername();
		mergeSort(0, players.size() - 1);
		System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
		System.out.println("===============================================");
		for (Player player : players) {
			if (player.getUsername().equals(curPlayerName)) {
				curPlayer = players.indexOf(player);
			}
			System.out.printf("| %-25s| Score: %-10d|\n", player.getUsername(), player.getScore());
		}
		System.out.println("===============================================");
		System.out.println("Press enter to continue...");
		scan.nextLine();
	}
	
	void mainMenu() {
		int choice = 0;
		do {
			System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
			System.out.println("===============================================");
			System.out.printf("| %-25s| Score: %-10d|\n", players.get(curPlayer).getUsername(), players.get(curPlayer).getScore());
			System.out.println("===============================================");
			System.out.println("\n1. Play game");
			System.out.println("2. View scoreboard");
			System.out.println("3. Exit");
			System.out.print(">> ");
			try {
				choice = scan.nextInt();
				scan.nextLine();
			} catch (Exception e) {
				// TODO: handle exception
				scan.nextLine();
			}
			switch (choice) {
			case 1:
				game();
				break;
			case 2:
				leaderBoard();
				break;
			}
		} while (choice != 3);
	}
	
	void game() {
		curLife = 5;
		curTime = 0;
		resLvl = 0;
		adsLvl = 0;
		decorLvl = 0;
		workerLvl = 0;
		finished = false;
		customers.clear();
		workers.clear();
		thread = new Thread(this);
		thread.start();	
		do {
			System.out.print(">> ");
			userOrder = scan.nextLine();
			if (ended) {
				break;
			}
			else if (finished) {
				if (userOrder.equals("continue")) {
					int choice = 0;
					do {
						System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
						System.out.println("===============================================");
						System.out.printf("| %-25s| Score: %-10d|\n", players.get(curPlayer).getUsername(), players.get(curPlayer).getScore());
						System.out.println("===============================================\n");
						System.out.println("1. Expand restaurant - " + (resLvl + 1) * 28500);
						if (decorLvl < 20) {
							System.out.println("2. Buy decorations   - " + (decorLvl + 1) * 35000);
						}
						else {
							System.out.println("2. Buy decorations   - MAX");
						}
						if (adsLvl < 20) {
							System.out.println("3. Purchase more ads - " + (adsLvl + 1) * 37500);
						}
						else {
							System.out.println("3. Purchase more ads - MAX");
						}
						System.out.println("4. Hire worker       - " + (workerLvl + 1) * 40000);
						System.out.println("5. Exit");
						System.out.print(">> ");
						try {
							choice = scan.nextInt();
							scan.nextLine();
						} catch (Exception e) {
							// TODO: handle exception
							scan.nextLine();
							choice = 0;
						}
						switch(choice) {
						case 1:
							if (players.get(curPlayer).getScore() >= (resLvl + 1) * 28500) {
								players.get(curPlayer).addScore(-(resLvl + 1) * 28500);
								resLvl++;
								System.out.println("Successfully expand restaurant");
							}
							else {
								System.out.println("Not enough money!");	
							}
							break;
						case 2:
							if (players.get(curPlayer).getScore() >= (decorLvl + 1) * 35000 && decorLvl < 20) {
								players.get(curPlayer).addScore(-(decorLvl + 1) * 35000);
								decorLvl++;
								System.out.println("Successfully bought decorations");
							}
							else if (decorLvl >= 20) {
								System.out.println("MAX Level");
							}
							else {
								System.out.println("Not enough money!");
							}
							break;
						case 3:
							if (players.get(curPlayer).getScore() >= (adsLvl + 1) * 37500 && adsLvl < 20) {
								players.get(curPlayer).addScore(-(adsLvl + 1) * 37500);
								adsLvl++;
								System.out.println("Successfully purchased more ads");
							}
							else if (adsLvl >= 20) {
								System.out.println("MAX Level!");
							}
							else {
								System.out.println("Not enough money!");
							}
							break;
						case 4:
							if (players.get(curPlayer).getScore() >= (workerLvl + 1) * 40000) {
								players.get(curPlayer).addScore(-(workerLvl + 1) * 40000);
								addWorker();
								System.out.println("Successfully hire new worker");
							}
							else {
								System.out.println("Not enough money!");	
							}
							break;
						case 5:
							break;
						default:
							System.out.println("Enter between 1 - 5");
							break;
						}
						System.out.println("Press [Enter] to continue");
						scan.nextLine();
					} while (choice != 5);
					saveGame();
					finished = false;
					thread = new Thread(this);
					thread.start();
				}
				else if (userOrder.equals("exit")) {
					break;
				}
				else {
					System.out.println("Press type exit or continue");
				}
			}
			else{
				finishOrder();
			}
		} while (!ended);
	}
	
	
	void login() {
		curPlayer = -1;
		String username;
		String pass;
		System.out.print("Enter your username: ");
		username = scan.nextLine();
		System.out.print("Enter your password: ");
		pass = scan.nextLine();
		for (Player player : players) {
			if (player.getUsername().equals(username) && player.getPassword().equals(pass)) {
				curPlayer = players.indexOf(player);
				break;
			}
		}
		if (curPlayer == -1) {
			System.out.println("Player not found!");
			System.out.println("Press enter to continue...");
			scan.nextLine();
		}
		else {
			mainMenu();
		}
	}
	
	void regis() {
		String username;
		String pass;
		boolean canUse = false;
		do {
			canUse = true;
			System.out.print("Input a unique username [5..20]: ");
			username = scan.nextLine();
			for (Player player : players) {
				if (player.getUsername().equals(username)) {
					System.out.println("Username has been taken, choose another username");
					canUse = false;
					break;
				}
			}
			if (username.length() < 5 || username.length() > 20) canUse = false;
		} while (!canUse);
		do {
			System.out.print("Input your password [8..20]: ");
			pass = scan.nextLine();
		} while (pass.length() < 8 || username.length() > 20);
		players.add(new Player(username, pass, 0));
		saveGame();
	}
	
	void saveGame() {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter("player.txt"));
			for (Player player : players) {
				writer.write(player.getUsername() + "#" + player.getPassword() + "#" + player.getScore() + "\n");
			}
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public Main() {
		// TODO Auto-generated constructor stub
		thread = new Thread(this);
		try {
			BufferedReader reader = new BufferedReader(new FileReader("player.txt"));
			String line;
			while ((line = reader.readLine()) != null) {
				String temp[] = line.split("#");
				players.add(new Player(temp[0], temp[1], Integer.parseInt(temp[2])));
			}
			reader.close();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			//empti
		}
		try {
			BufferedReader reader = new BufferedReader(new FileReader("food.txt"));
			String line;
			while ((line = reader.readLine()) != null) {
				String temp[] = line.split("#");
				foods.add(new Edible(temp[0], Integer.parseInt(temp[1]), Integer.parseInt(temp[2])));
			}
			reader.close();
			foodAmount = foods.size();
			reader = new BufferedReader(new FileReader("drink.txt"));
			while ((line = reader.readLine()) != null) {
				String temp[] = line.split("#");
				foods.add(new Edible(temp[0], Integer.parseInt(temp[1]), Integer.parseInt(temp[2])));
			}
			reader.close();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			//empti
		}
		int choice = 0;
		do {
			System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
			System.out.println("1. Login");
			System.out.println("2. Register");
			System.out.println("3. Exit");
			System.out.print(">> ");
			try {
				choice = scan.nextInt();
				scan.nextLine();
			} catch (Exception e) {
				// TODO: handle exception
				scan.nextLine();
			}
			switch(choice) {
			case 1:
				login();
				break;
			case 2:
				regis();
				break;
			}
		} while (choice != 3);
	}

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Main();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		updateInfo();
		finished = false;
		ended = false;
		curTime = 0;
		while (!finished) {
			curTime++;
			if (curTime >= 60) {
				finished = true;
				continue;
			}
			orderDone();
			reducePatience();
			getNewCustomer();
			workerWork();
			System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
			System.out.println("===============================================");
			System.out.printf("| Time: %03d | Life: %d | Score: %-15d|\n", curTime, curLife, players.get(curPlayer).getScore());
			System.out.println("===============================================\n");
			if (workerLvl != 0) {
				System.out.println("=========================");
				System.out.println("| Worker | Cooking      |");
				System.out.println("=========================");
				for (int i = 0; i < workerLvl; i++) {
					System.out.printf("| %-7d| [%-10s] |\n", i + 1, "#".repeat(workers.get(i)));
				}
				System.out.println("=========================\n");
			}
			System.out.println("====================================================================================");
			System.out.println("| No |   Patience   |           Food           |          Drink          |  Price  |");
			System.out.println("====================================================================================");
			for (int i = 0; i < maxCus; i++) {
				if (customers.size() > i) {
					Customer curCust = customers.get(i);
					System.out.printf("| %-3d| [%-10s] | %-25s| %-24s| %-8d|\n", i + 1, 
							"#".repeat(curCust.getPatience()), String.format("%s [%s]", curCust.getFood().getName(), curCust.getFoodDone()),
							String.format("%s [%s]", curCust.getDrink().getName(), curCust.getDrinkDone()), (curCust.getFood().getPrice() + curCust.getDrink().getPrice()) * curCust.getPatience() / 10);
				}
				else {
					System.out.printf("| %-3d|\t\t    |\t\t\t       |\t\t\t |\t   |\n", i + 1);
				}
			}
			System.out.println("====================================================================================");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		if (!ended) {
			System.out.println("\n\nLEVEL CLEARED");
			System.out.println("Type exit to stop playing or continue to continue...");
		}
		else {
			System.out.println("\n\nYOU LOST");
			System.out.println("Press [Enter] to continue...");
		}
	}
	
	void orderDone() {
		for (Customer cust : customers) {
			if (cust.isFoodDone() && cust.isDrinkDone()) {
				players.get(curPlayer).addScore((cust.getFood().getPrice() + cust.getDrink().getPrice()) * cust.getPatience() / 10);
				customers.remove(cust);
				break;
			}
		}
	}
	
	void finishOrder() {
		for (Customer cust : customers) {
			if (!cust.isFoodDone() && userOrder.equals(cust.getFood().getName())) {
				cust.setFoodDone(true);
				break;
			}
			else if (!cust.isDrinkDone() && userOrder.equals(cust.getDrink().getName())) {
				cust.setDrinkDone(true);
				break;
			}
		}
	}
	
	synchronized void finishOrderWorker() {
		for (Customer cust : customers) {
			if (!cust.isFoodDone()) {
				cust.setFoodDone(true);
				break;
			}
			else if (!cust.isDrinkDone()) {
				cust.setDrinkDone(true);
				break;
			}
		}
	}
	
	int getPercent() {
		return rand.nextInt(100) + 1;
	}
	
	void workerWork() {
		for (int i = 0; i < workerLvl; i++) {
			if (getPercent() <= 20) {
				workers.set(i, workers.get(i) - 1);
				if (workers.get(i) <= 0) {
					finishOrderWorker();
					workers.set(i, 10);
				}
			}
		}
	}
	
	void getNewCustomer() {
		if (getPercent() <= cusChance && customers.size() < maxCus) {
			int food = rand.nextInt(foodAmount);
			int drink = rand.nextInt(foods.size() - foodAmount) + foodAmount;
			customers.add(new Customer(foods.get(food), foods.get(drink)));
		}
	}
	
	void reducePatience() {
		int cusToRemove = -1;
		for (Customer cust : customers) {
			if (getPercent() <= patChance) {
				cust.decreasePatience();
				if (cust.getPatience() <= 0 && cusToRemove == -1) {
					cusToRemove = customers.indexOf(cust);
				}
			}
		}
		if (cusToRemove != -1) {
			curLife--;
			customers.remove(cusToRemove);
			if (curLife <= 0) {
				endGame();
			}
		}
	}
	
	void endGame() {
		finished = true;
		ended = true;
		saveGame();
	}
	
	void addWorker() {
		workerLvl++;
		workers.add(10);
	}
	
	void updateInfo() {
		maxCus = resLvl + 5;
		cusChance = adsLvl * 5 + 15;
		patChance = decorLvl * 5 + 20;
	}

}
