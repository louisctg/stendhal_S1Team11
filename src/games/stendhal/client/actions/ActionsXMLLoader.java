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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javafx.util.Pair;





public final class ActionsXMLLoader extends DefaultHandler {

	/** the logger instance. */
	private static final Logger LOGGER = Logger.getLogger(ActionsXMLLoader.class);

	private Class< ? > implementation;


	private String name;

	private String type;//

	//private String text;
	private int maximumParameters;//
	private int minimumParameters;//
	private List<Pair<String, String>> putAction;//
	
	private List<DefaultAction> list;

	
	
	
	
	public List<DefaultAction> load(final URI uri) throws SAXException {
		list = new LinkedList<DefaultAction>();
		// Use the default (non-validating) parser
		final SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			// Parse the input
			final SAXParser saxParser = factory.newSAXParser();

			final InputStream is = ActionsXMLLoader.class.getResourceAsStream(uri.getPath());

			if (is == null) {
				throw new FileNotFoundException("cannot find resource '" + uri
						+ "' in classpath");
			}
			try {
				saxParser.parse(is, this);
			} finally {
				is.close();
			}
		} catch (final ParserConfigurationException t) {
			LOGGER.error(t);
		} catch (final IOException e) {
			LOGGER.error(e);
			throw new SAXException(e);
		}

		return list;
	}

	@Override
	public void startDocument() {
		// do nothing
	}

	@Override
	public void endDocument() {
		// do nothing
	}

	@Override
	public void startElement(final String namespaceURI, final String lName, final String qName,
			final Attributes attrs) {
		//text = "";
		if (qName.equals("action")) {
			name = attrs.getValue("name");
			maximumParameters = 0;
			minimumParameters = 0;
			type = "";
			implementation = null;
			putAction = new ArrayList<Pair<String, String>>();
		} else if (qName.equals("type")) {
			type = attrs.getValue("type-name");
		} else if (qName.equals("implementation")) {

			final String className = attrs.getValue("class-name");

			try {
				implementation = Class.forName(className);
			} catch (final ClassNotFoundException ex) {
				LOGGER.error("Unable to load class: " + className);
			}
		} else if (qName.equals("MaximumParameters")) {
			maximumParameters = Integer.parseInt(attrs.getValue("param-num"));
		} else if (qName.equals("MinimumParameters")) {
			minimumParameters = Integer.parseInt(attrs.getValue("param-num"));
		} else if (qName.equals("putAction")) {
			Pair<String, String> pair = new Pair<String, String>(attrs.getValue("param-name"), attrs.getValue("remainder"));
			putAction.add(pair);
		}	
	}

	@Override
	public void endElement(final String namespaceURI, final String sName, final String qName) {
		if (qName.equals("action")) {
			final DefaultAction action = new DefaultAction();
			
			action.setActionName(name);
			action.setType(type);
			action.setMaximumParameters(maximumParameters);
			action.setMinimumParameters(minimumParameters);
			
			
			/* action that will be put */
			if (putAction != null && !putAction.isEmpty()) {
				action.initializeActionsList(putAction);
				this.putAction.clear();
			}		

			if (implementation == null) {
				LOGGER.error("action without defined implementation: " + name);
				return;
			}

			action.setImplementation(implementation);			
			list.add(action);
		
		}
	}

}


