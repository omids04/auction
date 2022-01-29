Feature: Bid related queries
  users should be able to query an auction bids based on different criteria


  Scenario: seeing an auction all bids
    Given auction with id "d53cc629-3aca-4f55-a633-7d1adae847eb"
    When user request to see all bids that belong to given auction
    Then he should be given a list of all bids


  Scenario: seeing all bids that been place by a specific account
    And an account with id "3e555344-28fe-4210-94cd-f8c6e99b3ef1"
    When when users wants to see all his bids
    Then he should be given a list of all bids
