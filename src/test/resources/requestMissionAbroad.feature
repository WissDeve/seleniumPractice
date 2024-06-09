Feature: Request mission abroad

Scenario: Approval of a Mission Request Abroad

Given that the employee submits a request for a mission abroad
And the request is awaiting approval
When the manager approves the request
And the budget director approves the request
And the HR director approves the request
Then the request should be approved


Scenario: Employee Verifies Notification of Request Validation

Given that the employee has submitted a request for reimbursement
And the request has been validated by the manager, budget director, and HR director
When the employee checks their notifications
Then they should receive a notification indicating that the request has been validated
And the employee can view the validated request details












