package com.guava;

import static org.junit.Assert.*;

import org.junit.Test;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;

public class StringUtilitiesTest {

	@Test
	public void test_BasicJoinerSkippingNulls() {
		Joiner joiner = Joiner.on("; ").skipNulls();
		assertEquals("Harry; Ron; Hermione", joiner.join("Harry", null, "Ron", "Hermione"));
	}
	
	@Test
	public void test_SplitterChar(){
		Joiner joiner = Joiner.on("; ");
		assertEquals("Harry; Ron; Hermione",joiner.join(Splitter.on('x').split("HarryxRonxHermione")));
	}

}
