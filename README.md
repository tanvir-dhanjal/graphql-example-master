# graphql-example-master
GraphQL is one of the most modern ways of building and querying APIs.GraphQL is a syntax that describes how to ask for data, and is generally used to load data from a server to a client.

In this example we have two entities `Employee` and `Task` with one to many relationship. Below are the steps to run this code.

1. Checkout code with Git or Download zip.
2. Run `mvn clean install`
3. Service should be up and running. You can verify it using `http://localhost:8088/api/v1/healthcheck`
4. Put below queries in **POST** request url `http://localhost:8088/api/v1/graphql`

 **Example 1 - Graph QL Query to fetch Employee** 
 
    {
 	    employee(EmployeeId : 102){
 		name
 		city
 		state
 	    }
    }
    
 **Graph QL Output**
 
    {
        "employee": {
          "name": "John",
          "city": "San Francisco",
          "state": "California"
        }
    }
  
 
 
 
**Example 2 - Graph QL Query to fetch Employee** 

    {
  	    employee(EmployeeId : 101){
            name
            city
            state
            tasks {
                name
            }
  	    }
    }
  
 **Graph QL Output**
 
    { 
      "employee": {
          "name": "Alex",
          "city": "Oakland",
          "state": "California",
          "tasks": [
              {
                  "name": "Set up office"
              },
              {
                  "name": "Call Mike"
              }
          ]
      }
    }
   