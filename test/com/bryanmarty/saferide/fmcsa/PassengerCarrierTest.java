package com.bryanmarty.saferide.fmcsa;

import static org.junit.Assert.*;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.LinkedList;

import org.apache.commons.codec.binary.Base64;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.bryanmarty.saferide.fmcsa.FMCSAScrapper.VehicleType;

public class PassengerCarrierTest {
	
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testPassengerCarrierQuery() {
		LinkedList<PassengerCarrier> pcList = FMCSAScrapper.query("44691", VehicleType.Motorcoach, "");
		for(PassengerCarrier i : pcList) {
			System.out.println(i);
			PassengerCarrier pc = FMCSAScrapper.getDetails(i);			
		}
	}
}
