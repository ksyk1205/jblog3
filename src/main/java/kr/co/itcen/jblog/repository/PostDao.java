package kr.co.itcen.jblog.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.itcen.jblog.vo.PostVo;

@Repository
public class PostDao {
	@Autowired
	private SqlSession sqlSession;
	
	public List<PostVo> getList(Long categoryno) {
		List<PostVo> list = sqlSession.selectList("post.getList",categoryno);
		return list;
	}

	public PostVo getPost(Long postNo, Long categoryno) {
		Map<String, Long>map = new HashMap<String, Long>();
		map.put("postNo",postNo);
		map.put("categoryno",categoryno);
		PostVo vo = sqlSession.selectOne("post.getpost",map);
		return vo;
	}

}
