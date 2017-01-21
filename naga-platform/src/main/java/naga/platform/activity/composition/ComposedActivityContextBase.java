package naga.platform.activity.composition;

import naga.platform.activity.ActivityContext;
import naga.platform.activity.ActivityContextBase;
import naga.platform.activity.ActivityContextFactory;

/**
 * @author Bruno Salmon
 */
public class ComposedActivityContextBase
        <THIS extends ComposedActivityContextBase<THIS, C1, C2>,
                C1 extends ActivityContext<C1>,
                C2 extends ActivityContext<C2>>

        extends ActivityContextBase<THIS>
        implements ComposedActivityContext<THIS, C1, C2> {

    private C1 activityContext1;
    private C2 activityContext2;

    public ComposedActivityContextBase(ActivityContext parentContext, ActivityContextFactory<THIS> contextFactory) {
        super(parentContext, contextFactory);
    }

    @Override
    public void setActivityContext1(C1 activityContext1) {
        this.activityContext1 = activityContext1;
    }

    @Override
    public C1 getActivityContext1() {
        return activityContext1;
    }

    @Override
    public void setActivityContext2(C2 activityContext2) {
        this.activityContext2 = activityContext2;
    }

    @Override
    public C2 getActivityContext2() {
        return activityContext2;
    }

}
