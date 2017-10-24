package words;

import io.ImportVoc;

import java.util.Map;

public class WordCollection {
	private String subject;
	private String pathToExcel;
	private int size;
	private Map<String, Word> wordList;
	public WordCollection(){
		subject = "no name";
		size = 0;
		wordList = null;
	}
	public WordCollection(String subject, String pathToExcel) throws Exception{
		this();
		this.subject = subject;
		wordList = ImportVoc.toWordHashMap(pathToExcel);
	}
	public void put(Word w){
		if(wordList.containsKey(w.getEn())) return;
		wordList.put(w.getEn(), w);
		++size;
	}
	public Word get(String en){
		return wordList.get(en);
	}
	public int getSize(){
		return size;
	}

	public Map<String, Word> getWordList() {
		return wordList;
	}

	public void setWordList(Map<String, Word> wordList) {
		this.wordList = wordList;
	}
}
