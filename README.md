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

TODO:
The app portion which provides endpoints to investigate the numbers after there are stored in mongodb as collections
