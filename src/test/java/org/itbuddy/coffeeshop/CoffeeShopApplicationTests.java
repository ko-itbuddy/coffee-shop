package org.itbuddy.coffeeshop;

import org.itbuddy.coffeeshop.config.TestContainerConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@ActiveProfiles("test")
@ExtendWith(TestContainerConfig.class)
@ContextConfiguration(initializers = TestContainerConfig.IntegrationTestInitializer.class)
@SpringBootTest
class CoffeeShopApplicationTests {

	@Test
	void contextLoads() {
	}

}
