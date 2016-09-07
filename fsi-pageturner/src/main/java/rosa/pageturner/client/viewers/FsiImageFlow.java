package rosa.pageturner.client.viewers;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;

import java.util.Map;

public class FsiImageFlow extends FsiBase {

    public FsiImageFlow() {
        super(Document.get().createElement("fsi-imageflow"));
    }

    /**
     * Initializes the FSI Image Flow instance with the given parameters.
     *
     * @param params .
     */
    public void init(Map<String, String> params) {

    }

    /**
     * Destroys the FSI ImageFlow instance leaving the empty container <div> only.
     * You can re-use the same container calling {@link FsiImageFlow#init(Map)}
     */
    public void destroy() {
        destroy(getElement());
    }

    /**
     * Rotate the carousel until the image with the given index is in the foreground.
     *
     * @param index index of desired image in image list
     */
    public void focusImage(int index) {
        focusImage(getElement(), index);
    }

    /**
     * Rotate the carousel until the image with the given index is in the foreground and
     * zoom the given image afterwards.
     *
     * @param index index of desired image in image list
     */
    public void zoomImage(int index) {
        zoomImage(getElement(), index);
    }

    /**
     * Remove the zoomed image if any.
     */
    public void closeZoom() {
        closeZoom(getElement());
    }

// --------------------------------------------------------------------------------------------------------
// -------- JSNI calls to FSI API -------------------------------------------------------------------------
// --------------------------------------------------------------------------------------------------------

    private native void destroy(Element el) /*-{
        el.destroy();
    }-*/;

    private native void closeZoom(Element el) /*-{
        el.closeZoom();
    }-*/;

    private native void focusImage(Element el, int index) /*-{
        el.focusImage(index);
    }-*/;

    private native void zoomImage(Element el, int index) /*-{
        el.zoomImage(index);
    }-*/;

}
