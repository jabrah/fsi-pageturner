package rosa.pageturner.client;

import com.google.gwt.dom.client.Document;

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
        changeImage(this, imageId);
    }

    /**
     * Destroys the given FSI Viewer JS 360 object. You should destroy the instance
     * before you remove the object spins <div> tag from the DOM tree.
     */
    public void destroy() {
        if (debug()) {
            console("Destroying FSI Viewer instance.");
        }
        destroy(this);
    }

    /**
     * @return string containing the FSI Viewer JS software version.
     */
    public String getVersion() {
        return getVersion(this);
    }

    /**
     * Reset the viewer to the initial magnification and rotation.
     */
    public void resetView() {
        resetView(this);
    }

    private native void changeImage(FsiViewer elem, String imageId) /*-{
        var param = { fpxsrc: imageId };
        elem.changeImage(param);
    }-*/;

    private native void destroy(FsiViewer elem) /*-{
        if (this.@rosa.pageturner.client.FsiViewer::debug()) {
            log("Destroying viewer instance.");
        }
        elem.destroy();
    }-*/;

    private native String getVersion(FsiViewer elem) /*-{
        return elem.getVersion();
    }-*/;

    private native void resetView(FsiViewer elem) /*-{
        elem.resetView();
    }-*/;

}
