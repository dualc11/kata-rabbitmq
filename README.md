#Rabbitmq Kata season
The goal of this repo is to be an introduction to rabbitmq with spring-boot.


## Rabbitmq core concepts 
- **What is rabbitMQ?** RabbitMQ is a message-queueing software also known as a message broker or queue manager.
- **Connection** - physical network, TCP layer, connection between the applications. They are meant to be long-lived, therefore it is strongly discouraged to reuse the client connection
- **Channel** -  is a virtual connection inside a connection. It reuses a connection resources.
- **Exchange** - is in charge of applying routing rules for messages, makes sure that the messages reaches their final destination,  in other words, message reach the queue that is bonded. Defines the routing based on data attributes passed along with the message.
- **Queue** - sequence of messages.
- **Binding** - binding is a virtual link between an exchange and a queue within the broker. When publishing a message to an exchange, applications use a routing-key attribute. **When a message is evaluated by an exchange to determine the appropriate queues it should be routed to, the message’s routing key is evaluated against the binding key.**


## Prerequisites
- Twitter account
- JVM 11

## Kata agenda
### Part I - Connect to twitter API
- Checkout the `kata-season` branch. There you will find everything you need to start the kata.
- Go to https://developer.twitter.com/en/portal/dashboard to generate API tokens.
- Create a TwitterWebClientConfiguration
- Create a JacksonConfiguration
- Create TwitterGateway
- Create TweetController
- Make a request to `GET - http://localhost:8080/subscribe/topic/cristiano`

### Part II - Create a rabbit producer
- Add rabbit to the docker-compose.yml
- Create a rabbit configuration, where you define the connection factory, connection and channel
- Create rabbit consumer using previously define rabbit configuration
- Using the twitter information, publish some messages

### Part III - Create a rabbit consumer
- Define a queue to each the consumer will consume the events
- Create a rabbit consumer
- Consume the messages published the previously created producer