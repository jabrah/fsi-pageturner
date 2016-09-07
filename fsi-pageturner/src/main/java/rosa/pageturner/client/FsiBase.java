package rosa.pageturner.client;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.FocusWidget;

import java.util.Map;
import java.util.Map.Entry;

public class FsiBase extends FocusWidget {

    private boolean debug;

    public FsiBase(Element elem) {
        super(elem);
    }

    protected native void console(String message) /*-{console.log("mooo");
        var id = this.@rosa.pageturner.client.FsiViewer::getElement();
        console.log("[FsiViewer(" + this.id + ")] " + message);
    }-*/;

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    protected boolean debug() {
        return debug;
    }

    public void setId(String id) {
        if (debug()) {
            console("Setting id: " + id);
        }

        getElement().setId(id);
    }

    public String getId() {
        return getElement().getId();
    }

    public void setOptions(Map<String, String> options) {
        for (Entry<String, String> option : options.entrySet()) {
            if (debug()) {
                console("Option: " + option.getKey() + "::" + option.getValue());
            }
            getElement().setAttribute(option.getKey(), option.getValue());
        }
    }

    public void setSize(String width, String height) {
        setSize(getElement(), width, height);
    }

    private final native void setSize(Element elem, String width, String height) /*-{
        elem.style.width = width;
        elem.style.height = height;
    }-*/;


}
