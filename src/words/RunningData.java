package words;

import io.ImportVoc;

import java.util.ArrayList;
import java.util.List;

/**
 * Class chứa dữ liệu hoạt động của chương trình
 */

public class RunningData {
	private static List<WordCollection> collectionList = new ArrayList<>(10);
//	private static HashSet<String> suggestionList;

//	public static HashSet<String> getSuggestionList() {
//		return suggestionList;
//	}

	/**
	 * Nạp dữ liệu vào chương trình
	 */
	public static void loadData() {
		collectionList.add(ImportVoc.loadExcelFile("res/voc/fruit.xlsx"));
//		suggestionList = ImportVoc.getSuggestion();
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
		collectionList.add(ImportVoc.loadExcelFile(xlsxPath));
	}
}
