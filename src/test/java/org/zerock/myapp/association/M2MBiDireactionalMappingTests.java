package org.zerock.myapp.association;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.LongStream;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Timeout;
import org.zerock.myapp.entity.Product2;
import org.zerock.myapp.entity.Shopper2;
import org.zerock.myapp.util.PersistenceUnits;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor

@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class M2MBiDireactionalMappingTests {
	private EntityManagerFactory emf;
	private EntityManager em;

	@BeforeAll
	void beforeAll() {
		log.trace("beforeAll() invoked.");

		this.emf = Persistence.createEntityManagerFactory(PersistenceUnits.H2);

		this.em = this.emf.createEntityManager();
		assertNotNull(this.em);
	} // beforeAll

	@AfterAll
	void afterAll() {
		log.trace("afterAll() invoked.");

		this.em.clear();
		try {
			this.em.close();
		} catch (Exception _ignored) {
		}
		try {
			this.emf.close();
		} catch (Exception _ignored) {
		}
	} // afterAll

//	@Disabled
	@Order(1)
	@Test
//	@RepeatedTest(1)
	@DisplayName("1. prepareData")
	@Timeout(value = 5L, unit = TimeUnit.MINUTES)
	void prepareData() {
		log.trace("prepareData invoked.");

//		IntStream.rangeClosed(1, 7).forEachOrdered(seq -> {
//
//			try {
//				this.em.getTransaction().begin();
//
//				// 엔티티.....
//				Product2 transientProduct = new Product2();
//				transientProduct.setName("NAME-" + seq);
//
//				this.em.persist(transientProduct);
//
//				this.em.getTransaction().commit();
//			} catch (Exception e) {
//				this.em.getTransaction().rollback();
//
//				throw e;
//			} // try-catch
//		}); // forEachOrdered

		Product2 foundProduct1 = this.em.<Product2>find(Product2.class, 1L);
		Product2 foundProduct2 = this.em.<Product2>find(Product2.class, 2L);
		Product2 foundProduct3 = this.em.<Product2>find(Product2.class, 3L);
		Product2 foundProduct4 = this.em.<Product2>find(Product2.class, 4L);
		Product2 foundProduct5 = this.em.<Product2>find(Product2.class, 5L);
		Product2 foundProduct6 = this.em.<Product2>find(Product2.class, 6L);
		Product2 foundProduct7 = this.em.<Product2>find(Product2.class, 7L);

		Objects.requireNonNull(foundProduct1);
		Objects.requireNonNull(foundProduct2);
		Objects.requireNonNull(foundProduct3);
		Objects.requireNonNull(foundProduct4);
		Objects.requireNonNull(foundProduct5);
		Objects.requireNonNull(foundProduct6);
		Objects.requireNonNull(foundProduct7);
//
//		// 3.
		LongStream.of(1L, 2L, 3L).forEachOrdered(seq -> {

			try {
				this.em.getTransaction().begin();

				// 엔티티.....
				Shopper2 transientShopper = new Shopper2();
				transientShopper.setName("NAME-" + seq);

				// 구매한 상품들을 1개이상 주문할 수 있다.
				if (seq == 1L) {
					transientShopper.order(foundProduct1);
					transientShopper.order(foundProduct2);
					transientShopper.order(foundProduct7);

//		               transientShopper.getMyOrderdProducts().add(foundProduct1);
				} else if (seq == 2L) {
					transientShopper.order(foundProduct3);
					transientShopper.order(foundProduct4);
				} else {
					transientShopper.order(foundProduct5);
					transientShopper.order(foundProduct6);
				} // else-if

				this.em.persist(transientShopper);

				this.em.getTransaction().commit();
			} catch (Exception e) {
				this.em.getTransaction().rollback();
				throw e;
			} // try-catch

		}); // forEachOrdered

	} // prepareData

//	@Disabled
	@Order(2)
	@Test
//	@RepeatedTest(1)
	@DisplayName("2. testM2MBiObjectGraphTraverseFromShopperToProduct")
	@Timeout(value = 5L, unit = TimeUnit.MINUTES)
	void testM2MBiObjectGraphTraverseFromShopperToProduct() {
		log.trace("testM2MBiObjectGraphTraverseFromShopperToProduct invoked.");
		var shopperId = new Random().nextLong(1L, 3L);

		Shopper2 shopper = this.em.<Shopper2>find(Shopper2.class, shopperId);
		log.info("\t+ shopper : {}", shopper);

		Objects.requireNonNull(shopper);

		shopper.getMyOrderedProducts().forEach(p -> log.info(p.toString()));
	} // testM2MBiObjectGraphTraverseFromShopperToProduct

} // end class
