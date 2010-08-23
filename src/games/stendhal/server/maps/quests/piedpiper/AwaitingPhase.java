package games.stendhal.server.maps.quests.piedpiper;

import java.util.List;
import java.util.Map;

public class AwaitingPhase extends TPPQuest {
	
	private int minPhaseChangeTime;
	private int maxPhaseChangeTime;

	/**
	 * constructor
	 * @param minTime ,
	 * @param maxTime - a pair of time parameters for phase timeout
	 */
	public AwaitingPhase(Map<String, Integer> timings) {
		super(timings);
		minPhaseChangeTime = timings.get(AWAITING_TIME_MIN);
		maxPhaseChangeTime = timings.get(AWAITING_TIME_MAX);
	}


	public int getMinTimeOut() {
		return minPhaseChangeTime;
	}
	

	public int getMaxTimeOut() {
		return maxPhaseChangeTime;
	}



	public void phaseToDefaultPhase(List<String> comments) {
		super.phaseToDefaultPhase(comments);		
	}


	public void phaseToNextPhase(ITPPQuest nextPhase, List<String> comments) {
		super.phaseToNextPhase(nextPhase, comments);
	}
	
	/**
	 *  Pied Piper sent rats away:-)
	 
	private void tellAllAboutRatsIsGone() {
		final String text = "Mayor Chalmers shouts: Thankfully, all the #rats are gone now, " +
							"the Pied Piper " +
							"hypnotized them and led them away to dungeons. "+
				            "Those of you, who helped Ados City with the rats problem, "+
							"can get your #reward now.";
		SingletonRepository.getRuleProcessor().tellAllPlayers(text);
	}*/
	

	public TPP_Phase getPhase() {
		return TPP_Phase.TPP_AWAITING;
	}

}
