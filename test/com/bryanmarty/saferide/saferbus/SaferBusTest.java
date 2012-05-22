package com.bryanmarty.saferide.saferbus;


import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;

public class SaferBusTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testCarrierByName() {
		SaferBusResponse res = SaferBus.getCarrierByName("greyhound");
	}

}
