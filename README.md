# lotteryapp
I am building a lottery app

It uses:
- Springboot
- Spring Data JPA for mongodb
- Docker - mongodb and mongoexpress
- REST
- GSON and selenium

It has an admin and an app part. All interactions are via REST endpoints.
Admin part serves to scrap the results from the various lottery companys' website.

Damacai and Magnum both exposes their results in json thus all calls are make via requestTemplate whereas Toto does not and thus needs to be scrap using selenium

WHAT IS DONE:
Endpoint to scrap based on date

example
http://localhost:8082/api/v1/{PROVIDER}/actions/scrap?date=20231115

Where provider is a path variable can be replace by either MAGNUM, DAMACAI or TOTO
date is a parameter and must enter in the format YYYYMMDD

Currently only DAMACAI and MAGNUM works. TOTO is work in progress.

If installed properly with docker compose setup and after successfully ran scarp endpoint, 
One should see 2 collections

![img.png](img.png)

Here's an example of the Damacai Result collection of document
![img_1.png](img_1.png)


TODO:
The app portion which provides endpoints to investigate the numbers after there are stored in mongodb as collections
