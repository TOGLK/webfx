package mongooses.core.entities.impl;

import mongooses.core.entities.OrganizationType;
import webfx.framework.orm.entity.EntityId;
import webfx.framework.orm.entity.EntityStore;
import webfx.framework.orm.entity.impl.DynamicEntity;

/**
 * @author Bruno Salmon
 */
public final class OrganizationTypeImpl extends DynamicEntity implements OrganizationType {

    public OrganizationTypeImpl(EntityId id, EntityStore store) {
        super(id, store);
    }
}