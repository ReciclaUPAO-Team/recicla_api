create table recompensas(

    id int not null auto_increment,
    categoria varchar(50) not null,
    titulo varchar(100) not null,
    descripcion varchar(100) not null,
    valor varchar(100),

    primary key(id)
)