# Totem Cafe Self-Service System Documentation

## Project Description
This is a Java-based self-service totem for cafeterias. It features a web interface that allows customers to order food and beverages through an interactive system.

## Technologies Used
- **Java 11**: The primary programming language for the backend.
- **HTTP Server**: For handling requests.
- **HTML5, CSS3, JavaScript**: For the frontend web interface.
- **Gson**: For JSON processing.
- **Maven**: For project management and build.

## Features
- **Interactive Initial Screen**: Engaging start for users.
- **Product Menu with Categories**: Easily navigable menu for different food items.
- **Shopping Cart**: Users can add products to their cart.
- **Multiple Payment Methods**: Various options to complete the purchase.
- **Order Confirmation**: Users receive confirmation of their orders.

## Project Structure
```
├── src
│   ├── main
│   │   ├── java
│   │   │   └── Main.java
│   │   └── resources
│   └── test
├── pom.xml
└── README.md
```

## How to Run
1. Clone the repository:
   ```sh
   git clone https://github.com/leonrdpavani/totem-cafe.git
   cd totem-cafe
   ```
2. Compile the project with Maven:
   ```sh
   mvn clean compile
   ```
3. Run the application:
   ```sh
   mvn exec:java -Dexec.mainClass="Main"
   ```
4. Access the application at [http://localhost:8080](http://localhost:8080)

## API Endpoints
| Method | Endpoint               | Description                          |
|--------|------------------------|--------------------------------------|
| GET    | /api/products          | Get list of all available products  |
| POST   | /api/order             | Place an order                       |
| GET    | /api/order/{id}       | Get order status                     |

## Usage Flow
1. User selects products from the menu.
2. Items are added to the shopping cart.
3. User proceeds to checkout and selects payment method.
4. User submits the order and receives confirmation.

## Design Features
- User-friendly interface.
- Responsive design for various devices.

## Available Products
- **Coffees**: Espresso, Latte, Americano.
- **Snacks**: Chips, Sandwiches.
- **Desserts**: Cakes, Cookies.

## Technical Notes
- Ensure that Java 11 and Maven are installed.
- The project utilizes an embedded HTTP server for local testing.

## Author Information
**Leonardo Pavani** - Creator of the Totem Cafe Self-Service System.  
Github: [leonrdpavani](https://github.com/leonrdpavani)  

*Documentation last updated on 2025-11-28.*