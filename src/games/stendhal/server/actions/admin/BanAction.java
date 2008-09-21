package games.stendhal.server.actions.admin;

import games.stendhal.server.actions.CommandCenter;
import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.entity.player.Player;

import java.sql.SQLException;

import marauroa.common.game.RPAction;
import marauroa.server.game.db.IDatabase;

import org.apache.log4j.Logger;

public class BanAction extends AdministrationAction {
	private static Logger logger = Logger.getLogger(BanAction.class);

	@Override
	protected void perform(final Player player, final RPAction action) {
		if (action.has("target")) {
			String bannedName = action.get("target");
			
			IDatabase playerDatabase = SingletonRepository.getPlayerDatabase();
			
			try {
				playerDatabase.setAccountStatus(playerDatabase.getTransaction(), bannedName, "banned");
				player.sendPrivateText("You have banned " + bannedName);
				//	TODO: use add game event and send message to supporters 
				Logger.getLogger(BanAction.class).info(player.getName() + " has banned " + bannedName);
			} catch (SQLException e) {
				logger.error("Error while trying to ban user", e);
			}
		}

	}
	public static void register() {
		CommandCenter.register("ban", new BanAction(), 1000);
	}
}
