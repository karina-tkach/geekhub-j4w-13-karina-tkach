CREATE VIEW daily_encoding_stats AS
SELECT DATE_TRUNC('day', date) AS day, algorithm, status, COUNT(*) AS count
FROM history
GROUP BY day, algorithm, status;
