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


import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Set;



/**
 * Manages Slash Action Objects.
 */
public class SlashActionRepository {

	/** Set of client supported Actions. */
	private static HashMap<String, SlashAction> actions = new HashMap<String, SlashAction>();
	
	private static HashMap<String, SlashAction> XMLActions = new HashMap<String, SlashAction>();
	
	/**
	 * Registers the available Action.
	 */
	public static void register() {
		
		loadXML();
		final SlashAction msg = XMLActions.get("MessageAction");
		final SlashAction supporta = XMLActions.get("SupportAnswerAction");
		final SlashAction who = XMLActions.get("WhoAction");
		final SlashAction help = XMLActions.get("HelpAction");
		final GroupMessageAction groupMessage = new GroupMessageAction();
		
		
		

		actions.put("/", XMLActions.get("RemessageAction"));
		actions.put("addbuddy", XMLActions.get("AddBuddyAction"));
		actions.put("adminlevel", XMLActions.get("AdminLevelAction"));
		actions.put("adminnote", XMLActions.get("AdminNoteAction"));
		actions.put("alter", XMLActions.get("AlterAction"));
		actions.put("altercreature", XMLActions.get("AlterCreatureAction"));
		actions.put("alterquest", XMLActions.get("AlterQuestAction"));
		actions.put("answer", XMLActions.get("AnswerAction"));
		actions.put("atlas", XMLActions.get("AtlasBrowserLaunchCommand"));
		actions.put("away", XMLActions.get("AwayAction"));

		actions.put("ban", XMLActions.get("BanAction"));

		actions.put("clear", XMLActions.get("ClearChatLogAction"));
		actions.put("clickmode", XMLActions.get("ClickModeAction"));
		actions.put("clientinfo", XMLActions.get("ClientInfoAction"));
		actions.put("commands", help);
		actions.put("config", XMLActions.get("ConfigAction"));

		actions.put("drop", XMLActions.get("DropAction"));
		
		//actions.put("echo", XMLActions.get("EchoAction"));

		actions.put("cast", XMLActions.get("CastSpellAction"));

		actions.put("gag", XMLActions.get("GagAction"));
		actions.put("gmhelp", XMLActions.get("GMHelpAction"));
		actions.put("group", new GroupManagementAction(groupMessage));
		actions.put("groupmessage", groupMessage);
		actions.put("grumpy", XMLActions.get("GrumpyAction"));

		actions.put("help", XMLActions.get("HelpAction"));

		actions.put("ignore", XMLActions.get("IgnoreAction"));
		actions.put("inspect", XMLActions.get("InspectAction"));
		actions.put("invisible", new InvisibleAction());
		

		actions.put("jail", XMLActions.get("JailAction"));

		actions.put("listproducers", XMLActions.get("ListProducersAction"));

		actions.put("me", XMLActions.get("EmoteAction"));
		actions.put("msg", msg);
		actions.put("mute", XMLActions.get("MuteAction"));

		actions.put("names", who);

		actions.put("p", groupMessage);
		actions.put("profile", new ProfileAction());
		actions.put("travellog", new TravelLogAction());

		actions.put("quit", new QuitAction());

		actions.put("removebuddy", XMLActions.get("RemoveBuddyAction"));

		actions.put("sentence", XMLActions.get("SentenceAction"));
		actions.put("status", XMLActions.get("SentenceAction")); // Alias for /sentence
		actions.put("settings", new SettingsAction());

		actions.put("sound", new SoundAction());
		actions.put("volume", new VolumeAction());
		actions.put("vol", new VolumeAction());

		actions.put("storemessage", XMLActions.get("StoreMessageAction"));
		actions.put("postmessage", XMLActions.get("StoreMessageAction"));

	//	actions.put("summonat", new SummonAtAction());
		actions.put("summon", new SummonAction());
		actions.put("supportanswer", supporta);
		actions.put("supporta", supporta);
		actions.put("support", XMLActions.get("SupportAction"));

		actions.put("takescreenshot", new ScreenshotAction());
		actions.put("teleport", XMLActions.get("TeleportAction"));
		actions.put("teleportto", XMLActions.get("TeleportToAction"));
		actions.put("tellall", XMLActions.get("TellAllAction"));
		actions.put("tell", msg);

		actions.put("where", XMLActions.get("WhereAction"));
		actions.put("who", who);
		actions.putAll(BareBonesBrowserLaunchCommandsFactory.createBrowserCommands());
//		actions.put("wrap", new WrapAction());

		/* Movement */
		actions.put("walk", new AutoWalkAction());
		actions.put("stopwalk", new AutoWalkStopAction());
		actions.put("movecont", new MoveContinuousAction());

		// PvP challenge actions
		actions.put("challenge", new CreateChallengeAction());
		actions.put("accept", new AcceptChallengeAction());
	}
	
	//LOAD
	
	private static void loadXML()
	{
		try {
			final ActionGroupsXMLLoader loader = new ActionGroupsXMLLoader(new URI("/data/conf/actions.xml"));
			List<DefaultAction> loadedDefaultActions = loader.load();
			for (DefaultAction defaultAction : loadedDefaultActions) {
				
				XMLSlashAction temp = defaultAction.getAction();
				XMLActions.put(defaultAction.getActionName(),temp);
				
				
			}
		} catch (Exception e) {
			System.out.println("ERROR OCCURED: "+ e);
		}
	}
	

	/**
	 * Gets the Action object for the specified Action name.
	 *
	 * @param name
	 *            name of Action
	 * @return Action object
	 */
	public static SlashAction get(String name) {
		String temp = name.toLowerCase(Locale.ENGLISH);
		return actions.get(temp);
	}

	/**
	 * Get all known command names.
	 *
	 * @return set of commands
	 */
	public static Set<String> getCommandNames() {
		return actions.keySet();
	}
}
