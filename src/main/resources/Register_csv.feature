@smoke
Feature: Steps | Register new user
  Scenario: User Registration with CSV Data
    Given Open the website login page
    When I enter registration details from the CSV file
    And I should see on the page
    Then I take a screenshot
