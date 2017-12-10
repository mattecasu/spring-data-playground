
Feature: payments CRUD

  Background:
    Given the db is clean
    When the client GET /payments
    And the client receives length 0

  Scenario: the client creates mock data
    Given the client POST a mock
    Then the client receives the POSTed object with id

  Scenario: the client updates a payment
    Given the client POST a mock
    When the client updates a payment with value "Updated Payment" on the type field
    Then the client receives status code of 200
    And the response contains value "Updated Payment" on the type field

  Scenario: the client posts with id
    When the client POST a mock with id
    Then the client receives status code of 400

  Scenario: the client updates with no id
    Given the client POST a mock
    When the client updates a payment with no id
    Then the client receives status code of 400

  Scenario: the client deletes a payment
    Given the client POST a mock
    When the client DELETE a payment
    And the client GET a payment
    Then the client receives status code of 404

  Scenario: the client deletes a non existing payment
    (idempotency of DELETE)
    Given the client POST a mock
    When the client DELETE a payment
    And the client DELETE a payment
    Then the client receives status code of 200

  Scenario: the client get paginated data
    Given the client POST 15 mocks
    When the client GET /payments with page 2 and size 3
    Then the client receives length 3