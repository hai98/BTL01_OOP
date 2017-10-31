import ui.GUIMain;
import words.RunningData;
import words.Search;

public class ProgramManager {
	public static void main(String[] args) {
		RunningData.loadData();
		System.out.println(RunningData.getCollectionList().get(0).getTopic());
		RunningData.addCollection("res/voc/animal.xlsx");
//		System.out.println(Search.searchAll("apple"));
		System.out.println(RunningData.getCollectionList().get(1).getTopic());
		GUIMain.main(args);
//		System.out.println(RunningData.getCollectionList().get(0).getSize());
	}
}
