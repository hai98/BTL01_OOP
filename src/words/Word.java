package words;

/**
 * Class biểu diễn một đối tượng từ vựng
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
	 * Khởi tạo từ vựng với tham số
	 * @param en từ trong tiếng anh
	 * @param vi nghĩa của từ trong tiếng việt
	 */
	public Word(String en, String vi, String topic, boolean seen){
		this();
		setEn(en);
		setVi(vi);
		setTopic(topic);
		this.seen = seen;
	}
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

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic.trim();
	}

	public String getImgPath() {
		return imgPath;
	}

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

	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (obj == this) return true;
		if (obj.getClass() != this.getClass()) return false;
		else {
			return ((Word) obj).vi.compareTo(this.vi) == 0;
		}
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	@Override
	public int compareTo(Word o) {
		if (o == this) return 0;
		return o.level - this.level;
	}
}
