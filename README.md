[![Ktlint](https://img.shields.io/badge/code%20style-%E2%9D%A4-FF4081.svg)](https://ktlint.github.io/)

# Android app for search ads

## Data structures

### User of service

He can publish ads. Consists of such information as:

* Unique identifier defined by API
* First name
* Last name
* Email
* Telephone number (can be omited)
* About (can be omited)
* Date and time of sign up

Example of JSON object that will be transmitted from API
to clients:

```json
{
    "id": 123456,
    "first_name": "Random",
    "last_name": "Valerka",
    "email": "valerka@example.com",
    "tel_num": "1-234-56-78",
    "about": "Some information about this man",
    "time_reg": "2012.10.1 15:34:41"
}
```

### Ad that can be published by users

* Unique identifier defined by API
* Title
* Price (can be omited)
* Country (can be omited)
* City (can be omited)
* Subway station (can be omited)
* Images (can be omited)
* Information about agent (just some fields from user structure)
* Description of service
* Creation date and time

Example of JSON object that will be transmitted from API
to clients:

```json
{
    "id": 1234,
    "title": "My awesome title",
    "price": 100500,
    "country": "Russia",
    "city": "Moscow",
    "subway_station": "Technopark",
    "images_url": ["ex.com/ad_id/1.png", "ex.com/ad_id/2.png"],
    "agent_info": {
        "id": 123456,
        "first_name": "Random",
        "last name": "Valerka",
        "email": "valerka@example.com",
        "tel_num": "1-234-56-78",
        "about": "Some information about this man",
        "time_reg": "2012.10.1 15:34:41"
    },
    "description": "it is awesome service with the best quality!",
    "time_cre": "2012.10.1 15:40:52"
}
```

### Error type

It will be sent to client in JSON format if something went wrong.

* Message
* Description
* Error Code

Example:

```json
{
    "message": "Can't create user",
    "description": "User with such email is already exists",
    "error": "UserEmailExists"
}
```
