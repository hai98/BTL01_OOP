import ui.GUIMain;
import words.RunningData;
import words.Search;

public class ProgramManager {
	public static void main(String[] args) throws Exception{
		RunningData.loadData();
//		System.out.println(Search.searchAll("apple"));
		GUIMain.main(args);
	}
}
