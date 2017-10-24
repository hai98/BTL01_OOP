package words;

import io.ImportVoc;
import java.util.Map;

/**
 * Class biểu diễn một đối tượng bộ từ vựng theo chủ đề
 */
public class WordCollection {
	private String subject;
	private String pathToExcel;
	private int size;
	private Map<String, Word> wordList;

	/**
	 * Khởi tạo bộ từ không tham số
	 */
	public WordCollection(){
		subject = "no name";
		size = 0;
		wordList = null;
	}

	/**
	 * Khởi tạo bộ từ có tham số
	 * @param subject chủ đề của bộ từ
	 * @param pathToExcel đường dẫn đến file excel chứa bô từ
	 */
	public WordCollection(String subject, String pathToExcel) {
		this();
		this.subject = subject;
		wordList = ImportVoc.toWordHashMap(pathToExcel);
		size = wordList.size();
	}

	/**
	 * Thêm một từ vựng vào bộ từ
	 * @param w từ vựng
	 */
	public void put(Word w){
		if(wordList.containsKey(w.getEn())) return;
		wordList.put(w.getEn(), w);
		++size;
	}

	/**
	 * Lấy một từ vựng chứa từ khoá
	 * @param en từ khóa tiếng anh
	 * @return từ vựng
	 */
	public Word get(String en){
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

//	public void setWordList(Map<String, Word> wordList) {
//		this.wordList = wordList;
//	}
}
