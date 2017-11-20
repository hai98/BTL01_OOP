package words;

import io.Export;
import io.Import;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
	private static int newWordPerDay;
	private static int revWordPerDay;
	private static int wordsForTest;

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
		readSettings();
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

	public static int getNewWordPerDay() {
		return newWordPerDay;
	}

	public static void setNewWordPerDay(int newWordPerDay) {
		RunningData.newWordPerDay = newWordPerDay;
	}

	public static int getRevWordPerDay() {
		return revWordPerDay;
	}

	public static void setRevWordPerDay(int revWordPerDay) {
		RunningData.revWordPerDay = revWordPerDay;
	}

	public static int getWordsForTest() {
		return wordsForTest;
	}

	public static void setWordsForTest(int wordsForTest) {
		RunningData.wordsForTest = wordsForTest;
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

	private static void saveCollectionList(){
		for(WordCollection i : collectionList){
			//todo change dir
			File file = new File("res/test/"+i.getFileName());
			Export.writeExcelFile(i, file);
		}
	}

	public static void saveData() {
		saveCollectionList();
		saveHistoryList();
		saveSettings();
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

	public static Queue<Word> prepareForLearn(WordCollection t){
		if (t.getWordList().size() <= newWordPerDay){
			return new LinkedList<>(t.getWordList().values());
		}
		//todo if all word seen
		Queue<Word> words = new LinkedList<>();
		ArrayList<Word> val = new ArrayList<>(t.getWordList().values());
		for (int i = 0; i < val.size();) {
			if(val.get(i).isSeen()) val.remove(i);
			else ++i;
		}
		if (val.size() <=newWordPerDay) return new LinkedList<>(val);
		int rand;
		Word w;
		while (words.size() < newWordPerDay){
			rand = (int) (Math.random()*val.size());
			w = val.get(rand);
			if(words.contains(w)) continue;
			words.offer(w);
		}
		return words;
	}

	public static List<Word> prepareForTest(WordCollection t){
		ArrayList<Word> list = new ArrayList<>(t.getWordList().values());
		/*for (int i = 0; i < list.size();) {
			if(!list.get(i).isSeen()) {
				list.remove(i);
			}else ++i;
		}*/
		LinkedList<Word> words = new LinkedList<>();
		int rand;
		Word w;
		while (words.size() < wordsForTest){
			rand = (int) (Math.random()*list.size());
			w = list.get(rand);
			if(!words.contains(w))
				words.add(w);
		}
		return words;
	}

	private static void readSettings(){
		try {
			File settingsFile = new File("res/settings.txt");
			FileReader reader = new FileReader(settingsFile);
			BufferedReader bufferedReader = new BufferedReader(reader);
			String num;
			num = bufferedReader.readLine();
			newWordPerDay = Integer.parseInt(num);
			num = bufferedReader.readLine();
			revWordPerDay = Integer.parseInt(num);
			num = bufferedReader.readLine();
			wordsForTest = Integer.parseInt(num);
			reader.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static void saveSettings(){
		try {
			System.out.println("called");
			File file = new File("res/settings.txt");
			FileWriter writer = new FileWriter(file, false);
			BufferedWriter bufferedWriter = new BufferedWriter(writer);
			bufferedWriter.write(String.valueOf(newWordPerDay));
			bufferedWriter.newLine();
			bufferedWriter.write(String.valueOf(revWordPerDay));
			bufferedWriter.newLine();
			bufferedWriter.write(String.valueOf(wordsForTest));
			bufferedWriter.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
