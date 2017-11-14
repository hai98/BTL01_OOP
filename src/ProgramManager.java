import ui.GUIMain;
import words.RunningData;

public class ProgramManager {
	public static void main(String[] args) {
		RunningData.loadData();
		System.out.println(RunningData.getCollectionList().get(0).getTopic());
		System.out.println(RunningData.searchAll("fly"));
//		RunningData.getCollectionList().get(0).resetSeen();
		GUIMain.main(args);
//		System.out.println(RunningData.searchAll("apple").get(0).isSeen());
		RunningData.saveData();
	}
}
