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

import java.util.List;

import games.stendhal.client.ClientSingletonRepository;
import marauroa.common.game.RPAction;

/**
 * Toggle between invisibility.
 */
public class XMLSlashAction implements SlashAction {

	private String name;
	
	private String type;
	
	private List<String> optionalParameters;
	
	private int maxParameters;
	
	private int minParameters;
	
	public XMLSlashAction(String name, String type, List<String> optionalParameters, int maxParameters, int minParameters)
	{
		this.name = name;
		this.type = type;
		this.optionalParameters = optionalParameters;
		this.maxParameters = maxParameters;
		this.minParameters = minParameters;
	}
	/**
	 * Execute a chat command.
	 *
	 * @param params
	 *            The formal parameters.
	 * @param remainder
	 *            Line content after parameters.
	 *
	 * @return <code>true</code> if was handled.
	 */
	@Override
	public boolean execute(final String[] params, final String remainder) {
		final RPAction action = new RPAction();

		action.put("type", type);
		//for loop
		/* loop from 0 to length of op
		 * 	if index == length of params
		 * 		put remainder
		 * 	else 
		 * 		put param[i]
		 */
		
		for(int i =0; i <= params.length; i++ )
		{
			if(i == params.length)
				action.put(optionalParameters.get(i), remainder);
			else
				action.put(optionalParameters.get(i), params[i]);
		}
		
		ClientSingletonRepository.getClientFramework().send(action);

		
		return true;
	}

	/**
	 * Get the maximum number of formal parameters.
	 *
	 * @return The parameter count.
	 */
	@Override
	public int getMaximumParameters() {
		return maxParameters;
	}

	/**
	 * Get the minimum number of formal parameters.
	 *
	 * @return The parameter count.
	 */
	@Override
	public int getMinimumParameters() {
		return minParameters;
	}
	
	

	
	public void setActionList(List<String> list)
	{
		this.optionalParameters = list;
	}
	
	public void setType(String type)
	{
		this.type = type;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public void setMinimumParameters(int num)
	{
		this.minParameters = num;
	}
	
	public void setMaximumParameters(int num)
	{
		this.maxParameters = num;
	}

}
