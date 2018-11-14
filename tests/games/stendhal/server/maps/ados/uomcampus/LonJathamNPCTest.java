package games.stendhal.server.maps.ados.uomcampus;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static utilities.SpeakerNPCTestHelper.getReply;

import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.common.parser.Expression;
import games.stendhal.common.parser.ExpressionType;
import games.stendhal.common.parser.Sentence;
import games.stendhal.common.parser.SentenceImplementation;
import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.npc.ConversationStates;
import games.stendhal.server.entity.npc.NPC;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.fsm.Engine;
import games.stendhal.server.maps.MockStendlRPWorld;
import games.stendhal.server.maps.ados.uomcampus.LonJathamNPC;
import marauroa.common.Log4J;
import utilities.PlayerTestHelper;

public class LonJathamNPCTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Log4J.init();
		MockStendlRPWorld.get();
	}
	
	/**
	 * Tests for configureZone and Lon Jatham existence.
	 */
	@Test
	public void testConfigureZone() {
		SingletonRepository.getRPWorld();
		final LonJathamNPC lonjathamConfigurator = new LonJathamNPC();

		final StendhalRPZone zone = new StendhalRPZone("testzone");
		lonjathamConfigurator.configureZone(zone, null);
		assertFalse(zone.getNPCList().isEmpty());
		final NPC lonJatham = zone.getNPCList().get(0);
		assertThat(lonJatham.getName(), is("Lon Jatham"));
		assertThat(lonJatham.getDescription(), is("You see a lecturer with matching glasses and shirt."));
	}
	
	/**
	 * Tests for hiandBye.
	 */
	@Test
	public void testHiandBye() {
		SingletonRepository.getRPWorld();
		final LonJathamNPC lonjathamConfigurator = new LonJathamNPC();
		final StendhalRPZone zone = new StendhalRPZone("testzone");
		lonjathamConfigurator.configureZone(zone, null);
		final SpeakerNPC lonJatham = (SpeakerNPC) zone.getNPCList().get(0);
		assertThat(lonJatham.getName(), is("Lon Jatham"));
		final Engine engine = lonJatham.getEngine();
		engine.setCurrentState(ConversationStates.IDLE);

		Sentence sentence = new SentenceImplementation(new Expression("hi", ExpressionType.VERB));
		engine.step(PlayerTestHelper.createPlayer("bob"), sentence);
		assertThat(engine.getCurrentState(), is(ConversationStates.ATTENDING));
		assertThat(getReply(lonJatham), is("GOOD MORNING! Would you like #help deciding if our university is a good fit for you? Alternatively, I can give #offer you a tip to be a better programmer."));
		
		sentence = new SentenceImplementation(new Expression("bye", ExpressionType.VERB));
		engine.step(PlayerTestHelper.createPlayer("bob"), sentence);
		assertThat(engine.getCurrentState(), is(ConversationStates.IDLE));
		assertThat(getReply(lonJatham), is("Bye."));
	}
	
	
	/**
	 * Tests for conversation.
	 */
	@Test
	public void testConversation() {
		SingletonRepository.getRPWorld();
		final LonJathamNPC lonjathamConfigurator = new LonJathamNPC();
		final StendhalRPZone zone = new StendhalRPZone("testzone");
		lonjathamConfigurator.configureZone(zone, null);
		final SpeakerNPC lonJatham = (SpeakerNPC) zone.getNPCList().get(0);
		assertThat(lonJatham.getName(), is("Lon Jatham"));
		final Engine engine = lonJatham.getEngine();
		engine.setCurrentState(ConversationStates.IDLE);

		Sentence sentence = new SentenceImplementation(new Expression("hi", ExpressionType.VERB));
		engine.step(PlayerTestHelper.createPlayer("bob"), sentence);
		assertThat(engine.getCurrentState(), is(ConversationStates.ATTENDING));
		assertThat(getReply(lonJatham), is("GOOD MORNING! Would you like #help deciding if our university is a good fit for you? Alternatively, I can give #offer you a tip to be a better programmer."));
		
		sentence = new SentenceImplementation(new Expression("help", ExpressionType.VERB));
		engine.step(PlayerTestHelper.createPlayer("bob"), sentence);
		assertThat(engine.getCurrentState(), is(ConversationStates.ATTENDING));
		assertThat(getReply(lonJatham), is("We offer course units related to Computer Architecture, Information Systems, Mobile Computing and Networks, Hardware, Algorithms, Software Engineering and many more!"));
		
		sentence = new SentenceImplementation(new Expression("offer", ExpressionType.VERB));
		engine.step(PlayerTestHelper.createPlayer("bob"), sentence);
		assertThat(engine.getCurrentState(), is(ConversationStates.ATTENDING));
		assertThat(getReply(lonJatham), is("Make sure to give your variables meaningful, appropriate names. If you do it, you'll make it far as a programmer."));
		
		sentence = new SentenceImplementation(new Expression("bye", ExpressionType.VERB));
		engine.step(PlayerTestHelper.createPlayer("bob"), sentence);
		assertThat(engine.getCurrentState(), is(ConversationStates.IDLE));
		assertThat(getReply(lonJatham), is("Bye."));
	}
		
}


