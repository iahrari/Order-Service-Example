# Order Service

This project is a spring boot application that contains rest api for adding a order and retrieve price and id of the saved order. 

## Requirements

This project needs java 11+. this service needs [pricing-service](https://github.com/iahrari/Pricing-Service-Example) to be running on port 8081.

## Execute
You can simply run this project with Intelij Idea or with Spring boot extension pack in VsCode alternatively you can run project with this command.

```bash
./mvnw spring-boot:run
```

## Requests
***Get all orders***
----
  Returns all saved orders

* **URL**

  /orders/

* **Method:**

  `GET`

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** 
    ```
    [
        { 
            "hashId : "Long Id", 
            "source" : "String", 
            "destination": "String", 
            "price": "decimal number" 
        }
    ]
    ```
***Add Order***
----
  Save a order in service and get price

* **URL**

  /orders/

* **Method:**

  `POST`

* **Request Body**
    * **Content:** 
        ```
        { 
            "source" : "String", 
            "destination": "String"
        }
        ```

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** 
    ```
    { 
        "hashId : "Long Id", 
        "source" : "String", 
        "destination": "String", 
        "price": "decimal number" 
    }
    ```
* **Error Response:**

  * **Code:** 400 <br />
    **Content:** 
    ```
    { 
        "field name": "String reason",
        "field name2": "String reason",
        .
        .
        .
    }
    ```

