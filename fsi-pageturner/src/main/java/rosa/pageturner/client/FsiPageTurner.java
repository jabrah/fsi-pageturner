package rosa.pageturner.client;

import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Float;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class FsiPageTurner extends Composite implements HasThumbnailClickHandler {
    private static final Map<String, String> SHARED_VIEWER_OPTIONS = new HashMap<>();
    private static final Map<String, String> IMAGE_FLOW_OPTIONS = new HashMap<>();

    static {
        SHARED_VIEWER_OPTIONS.put("enableZoom", "false");
        SHARED_VIEWER_OPTIONS.put("hideUI", "true");

        IMAGE_FLOW_OPTIONS.put("mirrorHeight", "0");
        IMAGE_FLOW_OPTIONS.put("curveHeight", "0.5");
        IMAGE_FLOW_OPTIONS.put("elementSpacing", "10");
        IMAGE_FLOW_OPTIONS.put("enableZoom", "false");
        IMAGE_FLOW_OPTIONS.put("paddingTop", "0");
        IMAGE_FLOW_OPTIONS.put("callBackStart", "imageFlowStart");
        IMAGE_FLOW_OPTIONS.put("callBackClick", "imageFlowClick");
    }

    private final FsiViewer left;
    private final FsiViewer right;
    private final FsiImageFlow thumbnailStrip;

    private boolean debug;

    private final List<ThumbnailClickHandler> thumbnailClickHandlers;

    public FsiPageTurner(String leftPage, String rightPage, String fsiDir, String[] thumbSrcs, int width, int height) {
        thumbnailClickHandlers = new ArrayList<>();

        Panel root = new FlowPanel("fsi-rosa-pageturner");

        left = new FsiViewer();
        right = new FsiViewer();
        thumbnailStrip = new FsiImageFlow();

        fsiDir = fsiDir.endsWith("/") ? fsiDir : fsiDir + "/";

        // Set viewer options here
        Map<String, String> options = new HashMap<>(SHARED_VIEWER_OPTIONS);

        // Left viewer
        options.put("onInit", "initFsiViewerV");
        options.put("src", fsiDir + leftPage);

        left.setId("rosa-pageturner-left");
        left.setOptions(options);
        left.setSize(width+"px", height+"px");

        // Right viewer
        options = new HashMap<>(SHARED_VIEWER_OPTIONS);
        options.put("onInit", "initFsiViewerR");
        options.put("src", fsiDir + rightPage);

        right.setId("rosa-pageturner-right");
        right.setOptions(options);
        right.setSize(width+"px", height+"px");

        // init thumbnail strip
        options = new HashMap<>(IMAGE_FLOW_OPTIONS);
        if (thumbSrcs == null || thumbSrcs.length == 0) {
            options.put("dir", fsiDir);
        } else {
            StringBuilder list = new StringBuilder();
            for (int i = 0; i < thumbSrcs.length; i++) {
                String src = thumbSrcs[i];
                if (src == null || src.isEmpty()) {
                    continue;
                }

                if (i > 0) {
                    list.append(",");
                }
                list.append(src);
            }
            options.put("imagesources", list.toString());
        }

        thumbnailStrip.setId("rosa-thumbnails");
        thumbnailStrip.setOptions(options);
        thumbnailStrip.setSize((width*2)+"px", "150px");

        root.add(left);
        root.add(right);
        root.add(thumbnailStrip);

        initWidget(root);

        createViewerCallbacks(this);

        setPositions(width, height);
        fsiInit();
    }

    private void setPositions(int viewer_width, int viewer_height) {
        if (left == null || right == null || thumbnailStrip == null) {
            return;
        }

        Style style = left.getElement().getStyle();
        style.setPosition(Position.RELATIVE);
        style.setLeft(0, Unit.PX);
        style.setTop(0, Unit.PX);
        style.setFloat(Float.LEFT);

        style = right.getElement().getStyle();
        style.setPosition(Position.RELATIVE);
        style.setLeft((viewer_width + 2), Unit.PX);
        style.setTop(0, Unit.PX);

        style = thumbnailStrip.getElement().getStyle();
        style.setLeft(0, Unit.PX);
        style.setTop(viewer_height, Unit.PX);
    }

    /**
     * Handle events generated when a user clicks on a thumbnail. All event handlers
     * added for this event will be run in order that they were added.
     *
     * NOTE: this method is called from native JavaScript, the 'imageFlowClick'
     * function.
     *
     * @param strImagePath ID of clicked image
     * @param nImageIndex index of clicked image in image list
     */
    private void onThumbnailClick(String strImagePath, int nImageIndex) {
        Console.log("[PageTurner#onThumbnailClick] " + nImageIndex + "\n" + strImagePath);
        for (ThumbnailClickHandler handler : thumbnailClickHandlers) {
            handler.handleThumbnailClick(strImagePath, nImageIndex);
        }
    }

    private native void createViewerCallbacks(FsiPageTurner el) /*-{
        var debug = el.@rosa.pageturner.client.FsiPageTurner::debug;

//        function initFsiViewerV(iInstance, id, oParameters) {
//            el.@rosa.pageturner.client.FsiPageTurner::left = iInstance;
//        }
//        function initFsiViewerR(iInstance, id, oParameters) {
//            el.@rosa.pageturner.client.FsiPageTurner::right = iInstance;
//        }
//        function imageFlowStarted(oInstance, idElement, srtDir, nImagesTotal) {
//            el.@rosa.pageturner.client.FsiPageTurner::thumbnailStrip = oInstance;
//        }

        $wnd.imageFlowClick = function(oInstance, idElement, nImageIndex, strImagePath) {
            console.log("[PageTurner] thumbnail clicked. " + strImagePath);

            el.@rosa.pageturner.client.FsiPageTurner::onThumbnailClick(Ljava/lang/String;I)(strImagePath, nImageIndex);

            var viewerV = el.@rosa.pageturner.client.FsiPageTurner::left;
            var viewerR = el.@rosa.pageturner.client.FsiPageTurner::right;

            if (!viewerV) {
                return;
            }

            var newOpening = $wnd.getOpening(strImagePath);
            if (!newOpening || !Array.isArray(newOpening)) {
                return;
            }

            if (newOpening.length > 0 && newOpening[0]) {
                jQuery(viewerV).show();
                viewerV.setAttribute("src", newOpening[0]);
                viewerV.changeImage({ fpxsrc: newOpening[0] });
            } else {
                jQuery(viewerV).hide();
            }
            if (newOpening.length > 1 && newOpening[1]) {
//                jQuery(viewerR).show();
                viewerR.style.display = "";
                viewerR.setAttribute("src", newOpening[1]);
                viewerR.changeImage({ fpxsrc: newOpening[1] });
            } else {
                jQuery(viewerR).hide();
            }
        }

//    **
//     * Get the opening of the clicked page.
//     *
//     * @param  {string} ID of clicked page
//     * @return array [verso.id recto.id]
//     *
        $wnd.getOpening = function(clickedPage) {
            if (!clickedPage || typeof clickedPage !== "string") {
                console.log("Error getting opening: input page does not exist or is not a string. ");
                return [];
            }

            // String image file extension
            var side = clickedPage.toLowerCase().charAt(clickedPage.length - 5);
            if (side === "r") {
                return [changePage(clickedPage, -1), clickedPage];
            } else if (side === "v") {
                return [clickedPage, changePage(clickedPage, 1)];
            } else {
                // Display single image
                return [clickedPage];
            }
        }

//    **
//     * TODO Doesn't handle frontmatter, endmatter, etc
//     *
//     * @param  {string} original    original image ID
//     * @param  {int}    incrementBy increment page number by this much
//     * @return {string}             new image ID
//     *
        function changePage(original, incrementBy) {
            if (!original || !incrementBy || typeof original !== "string") {
                console.log("Bad input paramenter. " + JSON.stringify({original: original, incrementBy: incrementBy}));
                return "";
            }

            var parts = original.split("\.");
            for (var i = 0; i < parts.length; i++) {
                var part = parts[i];
                if (!part.match(pageRegEx)) {
                    continue;
                }

                var matches = pageRegEx.exec(part);
                if (matches.length < 4) {
                    continue;
                }

//          [xv,x+1r]
//          increment +
//            on "v", change to "r", change page #
//            on "r", change to "v"
//          increment -
//            on "v", change to "r"
//            on "r", change to "v", change page #

                if (incrementBy > 0 && matches[3] === "v") {
                    matches[2] = parseInt(matches[2]) + 1;
                } else if (incrementBy < 0 && matches[3] === "r") {
                    matches[2] = parseInt(matches[2]) - 1;
                } else {  }

                matches[2] = padPageNumber(matches[2]);
                if (!matches[2]) {    // check for error
                    return "";
                }

                matches[3] = matches[3].toLowerCase() === "r" ? "v" : "r";
                parts[i] = matches.slice(1, matches.length).join("");
            }

            return parts.join(".");
        }

        function padPageNumber(pageNumber) {
            if (pageNumber <= 0) {
                return undefined;
            } else if (pageNumber < 10) {
                return "00" + pageNumber;
            } else if (pageNumber < 100) {
                return "0" + pageNumber;
            } else {
                return pageNumber;
            }
        }
    }-*/;

    private native void fsiInit() /*-{
        $wnd.$FSI.initCustomTags();
    }-*/;

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    @Override
    public void addThumbnailClickHandler(ThumbnailClickHandler handler) {
        thumbnailClickHandlers.add(handler);
    }

    public void clearThumbnailClickHandlers() {
        thumbnailClickHandlers.clear();
    }
}
