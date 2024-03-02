# SimpleDB Engine

SimpleDB Engine is a lightweight, file-based database engine designed to support basic CRUD operations, simple query processing, and concurrency control.
<br>
<br>
Inspired by the need for a straightforward and easy-to-use database system, SimpleDB Engine offers a robust solution for small-scale applications that require minimal setup and maintenance.

<br>

## Table of Contents

1. [Features](#features)
1. [Installation & Usage](#installation-and-usage)
1. [Contributing](#contributing)
1. [Future Improvements](#future-improvements)
1. [License](#license)

<br>

## Features

- **Lightweight and File-based**: Utilizes JSON for easy data manipulation and human readability.
- **CRUD Operations**: Supports Create, Read, Update, and Delete operations.
- **Simple Query Processing**: Uses RegEx for parsing simple query syntax.
- **Concurrency Control**: Implements basic locking mechanisms for safe concurrent read/write operations.
- **CLI Interface**: Provides a Command Line Interface for database management and query execution.

<br>

## Installation and Usage
1. **Clone the Repository**:
    ```bash
    git clone https://github.com/siddhant-vij/SimpleDB-Engine.git
    ```
2. **Compile the Source Code**: Navigate to the project directory and compile the Java files in the `src` folder. Following command works in Windows Bash.
    ```bash
    cd SimpleDB-Engine
    javac -d bin -classpath 'lib/*' $(find src/com/simpledb -name '*.java')
    ```
3. **Run the Application**: Start the application using the Java command. Following command works in Windows Bash.
    ```bash
    java -cp "bin;lib/*" com.simpledb.Main
    ```
4. **Using the CLI**: Follow the on-screen prompts to perform database operations.

<br>

## Contributing

Contributions are what make the open-source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

1. **Fork the Project**
2. **Create your Feature Branch**: 
    ```bash
    git checkout -b feature/AmazingFeature
    ```
3. **Commit your Changes**: 
    ```bash
    git commit -m 'Add some AmazingFeature'
    ```
4. **Push to the Branch**: 
    ```bash
    git push origin feature/AmazingFeature
    ```
5. **Open a Pull Request**

<br>

## Future Improvements

- **Enhanced Query Processing**: Introduce more complex querying capabilities for advanced SQL queries.
- **Improved Transaction Management**: Implement transaction management with the ability to Commit/Rollback changes to a consistent state with a complete focus on ACID properties.
- **Performance Optimization**: Optimize data storage and retrieval processes for faster access times.
- **Security and Authentication**: Add security and user authentication mechanisms for improved database access.
- **GUI Interface**: Develop a graphical user interface for easier database management.

<br>

## License

Distributed under the MIT License. See [`LICENSE`](https://github.com/siddhant-vij/SimpleDB-Engine/blob/main/LICENSE) for more information.