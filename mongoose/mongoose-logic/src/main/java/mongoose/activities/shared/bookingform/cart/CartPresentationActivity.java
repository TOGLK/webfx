package mongoose.activities.shared.bookingform.cart;

import naga.framework.activity.combinations.domainpresentation.impl.DomainPresentationActivityImpl;

/**
 * @author Bruno Salmon
 */
public class CartPresentationActivity extends DomainPresentationActivityImpl<CartPresentationModel> {

    public CartPresentationActivity() {
        super(CartPresentationViewActivity::new, CartPresentationLogicActivity::new);
    }
}