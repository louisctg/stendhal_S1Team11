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
import games.stendhal.common.StringHelper;
import javafx.util.Pair;
import marauroa.common.game.RPAction;

/**
 * Toggle between invisibility.
 */
public class XMLSlashAction implements SlashAction {

	private String name;
	
	private String type;
	
	private List<Pair<String, String>> optionalParameters;
	
	private int maxParameters;
	
	private int minParameters;
	
	public XMLSlashAction()
	{
		
		
	}
	
	
	
	
	public XMLSlashAction(String name, String type, List<Pair<String, String>> optionalParameters, int maxParameters, int minParameters)
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
		if(this.type!=null)
		{
			action.put("type", type);
		}
			
		if(optionalParameters != null && !optionalParameters.isEmpty())
		{
			for(int i =0; i < optionalParameters.size(); i++ )
			{
				if(optionalParameters.get(i).getValue().equals("remainderString"))
				{
					action.put(optionalParameters.get(i).getKey(), StringHelper.unquote(remainder));
				}	
				else if(optionalParameters.get(i).getValue().equals("remainder"))
				{
					action.put(optionalParameters.get(i).getKey(), remainder);
				}
				else
				{
					action.put(optionalParameters.get(i).getKey(), params[i]);
				}					
			}
						
		}	
		
		if(this.type!=null)
		{
			ClientSingletonRepository.getClientFramework().send(action);
		}
		

		
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
	
	public List<Pair<String, String>> getActionList()
	{
		return optionalParameters;
	}
	
	public String getType() {
		return type;
	}
	
	public String getName() {
		return name;
	}
	
	
	public void setActionList(List<Pair<String, String>> list)
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
	
	/**
	 * Checks whether the arguments passed are valid for execution.
	 *
	 * @param params to be evaluated
	 * @param remainder to be evaluated
	 * @return true if <code>params</code>.length too short or remainder is <code>null</code>
	 */
	private boolean hasInvalidArguments(final String[] params, final String remainder) {
		return (params == null) || (remainder == null) || (params.length < getMinimumParameters());
	}
	
	@Override
	public String toString()
	{
		return getName();
	}

}
