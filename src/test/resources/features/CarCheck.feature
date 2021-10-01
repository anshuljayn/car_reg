Feature: Free Car Check

  @car
  Scenario Outline: Car Check TC<TC No> - <inputFile>
    Given there is input file "<inputFile>" having car registrations
    When I perform Free Car Check on "CarTaxCheck"
    Then data matches with car output file "<outputFile>"
    Examples:
      | TC No | inputFile      | outputFile      |
      | 1     | car_input.txt  | car_output.txt  |
      | 2     | car_input1.txt | car_output1.txt |


#  Scenario: Do Nothing scenario
#    Given there are input files having car registrations
#    When I perform "Free Car Check" on "CarTaxCheck"
#    Then data matched with car out file