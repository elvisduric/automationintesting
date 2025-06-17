Feature: Booking Page Elements
  As a user
  I want to verify that booking page contains required elements
  So that I can make reservations

  Background:
    Given I navigate to the booking page

  Scenario: Verify checkin and checkout elements are present
    Then I should see checkin element
    And I should see checkout element

  @debug
  Scenario: Verify checkin and checkout elements are interactive
    When I click on checkin element
    Then checkin calendar should be active
    When I click on checkout element
    Then checkout calendar should be active

  @regression
  Scenario: Verify booking page loads correctly
    Then the page title should contain booking information
    And the current URL should contain "booking"

  git branch -M master