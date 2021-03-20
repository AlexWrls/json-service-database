drop database if exists serviceDB;
create database serviceDB with  owner = postgres encoding = 'UTF8';
alter database serviceDB owner to postgres;

drop table if exists public.product cascade;
create table if not exists public.product
(
    id bigint not null ,
    name character varying(100) not null ,
    price integer not null ,
    primary key (id)
);
alter table public.product owner to postgres;

drop table if exists public.buyer cascade ;
create table if not exists public.buyer
(
    id bigint not null ,
    first_name character varying(100) not null ,
    last_name character varying(100) not null ,
    primary key (id)
);
alter table public.buyer owner to postgres;

drop table if exists public.purchase cascade ;
create table if not exists public.purchase
(
    id bigint not null ,
    product_id bigint not null ,
    buyer_id bigint not null ,
    date date,
    primary key (id),
    constraint product_fk foreign key (product_id) references product(id)
        on delete cascade on  update cascade ,
    constraint buyer_fk foreign key (buyer_id) references buyer(id)
        on delete cascade on update cascade
);
