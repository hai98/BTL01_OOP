package words;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Class biểu diễn một đối tượng bộ từ vựng theo chủ đề
 */
public class WordCollection {
	private String topic;
	private String fileName;
	private Map<String, Word> wordList;

	/**
	 * Khởi tạo bộ từ mặc định
	 */
	public WordCollection(){
		topic = "no name";
		wordList = new HashMap<>();
	}

	public WordCollection(String topic){
		this();
		this.topic = topic;
	}

	/**
	 * Khởi tạo bộ từ có tham số
	 * @param topic chủ đề của bộ từ
	 * @param wordList HashMap chứa các từ
	 */
	public WordCollection(String topic, Map<String, Word> wordList) {
		this.topic = topic;
		this.wordList = wordList;
	}

	/**
	 * Thêm một từ vựng vào bộ từ
	 * @param w từ vựng
	 */
	public void putWord(Word w){
		wordList.put(w.getEn(), w);
	}

	/**
	 * Lấy một từ vựng chứa từ khoá
	 * @param en từ khóa tiếng anh
	 * @return từ vựng
	 */
	public Word getWord(String en){
		if(en != null)
			return wordList.get(en);
		return null;
	}

	/**
	 * Lấy số lượng từ trong bộ từ
	 * @return số lượng từ
	 */
	public int getSize(){
		return wordList.size();
	}

	/**
	 * Lấy danh sách từ trong bộ từ
	 * @return danh sách từ
	 */
	public Map<String, Word> getWordList() {
		return wordList;
	}

	/**
	 * Xoá từ trong bộ từ
	 * @param key từ cần xoá
	 */
	public void deleteWord(String key){
		wordList.remove(key);
	}

	/**
	 * Lấy chủ đề bộ từ
	 * @return chủ đề bộ từ
	 */
	public String getTopic() {
		return topic;
	}

	/**
	 * Đặt chủ đề bộ từ
	 * @param topic chủ đề muốn đặt
	 */
	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public String toString() {
		return topic;
	}

	/**
	 * Kiểm tra từ đã tồn tại chưa
	 * @param key từ
	 * @return true nếu đã tồn tại, ngược lại false
	 */
	public boolean containsWord(String key){
		return wordList.containsKey(key.trim().toLowerCase());
	}

	public void resetSeen(){
		List<Word> list = new LinkedList<>(wordList.values());
		for (Word i : list){
			i.setSeen(false);
		}
		list = null;
	}
}
