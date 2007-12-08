/*
 * @(#) src/games/stendhal/server/config/ZonesXMLLoader.java
 *
 * $Id$
 */

package games.stendhal.server.config;

//
//

import games.stendhal.server.StendhalRPWorld;
import games.stendhal.server.StendhalRPZone;
import games.stendhal.server.config.zone.ConfiguratorXMLReader;
import games.stendhal.server.config.zone.EntitySetupXMLReader;
import games.stendhal.server.config.zone.PortalSetupXMLReader;
import games.stendhal.server.config.zone.SetupDescriptor;
import games.stendhal.server.config.zone.SetupXMLReader;

import games.stendhal.tools.tiled.LayerDefinition;
import games.stendhal.tools.tiled.ServerTMXLoader;
import games.stendhal.tools.tiled.StendhalMapStructure;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;


import org.apache.log4j.Logger;

/**
 * Load and configure zones via an XML configuration file.
 */
public class ZonesXMLLoader {
	/**
	 * Logger
	 */
	private static final Logger logger = Logger.getLogger(ZonesXMLLoader.class);

	/**
	 * The ConfiguratorDescriptor XML reader.
	 */
	protected static final SetupXMLReader configuratorReader = new ConfiguratorXMLReader();

	/**
	 * The EntitySetupDescriptor XML reader.
	 */
	protected static final SetupXMLReader entitySetupReader = new EntitySetupXMLReader();

	/**
	 * The PortalSetupDescriptor XML reader.
	 */
	protected static final SetupXMLReader portalSetupReader = new PortalSetupXMLReader();

	/**
	 * The zone group file.
	 */
	protected URI uri;

	/**
	 * Create an xml based loader of zones.
	 */
	public ZonesXMLLoader(URI uri) {
		this.uri = uri;
	}

	//
	// ZonesXMLLoader
	//

	/**
	 * Load a group of zones into a world.
	 *
	 * @throws SAXException
	 *             If a SAX error occurred.
	 * @throws IOException
	 *             If an I/O error occurred.
	 * @throws FileNotFoundException
	 *             If the resource was not found.
	 */
	public void load() throws SAXException, IOException {
		InputStream in = getClass().getResourceAsStream(uri.getPath());

		if (in == null) {
			throw new FileNotFoundException("Cannot find resource: " + uri);
		}

		try {
			load(in);
		} finally {
			in.close();
		}
	}

	/**
	 * Load a group of zones into a world using a config file.
	 *
	 * @param in
	 *            The config file stream.
	 *
	 * @throws SAXException
	 *             If a SAX error occurred.
	 * @throws IOException
	 *             If an I/O error occurred.
	 */
	protected void load(InputStream in) throws SAXException, IOException {
		Document doc = XMLUtil.parse(in);

		/*
		 * Load each zone
		 */
		for (Element element : XMLUtil.getElements(doc.getDocumentElement(),
				"zone")) {
			ZoneDesc zdesc = readZone(element);

			if (zdesc == null) {
				continue;
			}

			String name = zdesc.getName();

			logger.info("Loading zone: " + name);

			try {
				StendhalMapStructure zonedata = ServerTMXLoader
						.load(StendhalRPWorld.MAPS_FOLDER + zdesc.getFile());

				if (verifyMap(zdesc, zonedata)) {
					StendhalRPZone zone = load(zdesc, zonedata);

					/*
					 * Setup Descriptors
					 */
					Iterator<SetupDescriptor> diter = zdesc.getDescriptors();

					while (diter.hasNext()) {
						diter.next().setup(zone);
					}
				}
			} catch (Exception ex) {
				logger.error("Error loading zone: " + name, ex);
			}
		}
	}

	private static final String[] REQUIRED_LAYERS = { "0_floor", "1_terrain",
			"2_object", "3_roof", "objects", "collision", "protection" };

	private boolean verifyMap(ZoneDesc zdesc, StendhalMapStructure zonedata) {
		for (String layer : REQUIRED_LAYERS) {
			if (!zonedata.hasLayer(layer)) {
				logger.error("Required layer " + layer + " missing in zone "
						+ zdesc.getFile());
				return false;
			}
		}
		return true;
	}

	/**
	 * Load zone data and create a zone from it. Most of this should be moved
	 * directly into ZoneXMLLoader.
	 *
	 *
	 */
	protected StendhalRPZone load(ZoneDesc desc, StendhalMapStructure zonedata)
			throws SAXException, IOException {
		String name = desc.getName();
		StendhalRPZone zone = new StendhalRPZone(name);

		zone.addTilesets(name + ".tilesets", zonedata.getTilesets());
		zone.addLayer(name + ".0_floor", zonedata.getLayer("0_floor"));
		zone.addLayer(name + ".1_terrain", zonedata.getLayer("1_terrain"));
		zone.addLayer(name + ".2_object", zonedata.getLayer("2_object"));
		zone.addLayer(name + ".3_roof", zonedata.getLayer("3_roof"));

		LayerDefinition layer = zonedata.getLayer("4_roof_add");

		if (layer != null) {
			zone.addLayer(name + ".4_roof_add", layer);
		}

		zone.addCollisionLayer(name + ".collision", zonedata
				.getLayer("collision"));
		zone.addProtectionLayer(name + ".protection", zonedata
				.getLayer("protection"));

		if (desc.isInterior()) {
			zone.setPosition();
		} else {
			zone.setPosition(desc.getLevel(), desc.getX(), desc.getY());
		}

		StendhalRPWorld.get().addRPZone(zone);

		zone.populate(zonedata.getLayer("objects"));

		return zone;
	}

