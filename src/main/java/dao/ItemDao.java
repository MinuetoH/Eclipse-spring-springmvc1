package dao;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
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
		//spring-jdbc 모듈
		template = new NamedParameterJdbcTemplate(dataSource);
	}
	public List<Item> list() {
		//mapper : db의 컬럼명과 mapper에 지정된 Item 클래스의 프로퍼티를
		//         비교하여 Item 클래스의 객체로 저장
		//query() : select 결과가 여러개의 레코드 가능
		return template.query
			("select * from item order by id", param,mapper);
	}
	public Item getItem(Integer id) {
		param.clear();
		param.put("id", id);
		//queryForObject() : select 결과가 한개 레코드만 가능
		//                   결과가 여러개인 경우 오류 발생
		return template.queryForObject
		("select * from item where id=:id", param, mapper);
	}
	//item 테이블의 id값중 최대값 리턴
	public int maxId() {
		//Integer.class : 리턴값의 자료형 설정
		return template.queryForObject(
		"select nvl(max(id),0) from item",param, Integer.class);
	}
	public void insert(Item item) {
		//BeanPropertySqlParameterSource(item)
		//   item 객체의 get프로퍼티를 이용하여 파라미터로 사용
		SqlParameterSource param = 
				      new BeanPropertySqlParameterSource(item);
		//:id : item.getId() 메서드 호출
		//:name : item.getName() 메서드 호출
		String sql = "insert into item "
			+ " (id, name,price,description,pictureUrl)"
			+ " values (:id,:name,:price,:description,:pictureUrl)";
		template.update(sql, param);		
	}
	
	public void update(Item item) {
		//item 객체의 값을 프로퍼티를 이용하여 파라미터로 사용함.
		SqlParameterSource param = 
			      new BeanPropertySqlParameterSource(item);
		String sql = "update item set name=:name, price=:price,"
			+ " description=:description, pictureUrl=:pictureUrl"
			+ " where id=:id";
		template.update(sql, param); //db 수정
	}
}