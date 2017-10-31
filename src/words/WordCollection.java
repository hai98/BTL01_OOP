package words;

import java.util.HashMap;
import java.util.Map;

/**
 * Class biểu diễn một đối tượng bộ từ vựng theo chủ đề
 */
public class WordCollection {
	private String topic;
	private String pathToExcel;
	private int size;
	private Map<String, Word> wordList;

	/**
	 * Khởi tạo bộ từ không tham số
	 */
	public WordCollection(){
		topic = "no name";
		size = 0;
		wordList = null;
	}

	/**
	 * Khởi tạo bộ từ có tham số
	 * @param topic chủ đề của bộ từ
	 * @param wordList HasMap chứa các từ
	 */
	public WordCollection(String topic, Map<String, Word> wordList) {
		this.topic = topic;
		this.wordList = wordList;
		size = this.wordList.size();
	}

	/**
	 * Thêm một từ vựng vào bộ từ
	 * @param w từ vựng
	 */
	public void putWord(Word w){
		if(wordList.containsKey(w.getEn())) return;
		wordList.put(w.getEn(), w);
		++size;
	}

	/**
	 * Lấy một từ vựng chứa từ khoá
	 * @param en từ khóa tiếng anh
	 * @return từ vựng
	 */
	public Word getWord(String en){
		if(wordList != null)
			return wordList.get(en);
		return null;
	}

	/**
	 * Lấy số lượng từ trong bộ từ
	 * @return số lượng từ
	 */
	public int getSize(){
		return size;
	}

	/**
	 * Lấy danh sách từ trong bộ từ
	 * @return danh sách từ
	 */
	public Map<String, Word> getWordList() {
		return wordList;
	}

	public void deleteWord(String key){
		wordList.remove(key);
		size = wordList.size();
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}
}
