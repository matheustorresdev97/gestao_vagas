CREATE TABLE job (
    id UUID PRIMARY KEY DEFAULT,
    description TEXT,                      
    benefits TEXT,                        
    level VARCHAR(255),                   
    company_id UUID NOT NULL,              
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP 
);
