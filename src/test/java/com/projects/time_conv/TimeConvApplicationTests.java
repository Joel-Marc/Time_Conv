package com.projects.time_conv;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TimeConvApplicationTests {

	@Test
	void contextLoads() {

		Extrac myClass = new Extrac();
		assertNull(myClass.extracted());  // JUnit assertion
	}

}
