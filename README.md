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

***Get specific order***
----
  Returns all saved orders

* **URL**

  /api/v1/order/{hashId}

* **Method:**

  `GET`

* **Data Params**

  * **hashId:** is a long unique id of order.

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
***Get all orders***
----
  Returns all saved orders

* **URL**

  /api/v1/order/

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
* **Error Response:**

  * **Code:** 404 <br />
    **Content:** 
    ```
    { 
        "message": "String detail"
    }
    ```
    
***Add Order***
----
  Save a order in service and get price

* **URL**

  /api/v1/order/

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

