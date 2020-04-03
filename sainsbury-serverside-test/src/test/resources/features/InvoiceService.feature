Feature: Calculate VAT

  Scenario Outline: Calculate vat=<vat> for gross=<gross>
    Given Gross value <gross>
    When Call calculateVat
    Then Return VAT value <vat>
    Examples:
      | gross  | vat   |
      | 144.44 | 28.89 |
      | 144.41 | 28.88 |