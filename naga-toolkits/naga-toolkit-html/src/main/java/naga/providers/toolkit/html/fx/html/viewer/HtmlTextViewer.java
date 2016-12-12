package naga.providers.toolkit.html.fx.html.viewer;

import naga.providers.toolkit.html.util.HtmlPaints;
import naga.providers.toolkit.html.util.HtmlUtil;
import naga.toolkit.fx.geometry.VPos;
import naga.toolkit.fx.scene.paint.Paint;
import naga.toolkit.fx.spi.viewer.base.TextViewerBase;
import naga.toolkit.fx.spi.viewer.base.TextViewerMixin;
import naga.toolkit.fx.scene.text.Font;
import naga.toolkit.fx.scene.text.Text;
import naga.toolkit.fx.scene.text.TextAlignment;

/**
 * @author Bruno Salmon
 */
public class HtmlTextViewer
        extends HtmlShapeViewer<Text, TextViewerBase, TextViewerMixin>
        implements TextViewerMixin, HtmlLayoutMeasurable {

    public HtmlTextViewer() {
        super(new TextViewerBase(), HtmlUtil.createSpanElement());
        setElementStyleAttribute("line-height", "100%");
    }

    @Override
    public void updateText(String text) {
        setElementTextContent(text);
        updateY();
    }

    @Override
    public void updateFill(Paint fill) {
        getElement().style.color = HtmlPaints.toHtmlCssPaint(fill);
    }

    @Override
    public void updateTextOrigin(VPos textOrigin) {
        updateY();
    }

    @Override
    public void updateX(Double x) {
        setElementStyleAttribute("left", toPx(x));
    }

    @Override
    public void updateY(Double y) {
        VPos textOrigin = getNode().getTextOrigin();
        if (textOrigin != null && textOrigin != VPos.TOP) {
            double clientHeight = getElement().clientHeight;
            if (textOrigin == VPos.CENTER)
                y = y - clientHeight / 2;
            else if (textOrigin == VPos.BOTTOM)
                y = y - clientHeight;
        }
        setElementStyleAttribute("top", toPx(y));
    }

    private void updateY() {
        updateY(getNode().getY());
    }

    @Override
    public void updateWrappingWidth(Double wrappingWidth) {
        if (wrappingWidth != null)
            setElementStyleAttribute("width", toPx(wrappingWidth));
        updateY();
    }

    @Override
    public void updateTextAlignment(TextAlignment textAlignment) {
        setElementStyleAttribute("text-align", toCssTextAlignment(textAlignment));
    }

    @Override
    public void updateFont(Font font) {
        setFontAttributes(font);
        updateY();
    }
}