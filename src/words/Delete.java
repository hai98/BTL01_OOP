package words;

import java.util.List;

public class Delete {
	public static void deleteWord(String key){
		List<WordCollection> list = RunningData.getCollectionList();
		for (WordCollection i : list){
			if(i.getWord(key)!=null)
				i.deleteWord(key);
		}
	}
}
