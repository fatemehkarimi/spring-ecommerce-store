Feature: any website visitor can see products list and product details

    Scenario: User can view product list
        When i send a request to URL "/api/products" to fetch products
        Then i must get a list of products

    Scenario Outline: User can see the details of a product
        When i send a request to URL "/api/product/<id>" to see the details of product "<id>"
        Then i get an object with id equals to "<id>"

        Examples:
          | id   |
          | 3    |
          | 5    |
          | 15   |