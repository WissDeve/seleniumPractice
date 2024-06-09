Feature: Expense Reimbursement Request

Scenario: Sending a Mission Refund Request
    Given I am logged in as a user
    And I navigate to the refund request page
    And I submit the refund request form
    Then I should see a success message indicating the request was sent successfully

Scenario: Request and Validate Expense Reimbursement Request as a GESTNF User
    Given I am logged in as a GESTNF user
    And there is a pending expense reimbursement request for GESTNF user
    When I validate the request as a GESTNF user
    Then the request status should be validated for GESTNF user

Scenario: Request and Validate Expense Reimbursement Request as a Responsible Manager
    Given I am logged in as a responsible manager
    And there is a pending expense reimbursement request for responsible manager
    When I validate the request as a responsible manager
    Then the request status should be validated for responsible manager

Scenario: Check Approved Expense Reimbursement Request
    Given there is an approved expense reimbursement request
    When I check the request status
    Then the request status should be marked as APPROVED
