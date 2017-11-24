package words;

import io.Export;
import io.Import;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.omg.IOP.IOR;

import java.io.*;
import java.text.Normalizer;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
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
	private static Queue<String> queueReview = new LinkedList<>();
	private static ObservableList<Integer> statistics = FXCollections.observableArrayList();
	private static LocalDate lastOpened;
	private static int learned =0;
	public static boolean[] complete = new boolean[2];

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
		readQueueReview();
		readStatistic();
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

	/**
	 * Lấy số lượng từ mới cần học 1 ngày
	 * @return số lượng từ mới
	 */
	public static int getNewWordPerDay() {
		return newWordPerDay;
	}

	/**
	 * Đặt số lượng từ mới cần học 1 ngày
	 * @param newWordPerDay số lượng từ mới
	 */
	public static void setNewWordPerDay(int newWordPerDay) {
		RunningData.newWordPerDay = newWordPerDay;
	}

	/**
	 * Lấy sô lượng từ cần ôn lại 1 ngày
	 * @return số lượng từ
	 */
	public static int getRevWordPerDay() {
		return revWordPerDay;
	}

	/**
	 * Đặt số lượng từ cần ôn lại 1 ngày
	 * @param revWordPerDay số lượng từ
	 */
	public static void setRevWordPerDay(int revWordPerDay) {
		RunningData.revWordPerDay = revWordPerDay;
	}

	/**
	 * Lấy số lượng từ cho 1 bài test
	 * @return số lượng từ
	 */
	public static int getWordsForTest() {
		return wordsForTest;
	}

	/**
	 * Đặt số lượng từ cho 1 bài test
	 * @param wordsForTest số lượng từ
	 */
	public static void setWordsForTest(int wordsForTest) {
		RunningData.wordsForTest = wordsForTest;
	}

	/**
	 * Lấy danh sách từ để ôn tập
	 * @return các từ để ôn tập
	 */
	public static Queue<String> getQueueReview() {
		return queueReview;
	}

	/**
	 * Lấy ngày gần nhất sử dụng chương trình
	 * @return ngày
	 */
	public static LocalDate getLastOpened() {
		return lastOpened;
	}

	/**
	 * Lấy số liệu thống kê
	 * @return số liệu thống kê
	 */
	public static ObservableList<Integer> getStatistics() {
		return statistics;
	}

	/**
	 * Lấy số từ học được trong 1 lần học
	 * @return số từ
	 */
	public static int getLearned() {
		return learned;
	}

	/**
	 * Đặt số từ học được trong 1 lần học
	 * @param learned số từ học được
	 */
	public static void setLearned(int learned) {
		RunningData.learned = learned;
	}

	/**
	 * Tăng 1 vào số từ đã học trong lần học
	 */
	public static void incLearned(){
		++learned;
	}

	/**
	 * Thêm 1 bộ từ
	 * @param fileExcel file excel chứa bộ từ
	 */
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

	/**
	 * Kiểm tra tên chủ đề
	 * @param topic tên chủ đề
	 * @return true nếu đã tồn tại, false nếu không
	 */
	public static boolean checkTopicName(String topic){
		topic = topic.toLowerCase();
		for(String i : topics){
			if (i.toLowerCase().equals(topic)) return true;
		}
		return false;
	}

	/**
	 * Xoá 1 từ trong tất cả bộ từ
	 * @param key từ cần xoá (tiếng Anh)
	 */
	public static void deleteWord(String key){
		for (WordCollection i : collectionList){
			if(i.getWord(key)!=null)
				i.deleteWord(key);
		}
	}

	/**
	 * Tạo chủ đề mới
	 * @param topic tên chủ đề
	 */
	public static void createTopic(String topic){
		topic = topic.trim();
		topics.add(topic);
		WordCollection t =new WordCollection(topic);
		String fileName = Normalizer.normalize(topic, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
		fileName = fileName.replaceAll("đ", "d").replaceAll("Đ", "D").replaceAll("\\s", "_").toLowerCase();
		t.setFileName(fileName+".xlsx");
		collectionList.add(t);
	}

	/**
	 * Lưu các chủ đề vào file excel
	 */
	private static void saveCollectionList(){
		for(WordCollection i : collectionList){
			File file = new File("res/voc/"+i.getFileName());
			Export.writeExcelFile(i, file);
		}
	}

	/**
	 * Lưu tất cả dữ liệu hoạt động của chương trình
	 */
	public static void saveData() {
		saveCollectionList();
		saveHistoryList();
		saveSettings();
		saveQueueReview();
		saveStatistic();
	}

	/**
	 * Xoá một bộ từ
	 * @param t bộ từ cần xoá
	 */
	public static void deleteCollection(WordCollection t){
		File file = new File("res/voc/"+t.getFileName());
		if (file.exists()) {
			file.delete();
		}
		topics.remove(t.getTopic());
		collectionList.remove(t);
	}

	/**
	 * Đọc dữ liệu lịch sử tìm từ file
	 * @return danh sách các từ đã tìm
	 */
	public static ObservableList<String> readHistoryList(){
		try {
			File historyFile = new File("res/voc/history.txt");
			FileReader reader = new FileReader(historyFile);
			BufferedReader bufferedReader = new BufferedReader(reader);
			String word;
			while ((word = bufferedReader.readLine()) != null){
				history.add(word);
			}
			bufferedReader.close();
			reader.close();
			return history;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Lưu dữ liệu lịch sử tìm vào file
	 */
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
			writer.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Gộp 2 bộ từ thành bộ từ mới
	 * @param t1 bộ từ 1
	 * @param t2 bộ từ 2
	 * @param name tên bộ từ mới
	 */
	public static void merge(WordCollection t1, WordCollection t2, String name){
		Map<String, Word> map = new HashMap<>(t1.getWordList());
		map.putAll(t2.getWordList());
		WordCollection t = new WordCollection(name, map);
		t.resetSeen();
		collectionList.add(t);
		topics.add(name);
	}

	/**
	 * Chuẩn bị dữ liệu để học từ mới
	 * @param t bộ từ
	 * @return các từ mới để học
	 */
	public static Queue<Word> prepareForLearn(WordCollection t){
		if (t.getWordList().size() <= newWordPerDay){
			return new LinkedList<>(t.getWordList().values());
		}
		Queue<Word> words = new LinkedList<>();
		ArrayList<Word> val = new ArrayList<>(t.getWordList().values());
		for (int i = 0; i < val.size();) {
			if(val.get(i).isSeen()) val.remove(i);
			else ++i;
		}
		if (val.isEmpty()) return null;
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

	/**
	 * Chuẩn bị dữ liệu để kiểm tra trắc nghiệm
	 * @param t bộ từ
	 * @return các từ để kiểm tra
	 */
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

	/**
	 * Chuẩn bị dữ liệu để ôn tập
	 * @return các từ để ôn tập, null nếu không có từ nào
	 */
	public static Queue<Word> prepareForReview(){
		if (queueReview.isEmpty()) return null;
		Queue<Word> review = new LinkedList<>();
		List<Word> w;
		String key;
		for(int i=0; i<revWordPerDay && !queueReview.isEmpty(); ++i){
			key = queueReview.poll();
			w = searchAll(key);
			if (!w.isEmpty()){
				review.offer(w.get(0));
			}
		}
		return review;
	}

	/**
	 * Đọc dữ liệu cài đặt từ file
	 */
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
			bufferedReader.close();
			reader.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Lưu dữ liệu cài đặt vào file
	 */
	private static void saveSettings(){
		try {
			File file = new File("res/settings.txt");
			FileWriter writer = new FileWriter(file, false);
			BufferedWriter bufferedWriter = new BufferedWriter(writer);
			bufferedWriter.write(String.valueOf(newWordPerDay));
			bufferedWriter.newLine();
			bufferedWriter.write(String.valueOf(revWordPerDay));
			bufferedWriter.newLine();
			bufferedWriter.write(String.valueOf(wordsForTest));
			bufferedWriter.close();
			writer.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Đọc dữ liệu ôn tập từ file
	 */
	private static void readQueueReview(){
		try {
			File file = new File("res/voc/queue_review.txt");
			FileReader reader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(reader);
			String word;
			while ((word = bufferedReader.readLine()) != null){
				queueReview.offer(word);
			}
			bufferedReader.close();
			reader.close();
		} catch (IOException e){
			throw new RuntimeException("error while reading \"queue_review.txt\"");
		}
	}

	/**
	 * Lưu dữ liệu ôn tập vào file
	 */
	private static void saveQueueReview(){
		try {
			File file = new File("res/voc/queue_review.txt");
			FileWriter writer = new FileWriter(file, false);
			BufferedWriter bufferedWriter = new BufferedWriter(writer);
			while (!queueReview.isEmpty()){
				bufferedWriter.write(queueReview.poll());
				bufferedWriter.newLine();
			}
			bufferedWriter.close();
			writer.close();
		} catch (IOException e){
			throw new RuntimeException("error while writing \"queue_review.txt\"");
		}
	}

	/**
	 * Đọc dữ liệu thống kê từ file
	 */
	private static void readStatistic(){
		try {
			File file = new File("res/statistics.txt");
			FileReader reader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(reader);
			String s;
			s = bufferedReader.readLine();
			lastOpened = LocalDate.parse(s);
			long days = ChronoUnit.DAYS.between(lastOpened, LocalDate.now());
			if(days >=10){
				while (statistics.size()<10){
					statistics.add(0);
				}
				return;
			}else {
				for (int i=0; i<days; ++i){
					s=bufferedReader.readLine();
				}
				for(int i=0; i<10-days; ++i){
					s=bufferedReader.readLine();
					statistics.add(Integer.parseInt(s));
				}
				while (statistics.size()<10){
					statistics.add(0);
				}
			}

			bufferedReader.close();
			reader.close();
		} catch (IOException e){
			throw new RuntimeException("error: read statistics.txt", e);
		} catch (DateTimeParseException e){
			throw new RuntimeException("error: parse date");
		}
	}

	/**
	 * Lưu dữ liệu thống kê vào file
	 */
	private static void saveStatistic(){
		try {
			File file = new File("res/statistics.txt");
			FileWriter writer = new FileWriter(file, false);
			BufferedWriter bufferedWriter = new BufferedWriter(writer);
			bufferedWriter.write(LocalDate.now().toString());
			bufferedWriter.newLine();
			for(int i : statistics){
				bufferedWriter.write(String.valueOf(i));
				bufferedWriter.newLine();
			}
			bufferedWriter.close();
			writer.close();
		} catch (IOException e){
			throw new RuntimeException("error: write statistics.txt", e);
		}
	}
}
