CREATE TABLE residuos (
        id bigint not null auto_increment,
        nombre varchar(100) not null,
        descripcion varchar(100),
        tipo varchar(100) not null,

        primary key (id)
);