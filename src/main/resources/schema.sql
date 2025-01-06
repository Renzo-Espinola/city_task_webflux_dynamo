CREATE TABLE IF NOT EXISTS task (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    job_id VARCHAR(255),
    task_hour INTEGER,
    task_minute INTEGER,
    execution_days TEXT,
    execution_command TEXT,
    execute_until TIMESTAMP WITH TIME ZONE,
    device_id VARCHAR(255),
    device_action TEXT,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);