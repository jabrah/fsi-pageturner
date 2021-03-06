package rosa.fsi.demo.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import rosa.pageturner.client.model.Book;
import rosa.pageturner.client.model.Opening;
import rosa.pageturner.client.model.Page;
import rosa.pageturner.client.util.Console;
import rosa.pageturner.client.viewers.FsiPageTurner;

import java.util.ArrayList;
import java.util.List;

public class FsiDemo implements EntryPoint {
    private static final Page missing = new Page("rose/missing_image.tif", "label", false, 3200, 4000);
    private static final String list =
            "rose/Morgan948/Morgan948.frontmatter.pastedown.tif,rose/Morgan948/Morgan948.frontmatter.flyleaf.01r.tif,rose/missing_image.tif,rose/missing_image.tif,rose/missing_image.tif,rose/Morgan948/Morgan948.002r.tif,rose/missing_image.tif,rose/missing_image.tif,rose/Morgan948/Morgan948.003v.tif,rose/Morgan948/Morgan948.004r.tif,rose/Morgan948/Morgan948.004v.tif,rose/Morgan948/Morgan948.005r.tif,rose/Morgan948/Morgan948.005v.tif,rose/Morgan948/Morgan948.006r.tif,rose/Morgan948/Morgan948.006v.tif,rose/Morgan948/Morgan948.007r.tif,rose/Morgan948/Morgan948.007v.tif,rose/Morgan948/Morgan948.008r.tif,rose/Morgan948/Morgan948.008v.tif,rose/Morgan948/Morgan948.009r.tif,rose/Morgan948/Morgan948.009v.tif,rose/Morgan948/Morgan948.010r.tif,rose/Morgan948/Morgan948.010v.tif,rose/Morgan948/Morgan948.011r.tif,rose/Morgan948/Morgan948.011v.tif,rose/Morgan948/Morgan948.012r.tif,rose/Morgan948/Morgan948.012v.tif,rose/Morgan948/Morgan948.013r.tif,rose/Morgan948/Morgan948.013v.tif,rose/Morgan948/Morgan948.014r.tif,rose/Morgan948/Morgan948.014v.tif,rose/Morgan948/Morgan948.015r.tif,rose/Morgan948/Morgan948.015v.tif,rose/Morgan948/Morgan948.016r.tif,rose/Morgan948/Morgan948.016v.tif,rose/Morgan948/Morgan948.017r.tif,rose/Morgan948/Morgan948.017v.tif,rose/Morgan948/Morgan948.018r.tif,rose/Morgan948/Morgan948.018v.tif,rose/Morgan948/Morgan948.019r.tif,rose/Morgan948/Morgan948.019v.tif,rose/Morgan948/Morgan948.020r.tif,rose/Morgan948/Morgan948.020v.tif,rose/Morgan948/Morgan948.021r.tif,rose/Morgan948/Morgan948.021v.tif,rose/Morgan948/Morgan948.022r.tif,rose/Morgan948/Morgan948.022v.tif,rose/Morgan948/Morgan948.023r.tif,rose/Morgan948/Morgan948.023v.tif,rose/Morgan948/Morgan948.024r.tif,rose/Morgan948/Morgan948.024v.tif,rose/Morgan948/Morgan948.025r.tif,rose/Morgan948/Morgan948.025v.tif,rose/Morgan948/Morgan948.026r.tif,rose/Morgan948/Morgan948.026v.tif,rose/Morgan948/Morgan948.027r.tif,rose/Morgan948/Morgan948.027v.tif,rose/Morgan948/Morgan948.028r.tif,rose/Morgan948/Morgan948.028v.tif,rose/Morgan948/Morgan948.029r.tif,rose/Morgan948/Morgan948.029v.tif,rose/Morgan948/Morgan948.030r.tif,rose/Morgan948/Morgan948.030v.tif,rose/Morgan948/Morgan948.031r.tif,rose/Morgan948/Morgan948.031v.tif,rose/Morgan948/Morgan948.032r.tif,rose/Morgan948/Morgan948.032v.tif,rose/Morgan948/Morgan948.033r.tif,rose/Morgan948/Morgan948.033v.tif,rose/Morgan948/Morgan948.034r.tif,rose/Morgan948/Morgan948.034v.tif,rose/Morgan948/Morgan948.035r.tif,rose/Morgan948/Morgan948.035v.tif,rose/Morgan948/Morgan948.036r.tif,rose/Morgan948/Morgan948.036v.tif,rose/Morgan948/Morgan948.037r.tif,rose/Morgan948/Morgan948.037v.tif,rose/Morgan948/Morgan948.038r.tif,rose/Morgan948/Morgan948.038v.tif,rose/Morgan948/Morgan948.039r.tif,rose/Morgan948/Morgan948.039v.tif,rose/Morgan948/Morgan948.040r.tif,rose/Morgan948/Morgan948.040v.tif,rose/Morgan948/Morgan948.041r.tif,rose/Morgan948/Morgan948.041v.tif,rose/Morgan948/Morgan948.042r.tif,rose/Morgan948/Morgan948.042v.tif,rose/Morgan948/Morgan948.043r.tif,rose/Morgan948/Morgan948.043v.tif,rose/Morgan948/Morgan948.044r.tif,rose/Morgan948/Morgan948.044v.tif,rose/Morgan948/Morgan948.045r.tif,rose/Morgan948/Morgan948.045v.tif,rose/Morgan948/Morgan948.046r.tif,rose/Morgan948/Morgan948.046v.tif,rose/Morgan948/Morgan948.047r.tif,rose/Morgan948/Morgan948.047v.tif,rose/Morgan948/Morgan948.048r.tif,rose/Morgan948/Morgan948.048v.tif,rose/Morgan948/Morgan948.049r.tif,rose/Morgan948/Morgan948.049v.tif,rose/Morgan948/Morgan948.050r.tif,rose/Morgan948/Morgan948.050v.tif,rose/Morgan948/Morgan948.051r.tif,rose/Morgan948/Morgan948.051v.tif,rose/Morgan948/Morgan948.052r.tif,rose/Morgan948/Morgan948.052v.tif,rose/Morgan948/Morgan948.053r.tif,rose/Morgan948/Morgan948.053v.tif,rose/Morgan948/Morgan948.054r.tif,rose/Morgan948/Morgan948.054v.tif,rose/Morgan948/Morgan948.055r.tif,rose/Morgan948/Morgan948.055v.tif,rose/Morgan948/Morgan948.056r.tif,rose/Morgan948/Morgan948.056v.tif,rose/Morgan948/Morgan948.057r.tif,rose/Morgan948/Morgan948.057v.tif,rose/Morgan948/Morgan948.058r.tif,rose/Morgan948/Morgan948.058v.tif,rose/Morgan948/Morgan948.059r.tif,rose/Morgan948/Morgan948.059v.tif,rose/Morgan948/Morgan948.060r.tif,rose/Morgan948/Morgan948.060v.tif,rose/Morgan948/Morgan948.061r.tif,rose/Morgan948/Morgan948.061v.tif,rose/Morgan948/Morgan948.062r.tif,rose/Morgan948/Morgan948.062v.tif,rose/Morgan948/Morgan948.063r.tif,rose/Morgan948/Morgan948.063v.tif,rose/Morgan948/Morgan948.064r.tif,rose/Morgan948/Morgan948.064v.tif,rose/Morgan948/Morgan948.065r.tif,rose/Morgan948/Morgan948.065v.tif,rose/Morgan948/Morgan948.066r.tif,rose/Morgan948/Morgan948.066v.tif,rose/Morgan948/Morgan948.067r.tif,rose/Morgan948/Morgan948.067v.tif,rose/Morgan948/Morgan948.068r.tif,rose/Morgan948/Morgan948.068v.tif,rose/Morgan948/Morgan948.069r.tif,rose/Morgan948/Morgan948.069v.tif,rose/Morgan948/Morgan948.070r.tif,rose/Morgan948/Morgan948.070v.tif,rose/Morgan948/Morgan948.071r.tif,rose/Morgan948/Morgan948.071v.tif,rose/Morgan948/Morgan948.072r.tif,rose/Morgan948/Morgan948.072v.tif,rose/Morgan948/Morgan948.073r.tif,rose/Morgan948/Morgan948.073v.tif,rose/Morgan948/Morgan948.074r.tif,rose/Morgan948/Morgan948.074v.tif,rose/Morgan948/Morgan948.075r.tif,rose/Morgan948/Morgan948.075v.tif,rose/Morgan948/Morgan948.076r.tif,rose/Morgan948/Morgan948.076v.tif,rose/Morgan948/Morgan948.077r.tif,rose/Morgan948/Morgan948.077v.tif,rose/Morgan948/Morgan948.078r.tif,rose/Morgan948/Morgan948.078v.tif,rose/Morgan948/Morgan948.079r.tif,rose/Morgan948/Morgan948.079v.tif,rose/Morgan948/Morgan948.080r.tif,rose/Morgan948/Morgan948.080v.tif,rose/Morgan948/Morgan948.081r.tif,rose/Morgan948/Morgan948.081v.tif,rose/Morgan948/Morgan948.082r.tif,rose/Morgan948/Morgan948.082v.tif,rose/Morgan948/Morgan948.083r.tif,rose/Morgan948/Morgan948.083v.tif,rose/Morgan948/Morgan948.084r.tif,rose/Morgan948/Morgan948.084v.tif,rose/Morgan948/Morgan948.085r.tif,rose/Morgan948/Morgan948.085v.tif,rose/Morgan948/Morgan948.086r.tif,rose/Morgan948/Morgan948.086v.tif,rose/Morgan948/Morgan948.087r.tif,rose/Morgan948/Morgan948.087v.tif,rose/Morgan948/Morgan948.088r.tif,rose/Morgan948/Morgan948.088v.tif,rose/Morgan948/Morgan948.089r.tif,rose/Morgan948/Morgan948.089v.tif,rose/Morgan948/Morgan948.090r.tif,rose/Morgan948/Morgan948.090v.tif,rose/Morgan948/Morgan948.091r.tif,rose/Morgan948/Morgan948.091v.tif,rose/Morgan948/Morgan948.092r.tif,rose/Morgan948/Morgan948.092v.tif,rose/Morgan948/Morgan948.093r.tif,rose/Morgan948/Morgan948.093v.tif,rose/Morgan948/Morgan948.094r.tif,rose/Morgan948/Morgan948.094v.tif,rose/Morgan948/Morgan948.095r.tif,rose/Morgan948/Morgan948.095v.tif,rose/Morgan948/Morgan948.096r.tif,rose/Morgan948/Morgan948.096v.tif,rose/Morgan948/Morgan948.097r.tif,rose/Morgan948/Morgan948.097v.tif,rose/Morgan948/Morgan948.098r.tif,rose/Morgan948/Morgan948.098v.tif,rose/Morgan948/Morgan948.099r.tif,rose/Morgan948/Morgan948.099v.tif,rose/Morgan948/Morgan948.100r.tif,rose/Morgan948/Morgan948.100v.tif,rose/Morgan948/Morgan948.101r.tif,rose/Morgan948/Morgan948.101v.tif,rose/Morgan948/Morgan948.102r.tif,rose/Morgan948/Morgan948.102v.tif,rose/Morgan948/Morgan948.103r.tif,rose/Morgan948/Morgan948.103v.tif,rose/Morgan948/Morgan948.104r.tif,rose/Morgan948/Morgan948.104v.tif,rose/Morgan948/Morgan948.105r.tif,rose/Morgan948/Morgan948.105v.tif,rose/Morgan948/Morgan948.106r.tif,rose/Morgan948/Morgan948.106v.tif,rose/Morgan948/Morgan948.107r.tif,rose/Morgan948/Morgan948.107v.tif,rose/Morgan948/Morgan948.108r.tif,rose/Morgan948/Morgan948.108v.tif,rose/Morgan948/Morgan948.109r.tif,rose/Morgan948/Morgan948.109v.tif,rose/Morgan948/Morgan948.110r.tif,rose/Morgan948/Morgan948.110v.tif,rose/Morgan948/Morgan948.111r.tif,rose/Morgan948/Morgan948.111v.tif,rose/Morgan948/Morgan948.112r.tif,rose/Morgan948/Morgan948.112v.tif,rose/Morgan948/Morgan948.113r.tif,rose/Morgan948/Morgan948.113v.tif,rose/Morgan948/Morgan948.114r.tif,rose/Morgan948/Morgan948.114v.tif,rose/Morgan948/Morgan948.115r.tif,rose/Morgan948/Morgan948.115v.tif,rose/Morgan948/Morgan948.116r.tif,rose/Morgan948/Morgan948.116v.tif,rose/Morgan948/Morgan948.117r.tif,rose/Morgan948/Morgan948.117v.tif,rose/Morgan948/Morgan948.118r.tif,rose/Morgan948/Morgan948.118v.tif,rose/Morgan948/Morgan948.119r.tif,rose/Morgan948/Morgan948.119v.tif,rose/Morgan948/Morgan948.120r.tif,rose/Morgan948/Morgan948.120v.tif,rose/Morgan948/Morgan948.121r.tif,rose/Morgan948/Morgan948.121v.tif,rose/Morgan948/Morgan948.122r.tif,rose/Morgan948/Morgan948.122v.tif,rose/Morgan948/Morgan948.123r.tif,rose/Morgan948/Morgan948.123v.tif,rose/Morgan948/Morgan948.124r.tif,rose/Morgan948/Morgan948.124v.tif,rose/Morgan948/Morgan948.125r.tif,rose/Morgan948/Morgan948.125v.tif,rose/Morgan948/Morgan948.126r.tif,rose/Morgan948/Morgan948.126v.tif,rose/Morgan948/Morgan948.127r.tif,rose/Morgan948/Morgan948.127v.tif,rose/Morgan948/Morgan948.128r.tif,rose/Morgan948/Morgan948.128v.tif,rose/Morgan948/Morgan948.129r.tif,rose/Morgan948/Morgan948.129v.tif,rose/Morgan948/Morgan948.130r.tif,rose/Morgan948/Morgan948.130v.tif,rose/Morgan948/Morgan948.131r.tif,rose/Morgan948/Morgan948.131v.tif,rose/Morgan948/Morgan948.132r.tif,rose/Morgan948/Morgan948.132v.tif,rose/Morgan948/Morgan948.133r.tif,rose/Morgan948/Morgan948.133v.tif,rose/Morgan948/Morgan948.134r.tif,rose/Morgan948/Morgan948.134v.tif,rose/Morgan948/Morgan948.135r.tif,rose/Morgan948/Morgan948.135v.tif,rose/Morgan948/Morgan948.136r.tif,rose/Morgan948/Morgan948.136v.tif,rose/Morgan948/Morgan948.137r.tif,rose/Morgan948/Morgan948.137v.tif,rose/Morgan948/Morgan948.138r.tif,rose/Morgan948/Morgan948.138v.tif,rose/Morgan948/Morgan948.139r.tif,rose/Morgan948/Morgan948.139v.tif,rose/Morgan948/Morgan948.140r.tif,rose/Morgan948/Morgan948.140v.tif,rose/Morgan948/Morgan948.141r.tif,rose/Morgan948/Morgan948.141v.tif,rose/Morgan948/Morgan948.142r.tif,rose/Morgan948/Morgan948.142v.tif,rose/Morgan948/Morgan948.143r.tif,rose/Morgan948/Morgan948.143v.tif,rose/Morgan948/Morgan948.144r.tif,rose/Morgan948/Morgan948.144v.tif,rose/Morgan948/Morgan948.145r.tif,rose/Morgan948/Morgan948.145v.tif,rose/Morgan948/Morgan948.146r.tif,rose/Morgan948/Morgan948.146v.tif,rose/Morgan948/Morgan948.147r.tif,rose/Morgan948/Morgan948.147v.tif,rose/Morgan948/Morgan948.148r.tif,rose/Morgan948/Morgan948.148v.tif,rose/Morgan948/Morgan948.149r.tif,rose/Morgan948/Morgan948.149v.tif,rose/Morgan948/Morgan948.150r.tif,rose/Morgan948/Morgan948.150v.tif,rose/Morgan948/Morgan948.151r.tif,rose/Morgan948/Morgan948.151v.tif,rose/Morgan948/Morgan948.152r.tif,rose/Morgan948/Morgan948.152v.tif,rose/Morgan948/Morgan948.153r.tif,rose/Morgan948/Morgan948.153v.tif,rose/Morgan948/Morgan948.154r.tif,rose/Morgan948/Morgan948.154v.tif,rose/Morgan948/Morgan948.155r.tif,rose/Morgan948/Morgan948.155v.tif,rose/Morgan948/Morgan948.156r.tif,rose/Morgan948/Morgan948.156v.tif,rose/Morgan948/Morgan948.157r.tif,rose/Morgan948/Morgan948.157v.tif,rose/Morgan948/Morgan948.158r.tif,rose/Morgan948/Morgan948.158v.tif,rose/Morgan948/Morgan948.159r.tif,rose/Morgan948/Morgan948.159v.tif,rose/Morgan948/Morgan948.160r.tif,rose/Morgan948/Morgan948.160v.tif,rose/Morgan948/Morgan948.161r.tif,rose/Morgan948/Morgan948.161v.tif,rose/Morgan948/Morgan948.162r.tif,rose/Morgan948/Morgan948.162v.tif,rose/Morgan948/Morgan948.163r.tif,rose/Morgan948/Morgan948.163v.tif,rose/Morgan948/Morgan948.164r.tif,rose/missing_image.tif,rose/Morgan948/Morgan948.165r.tif,rose/Morgan948/Morgan948.165v.tif,rose/Morgan948/Morgan948.166r.tif,rose/Morgan948/Morgan948.166v.tif,rose/Morgan948/Morgan948.167r.tif,rose/Morgan948/Morgan948.167v.tif,rose/Morgan948/Morgan948.168r.tif,rose/Morgan948/Morgan948.168v.tif,rose/Morgan948/Morgan948.169r.tif,rose/Morgan948/Morgan948.169v.tif,rose/Morgan948/Morgan948.170r.tif,rose/Morgan948/Morgan948.170v.tif,rose/Morgan948/Morgan948.171r.tif,rose/Morgan948/Morgan948.171v.tif,rose/Morgan948/Morgan948.172r.tif,rose/Morgan948/Morgan948.172v.tif,rose/Morgan948/Morgan948.173r.tif,rose/Morgan948/Morgan948.173v.tif,rose/Morgan948/Morgan948.174r.tif,rose/Morgan948/Morgan948.174v.tif,rose/Morgan948/Morgan948.175r.tif,rose/Morgan948/Morgan948.175v.tif,rose/Morgan948/Morgan948.176r.tif,rose/Morgan948/Morgan948.176v.tif,rose/Morgan948/Morgan948.177r.tif,rose/Morgan948/Morgan948.177v.tif,rose/Morgan948/Morgan948.178r.tif,rose/Morgan948/Morgan948.178v.tif,rose/Morgan948/Morgan948.179r.tif,rose/Morgan948/Morgan948.179v.tif,rose/Morgan948/Morgan948.180r.tif,rose/Morgan948/Morgan948.180v.tif,rose/Morgan948/Morgan948.181r.tif,rose/Morgan948/Morgan948.181v.tif,rose/Morgan948/Morgan948.182r.tif,rose/Morgan948/Morgan948.182v.tif,rose/Morgan948/Morgan948.183r.tif,rose/Morgan948/Morgan948.183v.tif,rose/Morgan948/Morgan948.184r.tif,rose/Morgan948/Morgan948.184v.tif,rose/Morgan948/Morgan948.185r.tif,rose/Morgan948/Morgan948.185v.tif,rose/Morgan948/Morgan948.186r.tif,rose/Morgan948/Morgan948.186v.tif,rose/Morgan948/Morgan948.187r.tif,rose/Morgan948/Morgan948.187v.tif,rose/Morgan948/Morgan948.188r.tif,rose/Morgan948/Morgan948.188v.tif,rose/Morgan948/Morgan948.189r.tif,rose/Morgan948/Morgan948.189v.tif,rose/Morgan948/Morgan948.190r.tif,rose/Morgan948/Morgan948.190v.tif,rose/Morgan948/Morgan948.191r.tif,rose/Morgan948/Morgan948.191v.tif,rose/Morgan948/Morgan948.192r.tif,rose/Morgan948/Morgan948.192v.tif,rose/Morgan948/Morgan948.193r.tif,rose/Morgan948/Morgan948.193v.tif,rose/Morgan948/Morgan948.194r.tif,rose/Morgan948/Morgan948.194v.tif,rose/Morgan948/Morgan948.195r.tif,rose/Morgan948/Morgan948.195v.tif,rose/Morgan948/Morgan948.196r.tif,rose/Morgan948/Morgan948.196v.tif,rose/Morgan948/Morgan948.197r.tif,rose/Morgan948/Morgan948.197v.tif,rose/Morgan948/Morgan948.198r.tif,rose/Morgan948/Morgan948.198v.tif,rose/Morgan948/Morgan948.199r.tif,rose/Morgan948/Morgan948.199v.tif,rose/Morgan948/Morgan948.200r.tif,rose/Morgan948/Morgan948.200v.tif,rose/Morgan948/Morgan948.201r.tif,rose/Morgan948/Morgan948.201v.tif,rose/Morgan948/Morgan948.202r.tif,rose/Morgan948/Morgan948.202v.tif,rose/Morgan948/Morgan948.203r.tif,rose/Morgan948/Morgan948.203v.tif,rose/Morgan948/Morgan948.204r.tif,rose/Morgan948/Morgan948.204v.tif,rose/Morgan948/Morgan948.205r.tif,rose/Morgan948/Morgan948.205v.tif,rose/Morgan948/Morgan948.206r.tif,rose/Morgan948/Morgan948.206v.tif,rose/Morgan948/Morgan948.207r.tif,rose/missing_image.tif,rose/missing_image.tif,rose/missing_image.tif,rose/missing_image.tif,rose/missing_image.tif,rose/Morgan948/Morgan948.210r.tif,rose/Morgan948/Morgan948.210v.tif,rose/missing_image.tif,rose/missing_image.tif";

