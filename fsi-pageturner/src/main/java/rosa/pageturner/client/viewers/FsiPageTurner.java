package rosa.pageturner.client.viewers;

import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Float;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;
import rosa.pageturner.client.Console;
import rosa.pageturner.client.model.Book;
import rosa.pageturner.client.model.Opening;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class FsiPageTurner extends Composite implements HasClickHandlers {
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

    private final Book model;

    public FsiPageTurner(Book book, String[] thumbSrcs, int width, int height) {
        this.model = book;

        Panel root = new FlowPanel("fsi-rosa-pageturner");

        left = new FsiViewer();
        right = new FsiViewer();
        thumbnailStrip = new FsiImageFlow();

        String fsiDir = book.fsiDirectory;
        fsiDir = fsiDir.endsWith("/") ? fsiDir : fsiDir + "/";

        Opening start = book.getOpening(0);

        // Set viewer options here
        Map<String, String> options = new HashMap<>(SHARED_VIEWER_OPTIONS);

        // Left viewer
        options.put("onInit", "initFsiViewerV");
        if (start.verso != null) {
            options.put("src", start.verso.id);
        }

        left.setId("rosa-pageturner-left");
        left.setOptions(options);
        left.setSize(width, height);

        // Right viewer
        options = new HashMap<>(SHARED_VIEWER_OPTIONS);
        options.put("onInit", "initFsiViewerR");
        if (start.recto != null) {
            options.put("src", start.recto.id);
        }

        right.setId("rosa-pageturner-right");
        right.setOptions(options);
        right.setSize(width, height);

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
        thumbnailStrip.setSize((width*2 + 2), 150);

        root.add(left);
        root.add(right);
        root.add(thumbnailStrip);

        initWidget(root);

        createViewerCallbacks(this);

        setPositions(width, height);
        fsiInit();
    }

    public boolean clickedOnVerso(ClickEvent event) {
        int x = event.getRelativeX(getElement());
        int y = event.getRelativeY(getElement());
        return x > left.getAbsoluteLeft() && x < (left.getAbsoluteLeft() + left.getOffsetWidth()) &&
                y > left.getAbsoluteTop() && y < (left.getAbsoluteTop() + left.getOffsetHeight());
    }

    public boolean clickedOnRecto(ClickEvent event) {
        int x = event.getRelativeX(getElement());
        int y = event.getRelativeY(getElement());
        return x > right.getAbsoluteLeft() && x < (right.getAbsoluteLeft() + right.getOffsetWidth()) &&
                y > right.getAbsoluteTop() && y < (right.getAbsoluteTop() + right.getOffsetHeight());
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
     * Handle events generated when a user clicks on a thumbnail. By default, this
     * widget will show the book opening containing the selected thumbnail. All event
     * handlers added for this event will then be run in order that they were added.
     *
     * NOTE: this method is called from native JavaScript, the 'imageFlowClick'
     * function.
     *
     * @param strImagePath ID of clicked image
     * @param nImageIndex index of clicked image in image list
     */
    private void onThumbnailClick(String strImagePath, int nImageIndex) {
        if (debug) {
            Console.log("[PageTurner#onThumbnailClick] " + nImageIndex + "\n" + strImagePath);
        }

        Opening selected = model.getOpening(strImagePath);
        if (selected.verso == null || selected.verso.missing) {
            left.changeImage(model.missingImage);
        } else {
            left.changeImage(selected.verso.id);
        }
        if (selected.recto == null || selected.recto.missing) {
            right.changeImage(model.missingImage);
        } else {
            right.changeImage(selected.recto.id);
        }
    }

    private native void createViewerCallbacks(FsiPageTurner el) /*-{
        $wnd.imageFlowClick = function(oInstance, idElement, nImageIndex, strImagePath) {
            el.@rosa.pageturner.client.viewers.FsiPageTurner::onThumbnailClick(Ljava/lang/String;I)(strImagePath, nImageIndex);
        }
    }-*/;

    private native void fsiInit() /*-{
        console.log("Initializing FSI tags.");
        $wnd.$FSI.initCustomTags();
    }-*/;

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    @Override
    public HandlerRegistration addClickHandler(ClickHandler handler) {
        return addDomHandler(handler, ClickEvent.getType());
    }
}
