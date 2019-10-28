Feature: Employee Functionality

  Scenario: Creating one employee in the list
    Given the list is empty
    When I put 1 employee in the list
    Then the list should contain only 1 employee

  Scenario: Putting few things in the bag
    Given the bag is empty
    When I put 1 potato in the bag
    And I put 2 cucumber in the bag
    Then the bag should contain 1 potato
    And the bag should contain 2 cucumber