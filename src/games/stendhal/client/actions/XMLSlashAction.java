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
	
	public XMLSlashAction()
	{
		
		
	}
	
	
	
	
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
		if(optionalParameters != null && !optionalParameters.isEmpty())
		{
			for(int i =0; i <= params.length; i++ )
			{
				if(i == params.length)
					action.put(optionalParameters.get(i), StringHelper.unquote(remainder));
				else
					action.put(optionalParameters.get(i), params[i]);
			}
		}
		// Cases to handle proper format according to action.
		switch(this.type) {
			case "jail": {
				if (remainder.length() == 0) {
					return false;
				}
				action.put("target", params[0]);
				action.put("minutes", params[1]);
				action.put("reason", remainder);
				}
			case "where": {
				action.put("target", StringHelper.unquote(remainder));
			}
			case "teleportto": {
				action.put("target", StringHelper.unquote(remainder));
			}
			case "addbuddy": {
				if (params == null) {
					return false;
				}
				action.put("target", params[0]);
			}
			case "adminlevel": {
				action.put("target", params[0]);

				if ((params.length > 1) && (params[1] != null)) {
					action.put("newlevel", params[1]);
				}
			}
			case "adminnote": {
				action.put("target", params[0]);
				action.put("note", remainder);
			}
			case "alter": {
				if (hasInvalidArguments(params, remainder)) {
					return false;
				}
				action.put("target", params[0]);
				action.put("stat", params[1]);
				action.put("mode", params[2]);
				action.put("value", remainder);
			}
			case "altercreature": {
				if ((params == null) || (params.length < getMinimumParameters())) {
					return false;
				}
				action.put("target", params[0]);
				action.put("text", params[1]);
			}
			case "alterquest": {
				if ((params == null) || (params.length < getMinimumParameters())) {
					return false;
				}
				action.put("target", params[0]);
				action.put("name", params[1]);
				if ((params.length > 2) && (params[2] != null)) {
					action.put("state", params[2]);
				}
			}
			case "answer": {
				if (!remainder.isEmpty()) {
				action.put("text", remainder);
				}
			}
			case "away": {
				if (remainder.length() != 0) {
					action.put("message", remainder);
				}
			}
			case "ban": {
				action.put("target", params[0]);
				action.put("hours", params[1]);
				action.put("reason", remainder);
			}
			case "gag": {
				if (remainder.length() == 0) {
					return false;
				}
				action.put("target", params[0]);
				action.put("minutes", params[1]);
				action.put("reason", remainder);
			}
			case "grumpy": {
				if (remainder.length() != 0) {
					action.put("reason", remainder);
				}
			}
			case "ignore": {
				if (params[0] == null) {
					action.put("list", "1");
				} else {
					action.put("target", params[0]);
					String duration = params[1];
					if (duration != null) {
						/*
						 * Ignore "forever" values
						 */
						if (!duration.equals("*") || !duration.equals("-")) {
						/*
						 * Validate it's a number
						 */
							try {
								Integer.parseInt(duration);
							} catch (final NumberFormatException ex) {
								return false;
							}

							action.put("duration", duration);
						}
					}

					if (remainder.length() != 0) {
						action.put("reason", remainder);
					}
				}
			}
			case "inspect": {
				action.put("target", params[0]);
			}
			case "emote": {
				action.put("text", remainder);
			}
			case "tell": {
				String lastPlayerTell = params[0];

				if (!remainder.isEmpty()) {
					action.put("target", lastPlayerTell);
					action.put("text", remainder);
				}
			}
			case "removebuddy": {
				action.put("target", params[0]);
			}
			case "sentence": {
				if (params == null) {
					return false;
				}
				action.put("value", remainder);
			}
			case "storemessage": {
				action.put("target", params[0]);
				action.put("text", remainder);
			}
			case "supportanswer": {
				action.put("target", params[0]);
				action.put("text", remainder);
			}
			case "support": {
				action.put("text", remainder);
			}
			case "teleport": {
				action.put("target", params[0]);
				action.put("zone", params[1]);
				action.put("x", params[2]);
				action.put("y", params[3]);
			}
			case "tellall": {
				action.put("text", remainder);
			}
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
	
	public List<String> getActionList()
	{
		return optionalParameters;
	}
	
	public String getType() {
		return type;
	}
	
	public String getName() {
		return name;
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

}
