import ui.GUIMain;
import words.RunningData;

/**
 * Class quản lý chương trình
 */
public class ProgramManager {
	/**
	 * Phương thức main
	 * @param args tham số dòng lệnh
	 */
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
