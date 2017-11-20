package words;

/**
 * Class biểu diễn một đối tượng từ
 */
public class Word implements Comparable<Word> {
	private String en;
	private String vi;
	private String topic;
	private String imgPath;
	private boolean seen;
	private int level;

	/**
	 * Khởi tạo mặc định
	 */
	private Word(){
		en = "en";
		vi = "vi";
		seen = false;
		imgPath = null;
		level = 1;
	}

	/**
	 * Khởi tạo từ mới với tham số
	 * @param en từ tiếng anh
	 * @param vi nghĩa của từ trong tiếng việt
	 * @param topic chủ đề
	 * @param seen đã học chưa
	 */
	public Word(String en, String vi, String topic, boolean seen){
		this();
		setEn(en);
		setVi(vi);
		setTopic(topic);
		this.seen = seen;
	}

	/**
	 * Khởi tạo từ mới với tham số
	 * @param en từ tiếng anh
	 * @param vi nghĩa của từ trong tiếng việt
	 * @param topic chủ đề
	 * @param seen đã học chưa
	 * @param imgPath đường dẫn ảnh minh hoạ
	 */
	public Word(String en, String vi,String topic, boolean seen, String imgPath){
		this(en, vi,topic,seen);
		this.imgPath = imgPath;
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
		this.en = en.trim().toLowerCase();
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
		this.vi = vi.trim();
	}

	/**
	 * Lấy chủ đề của từ
	 * @return chủ đề
	 */
	public String getTopic() {
		return topic;
	}

	/**
	 * Đặt chủ đề cho từ
	 * @param topic chủ đề
	 */
	public void setTopic(String topic) {
		this.topic = topic.trim();
	}

	/**
	 * Lấy đường dẫn ảnh minh hoạ
	 * @return đường dẫn ảnh
	 */
	public String getImgPath() {
		return imgPath;
	}

	/**
	 * Đặt đường dẫn ảnh minh hoạ cho từ
	 * @param imgPath đường dẫn ảnh
	 */
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
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
		return String.format("%s: %s", en, vi);
	}

	/**
	 * So sánh một từ với 1 đối tượng
	 * @param obj đối tượng
	 * @return true nếu bằng nhau, false nếu không
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (obj == this) return true;
		if (obj.getClass() != this.getClass()) return false;
		else {
			return ((Word) obj).vi.compareTo(this.vi) == 0;
		}
	}

	/**
	 * Lấy level của từ
	 * @return level
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * Đặt level cho từ
	 * @param level level
	 */
	public void setLevel(int level) {
		this.level = level;
	}

	/**
	 * So sánh level với từ khác
	 * @param o từ khác
	 * @return kết quả so sánh
	 */
	@Override
	public int compareTo(Word o) {
		if (o == this) return 0;
		return o.level - this.level;
	}
}
