package rosa.pageturner.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.RootPanel;
import rosa.pageturner.client.model.Book;
import rosa.pageturner.client.model.Opening;
import rosa.pageturner.client.model.Page;
import rosa.pageturner.client.viewers.FsiPageTurner;

import java.util.ArrayList;
import java.util.List;

public class Main implements EntryPoint {
    private static final String missing = "rose/missing_image.tif";
    private static final String list =
            "rose/Arsenal3339/Arsenal3339.frontmatter.pastedown.tif," +
                    "rose/Arsenal3339/Arsenal3339.frontmatter.flyleaf.01r.tif,rose/Arsenal3339/Arsenal3339.frontmatter.flyleaf.01v.tif," +
                    "rose/Arsenal3339/Arsenal3339.frontmatter.flyleaf.02r.tif,rose/Arsenal3339/Arsenal3339.frontmatter.flyleaf.02v.tif," +
                    "rose/Arsenal3339/Arsenal3339.001r.tif,rose/Arsenal3339/Arsenal3339.001v.tif,rose/Arsenal3339/Arsenal3339.002r.tif," +
                    "rose/Arsenal3339/Arsenal3339.002v.tif,rose/Arsenal3339/Arsenal3339.003r.tif,rose/Arsenal3339/Arsenal3339.003v.tif," +
                    "rose/Arsenal3339/Arsenal3339.004r.tif,rose/Arsenal3339/Arsenal3339.004v.tif,rose/Arsenal3339/Arsenal3339.005r.tif," +
                    "rose/Arsenal3339/Arsenal3339.005v.tif,rose/Arsenal3339/Arsenal3339.006r.tif,rose/Arsenal3339/Arsenal3339.006v.tif," +
                    "rose/Arsenal3339/Arsenal3339.007r.tif,rose/Arsenal3339/Arsenal3339.007v.tif,rose/Arsenal3339/Arsenal3339.008r.tif," +
                    "rose/Arsenal3339/Arsenal3339.008v.tif,rose/Arsenal3339/Arsenal3339.009r.tif,rose/Arsenal3339/Arsenal3339.009v.tif," +
                    "rose/Arsenal3339/Arsenal3339.010r.tif,rose/Arsenal3339/Arsenal3339.010v.tif,rose/Arsenal3339/Arsenal3339.011r.tif," +
                    "rose/Arsenal3339/Arsenal3339.011v.tif,rose/Arsenal3339/Arsenal3339.012r.tif,rose/Arsenal3339/Arsenal3339.012v.tif," +
                    "rose/Arsenal3339/Arsenal3339.013r.tif,rose/Arsenal3339/Arsenal3339.013v.tif,rose/Arsenal3339/Arsenal3339.014r.tif," +
                    "rose/Arsenal3339/Arsenal3339.014v.tif,rose/Arsenal3339/Arsenal3339.015r.tif,rose/Arsenal3339/Arsenal3339.015v.tif," +
                    "rose/Arsenal3339/Arsenal3339.016r.tif,rose/Arsenal3339/Arsenal3339.016v.tif,rose/Arsenal3339/Arsenal3339.017r.tif," +
                    "rose/Arsenal3339/Arsenal3339.017v.tif,rose/Arsenal3339/Arsenal3339.018r.tif,rose/Arsenal3339/Arsenal3339.018v.tif,";

    public void onModuleLoad() {
        GWT.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
            @Override
            public void onUncaughtException(Throwable e) {
                Console.log("An unexpected error has occurred.\n" + e.getMessage());
            }
        });

        Book fakeBook = fakeBook();
        final FsiPageTurner pageTurner = new FsiPageTurner(fakeBook, fakeBook.getPagesList().split(","), 400, 600);
        pageTurner.setDebug(false);

        pageTurner.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (pageTurner.clickedOnVerso(event)) {
                    Console.log("Verso clicked!");
                } else if (pageTurner.clickedOnRecto(event)) {
                    Console.log("Recto clicked!");
                }
            }
        });

        RootPanel.get().add(pageTurner);
    }

    private Book fakeBook() {
        List<Opening> openings = new ArrayList<>();
        openings.add(new Opening(
                null,
                new Page("rose/Arsenal3339/Arsenal3339.binding.frontcover.tif", "Binding Front cover", false, 600, 400),
                "",
                0
        ));
        String[] parts = list.split(",");
        for (int i = 0; i < parts.length - 2;) {
            String left = parts[i++];
            String right = parts[i++];

            openings.add(new Opening(
                    new Page(left, left, false, 600, 400),
                    new Page(right, right, false, 600, 400),
                    "Label " + i,
                    openings.size()
            ));
        }
        return new Book("rose/Arsenal3339/", openings, missing);
    }

}
