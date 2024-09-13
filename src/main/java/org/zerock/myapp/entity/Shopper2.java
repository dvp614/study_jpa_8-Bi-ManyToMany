package org.zerock.myapp.entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Vector;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@Entity(name = "Shopper2")
@Table(name = "shopper2")
public class Shopper2 implements Serializable { // T1 Many(N)
	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "shopper_id", length = 1000)
	private Long id;

	@Basic(optional = false)
	@Column(length = 1000)
	private String name;

	@ManyToMany(targetEntity = Product2.class)
	@JoinTable( 
				name = "shopper2_product2", 
				joinColumns = { 
						@JoinColumn(name = "shopper_id") 
				}, 
				inverseJoinColumns = {
						@JoinColumn(name = "product_id") 
				}
			  ) 
	@ToString.Exclude
	private List<Product2> myOrderedProducts = new Vector<>();
	
	public void order(Product2 newProduct) {
		log.trace("order({}) invoked.", newProduct);
		
		this.myOrderedProducts.add(newProduct);
		newProduct.setShopper(this);
	} // order
	
} // end class
