package rosa.pageturner.client.viewers;

import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import rosa.pageturner.client.model.Opening;

public interface PageTurner {
    /**
     * @return the opening currently displayed in this PageTurner
     */
    Opening currentOpening();

    /**
     * Add an event handler to be called whenever the opening is changed
     * in the PageTurner. When the opening is changed, all of these
     * handlers will be called in the order they were added to the
     * PageTurner.
     *
     * @param handler change handler
     * @return handler registration object
     */
    HandlerRegistration addOpeningChangedHandler(ValueChangeHandler<Opening> handler);
}
