CREATE TABLE actividades (
        id BIGINT AUTO_INCREMENT PRIMARY KEY,
        nombre VARCHAR(155) not null,
        puntos_asociados INT DEFAULT 0,
        fecha timestamp not null default (now()),
        cantidad_reciclada DOUBLE not null,
        imagen VARCHAR(255) not null,
        residuo_id BIGINT not null,
        usuario_id BIGINT not null,

        FOREIGN KEY (usuario_id) REFERENCES usuarios(id),
        FOREIGN KEY (residuo_id) REFERENCES residuos(id)
);

