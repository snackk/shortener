# Shortener
Yet another URL shortener.<p> 
This one really sparks joy as it's probably one of the worst implementation 
of tools of such kind :)
Only one test, this is what I'll strive for, less tests means we can 
focus on more features to come ( LOL ) 

## Reference Documentation

### API's
### Create a short URL
Request:
```
curl --location 'http://127.0.0.1:8080/api/urls' \
--header 'Content-Type: application/json' \
--data '{
    "originalUrl": "www.google.com/search?q=badjoras"
}'
```
Response:
200 ok, with "zTIY5hypea"

### Redirect to URL
Request:
```
curl --location 'http://127.0.0.1:8080/api/urls/zTIY5hypea'
```
Response:
302 found, redirecting with the Location in the headers.

