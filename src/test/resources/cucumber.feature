Feature: payments CRUD

  Background:
    Given the db is clean
    When the client GET /payments
    And the client receives 0 payments

  Scenario: reactive POST
    Given the client POST 1 mock

  Scenario: the client updates a payment
    Given the client POST 1 mock
    When the client updates a payment with value "Updated Payment" on the type field

  Scenario: the client posts with id
    When the client POST a mock with id
    Then the client receives status code of 400

  Scenario: the client updates with no id
    Given the client POST 1 mock
    When the client updates a payment with no id
    Then the client receives status code of 400

  Scenario: the client deletes a payment
    Given the client POST 1 mock
    When the client DELETE a payment
    And the client GET a payment
    Then the client receives status code of 404

  Scenario: the client deletes a non existing payment
    Given the client POST 1 mock
    When the client DELETE a payment
    And the client DELETE a payment
    Then the client receives status code of 404

  Scenario Outline: the client gets payments by beneficiaryName
    Given the client POST 15 mocks
    When the client GET /payments with beneficiaryName "<string>"
    Then the client receives <number> payments
    Examples:
      | string   | number |
      | jeremiah | 15     |
      | mark     | 0      |