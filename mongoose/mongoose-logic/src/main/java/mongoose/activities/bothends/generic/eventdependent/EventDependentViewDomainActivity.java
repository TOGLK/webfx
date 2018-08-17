package mongoose.activities.bothends.generic.eventdependent;

import mongoose.activities.bothends.generic.MongooseButtonFactoryMixin;
import naga.framework.activity.base.combinations.viewdomain.impl.ViewDomainActivityContextFinal;
import naga.framework.activity.base.elementals.view.impl.ViewDomainActivityBase;

/**
 * @author Bruno Salmon
 */
public abstract class EventDependentViewDomainActivity
    extends ViewDomainActivityBase
    implements EventDependentActivityMixin<ViewDomainActivityContextFinal>,
        MongooseButtonFactoryMixin {

    // Should the presentation model be stored in the context instead (like for logic presentation activity?)
    private final EventDependentPresentationModel presentationModel = new EventDependentPresentationModelImpl();

    @Override
    public EventDependentPresentationModel getPresentationModel() {
        return presentationModel;
    }

    @Override
    protected void updateModelFromContextParameters() {
        updateEventDependentPresentationModelFromContextParameters();
        super.updateModelFromContextParameters();
    }
}