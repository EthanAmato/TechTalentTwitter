package com.tts.techtalenttwitter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tts.techtalenttwitter.model.Tag;

@Repository
public interface TagRepository extends CrudRepository<Tag,Long>{
	Tag findByPhrase(String phrase);

}
