package com.crud.app.controller;


import java.util.List;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.crud.app.dao.EmpDao;
import com.crud.app.dao.UserDao;
import com.crud.app.entity.Emp;
import com.crud.app.entity.User;


@Controller
public class HomeController {
	@Autowired
	private EmpDao empDao;
	
	@Autowired
	private UserDao userDao;   
	
	@RequestMapping(path="/home",method=RequestMethod.GET)
	public String home(Model model)
	{
		
		List<Emp> list=empDao.getAllEmp();
		model.addAttribute("empList", list);
		return "home";
	}
	@RequestMapping(path="/addEmp",method=RequestMethod.GET)
	public String addEmp()
	{
		return "add_emp"; 
	}
	@Transactional
	@RequestMapping(path="/createEmp",method=RequestMethod.POST)
	public String createEmp(@ModelAttribute Emp emp,HttpSession session)
	{
		System.out.println(emp);  
		int i=empDao.saveEmp(emp);
		session.setAttribute("msg", "Data Added Successfully");
		return "redirect:/addEmp";
	}
	@RequestMapping(path="/editEmp/{id}")
	public String editEmp(@PathVariable int id,Model model)
	{
		
		Emp emp=empDao.getEmpById(id);
		model.addAttribute("emp", emp);
		return "edit_emp";
		
	}
	@RequestMapping(path="/updateEmp",method=RequestMethod.POST)
	public String updateEmp(@ModelAttribute Emp emp,HttpSession session)
	{
		empDao.update(emp);
		session.setAttribute("msg","updated Successfully");
		return "redirect:/home";
		
	}
	@RequestMapping("/deleteEmp/{id}")
	public String deleteEmp(@PathVariable int id,HttpSession session)
	{
		empDao.deleteEmp(id);
		session.setAttribute("msg", "Deleted Successfully");
		return "redirect:/home";
	}
	@RequestMapping("/register")
	public String registerPage()
	{
		return "register";
	}
	@RequestMapping("/login")
	public String loginPage()
	{
		return "login";
	}
	@RequestMapping(path="/createUser",
			 method=RequestMethod.POST)
	public String register(@ModelAttribute User u,HttpSession session)
	{
		System.out.println(u);
		
		userDao.saveUser(u);
		session.setAttribute("msg", "User Registered Successfully..");
		
		return "redirect:/register"; 
	} 
	
	
	@RequestMapping(path="/userLogin",method=RequestMethod.POST)
	public String login(@RequestParam("email") String email,
						@RequestParam("password") String password,HttpSession session)
	{
		User user=userDao.loginUser(email, password);
		if(user!=null)
		{
			session.setAttribute("loginuser", user);
			return "profile";
			
		}else {
			session.setAttribute("msg", "invalid Email and password");
			return "redirect:/login";
		}
		
		
	}
	@RequestMapping("/myProfile")
	public String myProfile()
	{
		return "profile";
	}
	
	@RequestMapping("/logout")
	public String logOut(HttpServletRequest req,HttpSession session)
	{
		session= req.getSession();
		session.removeAttribute("loginuser");
		session.setAttribute("msg", "logout Successfully");
		
		return "login";
	}

}
