package com.juank.entity;

import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends Auditable{
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotNull(message = "name is required")
	private String name;
	
	@NotNull(message = "last name is required")
	private String lastName;
	
	@NotNull(message = "address is required")
	private String address;
	
	@NotNull(message = "phone number is required")
	private String phoneNumber;
	
	@NotNull(message = "date of birth is required")
	private Date dob;
	
	@ManyToMany(fetch = FetchType.LAZY,
			cascade = {
					CascadeType.MERGE
	}
			)
	@JoinTable(
			  name = "user_book", 
			  joinColumns = {@JoinColumn(name = "user_id")}, 
			  inverseJoinColumns = @JoinColumn(name = "book_id"))
	private List<Book> books;
	
	
}
