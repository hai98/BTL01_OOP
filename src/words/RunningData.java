package words;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Class chứa dữ liệu hoạt động của chương trình
 */

public class RunningData {
	private static List<WordCollection> collectionList;

	/**
	 * Nạp dữ liệu vào chương trình
	 */
	public static void loadData() {
		collectionList = new ArrayList<>(10);
		collectionList.add(new WordCollection("fruit","res/voc/fruit.xlsx"));
	}

	/**
	 * Lấy danh sách các bộ từ vựng
	 * @return danh sách các bộ từ vựng
	 */
	public static List<WordCollection> getCollectionList() {
		return collectionList;
	}

	/*private static void setCollectionList(List<WordCollection> collectionList) {
		RunningData.collectionList = collectionList;
	}*/
}
