package com.juank.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Book extends Auditable {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String googleId;
	
	@NotNull(message = "name is required")
	private String title;
	
	@NotNull(message = "subtitle is required")
	private String subtitle;
	
	@NotNull(message = "authors name is required")
	private List<String> authors;
	
	@NotNull(message = "year is required")
	private Integer year;
	
	@NotNull(message = "type is required")
	private String type;
	
	private String description;
	
	private String ISBN;
	
	private List<String> categories;
	
	private String imageLink;
	
	private String language;
	
	private String previewLink;
	
	private String infoLink;
	
	private Integer pageCount;
	
	@Enumerated(EnumType.STRING)
	private BookSize size;

	 @ManyToMany(fetch = FetchType.LAZY,
		      cascade = {
		          CascadeType.MERGE
		      },
		      mappedBy = "books")
	private List<User> users;
	 
	 public enum BookSize {
			BIG, MEDIUM, SMALL
	 }
}


