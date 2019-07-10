CREATE TABLE IF NOT EXISTS deathspawn(
                                         id INT PRIMARY KEY,
                                         player_id varchar(40),
                                         death_world varchar(40),
                                         death_x DOUBLE,
                                         death_y DOUBLE,
                                         death_z DOUBLE,
                                         death_pitch FLOAT,
                                         death_yaw FLOAT
)