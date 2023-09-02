-- liquibase formatted sql

-- changeSet annabelousova:1
CREATE INDEX student_name_index ON student (name);

-- changeSet annabelousova:2
CREATE INDEX faculty_name_color_index ON faculty (name,color);