package words;

import io.Export;
import io.Import;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;

import java.io.*;
import java.text.Normalizer;
import java.util.*;

/**
 * Class chứa dữ liệu hoạt động của chương trình
 */

public class RunningData {
	private static List<WordCollection> collectionList = new ArrayList<>(10);
	private static List<String> topics = new LinkedList<>();
	private static ObservableList<String> history = FXCollections.observableArrayList();

//	private static HashSet<String> suggestionList;

//	public static HashSet<String> getSuggestionList() {
//		return suggestionList;
//	}

	/**
	 * Nạp dữ liệu vào chương trình
	 */
	public static void loadData() {
//		suggestionList = Import.getSuggestion();
		File f = new File("res/voc");
		File[] files = f.listFiles(pathname -> pathname.getName().endsWith(".xlsx"));
		for (File i : files) {
			addCollection(i);
		}
	}

	/**
	 * Lấy danh sách các bộ từ vựng
	 * @return danh sách các bộ từ vựng
	 */
	public static List<WordCollection> getCollectionList() {
		return collectionList;
	}

	private static void setCollectionList(List<WordCollection> collectionList) {
		RunningData.collectionList = collectionList;
	}

	public static void addCollection(File fileExcel){
		WordCollection t = Import.readExcelFile(fileExcel);
		collectionList.add(t);
		topics.add(t.getTopic());
	}

	/**
	 * Tìm từ trong tất cả các bộ từ vựng
	 * @param key từ khóa tìm kiếm (tiếng Anh)
	 * @return List các từ vựng
	 */
	public static List<Word> searchAll(String key){
		key = key.trim().toLowerCase();
		List<Word> result = new LinkedList<>();
		for (WordCollection i : collectionList){
			if(i.containsWord(key))
				result.add(i.getWord(key));
		}
		return result;
	}

	public static List<String> getTopics() {
		return topics;
	}

	public static boolean checkTopicName(String topic){
		topic = topic.toLowerCase();
		for(String i : topics){
			if (i.toLowerCase().equals(topic)) return true;
		}
		return false;
	}

	public static void deleteWord(String key){
		for (WordCollection i : collectionList){
			if(i.getWord(key)!=null)
				i.deleteWord(key);
		}
	}

	public static void createTopic(String topic){
		topic = topic.trim();
		topics.add(topic);
		WordCollection t =new WordCollection(topic);
		String fileName = Normalizer.normalize(topic, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
		fileName = fileName.replaceAll("đ", "d").replaceAll("Đ", "D").replaceAll("\\s", "_").toLowerCase();
		t.setFileName(fileName+".xlsx");
		collectionList.add(t);
	}

	public static void export(File dst, WordCollection t){
		Export.writeExcelFile(t, dst);
	}

	public static void saveData() {
		for(WordCollection i : collectionList){
			//todo change dir
			File file = new File("res/test/"+i.getFileName());
			Export.writeExcelFile(i, file);
		}
		saveHistoryList();
	}

	public static void deleteCollection(WordCollection t){
		//todo complete this feature (final)
		File file = new File("res/test/"+t.getFileName());
		if (file.exists()) {
			file.delete();
		}
		topics.remove(t.getTopic());
		collectionList.remove(t);
	}

	public static ObservableList<String> loadHistoryList(){
		try {
			File historyFile = new File("res/voc/history.txt");
			FileReader reader = new FileReader(historyFile);
			BufferedReader bufferedReader = new BufferedReader(reader);
			String word;
			while ((word = bufferedReader.readLine()) != null){
				history.add(word);
			}
			reader.close();
			return history;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static void saveHistoryList(){
		try {
			File file = new File("res/voc/history.txt");
			FileWriter writer = new FileWriter(file, false);
			BufferedWriter bufferedWriter = new BufferedWriter(writer);
			for(String i : history){
				bufferedWriter.write(i);
				bufferedWriter.newLine();
			}
			bufferedWriter.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static void merge(WordCollection t1, WordCollection t2, String name){
		Map<String, Word> map = new HashMap<>(t1.getWordList());
		map.putAll(t2.getWordList());
		WordCollection t = new WordCollection(name, map);
		t.resetSeen();
		collectionList.add(t);
		topics.add(name);
	}
}
