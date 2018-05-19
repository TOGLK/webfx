package mongoose.activities.backend.events;

import naga.framework.activity.combinations.domainpresentation.impl.DomainPresentationActivityImpl;

/**
 * @author Bruno Salmon
 */
class EventsPresentationActivity extends DomainPresentationActivityImpl<EventsPresentationModel> {

    EventsPresentationActivity() {
        super(EventsPresentationViewActivity::new, EventsPresentationLogicActivity::new);
    }
}
