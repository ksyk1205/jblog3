package kr.co.itcen.jblog.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class CategoryDao {
	@Autowired
	private SqlSession sqlSession;
	
	public void default_insert(String blog_id) {
		sqlSession.insert("category.default_insert",blog_id);
		
	}

}
