package mongoose.activities.backend.event.letters;

import naga.framework.activity.combinations.domainpresentation.impl.DomainPresentationActivityImpl;

/**
 * @author Bruno Salmon
 */
class LettersPresentationActivity extends DomainPresentationActivityImpl<LettersPresentationModel> {

    LettersPresentationActivity() {
        super(LettersPresentationViewActivity::new, LettersPresentationLogicActivity::new);
    }
}
