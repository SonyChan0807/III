package homework4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Score {
	// 每次小成績List
	
	private List<Integer> score = new ArrayList<>();

	public Score(int... scores) {
		for (int s : scores) {
			this.score.add(s);
		}
	}

	public int findMax() {    			//尋找最高分
		return Collections.max(score);
	}

	public int getSingleScore(int index) { //取得個人分數
		return score.get(index);
	}

	public int getSize() {            //取得幾位同學
		return score.size();
	}
	

}
