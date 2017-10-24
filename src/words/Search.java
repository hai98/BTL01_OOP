package words;

import java.util.List;

public class Search {
	public static Word searchAll(String key){
		List<WordCollection> list = RunningData.getCollectionList();
		for (WordCollection i : list){
			if(i.get(key)!=null)
				return i.get(key);
		}
		return null;
	}
}
