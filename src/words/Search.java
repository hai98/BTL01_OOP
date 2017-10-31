package words;

import java.util.LinkedList;
import java.util.List;

/**
 * Class mô tả việc tìm kiếm
 */
public class Search {
	/**
	 * Tìm từ trong tất cả các bộ từ vựng
	 * @param key từ khóa tìm kiếm (tiếng Anh)
	 * @return List các từ vựng
	 */
	public static List<Word> searchAll(String key){
		List<WordCollection> list = RunningData.getCollectionList();
		List<Word> result = new LinkedList<>();
		for (WordCollection i : list){
			if(i.getWord(key)!=null)
				result.add(i.getWord(key));
		}
		return result;
	}
}
