select u.id, u.username, u.password, u.status, r.role, e.email
from user_role u_r
join
roles r on r.id = u_r.role_id
join users u on u_r.user_id = u.id
join emails e on e.user_id = u.id where u.id = ?;