package add.haslearntit.application.entry;

import static java.util.Arrays.asList;
import static org.fest.assertions.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.util.ListModel;
import org.junit.Test;

import add.haslearntit.HasLearntItBaseWicketIT;
import add.haslearntit.domain.entry.Entry;

public class TimelinePanelTest extends HasLearntItBaseWicketIT {

    @Test
    public void shouldDisplayAllLearntSkills() throws Exception {

        // given:
        Entry someEntry = aEntry("java programming");
        Entry otherEntry = aEntry("sky diving");

        // when:
        tester.startComponentInPage(new TimelinePanel("learntSkillsList", modelContainingSkills(someEntry, otherEntry)));

        // then:
        tester.assertListView("learntSkillsList:list", asList(someEntry, otherEntry));
        tester.assertContains(someEntry.asMessage());
        tester.assertContains(otherEntry.asMessage());
    }

    @Test
    public void shouldDisplayEncouragementMessageIfUserHasNoSkills() throws Exception {

        // given:

        // when:
        tester.startComponentInPage(new TimelinePanel("learntSkillsList", modelContainingSkills()));

        // then:
        tester.assertContains("You haven't recorded any skills. For sure there is something you have learnt lately!");
    }

    @Test
    public void shouldHideEncouragementMessageIfUserHasAlLeastOneSkill() throws Exception {

        // given:

        // when:
        tester.startComponentInPage(new TimelinePanel("learntSkillsList", modelContainingSkills(aEntry("skuba diving"))));

        // then:
        tester.assertContainsNot("You haven't recorded any skills. For sure there is something you have learnt lately!");
    }

    @Test
    public void shouldDisplayLearnPoints() {
        // given
        Entry someEntry = new Entry("java programming", "EASY", "1");

        // when
        tester.startComponentInPage(new TimelinePanel("learntSkillsList", modelContainingSkills(someEntry)));

        // then
        tester.assertLabel("learntSkillsList:list:0:skillPoints", Integer.toString(someEntry.getEarnedPoints()));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldLimitNumberOfDisplayedLearnedSkills() {
        //given

        List<Entry> entries = createGivenNumberOfEntries(TimelinePanel.DEFAULT_VIEW_SIZE + 1);

        //when
        tester.startComponentInPage(new TimelinePanel("learntSkillsList", new StaticTimelineModel(entries)));

        //then
        ListView<Entry> entryList = (ListView<Entry>) tester.getComponentFromLastRenderedPage("learntSkillsList:list");
        tester.assertListView("learntSkillsList:list", entries);
        //TODO: MZA: Can it be tested better? E.g. by a check which entries are visible?
        assertThat(entryList.getViewSize()).isEqualTo(TimelinePanel.DEFAULT_VIEW_SIZE);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldShowAllLearnedSkillsOnRequest() {
        //given
        final int requestedNumerOfEntries = TimelinePanel.DEFAULT_VIEW_SIZE + 1;
        List<Entry> entries = createGivenNumberOfEntries(requestedNumerOfEntries);
        tester.startComponentInPage(new TimelinePanel("learntSkillsList", new StaticTimelineModel(entries)));
        ListView<Entry> entryList = (ListView<Entry>) tester.getComponentFromLastRenderedPage("learntSkillsList:list");
        assertThat(entryList.getViewSize()).isEqualTo(TimelinePanel.DEFAULT_VIEW_SIZE);

        //when
        tester.clickLink("learntSkillsList:showMore");

        //then
        assertThat(entryList.getViewSize()).isEqualTo(requestedNumerOfEntries);
    }

    private List<Entry> createGivenNumberOfEntries(int numberOfEntries) {
        List<Entry> entries = new ArrayList<Entry>(numberOfEntries);
        for (int i = 0; i < numberOfEntries; i++) {
            entries.add(aEntry("Entry " + i));
        }
        return entries;
    }

    // --

    private TimelineModel modelContainingSkills(Entry... entries) {
        return new StaticTimelineModel(asList(entries));
    }

    private Entry aEntry(String name) {
        return new Entry(name, "easy", "1 minute");
    }

    public class StaticTimelineModel extends ListModel<Entry> implements TimelineModel {

        public StaticTimelineModel(List<Entry> entries) {
            super(entries);
        }

    }
}
