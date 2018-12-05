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
package games.stendhal.server.core.rule.defaultruleset;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Map;

import games.stendhal.client.actions.XMLSlashAction;
import games.stendhal.server.core.rule.defaultruleset.creator.AbstractCreator;
import games.stendhal.server.core.rule.defaultruleset.creator.DefaultActionCreator;
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


	private Class< ? > implementation = null;
	
	private List<String> optionalFields = null;

	
	public DefaultAction(final String name, final String type) {
		this.type = type;
		this.name = name;
		
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

	/**
	 * Build a creator for the class. It uses the following constructor search
	 * order:<br>
	 *
	 * <ul>
	 * <li><em>Class</em>(<em>name</em>, <em>clazz</em>,
	 * <em>subclazz</em>, <em>attributes</em>)
	 * <li><em>Class</em>(<em>attributes</em>)
	 * <li><em>Class</em>()
	 * </ul>
	 *
	 * @param implementation
	 *            The implementation class.
	 *
	 * @return A creator, or <code>null</code> if none found.
	 */
	protected AbstractCreator<XMLSlashAction> buildCreator(final Class< ? > implementation) {
		Constructor< ? > construct;

		/*
		 * <Class>(name, clazz, subclazz, attributes)
		 */
		try {
			construct = implementation.getConstructor(new Class[] {
					String.class, String.class, String.class, Map.class });

			return new FullActionCreator(this, construct);
		} catch (final NoSuchMethodException ex) {
			// ignore and continue
		}


		/*
		 * <Class>()
		 */
		try {
			construct = implementation.getConstructor(new Class[] {});

			return new DefaultActionCreator(this, construct);
		} catch (final NoSuchMethodException ex) {
			// ignore and continue
		}

		return null;
	}

	/**
	 * Returns an item-instance.
	 *
	 * @return An item, or <code>null</code> on error.
	 */
	/*
	public Item getItem() {

		/*
		 * Just in case - Really should generate fatal error up front (in
		 * ItemXMLLoader).
		 
		if (creator == null) {
			return null;
		}
		final Item item = creator.create();
		if (item != null) {
			item.setEquipableSlots(slots);
			item.setDescription(description);
			if (damageType != null) {
				item.setDamageType(damageType);
			}
			item.setSusceptibilities(susceptibilities);

			/* Set a list of status resistances for StatusResistantItem. 
			if ((this.resistances != null) && (!this.resistances.isEmpty())) {
				item.initializeStatusResistancesList(resistances);
			}

			/* Set a list of active slots for SlotActivatedItem. 
			if ((this.activeSlotsList != null)
					&& (!this.activeSlotsList.isEmpty())) {
				item.initializeActiveSlotsList(this.activeSlotsList);
			}

			item.setUseBehavior(useBehavior);
		}

		return item;
	}
*/

	public String getActionName() {
		return name;
	}

	public void setActionName(final String val) {
		name = val;
	}

	public String getActionType() {
		return type;
	}

	public void setActionType(final String val) {
		type = val;
	}
	
	public void setOptionalFields()
	{
		
	}
	
	public List<String> getOptionalFields()
	{
		return optionalFields;
	}
	
	public String toXML() {
		final StringBuilder os = new StringBuilder();
		
		//TODO: Create putparameter list with a for loop
		os.append("  <action name=\"" + name + "\" type=\"" +type+ "\">\n");
		
		os.append("    <implementation class-name=\""
				+ implementation.getCanonicalName() + "\"/>");
		

		os.append("  </action>\n");
		return os.toString();
	}
}
