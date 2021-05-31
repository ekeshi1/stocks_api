#### Running locally

https://hub.docker.com/_/postgres

With docker-compose:
```
docker-compose -f stack.yml up -d
```

To take the psql service down:
```
docker-compose -f stack.yml down
```

With a docker swarm setup:
```
docker stack deploy -c stack.yml postgres
```
