package homework9;

public class TestEatingContest2 {
	public static void main(String args[]) {
		String player1 = "饅頭人";
		String player2 = "詹姆士";
		
		System.out.println("------大胃王比賽開始------");
		ContestRunnable2 cr1 = new ContestRunnable2(player1, 0);
		ContestRunnable2 cr2 = new ContestRunnable2(player2, 0);
		
		try{
			cr1.t.join();
			cr2.t.join();
		}catch (InterruptedException ie){
			ie.printStackTrace();
		}
		
		System.out.println("------大胃王比賽結束------");
		
		
	}
}

class ContestRunnable2 implements Runnable {
	Thread t;

	private String contestant;
	private int count;

	public ContestRunnable2(String contestant, int count) {
		this.contestant = contestant;
		this.count = count;
		t = new Thread(this);   // Start the thread once the instance has been created.
		t.start();
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public void run() {

		while (count < 10) {
			++count;
			System.out.println(contestant + "吃第" + count + "碗飯");

			try {
				Thread.sleep((int) (Math.random() * 2501) + 500);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		System.out.println(contestant + "吃完了!! YA~");

	}
}
