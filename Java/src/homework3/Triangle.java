package homework3;

import java.util.Arrays;

public class Triangle {

	double max;
	double med;
	double min;

	/*List<Double> list = new ArrayList<>();

	public Triangle(double[] input) {
		this.list = DoubleStream.of(input).boxed().collect(Collectors.toList()); // lambda
		list.sort(null);
		this.max = list.get(2);
		this.med = list.get(1);
		this.min = list.get(0);

	}*/

	public Triangle(double[] input) {
		Arrays.sort(input); //先排序
		this.max = input[2];
		this.med = input[1];
		this.min = input[0];
	}

	Boolean isTriangle() {
		if (max >= 0 && med >= 0 && min >= 0) {
			if (max + med > min && max + min > med && med + min > max) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	String getTriangleType() {

		if (max == med && min == med) {
			return "正三角形";
		} else if (max == med || min == med) {
			return "等腰三角形";
		} else {
			return "其他三角形";
		}
	}

	String getRightTri() {
		double squardSum = Math.pow(med, 2) + Math.pow(min, 2);
		double squard = Math.pow(max, 2);

		if (squard == squardSum) {
			return "直角三角形";
		} else {
			return "不是直角三角形";
		}
	}

}
