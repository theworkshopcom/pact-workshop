# Docker
Build dev environment
```bash
docker-compose up --force-recreate
```

Open dev environment terminal
```bash
docker exec -it dev-environment /bin/bash
```

Start applications
```bash
cd product-service
./mvnw install
cd ..
cd cart-service
./mvnw install
cd ..
cd angular-app
npm install
npm run start:all
```
Start using pact-cli container
```bash
docker docker exec -it pact-cli /bin/sh
```
<br/>


# Backend
Build backend
```bash
./mvnw clean install -DskipTests
```
Unit Tests
```bash
./mvnw test
```
Contract Tests
```bash
./mvnw test -Ppact
```
Force a version
```bash
./mvnw versions:set -DnewVersion=0.0.2-SNAPSHOT
```
<br/>

### Endpoints backend


###### product-service:

- Get All Products:  **GET**  http://localhost:9001/api/products
- Get Product by Ref:  **GET**  http://localhost:9001/api/products/product?ref=111 

###### cart-service:

- Add Product:  **POST** http://localhost:9002/api/cart/product `{"ref": "111"}`
- Get Cart:  **GET** http://localhost:9002/api/cart
- Clear Cart **PUT** http://localhost:32501/api/cart/clear

<br/>

# Frontend
Run angular-app
```bash
npm run start:all
```
**URL**:
http://localhost:3001/

<br/>

# Pact Broker
**URL**: http://localhost:9003/

List of environments
```bash
pact-broker list-environments
```
Publish Consumer contracts
```bash
./mvnw pact:publish
```
Record Provider deployment
```bash
pact-broker record-deployment --pacticipant products-service --version 0.0.1 --environment production
```
Record Consumer deployment
```bash
pact-broker record-deployment --pacticipant cart-service --version 0.0.1 --environment production
```
Can-I-deploy Provider version
```bash
pact-broker can-i-deploy --pacticipant products-service --version 0.0.1 --to-environment production
```
Can-I-deploy Consumer version
```bash
pact-broker can-i-deploy --pacticipant cart-service --version 0.0.1 --to-environment production
```
Verifying product angular microfrontend contract
```bash
./mvnw test -Dtest=com.workshop.products.contracts.ProviderContractTest
```