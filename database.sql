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


 /*SELECT usuarios.id,usuarios.nombre,usuarios.apellidos,sexo.sexo,usuarios.correo,usuarios.fechaNac
from usuarios inner join sexo on usuarios.fkSexo = sexo.id;

update usuarios set usuarios.nombre=?, usuarios.apellidos=?, usuarios.sexo=?, usuarios.correo=?, usuarios.fechaNac=? where usuarios.id=?;*/

