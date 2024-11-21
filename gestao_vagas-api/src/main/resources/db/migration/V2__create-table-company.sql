CREATE TABLE company (
    id UUID DEFAULT RANDOM_UUID() PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE, 
    email VARCHAR(255) NOT NULL UNIQUE,    
    password VARCHAR(100) NOT NULL,        
    website VARCHAR(255),                 
    name VARCHAR(255),                     
    description TEXT,                   
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP 
);