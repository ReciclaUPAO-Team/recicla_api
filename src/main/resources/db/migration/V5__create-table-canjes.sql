create table canjes (
    id bigint not null,
    fecha_solicitada date,
    puntos_canjear double precision,
    recompensa_id bigint,

    primary key (id)
)