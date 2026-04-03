-- MySQL initialization script for misk-playground Docker setup
-- This script runs when the MySQL container starts for the first time

-- Ensure the database exists (it should be created by MYSQL_DATABASE env var)
CREATE DATABASE IF NOT EXISTS exemplar_testing;

-- Grant necessary permissions to root user from any host
GRANT ALL PRIVILEGES ON exemplar_testing.* TO 'root'@'%';

-- Create a dedicated misk user for the application (optional, more secure)
CREATE USER IF NOT EXISTS 'misk'@'%' IDENTIFIED BY 'miskpassword';
GRANT ALL PRIVILEGES ON exemplar_testing.* TO 'misk'@'%';

FLUSH PRIVILEGES;

-- Switch to our database
USE exemplar_testing;

-- Note: The actual table creation will be handled by Flyway migrations
-- when the misk service starts up and runs the migration files