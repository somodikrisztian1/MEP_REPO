package hu.mep.utils.others;
import hu.mep.datamodells.Place;
import hu.mep.datamodells.Topic;

public interface FragmentLevel2EventHandler {
	public void onTopicSelected(Topic selectedTopic);
	public void onRemoteMonitoringSelected(Place selectedPlace);
}
