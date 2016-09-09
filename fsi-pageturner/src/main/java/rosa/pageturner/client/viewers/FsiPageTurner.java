package rosa.pageturner.client.viewers;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;
import rosa.pageturner.client.model.Page;
import rosa.pageturner.client.util.Console;
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
        SHARED_VIEWER_OPTIONS.put("plugins", "FullScreen, Resize");

        IMAGE_FLOW_OPTIONS.put("mirrorHeight", "0");
        IMAGE_FLOW_OPTIONS.put("curveHeight", "0.5");
        IMAGE_FLOW_OPTIONS.put("elementSpacing", "10");
        IMAGE_FLOW_OPTIONS.put("enableZoom", "false");
        IMAGE_FLOW_OPTIONS.put("paddingTop", "0");
        IMAGE_FLOW_OPTIONS.put("callBackStart", "imageFlowStart");
        IMAGE_FLOW_OPTIONS.put("callBackClick", "imageFlowClick");
    }

    private final FsiViewer zoomView;
    private final FsiViewer left;
    private final FsiViewer right;
    private final FsiImageFlow thumbnailStrip;
    private final Panel controls;

    private Anchor closeBtn;

    private boolean debug;
    private boolean zoomed;

    private final Book model;

    public FsiPageTurner(Book book, String[] thumbSrcs, int width, int height) {
        this.model = book;

        Panel root = new FlowPanel("fsi-rosa-pageturner");

        controls = new FlowPanel("div");
        thumbnailStrip = new FsiImageFlow();

        controls.setStyleName("pageturner-controls");
        thumbnailStrip.setStyleName("pageturner-thumbs");

        String fsiDir = book.fsiDirectory;
        fsiDir = fsiDir.endsWith("/") ? fsiDir : fsiDir + "/";

        Opening start = book.getOpening(0);

        // Set viewer options here
        Map<String, String> options = new HashMap<>(SHARED_VIEWER_OPTIONS);

// ---------------------------------------------------------------------------------------------------------------------
// ---------- Left viewer ----------------------------------------------------------------------------------------------
// ---------------------------------------------------------------------------------------------------------------------
        left = new FsiViewer();

        options.put("onInit", "initFsiViewerV");
        if (start.verso != null) {
            options.put("src", start.verso.id);
        }

        left.setStyleName("viewer");
        left.addStyleName("pageturner-left");
        left.setId("rosa-pageturner-left");
        left.setOptions(options);
        left.setSize(width, height);

        left.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (!zoomed) {
                    zoomOnPage(left);
                }
            }
        });

// ---------------------------------------------------------------------------------------------------------------------
// ----------- Right viewer --------------------------------------------------------------------------------------------
// ---------------------------------------------------------------------------------------------------------------------
        right = new FsiViewer();

        options = new HashMap<>(SHARED_VIEWER_OPTIONS);
        options.put("onInit", "initFsiViewerR");
        if (start.recto != null) {
            options.put("src", start.recto.id);
        }

        right.setStyleName("viewer");
        right.addStyleName("pageturner-right");
        right.setId("rosa-pageturner-right");
        right.setOptions(options);
        right.setSize(width, height);

        right.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (!zoomed) {
                    zoomOnPage(right);
                }
            }
        });

// ---------------------------------------------------------------------------------------------------------------------
// ----------- Thumbnails ----------------------------------------------------------------------------------------------
// ---------------------------------------------------------------------------------------------------------------------
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

