package mongoose.entities.markers;

import mongoose.entities.ItemFamily;
import mongoose.entities.ItemFamilyType;
import naga.framework.orm.entity.EntityId;

/**
 * @author Bruno Salmon
 */
public interface HasItemFamily extends HasItemFamilyType {

    void setItemFamily(Object itemFamily);

    EntityId getItemFamilyId();

    ItemFamily getItemFamily();

    default boolean hasItemFamily() {
        return getItemFamily() != null;
    }

    @Override
    default ItemFamilyType getItemFamilyType() {
        ItemFamily itemFamily = getItemFamily();
        return itemFamily == null ? ItemFamilyType.UNKNOWN : itemFamily.getItemFamilyType();
    }
}
