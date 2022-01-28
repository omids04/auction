Feature: change auction time
  users should be able to change their auction timing


  Scenario: user wants to change both start time and duration
    Given an auction with id "6173e0e9-1862-45d1-a5af-9745abe9bea3"
    And start time tomorrow at noon and duration 30 minutes
    When user wants to change start time to day after tomorrow and duration to 50 minutes
    Then he should be able to do so

  Scenario: user wants to change start time or duration of an already finished auction
    Given a finished auction with id "5a47d715-34cc-4b97-b95d-0d873367f067"
    When user wants to change start time to day after tomorrow and duration to 50 minutes
    Then he should not be able to do so

  Scenario: expanding duration of a live auction
    Given a live auction with id "8425e611-da94-4f12-8775-717a2c042f20"
    And duration of 30 minutes
    When user wants to change duration to 50 minutes
    Then he should be able to do so

  Scenario: reducing duration of a live auction
    Given a live auction with id "6a04fbf1-e0c8-4638-a673-e7c32a6203a7"
    And duration of 30 minutes
    When user wants to change duration to 20 minutes
    Then he should not be able to do so

  Scenario: changing startTime of a live auction
    Given a live auction with id "8425e611-da94-4f12-8775-717a2c042f20"
    When user wants to change start time
    Then he should not be able to do so


