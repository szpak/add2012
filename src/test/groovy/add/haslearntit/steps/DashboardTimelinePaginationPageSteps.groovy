package add.haslearntit.steps

import add.haslearntit.pages.*
import cucumber.table.DataTable

this.metaClass.mixin(cucumber.runtime.groovy.Hooks)
this.metaClass.mixin(cucumber.runtime.groovy.EN)

When(~'^I enter global timeline page$') { ->
    browser.to DashboardPage
}

Then(~'^I should see following entries in timeline$') { DataTable entryNames ->
    browser.at DashboardPage

    List<String> displayedSkills = browser.page.learntSkills()
    List<Map<String, String>> entryNamesAsListOfMap = entryNames.asMaps()

    assert browser.page.learntSkills().size() == entryNamesAsListOfMap.size()
    entryNamesAsListOfMap.eachWithIndex { row, i ->
        displayedSkills[i].contains(row["entry"])
    }
}

When(~'^I request more results in timeline$') { ->
    browser.page.clickShowMoreEntries()
}
