package games.stendhal.server.core.config;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.junit.Test;
import org.xml.sax.SAXException;

import games.stendhal.server.core.rule.defaultruleset.DefaultAction;

public class ActionGroupsXMLLoaderTest {
	@Test
	public void testLoad() throws SAXException, IOException, URISyntaxException{
		final ActionGroupsXMLLoader groupsLoader = new ActionGroupsXMLLoader(new URI("/data/conf/actions.xml"));
		List<DefaultAction> actions = groupsLoader.load();
		assertNotNull(actions);		
	}
}
