package homework9;

public class TestEatingContest {

	public static void main(String[] args) {

		String contestant1 = "饅頭人";
		String contestant2 = "詹姆士";

		ContestRunnable.setPlayerNum(2);
		ContestRunnable cr1 = new ContestRunnable(contestant1, 0);
		Thread t1 = new Thread(cr1);
		ContestRunnable cr2 = new ContestRunnable(contestant2, 0);
		Thread t2 = new Thread(cr2);

		System.out.println("------大胃王比賽開始------");
		t1.start();
		t2.start();
	}

}

class ContestRunnable implements Runnable {

	private static int finishNum; // 完成各數
	private static int playerNum; // 參賽人數
	private String contestant;
	private int count;

	static {
		finishNum = 0;
	}

	public ContestRunnable(String contestant, int count) {
		this.contestant = contestant;
		this.count = count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public static void setPlayerNum(int playerNum) {
		ContestRunnable.playerNum = playerNum;
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

		finishNum++; // 參賽者吃完了
		System.out.println(contestant + "吃完了!! YA~");

		if (finishNum == playerNum) { // 所有參賽者吃完了
			System.out.println("------大胃王比賽結束------");   // 最後一個結束後比賽結束
		}

	}

}
