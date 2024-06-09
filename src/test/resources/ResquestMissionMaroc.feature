Feature: Passer une demande ordre de mission maroc
    As an employee
    I want to submit a mission order request
    So that it can be approved by the responsible collaborator

Scenario: Submit a mission order request
    Given I am logged in as an employee
    When I enter the details for the mission order request
    And I submit the request
    Then I should see a confirmation message indicating the request was sent successfully

Scenario: Approve the mission order request
    Given I am logged in as the responsible collaborator
    When I view the pending mission order requests
    And I approve the request
    Then the request should be marked as approved
