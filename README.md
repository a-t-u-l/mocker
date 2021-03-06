# mocker
**API mocking server using java play framework**

Mocker aims to be the hosted central server which can mock the behaviour of all the API's you might be consuming. It is based on java play framework and uses its hot reload capability to generate the mocks at the runtime. What it means is that once you host it on any server you get a fully functional API environment with capability to generate API mocks on fly.

`Mocker uses scala version > 2.11.11` and `Java > 8` hence you need to have `sbt` and `java`  with same or greater version to run.

**Steps to getting started:**

1. clone this project
```bash
$ git clone git@github.com:a-t-u-l/mocker.git
```

2. In terminal go to the root directory viz mocker
```bash
$ cd mocker/
```

3. If you don't have sbt installed then install it. Refer this [guide](https://www.scala-sbt.org/download.html). On Mac you can use homebrew (On Mac, you might need xcode command line tools installled. Just follow the instructions in terminal) :
```bash
$ brew install sbt@1
```

4. execute command to get started. It takes sometime to install all dependencies : 

```bash
sbt run
```

5. It should fetch all of the dependencies and start the server at _port 9001_. If you wish to change the port go to `build.sbt` and change the port. then open following url in a browser :

```
http://localhost:9001
``` 
This would be the default location.

You can generate mock either by using the UI form or by calling the mock generation API. Once generated you can use the specification you provided along with a unique mock id in header to invoke the API and get the specified response.


**A typical mock Get API creation request would be :** 

```
Method : POST

URL: http://localhost:9001/generate

Header :
Content-Type: application/json

Request Body :

{
    "requestType": "GET",
    "URI" : "/complete/path",
    "queryParams":{
    	"queryParamkey":"queryParamValue"
    },
    "responseStatusCode": 200,
    "responseBody" :"{\"message\":\"essentially a stringified json body\"}",
    "headers" : {
        "key1" : ["value1"],
        "key2" : ["value2"]
    }
}
```

**CURL for the same :**
```
curl -X POST \
  http://localhost:9001/generate \
  -H 'Content-Type: application/json' \
  -d '{
    "requestType": "GET",
    "URI" : "/complete/path",
    "queryParams":{
    	"queryParamkey":"queryParamValue"
    },
    "responseStatusCode": 200,
    "responseBody" :"{\"message\":\"essentially a stringified json body\"}",
    "headers" : {
        "key1" : ["value1"],
        "key2" : ["value2"]
    }
}'
```

Where 'http://localhost:9001' is your hosted server. Once you send this request you would get response as :

```
{
    "message": "Successfully generated the mock. Use following identifier to access your mocked API.",
    "created": true,
    "uri": "/complete/path?queryParamkey=queryParamValue",
    "body": "",
    "mockId": "7uAzgK5sxkeR",
    "headers": {
        "key1": [
            "value1"
        ],
        "key2": [
            "value2"
        ]
    }
}
```

Now you can invoke the mock API as:

```
Method : GET

URL: http://localhost:9001/complete/path?queryParamkey=queryParamValue

Headers :
key1 : value1
key2 : value2
mockId : 7uAzgK5sxkeR
```

**CURL for the same :**

```
curl -X GET \
  'http://localhost:9001/complete/path?queryParamkey=queryParamValue' \
  -H 'key1: value1' \
  -H 'key2: value2' \
  -H 'mockId: 7uAzgK5sxkeR'
```

And you would get the response you have assigned to this mapping. If you want different responses for same request you just need to create header which can identify the response. for e.g. you can have a header as 'status : (pass/fail)' based on whether you want to get pass mapped response of failed mapped response.

For POST mock APIs the 'requestType' would be POST' and you just need to add a additional field in the request body as 'requestBody'.

For wildcard path param matching in the 'URI' field you should pass path as '/complete/:wildcard/path'
Now you can pass anything in place of ':wildcard' and the same API would be invoked. Currently the project supports upto 3 wildcards as path param.

Note : after creation of mock once you invoke the API there might be slight delay in response as the hot redeployment is in progress, after that the API reponse time will be really small.


**Default API List**

Following API paths are reserved:

```
1.   GET     /                           Index Page
2.   POST    /generate                   API path to generate mock via API
3.   GET     /generated/list             To fetch the list of existing mocks
4.   GET     /generator                  API path to generate mock via UI
5.   POST    /generator                  API path to generate mock via UI
6.   GET     /assets/*file               Asset upload
```