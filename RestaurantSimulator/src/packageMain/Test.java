package packageMain;

public class Test implements Runnable{
	Thread thread;

	public Test() {
		// TODO Auto-generated constructor stub
	}
	
	public void tes() {
		thread = new Thread();
		thread.start();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true) {
			System.out.println("Benis");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
