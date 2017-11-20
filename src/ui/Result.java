package ui;

public class Result{
	private int n;
	private boolean result;
	private String word;
	private String userAns;
	private String rightAns;
	private static int rate= 0;

	Result(int n, boolean result, String word, String userAns, String rightAns) {
		this.n = n;
		this.result = result;
		this.word = word;
		this.userAns = userAns;
		this.rightAns = rightAns;
		if (result) ++rate;
	}

	public int getN() {
		return n;
	}

	static int getRate() {
		return rate;
	}

	static void setRate(int rate) {
		Result.rate = rate;
	}

	public boolean isResult() {
		return result;
	}

	public String getWord() {
		return word;
	}

	public String getUserAns() {
		return userAns;
	}

	public String getRightAns() {
		return rightAns;
	}

	@Override
	public String toString() {
		return n +" "+ result +" "+ word +" "+ userAns +" "+rightAns;
	}
}
