## ERD

![erd](https://github.com/hikaruyuzu/store-image/blob/main/erd.png?raw=true)

### endpoint

- /movie/add [post]

to add new movie

example request
 ```
{
    "title" : "Kaguya-sama wa Kokurasetai: Ultra Romantic, desu!",
    "showStatus" : true,
    "duration" : 240,
    "startDate" : "2017-05-19T14:00:00",
    "endDate" : "2022-04-23T18:25:43.511Z",
    "description" : "The elite members of Shuchiin Academy's student council continue their competitive day-to-day antics. Council president Miyuki Shirogane clashes daily against vice-president Kaguya Shinomiya, each fighting tooth and nail to trick the other into confessing their romantic love. Kaguya struggles within the strict confines of her wealthy, uptight family, rebelling against her cold default demeanor as she warms to Shirogane and the rest of her friends",
    "genre" : "Romcance, Comedy, Seinen",
    "country" : "japan",
    "language" : "Japanese",
    "schedules" : [
        {
            "showDate" : "2022-04-23T18:25:43.511Z",
            "startTime": "2022-04-23T18:25:43.511Z",
            "endTime" : "2022-04-23T18:25:43.511Z",
            "price": "100000"
        },
        {
            "showDate" : "2017-05-19T14:00:00",
            "startTime": "2017-05-19T14:00:00",
            "endTime" : "2017-05-19T14:00:00",
            "price": "200000"
        }
    ],
    "studios" : [
        {
            "name" : "Sagiri chan",
            "status" : true
        },
        {
            "name" : "Kaguya chan",
            "status" : true
        }
    ]

}
```

example response
```
{
    "message": "successfully added movie with id ad3619cb-ac3d-4743-bec5-2123d92cc6df",
    "status": 200,
    "data": {
        "id": "ad3619cb-ac3d-4743-bec5-2123d92cc6df",
        "title": "Kaguya-sama wa Kokurasetai: Ultra Romantic, desu!",
        "showStatus": true,
        "duration": 240,
        "startDate": "2017-05-19 14:00:00",
        "endDate": "2022-04-23 18:25:43",
        "description": "The elite members of Shuchiin Academy's student council continue their competitive day-to-day antics. Council president Miyuki Shirogane clashes daily against vice-president Kaguya Shinomiya, each fighting tooth and nail to trick the other into confessing their romantic love. Kaguya struggles within the strict confines of her wealthy, uptight family, rebelling against her cold default demeanor as she warms to Shirogane and the rest of her friends",
        "genre": "Romcance, Comedy, Seinen",
        "country": "japan",
        "language": "Japanese",
        "active": null,
        "schedules": [
            {
                "id": "e64f2159-495c-4ce4-9e1d-9aeaad6d5ba2",
                "movieId": "ad3619cb-ac3d-4743-bec5-2123d92cc6df",
                "showDate": "2022-04-23 18:25:43",
                "startTime": "2022-04-23 18:25:43",
                "endTime": "2022-04-23 18:25:43",
                "price": 100000,
            },
            {
                "id": "58d614af-2ce5-4a00-aebb-7625af06048c",
                "movieId": "ad3619cb-ac3d-4743-bec5-2123d92cc6df",
                "showDate": "2017-05-19 14:00:00",
                "startTime": "2017-05-19 14:00:00",
                "endTime": "2017-05-19 14:00:00",
                "price": 200000,
            }
        ],
        "studios": [
            {
                "id": "Sagiri chan",
                "movieId": "ad3619cb-ac3d-4743-bec5-2123d92cc6df",
                "name": "Sagiri chan",
                "status": true,
            },
            {
                "id": "Kaguya chan",
                "movieId": "ad3619cb-ac3d-4743-bec5-2123d92cc6df",
                "name": "Kaguya chan",
                "status": true,
            }
        ]
    }
}
```

- /movie/update/{id-movie} [put]

change movie name

example request
```
url : http://localhost:8080/movie/update/d151f502-3b88-42bb-9e91-3d62f3b0179c

{
    "title" : "Kaguya-sama love is war"

}
```
example response
```
{
    "message": "successfully update movie with id d151f502-3b88-42bb-9e91-3d62f3b0179c",
    "status": 200,
    "data": {
        "rowAffected": 1,
        "updatedAt": "2022-09-23 08:21:01"
    }
}
```

- /movie/delete/{id-movie} [delete]

example request 
```
http://localhost:8080/movie/delete/d151f502-3b88-42bb-9e91-3d62f3b0179c
```

example response
```
{
    "message": "successfully delete data with id d151f502-3b88-42bb-9e91-3d62f3b0179c",
    "status": 200,
}
```

- /movie/show?status=[true/false] [get]

show currently showing movies / not

example request
```
url : http://localhost:8080/movie/show?status=true

{
    "message": "success taking all the films that are currently showing",
    "status": 200,
    "data": [
        {
            "id": "b6025eaa-2c12-40b0-ad78-5afc65c41af4",
            "title": "Kaguya-sama wa Kokurasetai: Ultra Romantic, desu!",
            "showStatus": true,
            "duration": 240,
            "startDate": "2017-05-19 14:00:00",
            "endDate": "2022-04-23 18:25:43",
            "createdAt": "2022-09-22 07:45:21",
            "updatedAt": null,
            "description": "The elite members of Shuchiin Academy's student council continue their competitive day-to-day antics. Council president Miyuki Shirogane clashes daily against vice-president Kaguya Shinomiya, each fighting tooth and nail to trick the other into confessing their romantic love. Kaguya struggles within the strict confines of her wealthy, uptight family, rebelling against her cold default demeanor as she warms to Shirogane and the rest of her friends",
            "genre": "Romcance, Comedy, Seinen",
            "country": "japan",
            "language": "Japanese"
        },
        {
            "id": "aad473cc-92ea-4eb5-8285-8da19500f468",
            "title": "Kaguya-sama wa Kokurasetai: Ultra Romantic, desu!",
            "showStatus": true,
            "duration": 240,
            "startDate": "2017-05-19 14:00:00",
            "endDate": "2022-04-23 18:25:43",
            "createdAt": "2022-09-22 11:37:38",
            "updatedAt": null,
            "description": "The elite members of Shuchiin Academy's student council continue their competitive day-to-day antics. Council president Miyuki Shirogane clashes daily against vice-president Kaguya Shinomiya, each fighting tooth and nail to trick the other into confessing their romantic love. Kaguya struggles within the strict confines of her wealthy, uptight family, rebelling against her cold default demeanor as she warms to Shirogane and the rest of her friends",
            "genre": "Romcance, Comedy, Seinen",
            "country": "japan",
            "language": "Japanese"
        },
    }
}
        
```

- /movie/schedule/{movie-id} [get]

show a specific movie schedule

example request
```
url : http://localhost:8080/movie/schedule/c05ebbad-5dea-46a6-a719-42559750840e

```

example response 
```
{
    "message": "successfully get movie schedule with id c05ebbad-5dea-46a6-a719-42559750840e",
    "status": 200,
    "data": {
        "id": "c05ebbad-5dea-46a6-a719-42559750840e",
        "title": "Kaguya-sama wa Kokurasetai: Ultra Romantic, desu!",
        "showStatus": true,
        "duration": 240,
        "startDate": "2017-05-19 14:00:00",
        "endDate": "2022-04-23 18:25:43",
        "createdAt": "2022-04-23 18:25:43",
        "updatedAt": null,
        "genre": "Romcance, Comedy, Seinen",
        "country": "japan",
        "language": "Japanese",
        "schedules": [
            {
                "id": "76368708-ea4c-4c40-a2e0-20be5870d57d",
                "movieId": "c05ebbad-5dea-46a6-a719-42559750840e",
                "showDate": "2022-04-23 18:25:43",
                "startTime": "2022-04-23 18:25:43",
                "endTime": "2022-04-23 18:25:43",
                "price": 100000.00,
                "createdAt": "2022-09-23 08:40:07",
                "updatedAt": null
            },
            {
                "id": "4f91936a-42d7-4cc1-b36d-329d00d24c32",
                "movieId": "c05ebbad-5dea-46a6-a719-42559750840e",
                "showDate": "2017-05-19 14:00:00",
                "startTime": "2017-05-19 14:00:00",
                "endTime": "2017-05-19 14:00:00",
                "price": 200000.00,
                "createdAt": "2022-09-23 08:40:07",
                "updatedAt": null
            }
        ],
        "studios": [
            {
                "id": "Sagiri chan",
                "movieId": "c05ebbad-5dea-46a6-a719-42559750840e",
                "name": "Sagiri chan",
                "status": true,
                "createdAt": "2022-09-22T07:45:21.475+00:00",
                "updatedAt": null
            },
            {
                "id": "Kaguya chan",
                "movieId": "c05ebbad-5dea-46a6-a719-42559750840e",
                "name": "Kaguya chan",
                "status": true,
                "createdAt": "2022-09-22T11:37:38.090+00:00",
                "updatedAt": null
            }
        ]
    }
}
```

- /user/register [post]

register new account

example request
```
{
    "name" : "Chino kaffu",
    "email" : "kaffupyon@gmail.com",
    "phone" : "082351252222",
    "password" :"kfufu"
}
```

example response
```
{
    "message": "successfully register user with id d871339c-f546-4134-974a-df9008099d91",
    "status": 200,
    "data": {
        "id": "d871339c-f546-4134-974a-df9008099d91",
        "name": "Chino kaffu",
        "email": "kaffupyon@gmail.com",
        "phone": "082351252222"
    }
}
```

- /user/update/{account-id} [put]

 change all or some data


example request
```
url : http://localhost:8080/user/update/d97a05a7-4318-4b6b-b489-3d6f8e544eae

{
    "oldPassword" : "kfufu",
    "newPassword" : "fufufu",
    "name" : "Chino kaffu",
    "email" : "chino@gmail.com"
}
```

example response
```
{
    "message": "successfully update user with id d871339c-f546-4134-974a-df9008099d91",
    "status": 200,
    "data": {
        "id": "d871339c-f546-4134-974a-df9008099d91",
        "name": "Chino kaffu",
        "email": "chino@gmail.com",
        "phone": "082351252222"
    }
}
```

- /user/delete/{account-id} [delete]

delete user account

exampe request
```
url : http://localhost:8080/user/delete/d871339c-f546-4134-974a-df9008099d91
```

example response
```
{
    "message": "successfully delete data with id d871339c-f546-4134-974a-df9008099d91",
    "status": 200
}
```

- /seat/add [post]

add seats

example request
```
{
    "seatCode" : "A2"
}
```

example response
```
{
    "message": "successfully add seat with id A2",
    "status": 200,
    "data": {
        "id": "A2",
        "seatCode": "A2"
    }
}
```

- /seat-details/add [post]

add studio and seating pairs

example request

```
{
    "studioName" : "Sagiri chan",
    "seatCode" : "A2"
}
```

example response
```
    "message": "successfully add seat and studio",
    "status": 200,
    "data": {
        "studioName": "Sagiri chan",
        "seatCode": "A2",
        "status": true
    }
}
```

- /seat-details/available?status = [true/false] [get]

get an available seats / not

example request
```
url : http://localhost:8080/seat-details/available?status=true

```

example response
```
{
    "message": "successfully to get all seats",
    "status": 200,
    "data": [
        {
            "studioName": "Sagiri chan",
            "seatCode": "A1",
            "status": true
        },
        {
            "studioName": "Kaguya chan",
            "seatCode": "A3",
            "status": true
        }
    }
}
```

- /order/ticket [post]

ticket reservations

example request
```
{
    "userId" : "7b021d69-7618-4c44-b8a9-57e10ea51a09",
    "orderDetailRequests" : [
        {
            "studioId" : "Sagiri chan",
            "scheduleId" : "74f6d86f-13e2-4792-a57f-10be6adb2e43",
            "quantity" : 2,
            "orderSeatRequests" : [
                {
                    "seatCode" : "B1",
                    "studioName" : "Sagiri chan"
                },
                {
                    "seatCode" : "B2",
                    "studioName" : "Sagiri chan"
                }
            ]
        },
        {
            "studioId" : "Sagiri chan",
            "scheduleId" : "d898fbc5-bb7b-4bfc-91c9-bbd7681784e8",
            "quantity" : 1,
            "orderSeatRequests" : [
                {
                    "seatCode" : "B3",
                    "studioName" : "Sagiri chan"
                }
                
            ]
        }

    ]
}
```

example response
```
{
    "message": "order successfully ",
    "status": 200,
    "data": {
        "orderId": "dbe81da0-b727-4c11-9c86-dd88832aedcf",
        "totalPrice": 300000.0,
        "currencyCode": "IDR",
        "currencySymbol": "Rp",
        "orderDetails": [
            {
                "scheduleId": "74f6d86f-13e2-4792-a57f-10be6adb2e43",
                "title": "Kaguya-sama wa Kokurasetai: Ultra Romantic, desu!",
                "showDate": "2022-04-23 18:25:43",
                "startTime": "18:25:43",
                "endTime": "18:25:43",
                "price": 100000.00,
                "currencyCode": "IDR",
                "currencySymbol": "Rp",
                "quantity": 2
            },
            {
                "scheduleId": "d898fbc5-bb7b-4bfc-91c9-bbd7681784e8",
                "title": "Kaguya-sama wa Kokurasetai: Ultra Romantic",
                "showDate": "2022-04-23 18:25:43",
                "startTime": "18:25:43",
                "endTime": "18:25:43",
                "price": 100000.00,
                "currencyCode": "IDR",
                "currencySymbol": "Rp",
                "quantity": 1
            }
        ]
    }
}
```