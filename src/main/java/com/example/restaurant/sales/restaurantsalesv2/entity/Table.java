package com.example.restaurant.sales.restaurantsalesv2.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

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

	@OneToMany(mappedBy = "table", fetch = FetchType.EAGER)
	private List<Sale> sales = new ArrayList<Sale>();

	@UpdateTimestamp
	private LocalDate lastUpdatedDate;

	@CreationTimestamp
	private LocalDate createdDate;

	private boolean isDeleted;

	public void addSale(Sale sale) {
		this.sales.add(sale);
	}
}
