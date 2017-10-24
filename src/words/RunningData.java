package words;

import java.util.LinkedList;
import java.util.List;

public class RunningData {
	private static List<WordCollection> collectionList;
	public static void loadData() throws Exception{
		collectionList = new LinkedList<WordCollection>();
		collectionList.add(new WordCollection("fruit","res/voc/fruit.xlsx"));
	}

	public static List<WordCollection> getCollectionList() {
		return collectionList;
	}

	private static void setCollectionList(List<WordCollection> collectionList) {
		RunningData.collectionList = collectionList;
	}
}
