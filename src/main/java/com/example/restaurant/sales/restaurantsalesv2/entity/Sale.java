package com.example.restaurant.sales.restaurantsalesv2.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
@SQLDelete(sql = "update sale set is_deleted = true where id = ?")
@Where(clause = "is_deleted = false")
public class Sale {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "SALE_DISH", joinColumns = @JoinColumn(name = "SALE_ID"), inverseJoinColumns = @JoinColumn(name = "DISH_ID"))
	private List<Dish> dishes = new ArrayList<Dish>();

	@OneToOne
	private Waiter waiter;

	@OneToOne
	private Table table;

	@UpdateTimestamp
	private LocalDate lastUpdatedDate;

	@CreationTimestamp
	private LocalDate createdDate;

	private Long neto;

	private Long tax;

	private Long total;

	private Long tip;

	private boolean isDeleted;

	public void addDish(Dish dish) {
		this.dishes.add(dish);
	}
}
