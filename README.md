# booking-service API


## How to Run the Project

Follow these steps to run the project:

1. Navigate to the project directory:
    ```bash
    cd booking-service
    ```

2. Clean and install the Maven dependencies:
    ```bash
    mvn clean install
    ```

3. Build the Docker containers:
    ```bash
    docker-compose build
    ```

4. Start the Docker containers:
    ```bash
    docker-compose up
    ```

The project should now be running and accessible. You can access it at [http://localhost:8080](http://localhost:8080).

When you run this project, some mock data is created to be easily used. It is created:
- A mock Owner
- A mock Guest
- A mock Property

## Database Architecture

This image illustrates the architecture of the database utilized by the booking-service API.
![Database Architecture](database-arch.png)

## Swagger UI

You can access the Swagger UI to explore the API endpoints by navigating to [http://localhost:8080/swagger-ui/index.html#](http://localhost:8080/swagger-ui/index.html#).

## API Postman Collection

You can also access the postman collection on /resources/booking_api_collection.json

## API curl details

## Owner Controller

- Create a new owner

```bash
curl --location 'localhost:8080/api/owner' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "Owner1",
    "document": "1234",
    "telephone": "1234",
    "address": "address 12",
    "mail": "test@test.com"
}'
```
- Get all owners
```bash
curl --location 'localhost:8080/api/owner' \
--data ''
```

- Get owner by id
```bash
curl --location --globoff 'localhost:8080/api/owner/{ownerId}' \
--data ''
```

- Delete an owner
```bash
curl --location --request DELETE 'localhost:8080/api/owner/{ownerId}' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "Owner1",
    "document": "123",
    "telephone": "1234",
    "address": "address 12",
    "mail": "test@test.com"
}'
```

- Update an owner
```bash
  curl --location --request PUT 'localhost:8080/api/owner/{ownerId}' \
  --header 'Content-Type: application/json' \
  --data-raw '{
  "name": "Owner1",
  "document": "123",
  "telephone": "1234",
  "address": "address 12",
  "mail": "test@test.com"
  }'
```

## Guest Controller

- Create a guest

```bash
   curl --location 'localhost:8080/api/guest' \
   --header 'Content-Type: application/json' \
   --data-raw '{
       "name": "Owner1",
       "document": "1243",
       "telephone": "1234",
       "address": "address 12",
       "mail": "test@test.com"
   }'
```

- Get all guests

```bash
curl --location 'localhost:8080/api/guest' \
--data ''
```

- Get guest by id
```bash
  curl --location --globoff 'localhost:8080/api/guest/{guestId}' \
  --data ''
```


- Delete a Guest
```bash
curl --location --request DELETE 'localhost:8080/api/owner/{guestId}' \
--data ''
```

- Update a guest
```bash
  curl --location --request PUT 'localhost:8080/api/guest/{guestId}' \
  --header 'Content-Type: application/json' \
  --data-raw '{
  "name": "Owner5",
  "document": "1243",
  "telephone": "1234",
  "address": "address 12",
  "mail": "test@test.com"
  }'
  ```

## Property Controller

- Create a property

```bash
curl --location 'localhost:8080/api/property' \
--header 'Content-Type: application/json' \
--data '{
  "owner": {
    "id": "f9193d3a-e1de-4b1e-887c-dc3d2b770fce"
  },
  "name": "Beach house",
  "description": "Beach house",
  "dailyValue": 100.00,
  "cleaningValue": 50.00
}'
```

- Get all properties

```bash
curl --location 'localhost:8080/api/property' \
--data ''
```

- Get property by id
```bash
curl --location 'localhost:8080/api/property/{propertyId}' \
--data ''
```


- Delete a property
```bash
curl --location --request DELETE 'localhost:8080/api/property/{propertyId}' \
--data ''
```

- Update a property
```bash
curl --location --request PUT 'localhost:8080/api/property/{propertyId}' \
--header 'Content-Type: application/json' \
--data '{
    "owner": {
        "id": "ba580417-854c-40d1-a2f4-b953040f4dfe"
    },
    "name": "Beach House",
    "description": "A beautiful beach house",
    "status": "AVAILABLE",
    "createdAt": "2024-01-31T19:35:52.508384",
    "dailyValue": 100.00,
    "cleaningValue": 20.00,
    "deleted": false
}'
  ```

## Booking Controller

- Create a booking

```bash
curl --location 'localhost:8080/api/booking' \
--header 'Content-Type: application/json' \
--data '{
  "property": {
    "id": "2b66df9e-4cfb-48a8-94e8-e8d41f1d6bcc"
  },
  "startDate": "2024-01-01T10:00:00",
  "endDate": "2024-01-05T10:00:00",
  "guest": {
    "id": "1c5aac17-d22c-4b61-a6ac-0c7821e15c2f"
  }
}'
```

- Get all bookings

```bash
curl --location 'localhost:8080/api/booking' \
--data ''
```

- Get booking by id
```bash
curl --location 'localhost:8080/api/booking/{bookingId}' \
--data ''
```


- Delete a booking
```bash
curl --location --request DELETE 'localhost:8080/api/booking/{bookingId}' \
--data ''
```

- Update a booking
```bash
curl --location --request PUT 'localhost:8080/api/booking/{bookingId}' \
--header 'Content-Type: application/json' \
--data '{
  "property": {
    "id": "89db9fb9-4e1f-4a06-b993-fca4b17980e3"
  },
  "startDate": "2024-01-01T11:00:00",
  "endDate": "2024-01-05T10:00:00",
  "guest": {
    "id": "f884a87a-7da7-481b-9d98-dddcdef1cda2"
  }
}'
  ```

- Rebook a canceled booking
```bash
curl --location --request PATCH 'localhost:8080/api/booking/rebook/{bookingId}' \
--header 'Content-Type: application/json' \
--data '{
  "property": {
    "id": "00f510bb-96f0-4580-a2c6-8721a99b4730"
  },
  "startDate": "2024-01-01T10:00:00",
  "endDate": "2024-01-10T10:00:00",
  "guest": {
    "id": "b251748e-7092-4c6d-a740-fe236c8a0c83"
  }
}'
  ```

- Cancel a booking
```bash
curl --location --request DELETE 'localhost:8080/api/booking/cancel/{bookingId}' \
--data ''
  ```

## Block Controller

- Create a Block

```bash
curl --location 'localhost:8080/api/booking-block' \
--header 'Content-Type: application/json' \
--data '{
  "property": {
    "id": "2b66df9e-4cfb-48a8-94e8-e8d41f1d6bcc"
  },
  "startDate": "2024-01-01T10:00:00",
  "endDate": "2024-01-05T10:00:00"
}'
```

- Get all blocks

```bash
curl --location 'localhost:8080/api/booking-block' \
--data ''
```

- Get block by id
```bash
curl --location 'localhost:8080/api/booking-block/{blockId}' \
--data ''
```


- Delete a block
```bash
curl --location --request DELETE 'localhost:8080/api/booking-block/{blockId}' \
--data ''
```

- Update a property
```bash
curl --location --request PUT 'localhost:8080/api/booking-block/{blockId}' \
--header 'Content-Type: application/json' \
--data '{
    "id": "10104557-4af3-4e58-8e69-fc867be2292f",
    "status": "BOOKED",
    "property": {
        "id": "973e2018-01b2-4dea-ba39-364eecee804f"
    },
    "startDate": "2024-02-01T10:00:00",
    "endDate": "2024-02-05T10:00:00",
    "bookingType": "MAINTENANCE"
}'
  ```
## H2 DATABASE

You can access the H2 database console by navigating to [http://localhost:8080/h2-console#](http://localhost:8080/h2-console).


