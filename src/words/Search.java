package words;

import java.util.List;

/**
 * Class mô tả việc tìm kiếm
 */
public class Search {
	/**
	 * Tìm từ trong tất cả các bộ từ vựng
	 * @param key từ khóa tìm kiếm (tiếng Anh)
	 * @return từ vựng nếu có, ngược lại trả về null
	 */
	public static Word searchAll(String key){
		List<WordCollection> list = RunningData.getCollectionList();
		for (WordCollection i : list){
			if(i.getWord(key)!=null)
				return i.getWord(key);
		}
		return null;
	}
}
