/* $Id$ */
/***************************************************************************
 *                   (C) Copyright 2003-2010 - Stendhal                    *
 ***************************************************************************
 ***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/
package games.stendhal.client.actions;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.client.StendhalClient;

public class XMLSlashActionTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
		StendhalClient.resetClient();
	}

	/**
	 * Tests for getMaximumParameters.
	 */
	@Test
	public void testGetMaximumParameters() {
		final XMLSlashAction action = new XMLSlashAction("testAction", "test",null,5,1);
		assertThat(action.getMaximumParameters(), is(5));
	}

	/**
	 * Tests for getMinimumParameters.
	 */
	@Test
	public void testGetMinimumParameters() {
		final XMLSlashAction action = new XMLSlashAction("testAction", "test",null,5,1);
		assertThat(action.getMinimumParameters(), is(1));
	}
	
	/**
	 * Tests for setters.
	 */
	@Test
	public void testSeters() {
		final XMLSlashAction action = new XMLSlashAction("testAction", "test",null,5,1);
		action.setType("test2");
	}

}