    public void onModuleLoad() {
        // Log any unexpected errors
        GWT.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
            @Override
            public void onUncaughtException(Throwable e) {
                Console.log("An unexpected error has occurred.\n" + e.getMessage());
            }
        });

        // Generate fake data to populate the viewer
        Book fakeBook = fakeBook();

        // Create the page turner! Use the books page list to define image order
        final FsiPageTurner pageTurner = new FsiPageTurner(fakeBook, fakeBook.getPagesList().split(","));
        // Turn on debugging
        pageTurner.setDebug(true);

        // Add a ValueChangeHandler to monitor when the page is turned
        pageTurner.addOpeningChangedHandler(new ValueChangeHandler<Opening>() {
            @Override
            public void onValueChange(ValueChangeEvent<Opening> event) {
                Console.log("Opening has changed! [" + event.getValue().position + "] " + event.getValue().label);
            }
        });

        Panel controls = new VerticalPanel();
        controls.setStyleName("demo-controls");

        Button changeOpening = new Button("Change Opening");
        changeOpening.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                pageTurner.setOpening(5);
            }
        });

        controls.add(changeOpening);

        DockLayoutPanel root = new DockLayoutPanel(Unit.PX);

        root.addWest(controls, 300);
        root.add(pageTurner);

//        root.setSize("1500px", "800px");

        // Add the page turner to the DOM
        RootLayoutPanel.get().add(root);
    }

    private Book fakeBook() {
        List<Opening> openings = new ArrayList<>();
        openings.add(new Opening(
                null,
                new Page("rose/missing_image.tif", "Morgan 948 front cover", true, 3200, 4000),
                "",
                0
        ));
        String[] parts = list.split(",");
        for (int i = 0; i < parts.length - 2;) {
            String left = parts[i++];
            String right = parts[i++];

            openings.add(new Opening(
                    new Page(left, i + "v", false, 3200, 4000),
                    new Page(right, i + "r", false, 3200, 4000),
                    "Label " + i,
                    openings.size()
            ));
        }
        return new Book("rose/Arsenal3339/", openings, missing);
    }

}
