CREATE TABLE usuarios (
        id bigint not null auto_increment,
        nombre varchar(100) not null,
        edad int,
        telefono char(10) unique,
        correo varchar(50) not null unique,
        puntos_acumulados int default 0,
        dni char(8) not null unique,
        rol varchar(100) not null,
        created_at timestamp not null default (now()),

        primary key (id)
);
