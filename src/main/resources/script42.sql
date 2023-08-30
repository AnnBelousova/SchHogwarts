//step_1

ALTER TABLE student
ADD CHECK (Age>=16);

ALTER TABLE student
ALTER COLUMN name SET NOT NULL;

ALTER TABLE student
ADD CONSTRAINT un_name UNIQUE (name);

ALTER TABLE faculty
ADD CONSTRAINT name_color UNIQUE (name,color);

