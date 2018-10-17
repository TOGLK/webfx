package webfx.fxkit.extra.mapper.spi.peer.impl;

import webfx.fxkit.extra.displaydata.DisplaySelection;
import webfx.fxkit.extra.control.SelectableDisplayResultControl;
import webfx.fxkit.extra.displaydata.SelectionMode;

/**
 * @author Bruno Salmon
 */
public interface SelectableDisplayResultControlPeerMixin
        <C, N extends SelectableDisplayResultControl, NB extends SelectableDisplayResultControlPeerBase<C, N, NB, NM>, NM extends SelectableDisplayResultControlPeerMixin<C, N, NB, NM>>

        extends DisplayResultControlPeerMixin<C, N, NB, NM> {

    void updateSelectionMode(SelectionMode mode);

    void updateDisplaySelection(DisplaySelection selection);
}