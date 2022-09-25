## How it work's

Just run next command from project root:
```sh
docker-compose -f docker/docker-compose.yaml up -d
```

## First run
When you have first run, mysql database need to initialize. Just see to log of container and wait when database has been created.
```log
mysql-db_1         | 2022-09-25 12:11:35+00:00 [Note] [Entrypoint]: MySQL init process done. Ready for start up.
mysql-db_1         | 
mysql-db_1         | 2022-09-25T12:11:36.042964Z 0 [Warning] [MY-011068] [Server] The syntax '--skip-host-cache' is deprecated and will be removed in a future release. Please use SET GLOBAL host_cache_size=0 instead.
mysql-db_1         | 2022-09-25T12:11:36.044004Z 0 [System] [MY-010116] [Server] /usr/sbin/mysqld (mysqld 8.0.30) starting as process 1
mysql-db_1         | 2022-09-25T12:11:36.049337Z 1 [System] [MY-013576] [InnoDB] InnoDB initialization has started.
mysql-db_1         | 2022-09-25T12:11:37.212857Z 1 [System] [MY-013577] [InnoDB] InnoDB initialization has ended.
mysql-db_1         | 2022-09-25T12:11:39.073882Z 0 [Warning] [MY-010068] [Server] CA certificate ca.pem is self signed.
mysql-db_1         | 2022-09-25T12:11:39.073941Z 0 [System] [MY-013602] [Server] Channel mysql_main configured to support TLS. Encrypted connections are now supported for this channel.
mysql-db_1         | 2022-09-25T12:11:39.230158Z 0 [Warning] [MY-011810] [Server] Insecure configuration for --pid-file: Location '/var/run/mysqld' in the path is accessible to all OS users. Consider choosing a different directory.
mysql-db_1         | 2022-09-25T12:11:39.266368Z 0 [System] [MY-011323] [Server] X Plugin ready for connections. Bind-address: '::' port: 33060, socket: /var/run/mysqld/mysqlx.sock
mysql-db_1         | 2022-09-25T12:11:39.266503Z 0 [System] [MY-010931] [Server] /usr/sbin/mysqld: ready for connections. Version: '8.0.30'  socket: '/var/run/mysqld/mysqld.sock'  port: 3306  MySQL Community Server - GPL.
```
When it created, restart or recreate projectx-application:
```sh
docker-compose -f docker/docker-compose.yaml restart projectx-mobile
```

## Known troubles with mysql

Sometimes, after stop, mysql don't saving files and can't start. 
For fix it, stop mysql and remove container, then start compose again:
```sh
docker-compose -f docker/docker-compose.yaml down
docker ps -aqf status=exited && xargs docker rm 
```

## Manual build

Also you can build application manually with next command:
```sh
docker build -f docker/Dockerfile . -t IMAGE_NAME
```

## How to rebuild app?

If you make some changes and want to run new version of image, just rebuild service:

```sh
docker-compose -f docker/docker-compose.yaml up -d --build

```
