@OrderServiceTest
Feature: Place Order
  @createOrder
  Scenario: Customer places new order
    Given the customer selects at least 1 Menu Item
    And fills out the customer info fields
    When the customer places the order
    Then a new order will be created with a PENDING Status