
# Generic IoT Infrastructure Project

## Overview
The Generic IoT Infrastructure Project is a scalable and flexible solution designed to manage IoT devices, store their data, and analyze it in a fast and easy way. This project was developed as part of my Java studies and incorporates various design patterns such as Factory, Singleton, Object Pool, and Observer, while adhering to SOLID and OOP principles. The infrastructure consists of multiple modules, each developed independently and integrated together to form a cohesive system. These modules were built using modular development practices and can be extended or replaced with custom implementations to meet specific requirements.

## Requirements
In my studies, I was required to work with the following set of requirements:
- Generics
- Concurrency
- Plug & Play
- Fault tolerance

To fulfill these requirements, the system is designed as follows:

1. The Gateway Servlet is a receiver for client requests, supporting various request types through HTTP (UDP in the future). It achieves this by employing generic data handling techniques. Additionally, each module within the system is designed to be generic, allowing for flexibility and reusability across different functionalities.

2. Concurrent processing techniques are employed in the Gateway Servlet to handle multiple requests simultaneously, ensuring efficient utilization of system resources.

3. The system will support a plug-and-play architecture, allowing for the dynamic integration of different components and modules based on specific requirements.

4. Additionally, fault tolerance mechanisms will be integrated to assist the system in handling failures and ensuring system stability and availability.

It's worth noting that although these modules have been built, they have not yet been integrated into the project.

## Flow
1. The Gateway Servlet receives requests from clients through HTTP protocols.
2. The Gateway Servlet parses the request into JSON format.
3. The parsed request is added as a new task to the Thread Pool.
4. The Thread Pool assigns the task to a worker thread for execution.
5. The Singleton Command Factory creates an instance of the selected command.
6. The executed command interacts with the MongoDB module for data storage and retrieval.
7. The command performs specific operations based on the requested function, executing I/O operations on the MongoDB database.
8. The result of the command execution is returned.
9. The Response is prepared and sent back to the client.

![FlowChart](https://github.com/OuriBerreby/IOTProject/assets/87609778/954ab60f-9146-47cf-aa3d-5c01dda8dc00)
