package rosa.pageturner.client.viewers;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import rosa.pageturner.client.Console;

public class FsiViewer extends FsiBase {

    public FsiViewer() {
        super(Document.get().createElement("fsi-viewer"));
        Console.log("[FsiViewer] creating new viewer.");
    }

    /**
     * Change current image
     *
     * @param imageId ID of image on FSI server (EX: rosa/Arsenal3339/Arsenal3339.001r.tif)
     */
    public void changeImage(String imageId) {
        if (debug()) {
            console("Changing image -> " + imageId);
        }
        getElement().setAttribute("src", imageId);
        changeImage(getElement(), imageId);
    }

    /**
     * Destroys the given FSI Viewer JS 360 object. You should destroy the instance
     * before you remove the object spins <div> tag from the DOM tree.
     */
    public void destroy() {
        if (debug()) {
            console("Destroying FSI Viewer instance.");
        }
        destroy(getElement());
    }

    /**
     * @return string containing the FSI Viewer JS software version.
     */
    public String getVersion() {
        return getVersion(getElement());
    }

    /**
     * Reset the viewer to the initial magnification and rotation.
     */
    public void resetView() {
        resetView(getElement());
    }

    private native void changeImage(Element elem, String imageId) /*-{
        var param = { fpxsrc: imageId };
        elem.changeImage(param);
    }-*/;

    private native void destroy(Element elem) /*-{
        if (elem.@rosa.pageturner.client.viewers.FsiViewer::debug()) {
            log("Destroying viewer instance.");
        }
        elem.destroy();
    }-*/;

    private native String getVersion(Element elem) /*-{
        return elem.getVersion();
    }-*/;

    private native void resetView(Element elem) /*-{
        elem.resetView();
    }-*/;

}
