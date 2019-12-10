insert into users (pis, username, password)
SELECT * FROM (SELECT 123456789, 'admin', 'YWRtaW46cGFzc3dvcmQ=') AS tmp
WHERE NOT EXISTS (
    SELECT pis FROM users WHERE pis = 123456789
) LIMIT 1;
