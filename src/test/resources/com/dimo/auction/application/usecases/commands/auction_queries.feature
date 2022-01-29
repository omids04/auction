Feature: Auction related queries
  users should be able to query auctions based on different criteria


  Scenario: seeing all auctions
    When user request to see all auctions
    Then he should be given a list of all auctions

  Scenario: seeing all live auctions
    When user request to see all live auctions
    Then he should be given a list of all live auctions


  Scenario: seeing all not yet started auctions
    When user request to see all not started yet auctions
    Then he should be given a list of all not started yet auctions

  Scenario: seeing all auctions that belong to a user
    Given a user with id "3e555344-28fe-4210-94cd-f8c6e99b3ef1"
    When user request to see all his auctions
    Then he should be given a list of all auctions that belong to his account