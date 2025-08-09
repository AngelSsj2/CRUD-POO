create database dbUsuarios;
use dbUsuarios;

create table sexo(
id int auto_increment not null primary key,
sexo varchar(50)
);
insert into sexo (sexo) values ("Masculino");
insert into sexo (sexo) values ("Femenino");
select * from sexo;

create table Usuarios(
id int auto_increment not null primary key,
nombre varchar(50),
apellidos varchar(50),
fkSexo int,
fechaNac date,
correo varchar(50),
foreign key (fkSexo) references sexo(id) on DELETE CASCADE ON UPDATE CASCADE
);



