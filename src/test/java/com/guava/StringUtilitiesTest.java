package com.guava;

import static org.junit.Assert.*;

import org.junit.Test;

import com.google.common.base.CaseFormat;
import com.google.common.base.CharMatcher;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;

public class StringUtilitiesTest {

	@Test
	public void test_Basic_Joiner_SkippingNulls() {
		Joiner joiner = Joiner.on("; ").skipNulls();
		assertEquals("Harry; Ron; Hermione", joiner.join("Harry", null, "Ron", "Hermione"));
	}
	
	@Test
	public void test_Splitter_Char(){
		Joiner joiner = Joiner.on("; ");
		assertEquals("Harry; Ron; Hermione",joiner.join(Splitter.on('x').split("HarryxRonxHermione")));
	}
	
	@Test
	public void test_Splitter_CharMatcher(){
		Joiner joiner = Joiner.on("; ");
		assertEquals("Harry; Ron; Hermione",joiner.join(Splitter.on(CharMatcher.anyOf("0O")).split("HarryORon0Hermione")));
	}
	
	@Test
	public void test_Splitter_CharMatcher_OmitEmptyStrings(){
		Joiner joiner = Joiner.on("; ");
		assertEquals("Harry; Ron; Hermione",joiner.join(Splitter.on(CharMatcher.anyOf("0O")).omitEmptyStrings().split("HarryO000Ron0Hermione")));
	}
	
	@Test
	public void test_Splitter_CharMatcher_OmitEmptyStringsWhitespace(){
		Joiner joiner = Joiner.on("; ");
		assertEquals("Harry; Ron; Hermione",joiner.join(Splitter.on(CharMatcher.anyOf("0O")).omitEmptyStrings().trimResults().split("Harry  O000Ron0Hermione")));
	}
	
	@Test
	public void test_CharMatcher_MatchesAll(){
		assertTrue(CharMatcher.ASCII.matchesAllOf("kjsdhnfkjncksadjfh"));
	}
	
	@Test
	public void test_CharMatcher_CollapseFrom(){
		assertEquals("loremxipsumxdolor", CharMatcher.WHITESPACE.collapseFrom("lorem ipsum dolor", 'x'));
	}
	
	@Test
	public void test_CaseFormat(){
		assertEquals("constantName",CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, "CONSTANT_NAME"));
	}

}
