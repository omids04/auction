Feature: biding on an auction
  users should be able to place a bid on an auction

  Scenario: A user wants to bid on an auction
    Given an open auction with id "4772c36d-d0c3-43f1-8e2d-e0f82ac3a8db" and 10 as price of highest bid
    And user account that have enough credit
    When user wants to bid 12 on that auction
    Then he should be able to bid

  Scenario: Biding on an auction with less than current highest bid
    Given an open auction with id "4772c36d-d0c3-43f1-8e2d-e0f82ac3a8db" and 10 as price of highest bid
    And user account that have enough credit
    When user wants to bid 8 on that auction
    Then he should not be able to bid

  Scenario: Biding on a closed auction
    Given a closed auction with id "2ede4707-cdf0-43a4-8aa6-c8f58e1cdead"
    When user wants to bid 8 on that auction
    Then he should not be able to bid