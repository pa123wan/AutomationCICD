@tag
  Feature: Purchase the order from Ecommerce Website
    Any description here

    Background:
      Given I landed on Ecommerce page

  @tag2
  Scenario Outline:Positive test of submitting the order
    Given logged in with Username <name> and Password <password>
    When I add the Product Name <productName> from Cart
    And Checkout <productName> and submit the order
    Then "THANKYOU FOR THE ORDER." message is displayed on confirmationPage.

    Examples:
    | name             | password| productName|
    |test001@gmail.com | Hinwali@123 | ADIDAS ORIGINAL |




