CREATE TABLE candidate (
    id UUID DEFAULT RANDOM_UUID() PRIMARY KEY,
    name VARCHAR(255),
    username VARCHAR(255) NOT NULL UNIQUE,  
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    description TEXT,
    curriculum TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP 
);