package springbook.user.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import springbook.user.domain.User;

public class UserDao {

	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;

	private RowMapper<User> rowMapper = new RowMapper<User>() {
		@Override
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			User user = new User();
			
			user.setId( rs.getString("id"));
			user.setName( rs.getString("name"));
			user.setPassword( rs.getString("password"));
			return user;
		}
	};

	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.dataSource = dataSource;
	}
	
	public void add(User user) {
		jdbcTemplate.update("insert into users values (?,?,?)",
				             user.getId(), user.getName(), user.getPassword() );
	}
	
	public void deleteAll() {
		jdbcTemplate.update("delete from users");
	}
	
	//public int deleteOne(String id) {
	//	jdbcTemplate.update("delete from users where id = ?", user.getId());
	//}
	
	public int getCount() {
		return jdbcTemplate.queryForObject("select count(*) from users", Integer.class);
	}

	public User get(String id) {
		return jdbcTemplate.queryForObject("select * from users where id=?", new Object[] {id}, rowMapper);
	}

	public List<User> getAll() {
		return jdbcTemplate.query("select * from users", rowMapper);
	}
	
	
	
}
