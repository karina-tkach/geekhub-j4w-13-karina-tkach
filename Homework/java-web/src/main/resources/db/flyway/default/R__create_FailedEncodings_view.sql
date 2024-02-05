CREATE VIEW failed_encodings AS
SELECT *
FROM history
WHERE status = 'FAIL'
