import ui.GUIMain;
import words.RunningData;
import words.Word;

public class ProgramManager {
	public static void main(String[] args) {
		RunningData.loadData();
		System.out.println(RunningData.getCollectionList().get(0).getTopic());
//		RunningData.addCollection("res/voc/animal.xlsx");
		System.out.println(RunningData.searchAll("apple"));
		RunningData.getCollectionList().get(0).putWord(new Word("le", "hai", "name", false));
		System.out.println(RunningData.searchAll("le"));
//		System.out.println(RunningData.getCollectionList().get(1).getTopic());
		GUIMain.main(args);
//		System.out.println(RunningData.getCollectionList().get(0).getSize());
//		System.out.println(RunningData.searchAll("apple").get(0).isSeen());
	}
}
