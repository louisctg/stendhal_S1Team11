package games.stendhal.client.actions;
/*
import games.stendhal.client.ClientSingletonRepository;
import games.stendhal.common.StringHelper;
import marauroa.common.game.RPAction;
*/
public class InvisibleAction extends XMLSlashAction
{
	public InvisibleAction()
	{
		
	}
	
	@Override
	public boolean execute(final String[] params, final String remainder) {
		/*final RPAction action = new RPAction();

		action.put("type", this.getType());	
		
		if(this.getActionList() != null && !this.getActionList().isEmpty())
		{
			for(int i =0; i <= params.length; i++ )
			{
				if(i == params.length)
					action.put(this.getActionList().get(i), StringHelper.unquote(remainder));
				else
					action.put(this.getActionList().get(i), params[i]);
			}
		}
		
		
		ClientSingletonRepository.getClientFramework().send(action);		*/
		
		return true;
	}
}
