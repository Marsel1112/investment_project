create table if not exists access_token
(
    date_created     timestamp(6),
    id               bigint generated by default as identity
        primary key,
    refresh_token_id bigint not null
        constraint fka416wtecgwhnkxs2rjfqkryi8
            references refresh_token,
    user_id          bigint not null
        constraint fk35enf10xnmb70oiu2rk91nchy
            references users,
    value            varchar(255)
);