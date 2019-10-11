package kr.co.itcen.jblog.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.itcen.jblog.service.BlogService;
import kr.co.itcen.jblog.vo.BlogVo;
import kr.co.itcen.jblog.vo.CategoryVo;
import kr.co.itcen.jblog.vo.PostVo;


//1)jblog3/id
//2)jblog3/id/pathNo1//카테고리 링크
//3)jblog3/id/pathNo1/pathNo2 // post링크
@Controller
@RequestMapping("/{id:(?!assets).*}")//assets밑에 있는 파일제외하고 받는다
public class BlogController {
	
	@Autowired
	private BlogService blogService;
	
	@RequestMapping( {"", "/{pathNo1}", "/{pathNo1}/{pathNo2}" } )
	public String index( 
		@PathVariable String id,
		@PathVariable Optional<Long> pathNo1,
		@PathVariable Optional<Long> pathNo2,
		Model model) {
		
		
		List<CategoryVo> list = blogService.getList(id);
		model.addAttribute("list",list);
		
		System.out.println(list.get(0));
		
		Long categoryno= list.get(0).getNo();//[0][1][2]...
		//리스트에 1번쨰 category
		
		Long postNo = 0L;
		
		//2,3일떄만 동작
		if(pathNo2.isPresent()) { //pathNo2가 존재할 때  
			categoryno=pathNo1.get(); 
			postNo=pathNo2.get();
		}else if(pathNo1.isPresent()) { 
			categoryno=pathNo1.get();
		}	
		
		
		List <PostVo> postlist = blogService.getpost(categoryno);
		model.addAttribute("postlist",postlist);
		
		
		PostVo postvo;
		if(postNo==0 && (!postlist.isEmpty())) { //postNo가 초기값이고 postlist가 비어있지 않을 때
			//postNo==0 -> 나갔다 들어왔을 때 0으로 세팅
			//2번째는 postlist가 있지만 리스트가 있는지 없는지를 비교
			postNo=postlist.get(0).getNo();
			postvo=blogService.getpost(postNo,categoryno);
		}else {//
			postvo=blogService.getpost(postNo,categoryno);
			
		}
		
		BlogVo blogvo = blogService.get(id);
		model.addAttribute("blogvo",blogvo);
		model.addAttribute("postvo",postvo);
		model.addAttribute("id",id);
		
		if(id.equals(blogvo.getId()))
			model.addAttribute("isMe",true);
		
		return "blog/blog-main";
	}

}


