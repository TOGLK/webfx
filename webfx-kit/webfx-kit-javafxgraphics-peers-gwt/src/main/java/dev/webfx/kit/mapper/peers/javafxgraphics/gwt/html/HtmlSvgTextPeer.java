package dev.webfx.kit.mapper.peers.javafxgraphics.gwt.html;

import dev.webfx.kit.mapper.peers.javafxgraphics.SceneRequester;
import dev.webfx.kit.mapper.peers.javafxgraphics.base.TextPeerBase;
import dev.webfx.kit.mapper.peers.javafxgraphics.base.TextPeerMixin;
import dev.webfx.kit.mapper.peers.javafxgraphics.gwt.html.layoutmeasurable.HtmlLayoutMeasurableNoHGrow;
import dev.webfx.kit.mapper.peers.javafxgraphics.gwt.shared.SvgRoot;
import dev.webfx.kit.mapper.peers.javafxgraphics.gwt.shared.SvgRootBase;
import dev.webfx.kit.mapper.peers.javafxgraphics.gwt.svg.SvgTextPeer;
import dev.webfx.kit.mapper.peers.javafxgraphics.gwt.util.HtmlUtil;
import dev.webfx.kit.mapper.peers.javafxgraphics.gwt.util.SvgUtil;
import elemental2.dom.Element;
import elemental2.dom.HTMLElement;
import javafx.geometry.VPos;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * @author Bruno Salmon
 */
public final class HtmlSvgTextPeer
        <N extends Text, NB extends TextPeerBase<N, NB, NM>, NM extends TextPeerMixin<N, NB, NM>>
        extends HtmlShapePeer<N, NB, NM>
        implements TextPeerMixin<N, NB, NM>, HtmlLayoutMeasurableNoHGrow {

    private SvgTextPeer svgTextPeer = new SvgTextPeer();

    public HtmlSvgTextPeer() {
        this((NB) new TextPeerBase(), HtmlUtil.createElement("div"));
    }

    public HtmlSvgTextPeer(NB base, HTMLElement element) {
        super(base, element);
    }

    @Override
    public void bind(N node, SceneRequester sceneRequester) {
        getNodePeerBase().setNode(node);
        Element svgElement = SvgUtil.createSvgElement("svg");
        SvgRoot svgRoot = new SvgRootBase();
        node.getProperties().put("svgRoot", svgRoot);
        svgElement.setAttribute("overflow", "visible"); // To avoid clipping the strokes
        // Arbitrary size (ok since overflow is visible)
        svgElement.setAttribute("width", 1); // 1 is enough
        svgElement.setAttribute("height", 100_000); // Have to use a big number (great than element height?), otherwise the element is translated down for any reason
        HtmlUtil.setChildren(svgElement, svgRoot.getDefsElement(), svgTextPeer.getElement());
        HtmlUtil.setChild(getElement(), svgElement);
        svgTextPeer.bind(node, sceneRequester);
        // Temporary hack for the WebFx website: making svg area unclickable (otherwise interfere with container clicks)
        //setElementStyleAttribute("pointer-events", "none");
    }

    @Override
    public NB getNodePeerBase() {
        return (NB) svgTextPeer.getNodePeerBase();
    }

    @Override
    public double measure(HTMLElement e, boolean width) {
        Element svgElement = svgTextPeer.getElement();
        return width ? svgElement.getBoundingClientRect().width : svgElement.getBoundingClientRect().height;
    }

/*
    private final HtmlLayoutCache cache = new HtmlLayoutCache();
    @Override
    public HtmlLayoutCache getCache() {
        return cache;
    }
*/

    @Override
    public void updateText(String text) {}

    @Override
    public void updateTextOrigin(VPos textOrigin) {}

    @Override
    public void updateX(Double x) {}

    @Override
    public void updateY(Double y) {}

    @Override
    public void updateWrappingWidth(Double wrappingWidth) {}

    @Override
    public void updateTextAlignment(TextAlignment textAlignment) {}

    @Override
    public void updateFont(Font font) {}

}
