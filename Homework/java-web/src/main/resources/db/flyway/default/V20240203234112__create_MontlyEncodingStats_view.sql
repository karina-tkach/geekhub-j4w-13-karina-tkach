CREATE VIEW monthly_encoding_stats AS
SELECT DATE_TRUNC('month', date) AS month, algorithm, status, COUNT(*) AS count
FROM history
GROUP BY month, algorithm, status;
