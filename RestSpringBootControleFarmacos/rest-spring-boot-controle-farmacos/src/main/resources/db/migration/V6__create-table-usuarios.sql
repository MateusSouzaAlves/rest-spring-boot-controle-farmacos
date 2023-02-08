create table usuarios
(
   id bigint not null auto_increment,
   login varchar (100) not null,
   senha varchar (255) not null,
   ativo tinyint,
   nome varchar (100) not null,
   email varchar (100) not null,
   cpf varchar (100) not null,
   cep varchar (100) not null,
   uf char (2) not null,
   cidade varchar (100) not null,
   bairro varchar (100) not null,
   complemento varchar (100),
   numero varchar (100),
   primary key (id)
);