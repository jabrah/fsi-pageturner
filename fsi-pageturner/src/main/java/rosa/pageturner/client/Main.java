package rosa.pageturner.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.google.gwt.user.client.ui.RootPanel;

public class Main implements EntryPoint {

    public void onModuleLoad() {
        GWT.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
            @Override
            public void onUncaughtException(Throwable e) {
                Console.log("An unexpected error has occurred.\n" + e.getMessage());
            }
        });

        FsiPageTurner pageTurner = new FsiPageTurner(
                "LudwigXV7.001v.tif",
                "LudwigXV7.002r.tif",
                "rose/LudwigXV7/",
                new String[] {},
                400,
                600);

        RootPanel.get().add(pageTurner);
    }

}
