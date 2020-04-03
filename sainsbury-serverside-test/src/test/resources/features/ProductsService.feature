Feature: Parsing products from Sainsbury's pages.

  Scenario: Return products
    Given Valid HTML pages
    When Call getProducts
    Then Return products list

  Scenario: Return ERROR-0001 Unknown Host
    Given Error connecting to the site
    When Call getProducts
    Then Return ERROR-0001 'Unknown Host'

  Scenario: Return ERROR-0002 HTTP request resulted in a not OK HTTP response
    Given Not 200 HTTP code
    When Call getProducts
    Then Return ERROR-0002 'HTTP request resulted in a not OK HTTP response'

  Scenario: Return ERROR-0003 Products parsing error
    Given Not valid products HTML scheme
    When Call getProducts
    Then Return ERROR-0003 'Products parsing error'

  Scenario: Return ERROR-0003 Product parsing error
    Given Not valid product HTML scheme
    When Call getProducts
    Then Return ERROR-0003 'Product parsing error'