Feature: User can manage profile and its addresses
  a registered user must be able to edit its profile information and manage its addresses

    Scenario Outline: User can edit his profile
        Given i am a logged in user with email = "<email>" and password = "<password>"
        When i send a request to URL "/api/user/profile/edit" to change my phone number to "<phoneNumber>"
        Then i receive a response that notifies me the account has been updated
        And i can see my updated profile at URL "/api/user/profile"

        Examples:
          | email                         | password  | phoneNumber |
          | fatemehkarimi.1998@yahoo.com  | admin     | 09000000000 |


    Scenario Outline: User can edit his address
      Given i am a logged in user with email = "<email>" and password = "<password>"
      When i send a request to URL "/api/user/address/<id>" to update the city of address with id = "<id>" to "<city>"
      Then i can see that the city name of that address has been updated at URL "/api/user/address"

      Examples:
      | email                         | password  | id  | city    |
      | fatemehkarimi.1998@yahoo.com  | admin     | 10  | Shiraz  |
