## Developing a message queueing system.
* Create your own queue that will hold messages in the form of JSON(Standard
library with queue implementation were not allowed).
* There can be more than one queue at any given point of time.
* There will be one publisher that can generate messages to a queue.
* There are multiple subscribers that will listen to queues for messages.
* Subscribers should not be tightly coupled to the system and can be added or
removed at runtime.
* When a subscriber is added to the system, It registers a callback function which
* makes an API call to the given end point with the json payload, this callback
function will be invoked in case some message arrives.
* Basic retry mechanism for handling error cases when some exception occurs in listening/processing a message, that must be retried.
