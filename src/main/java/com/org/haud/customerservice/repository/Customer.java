package com.org.haud.customerservice.repository;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private Long id;

	@NotBlank(message = "First name is mandatory")
	@Column(nullable = false)
	private String firstName;

	@NotBlank(message = "Last name is mandatory")
	@Column(nullable = false)
	private String lastName;

	@Email(message = "Enter a valid email")
	@Column(nullable = false, unique = true)
	private String email;

	@NotNull(message = "DOB is mandatory")
	@Column(nullable = false)
	private Date dateOfBirth;

	@OneToMany(fetch = FetchType.LAZY)  
	@JoinColumn(name="sim_card_id")
	private SimCard simCard;
	
	@NotNull
	@Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
	@Builder.Default
	private Boolean isDelete = false;
}