// ---------------------------------------------------------------------------------------------------------------------
// ----------- Zoom viewer ---------------------------------------------------------------------------------------------
// ---------------------------------------------------------------------------------------------------------------------
        zoomView = new FsiViewer();
        options = new HashMap<>();
        options.put("plugins", "FullScreen, Resize");
        options.put("hideUI", "false");
        options.put("enableZoom", "true");
        options.put("src", book.missingImage.id);
        zoomView.setId("rosa-pageturner-zoomview");
        zoomView.setOptions(options);
        zoomView.setSize(width * 2 + 2, height);
        zoomView.setVisible(false);

        root.add(left);
        root.add(right);
        root.add(controls);
        root.add(thumbnailStrip);
        root.add(zoomView);

        initWidget(root);

        createViewerCallbacks(this);

        setControls();
        setPositions(width, height);
        fsiInit();
    }

    private void zoomOnPage(FsiViewer viewer) {
        zoomed = true;
        String clickedImage = viewer.getElement().getAttribute("src");
        zoomView.changeImage(clickedImage);
        closeBtn.setVisible(true);
        zoomView.setVisible(true);
    }

    /**
     * @return current opening visible in viewer, NULL if none was found
     */
    public Opening currentOpening() {
        String lpage = left.getElement().getAttribute("src");
        String rpage = right.getElement().getAttribute("src");

        if (lpage != null && !lpage.isEmpty() && !lpage.equals(model.missingImage.id)) {
            return model.getOpening(lpage);
        } else if (rpage != null && !rpage.isEmpty() && !rpage.equals(model.missingImage.id)) {
            return model.getOpening(rpage);
        } else {
            return null;
        }
    }

    private void setPositions(int viewer_width, int viewer_height) {
        if (left == null || right == null || thumbnailStrip == null) {
            return;
        }

        Style style = controls.getElement().getStyle();
        style.setTop(viewer_height, Unit.PX);
        style.setWidth((viewer_width * 2 + 2), Unit.PX);

        style = right.getElement().getStyle();
        style.setLeft(viewer_width + 2, Unit.PX);

        style = zoomView.getElement().getStyle();
        style.setPosition(Position.ABSOLUTE);
        style.setTop(0, Unit.PX);
        style.setLeft(0, Unit.PX);
    }

    private void setControls() {
        closeBtn = newControl(new String[]{"fa", "fa-2x",  "fa-times"}, "Close zoom view", new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (zoomed) {
                    closeBtn.setVisible(false);
                    zoomView.setVisible(false);
                    zoomed = false;
                }
            }
        });
        closeBtn.setStyleName("pageturner-btn-zoom-close");
        closeBtn.setVisible(false);
        controls.add(closeBtn);

        controls.add(newControl(new String[]{"fa", "fa-lg", "fa-chevron-left"}, "Previous page", new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                int prev = currentOpening().position - 1;
                if (prev >= 0) {
                    Opening opening = model.getOpening(prev);
                    changeToOpening(opening);
                    focusThumb(opening.verso);
                }
            }
        }));
        controls.add(newControl(new String[]{"fa", "fa-lg", "fa-chevron-right"}, "Next page", new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                int next = currentOpening().position + 1;
                if (next < model.openings.size()) {
                    Opening opening = model.getOpening(next);
                    changeToOpening(opening);
                    focusThumb(opening.verso);
                }
            }
        }));
    }

    /**
     * Create a new UI control for the page turner. This control is not bound to the DOM
     * during this method, so it can be inserted anywhere.
     *
     * @param iconClasses array of class names to specify the icon. EX: ["fa", "fa-lg", "fa-chevron-down"]
     * @param title icon title/tool tip text
     * @param onClick onclick callback
     * @return the control element that can be added to the UI
     */
    private Anchor newControl(String[] iconClasses, String title, ClickHandler onClick) {
        Anchor a = new Anchor();
        a.setHref("javascript:;");
        a.setTitle(title);

        if (iconClasses != null) {
            Element icon = Document.get().createElement("i");
            for (String name : iconClasses) {
                icon.addClassName(name);
            }
            a.getElement().appendChild(icon);
            a.addClickHandler(onClick);
        }

        return a;
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
        changeToOpening(model.getOpening(strImagePath));
    }

    private void changeToOpening(Opening opening) {
        if (opening.verso == null || opening.verso.missing) {
            left.changeImage(model.missingImage.id);
        } else {
            left.changeImage(opening.verso.id);
        }
        if (opening.recto == null || opening.recto.missing) {
            right.changeImage(model.missingImage.id);
        } else {
            right.changeImage(opening.recto.id);
        }
    }

    private void focusThumb(Page focusPage) {
        int index = model.getPagePosition(focusPage.id);
        if (index >= 0) {
            thumbnailStrip.focusImage(index);
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
