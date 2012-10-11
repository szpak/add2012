Feature: Timelines should show limited number of entries

    Scenario: Limit displayed entries to 5
        Given there are following entries
            | entry     |
            | Java      |
            | Scala     |
            | Cucumber  |
            | Wicket    |
            | TDD       |
            | Groovy    |
        When I enter global timeline page
        Then I should see following entries in timeline
            | entry     |
            | Java      |
            | Scala     |
            | Cucumber  |
            | Wicket    |
            | TDD       |

    Scenario: Display entries from most recent to most outdated
            | entry     | added         |
            | Java      | 2010-01-01    |
            | Scala     | 2012-03-03    |
            | Cucumber  | 2011-11-11    |
        When I enter global timeline page
        Then I should see following entries in timeline
            | entry     |
            | Scala     |
            | Cucumber  |
            | Java      |

    Scenario: Extend timeline if user request more results
        Given there are following entries
            | entry     |
            | Java      |
            | Scala     |
            | Cucumber  |
            | Wicket    |
            | TDD       |
            | Groovy    |
        When I enter global timeline page
        When I request more results in timeline
        Then I should see following entries in timeline
            | entry     |
            | Java      |
            | Scala     |
            | Cucumber  |
            | Wicket    |
            | TDD       |
            | Groovy    |
