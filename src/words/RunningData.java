package words;

import io.Import;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Class chứa dữ liệu hoạt động của chương trình
 */

public class RunningData {
	private static List<WordCollection> collectionList = new ArrayList<>(10);
	private static List<String> topics = new LinkedList<>();
//	private static HashSet<String> suggestionList;

//	public static HashSet<String> getSuggestionList() {
//		return suggestionList;
//	}

	/**
	 * Nạp dữ liệu vào chương trình
	 */
	public static void loadData() {
		addCollection("res/voc/fruit.xlsx");
		addCollection("res/voc/animal.xlsx");
//		suggestionList = Import.getSuggestion();
	}

	/**
	 * Lấy danh sách các bộ từ vựng
	 * @return danh sách các bộ từ vựng
	 */
	public static List<WordCollection> getCollectionList() {
		return collectionList;
	}

	private static void setCollectionList(List<WordCollection> collectionList) {
		RunningData.collectionList = collectionList;
	}

	public static void addCollection(String xlsxPath){
		WordCollection t = Import.readExcelFile(xlsxPath);
		collectionList.add(t);
		topics.add(t.getTopic());
	}

	/**
	 * Tìm từ trong tất cả các bộ từ vựng
	 * @param key từ khóa tìm kiếm (tiếng Anh)
	 * @return List các từ vựng
	 */
	public static List<Word> searchAll(String key){
		key = key.trim().toLowerCase();
		List<Word> result = new LinkedList<>();
		for (WordCollection i : collectionList){
			if(i.getWord(key)!=null)
				result.add(i.getWord(key));
		}
		return result;
	}

	public static List<String> getTopics() {
		return topics;
	}

	public static void addWord(Word w){
		int i = topics.indexOf(w.getTopic());
		collectionList.get(i).putWord(w);
	}

	public static void deleteWord(String key){
		for (WordCollection i : collectionList){
			if(i.getWord(key)!=null)
				i.deleteWord(key);
		}
	}
}