	public ZoneDesc readZone(final Element element) {
		if (!element.hasAttribute("name")) {
			logger.error("Unnamed zone");
			return null;
		}

		String name = element.getAttribute("name");

		if (!element.hasAttribute("file")) {
			logger.error("Zone [" + name + "] without 'file' attribute");
			return null;
		}

		String file = element.getAttribute("file");

		int level;
		int x;
		int y;

		/**
		 * Interior zones don't have levels (why not?)
		 */
		if (!element.hasAttribute("level")) {
			level = ZoneDesc.UNSET;
			x = ZoneDesc.UNSET;
			y = ZoneDesc.UNSET;
		} else {
			String s = element.getAttribute("level");

			try {
				level = Integer.parseInt(s);
			} catch (NumberFormatException ex) {
				logger.error("Zone [" + name + "] has invalid level: " + s);
				return null;
			}

			if (!element.hasAttribute("x")) {
				logger.error("Zone [" + name + "] without x coordinate");
				return null;
			} else {
				s = element.getAttribute("x");

				try {
					x = Integer.parseInt(s);
				} catch (NumberFormatException ex) {
					logger.error("Zone [" + name
							+ "] has invalid x coordinate: " + s);
					return null;
				}
			}

			if (!element.hasAttribute("y")) {
				logger.error("Zone [" + name + "] without y coordinate");
				return null;
			} else {
				s = element.getAttribute("y");

				try {
					y = Integer.parseInt(s);
				} catch (NumberFormatException ex) {
					logger.error("Zone [" + name
							+ "] has invalid y coordinate: " + s);
					return null;
				}
			}
		}

		ZoneDesc desc = new ZoneDesc(name, file, level, x, y);

		/*
		 * Title element
		 */
		List<Element> list = XMLUtil.getElements(element, "title");

		if (!list.isEmpty()) {
			if (list.size() > 1) {
				logger.error("Zone [" + name + "] has multiple title elements");
			}

			desc.setTitle(XMLUtil.getText(list.get(0)).trim());
		}

		/*
		 * Setup elements
		 */
		for (Element child : XMLUtil.getElements(element)) {
			String tag = child.getTagName();

			SetupDescriptor setupDesc;

			if (tag.equals("configurator")) {
				setupDesc = configuratorReader.read(child);
			} else if (tag.equals("entity")) {
				setupDesc = entitySetupReader.read(child);
			} else if (tag.equals("portal")) {
				setupDesc = portalSetupReader.read(child);
			} else if (tag.equals("title")) {
				// Ignore
				continue;
			} else {
				logger.warn("Zone [" + name + "] has unknown element: " + tag);
				continue;
			}

			if (setupDesc != null) {
				desc.addDescriptor(setupDesc);
			}
		}

		return desc;
	}

	//
	//

	/*
	 * TODO: XXX - THIS REQUIRES StendhalRPWorld SETUP (i.e. marauroa.ini)
	 */
	public static void main(String[] args) throws Exception {
		if (args.length != 1) {
			System.err.println("Usage: java " + ZonesXMLLoader.class.getName()
					+ " <filename>");
			System.exit(1);
		}

		ZonesXMLLoader loader = new ZonesXMLLoader(new URI(args[0]));

		try {
			loader.load();
		} catch (org.xml.sax.SAXParseException ex) {
			System.err.print("Source " + args[0] + ":" + ex.getLineNumber()
					+ "<" + ex.getColumnNumber() + ">");

			throw ex;
		}
	}

	//
	//

	/**
	 * A zone descriptor.
	 */
	protected static class ZoneDesc {
		public static final int UNSET = Integer.MIN_VALUE;

		protected String name;

		protected String file;

		protected String title;

		protected int level;

		protected int x;

		protected int y;

		protected ArrayList<SetupDescriptor> descriptors;

		public ZoneDesc(String name, String file, int level, int x, int y) {
			this.name = name;
			this.file = file;
			this.level = level;
			this.x = x;
			this.y = y;

			descriptors = new ArrayList<SetupDescriptor>();
		}

		//
		// ZoneDesc
		//

		/**
		 * Add a setup descriptor.
		 *
		 */
		public void addDescriptor(SetupDescriptor desc) {
			descriptors.add(desc);
		}

		/**
		 * Get the zone file.
		 *
		 */
		public String getFile() {
			return file;
		}

		/**
		 * Get the level.
		 *
		 */
		public int getLevel() {
			return level;
		}

		/**
		 * Get the zone name.
		 *
		 */
		public String getName() {
			return name;
		}

		/**
		 * Get an iterator of setup descriptors.
		 *
		 */
		public Iterator<SetupDescriptor> getDescriptors() {
			return descriptors.iterator();
		}

		/**
		 * Get the zone title.
		 *
		 */
		public String getTitle() {
			return title;
		}

		/**
		 * Get the X coordinate.
		 *
		 */
		public int getX() {
			return x;
		}

		/**
		 * Get the Y coordinate.
		 *
		 */
		public int getY() {
			return y;
		}

		public boolean isInterior() {
			return (getLevel() == UNSET);
		}

		/**
		 * Set the zone title.
		 *
		 */
		public void setTitle(String title) {
			this.title = title;
		}
	}
}
