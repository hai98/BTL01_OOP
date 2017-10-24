package words;

/**
 * Class biểu diễn một đối tượng từ vựng
 */
public class Word {
	private String en;
	private String vi;
//	private String pronun;
//	private String imgPath;
	private boolean seen;

	/**
	 * Khởi tạo mặc định
	 */
	private Word(){
		en = "en";
		vi = "vi";
		seen = false;
	}

	/**
	 * Khởi tạo từ vựng với tham số
	 * @param en từ trong tiếng anh
	 * @param vi nghĩa của từ trong tiếng việt
	 */
	public Word(String en, String vi){
		this();
		this.en = en;
		this.vi = vi;
	}

	/**
	 * Lấy từ tiếng anh
	 * @return từ tiếng anh
	 */
	public String getEn() {
		return en;
	}

	/**
	 * Đặt từ tiếng anh
	 * @param en từ tiếng anh
	 */
	public void setEn(String en) {
		this.en = en;
	}

	/**
	 * Lấy nghĩa tiếng việt của từ
	 * @return nghĩa tiếng việt
	 */
	public String getVi() {
		return vi;
	}

	/**
	 * Đặt nghĩa tiếng việt cho từ
	 * @param vi nghiã tiếng việt
	 */
	public void setVi(String vi) {
		this.vi = vi;
	}

	/**
	 * Từ vựng đã học chưa
	 * @return true nếu đã học, ngược lại thì false
	 */
	public boolean isSeen() {
		return seen;
	}

	/**
	 * Đặt trạng thái đã học
	 * @param seen đã học chưa (true/false)
	 */
	public void setSeen(boolean seen) {
		this.seen = seen;
	}

	/**
	 * Biểu diễn từ dưới dạng Anh - Việt
	 * @return dạng Anh - Việt
	 */
	@Override
	public String toString(){
		return String.format("%s:%n%s", en, vi);
	}

}
