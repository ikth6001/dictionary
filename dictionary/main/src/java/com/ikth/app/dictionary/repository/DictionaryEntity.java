package com.ikth.app.dictionary.repository;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="Dictionary")
@Entity
public class DictionaryEntity {

	@Id
	@Column(name="word")
	private String word;
	
	@Column(name="meaning", length=4000)
	private String meaning;

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getMeaning() {
		return meaning;
	}

	public void setMeaning(String meaning) {
		this.meaning = meaning;
	}
}
