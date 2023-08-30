//step_2
CREATE TABLE car(
	id serial PRIMARY KEY,
	brand varchar(255),
	model varchar(255),
	price int
)

CREATE TABLE driver (
id serial PRIMARY KEY,
	name varchar(255),
	age int,
	license boolean,
	car_id serial REFERENCES car(id)
)
//step_3
SELECT s.name AS student_name, 
s.age AS student_age, 
f.name AS faculty_name from student s
LEFT JOIN faculty f ON s.faculty_id = f.id;

SELECT s.name AS student_name from student s
INNER JOIN avatar a ON s.id = a.student_id;