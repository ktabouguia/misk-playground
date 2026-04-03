-- Create users table for Exemplar service
CREATE TABLE users (
  id BIGINT NOT NULL AUTO_INCREMENT,
  created_at DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  updated_at DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
  email VARCHAR(255) NOT NULL,
  name VARCHAR(255) NOT NULL,
  is_active BOOLEAN NOT NULL DEFAULT TRUE,

  PRIMARY KEY (id),
  UNIQUE KEY uk_users_email (email),
  KEY idx_users_created_at (created_at),
  KEY idx_users_is_active (is_active)
);