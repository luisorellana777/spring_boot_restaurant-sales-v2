package com.example.restaurant.sales.restaurantsalesv2.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SQLDelete(sql = "update table set is_deleted = true where id = ?")
@Where(clause = "is_deleted = false")
@javax.persistence.Table(name = "table_restaurant")
public class Table {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private int number;
	private int seats;

	@OneToOne(mappedBy = "table", fetch = FetchType.LAZY)
	private Sale sale;

	@UpdateTimestamp
	private LocalDate lastUpdatedDate;

	@CreationTimestamp
	private LocalDate createdDate;

	private boolean isDeleted;
}
