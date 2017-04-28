package homework8;

public class Train implements Comparable<Train>{
	

	private int number; // 班次
	private String type; // 車種
	private String start; // 出發地
	private String dest; // 目的地
	private double price; // 價錢

	public Train(int number, String type, String start, String dest, double price) {
		setNumber(number);
		setType(type);
		setStart(start);
		setDest(dest);
		setPrice(price);
	}
	
	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getDest() {
		return dest;
	}

	public void setDest(String dest) {
		this.dest = dest;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	
	// for TreeSet
	
	public int compareTo(Train train){
		if (this.number > train.number){
			return -1;
		}else if (this.number == train.number){
			return 0;
		}else {
			return 1;
		}
	}

	
	// for HashSet
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + number;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Train other = (Train) obj;
		if (number != other.number)
			return false;
		return true;
	}
	
	
}
