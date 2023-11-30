# Simple REST API security with JWT
### 1. Prepare to work:
* Set up the configuration in the application.properties file
* Come up with a secret for the token and enter the validity time of the token
  
  > ![image](https://github.com/rosenyz/security-jwt/assets/49805290/156de543-bc81-4b9e-b008-1149b16d505d)
  >
  > jwt.secret = "YOURSECRET"
  >
  > jwt.lifetime = 30m (30 minutes)

* Download Postman if you don't have it

### 2. Register:
* To register, use the url - localhost:8080/auth/register and select POST request

  > ![image](https://github.com/rosenyz/security-jwt/assets/49805290/5e02c4f3-51f8-413d-a71a-c91c63501e13)
  >
  > The url may change depending on the port used by the server

* Insert JSON with registration data into body

  > ![image](https://github.com/rosenyz/security-jwt/assets/49805290/bbeca4cb-0415-4222-bc5a-749af8ada99a)

* Send request! If you've done everything correctly, a response will come back:

  >  ![image](https://github.com/rosenyz/security-jwt/assets/49805290/9782fc4b-4174-44f7-8fdf-2e374316c85b)

### 3. Login:
* To login, use the url - localhost:8080/auth/login and select POST request

* Insert JSON with login data into body

  > ![image](https://github.com/rosenyz/security-jwt/assets/49805290/bbeca4cb-0415-4222-bc5a-749af8ada99a)

* Send request! If you've done everything correctly, a response with auth token will come back:

  > ![image](https://github.com/rosenyz/security-jwt/assets/49805290/0ebf2ca6-3179-4d01-8f5e-bb6042beebd3)

### 4. Requests to a secured data area:
* Copy the token

* Use the url - localhost:8080/secured/user and select GET request

  > ![image](https://github.com/rosenyz/security-jwt/assets/49805290/7a48c708-ba3b-4d9b-abbd-ff4690f0be0d)

* Go to the Authorization tab, select the Bearer token authorization type and insert the token

  > ![image](https://github.com/rosenyz/security-jwt/assets/49805290/c8461946-ef4a-4591-8fa2-ea9c1c0ef03e)

* Send a request! If you have done everything correctly, you will receive a responce with your username:

  > ![image](https://github.com/rosenyz/security-jwt/assets/49805290/e3ed4563-e15f-4031-906f-97546d382cd6)
