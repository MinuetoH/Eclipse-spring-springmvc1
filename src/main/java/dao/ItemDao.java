package dao;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import logic.Item;

/*
 * @Component : 해당 클래스를 객체화
 * model 기능 : db와 연결된 클래스
 */
@Repository  //@Component + model 기능
public class ItemDao {
	private NamedParameterJdbcTemplate template;
	private Map<String,Object> param = new HashMap<>();
	private RowMapper<Item> mapper = 
			   new BeanPropertyRowMapper<>(Item.class);
	@Autowired  //객체 중 DataSource 객체를 주입.
	//DataSource :...DriverManagerDataSource 객체 주입
	public void setDataSource(DataSource dataSource) {
		template = new NamedParameterJdbcTemplate(dataSource);
	}
	public List<Item> list() {
		//mapper : db의 컬럼명과 mapper에 지정된 Item 클래스의 프로퍼티를
		//         비교하여 Item 클래스의 객체로 저장
		return template.query
			("select * from item order by id", param,mapper);
	}
	public Item getItem(Integer id) {
		param.clear();
		param.put("id", id);
		return template.queryForObject
		("select * from item where id=:id", param, mapper);
	}
}