package add.haslearntit.application.entry;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;

import add.haslearntit.domain.entry.Entry;

public class TimelinePanel extends Panel {

    private static final long serialVersionUID = -9134642394089794933L;

    //visible for testing
    static final int DEFAULT_VIEW_SIZE = 5;
    private static final int UNLIMITED_VIEW_SIZE = -1;

    private final TimelineModel model;
    private ListView<Entry> entryListView;

    public TimelinePanel(String id, TimelineModel model) {
        super(id);
        this.model = model;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        initializeComponents();
    }

    private void initializeComponents() {
        initializeEncouragement();
        initializeSkillsList();
        initializeAddMoreLink();
    }

    private void initializeAddMoreLink() {
        add(new Link<String>("showMore") {
            @Override
            public void onClick() {
                entryListView.setViewSize(UNLIMITED_VIEW_SIZE);
            }
        });
    }

    private void initializeSkillsList() {
        entryListView = new ListView<Entry>("list", model) {
            @Override
            protected void populateItem(ListItem<Entry> item) {
                item.add(new Label("skill", item.getModelObject().asMessage()));
                item.add(new Label("skillPoints", String.valueOf(item.getModelObject().getEarnedPoints())));
            }
        }.setViewSize(DEFAULT_VIEW_SIZE);
        add(entryListView);
    }

    private void initializeEncouragement() {
        add(new WebMarkupContainer("encouragement") {
            @Override
            public boolean isVisible() {
                return model.getObject().size() == 0;
            }
        });
    }
}
