package species.colony.build;

public class BuildQueueEntry {
	
	private int buildTimeLeft;
	
	private Buildable build;
	private Buildable after;
	
	private BuildActionType buildAction;
	private BuildActionType afterAction;
	
	public BuildQueueEntry(Buildable b, BuildActionType a, int t) {
		build = b;
		buildAction = a;
		buildTimeLeft = t;
	}
	
	public BuildQueueEntry(District b, BuildActionType a, District d, BuildActionType ad, int t) {
		build = b;
		buildAction = a;
		after = d;
		afterAction = ad;
		buildTimeLeft = t;
	}

	public boolean buildTick(int time) {
		
		buildTimeLeft -= time;
		
		if(buildTimeLeft <= 0) {
			build.buildAction(buildAction);
			if(after != null) {
				after.buildAction(afterAction);
			}
			return true;
		}
		
		return false;
		
	}

}
