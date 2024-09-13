package org.zerock.myapp.entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Vector;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity(name = "Product2")
@Table(name = "product2")
@SequenceGenerator(name = "MySequence", sequenceName = "seq_product2")
public class Product2 implements Serializable{ // T2 Many (M)
	@Serial private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MySequence")
	@Column(name = "product_id", length = 1000)
	private Long id;
	
	@Basic(optional = false, fetch = FetchType.EAGER)
	@Column(length = 1000)
	private String name;
	
	// 다대다 양방향 관계에서, FK 속성명을 연관관계의 주인으로 지정해야 함이
	// 당연한데, 정작 T1에는 FK 속성자체가 없습니다. 이유는 다대다이니, 모든
	// T1 과 T2에 대한 FK 속성은 Cross Table에 있습니다. 이렇데 다대다 양방향
	// 관계에서, 연관관계의 주인을 정할 때에는, T1의 컬렉션 속성명을 지정합니다.
	@ManyToMany(
			mappedBy = "myOrderedProducts",
			targetEntity = Shopper2.class)
	private List<Shopper2> myShoppers = new Vector<>();
	
	private Shopper2 shopper;
} // end class
