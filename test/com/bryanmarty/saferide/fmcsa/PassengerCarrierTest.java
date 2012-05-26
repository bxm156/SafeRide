package com.bryanmarty.saferide.fmcsa;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.bryanmarty.saferide.fmcsa.PassengerCarrier.VehicleType;

public class PassengerCarrierTest {
	
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testPassengerCarrierQuery() {
		PassengerCarrier.query("44691", VehicleType.Motorcoach, "");
	}
	
}
