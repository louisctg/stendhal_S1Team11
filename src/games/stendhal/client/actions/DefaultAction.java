/* $Id$ */
/***************************************************************************
 *                      (C) Copyright 2003 - Marauroa                      *
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


import java.lang.reflect.Constructor;
import java.util.ArrayList;

import java.util.List;

import games.stendhal.client.actions.XMLSlashAction;
import games.stendhal.server.core.rule.defaultruleset.creator.AbstractCreator;

import games.stendhal.server.core.rule.defaultruleset.creator.FullActionCreator;





/**
 * All default items which can be reduced to stuff that increase the attack
 * point and stuff that increase the defense points.
 *
 * @author Matthias Totz, chad3f 
 */
public class DefaultAction {

	/** Implementation creator. */
	private AbstractCreator<XMLSlashAction> creator;


	/** items type. */
	private String name;
	
	private String type;
	
	private int maximumParameters;//
	private int minimumParameters;//

	private Class< ? > implementation = null;
	
	private List<String> putAction = null;

	
	public DefaultAction() {
		
		
	}



	public void setImplementation(final Class< ? > implementation) {
		this.implementation = implementation;
		creator = buildCreator(implementation);
	}

	/**
	 * Set the use behavior.
	 *
	 * @param behavior new behavior
	 */

	public Class< ? > getImplementation() {
		return implementation;
	}

	
	
	protected AbstractCreator<XMLSlashAction> buildCreator(final Class< ? > implementation) {
		Constructor< ? > construct;

		/*
		 * <Class>()
		 */
		try {
			construct = implementation.getConstructor(new Class[] {});

			return new FullActionCreator(this, construct);
		} catch (final NoSuchMethodException ex) {
			// ignore and continue
		}
		

		return null;
	}
	
	
	
	
	public XMLSlashAction getAction() {

		/*
		 * Just in case - Really should generate fatal error up front (in
		 * ActionsXMLLoader). */
		 
		if (creator == null) {
			return null;
		}
		final XMLSlashAction action = creator.create();
		if (action != null) {
			action.setActionList(putAction);
			action.setType(type);
			action.setName(name);
			action.setMinimumParameters(minimumParameters);
			action.setMaximumParameters(maximumParameters);
		}

		return action;
	}


	
	
	 
	/*MAXIMUM*/
	public int getMaximumParameters()
	{
		return maximumParameters;
	}	
	
	public void setMaximumParameters(int param)
	{
		this.maximumParameters = param;
	}
	
	/*MINIMUM*/
	public int getMinimumParameters()
	{
		return minimumParameters;
	}	
	
	public void setMinimumParameters(int param)
	{
		this.minimumParameters = param;
	}	
	
	
	/*NAME*/
	public String getActionName() {
		return name;
	}

	public void setActionName(final String val) {
		name = val;
	}
	
	/*TYPE*/
	public String getType() {
		return type;
	}

	public void setType(final String val) {
		type = val;
	}
	
	/*PUTACTIONS*/
	public void initializeActionsList(List<String> res) {
		putAction = new ArrayList<String>();

		for (int i=0;i<res.size();i++) 
		{
			putAction.add(res.get(i));
		}
	}	
	
	
	public List<String> getActionList()
	{
		return putAction;
	}
	
	
	public String toXML() {
		final StringBuilder os = new StringBuilder();
		
	
		os.append("  <action name=\"" + name +  "\">\n");
		
		os.append("    <implementation class-name=\""
				+ implementation.getCanonicalName() + "\"/>\n");
		
		os.append("    <MaximumParameters>"+maximumParameters +"</MaximumParameters>\n");
		
		os.append("    <MinimumParameters>"+minimumParameters +"</MinimumParameters>\n");
		
		os.append("    <type>"+type +"</type>\n");
		
		for (final String putparam : putAction) {
			os.append("    	<putAction name=\"" + putparam + "\"/>\n");
		}
		

		os.append("  </action>\n");
		return os.toString();
	}
}
