Feature: adding new auction
  users should be able to create new auctions

  Scenario: some user wants to create an auction at noon for duration of 30 minutes
    Given item with id "d53cc629-3aca-4f55-a633-7d1adae847eb"
    And account with id "3e555344-28fe-4210-94cd-f8c6e99b3ef1"
    And duration 30 minutes
    And base price "10"
    And start time tomorrow at noon
    When user want to create a new auction
    Then he should be able to do so

  Scenario: some user wants to create an auction with duration less than 5 minutes
    Given item with id "d53cc629-3aca-4f55-a633-7d1adae847eb"
    And account with id "3e555344-28fe-4210-94cd-f8c6e99b3ef1"
    And duration 3 minutes
    And base price "10"
    And start time tomorrow at noon
    Then he's request should be denied with an error message

  Scenario: some user wants to create an auction with startTime outside allowed hours
    Given item with id "d53cc629-3aca-4f55-a633-7d1adae847eb"
    And account with id "3e555344-28fe-4210-94cd-f8c6e99b3ef1"
    And duration 15 minutes
    And base price "10"
    And start time 22PM at night
    Then he's request should be denied with an error message