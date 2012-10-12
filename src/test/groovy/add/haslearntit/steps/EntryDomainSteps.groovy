package add.haslearntit.steps

import cucumber.table.DataTable

this.metaClass.mixin(cucumber.runtime.groovy.Hooks)
this.metaClass.mixin(cucumber.runtime.groovy.EN)

    When(~'^skill should not be recorded$'){ ->
        
        assert entriesCount() == 0
        
    }

Given(~'^there are following entries$') { DataTable entries ->
    entries.asMaps().each { entry ->
        createEntry(entry["entry"], "easy", "1")
    }
}
