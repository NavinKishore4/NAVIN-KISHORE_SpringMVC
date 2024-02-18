package com.controller;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.model.Employee;

@Controller
public class MyController  {
	
	@RequestMapping("/")
	public String home()
	{
		return "home";
	}
	
	@RequestMapping("/allemployees")
	public String allemployee(Model data)
	{
		SessionFactory factory = new Configuration().configure().buildSessionFactory();
		Session session = factory.openSession();

		try {
			Transaction tx=null;
			tx  = session.beginTransaction();
			
			Query q1=session.createQuery("from Employee");
			List<Employee> e1 = new ArrayList<>();
			e1=q1.getResultList();
			
			data.addAttribute("emps",e1);

			tx.commit();

		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
		
		return "all-employees";
	}
	
	@RequestMapping("/add-record-form")
	public String addRecordForm()
	{
		return "add-record-form";
	}
	
	@PostMapping("/addrecord")
	public String addRecord(@RequestParam String employeeName,@RequestParam String employeeAddress,@RequestParam String employeePhone,@RequestParam String employeeSalary,Model data)
	{
		SessionFactory factory = new Configuration().configure().buildSessionFactory();
		Session session = factory.openSession();
		try {
			Transaction tx=null;
			tx  = session.beginTransaction();
			
			Employee e1=new Employee(0, employeeName, employeeAddress, employeePhone, employeeSalary);
			session.save(e1);

			tx.commit();

		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
		
		Query q1=session.createQuery("from Employee");
		List<Employee> e1 = new ArrayList<>();
		e1=q1.getResultList();
		
		data.addAttribute("emps",e1);
		
		return "all-employees";
	}
	
	@GetMapping("/update-employee-form")
	public String updateForm(@RequestParam int id,Model data)
	{
		SessionFactory factory = new Configuration().configure().buildSessionFactory();
		Session session = factory.openSession();
		try {
			Transaction tx=null;
			tx  = session.beginTransaction();
			
			Employee updateemp=session.get(Employee.class, id);
			data.addAttribute("update",updateemp);
			
			tx.commit();

		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
		
		return "update-employee-form";
	}
	
	@PostMapping("/update")
	public String update(@RequestParam int id,@RequestParam String employeeName,@RequestParam String employeeAddress,@RequestParam String employeePhone,@RequestParam String employeeSalary,Model data)
	{
		SessionFactory factory = new Configuration().configure().buildSessionFactory();
		Session session = factory.openSession();
		try {
			Transaction tx=null;
			tx  = session.beginTransaction();
			
			Employee e1=new Employee(id, employeeName, employeeAddress, employeePhone, employeeSalary);
			session.update(e1);
			tx.commit();

		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
		
		Query q1=session.createQuery("from Employee");
		List<Employee> e1 = new ArrayList<>();
		e1=q1.getResultList();
		
		data.addAttribute("emps",e1);
		
		return "all-employees";
	}
	
	@GetMapping("/delete-employee")
	public String delete(@RequestParam int id,Model data)
	{
		SessionFactory factory = new Configuration().configure().buildSessionFactory();
		Session session = factory.openSession();
		try {
			Transaction tx=null;
			tx  = session.beginTransaction();
			
			Employee e1=new Employee(id, null, null, null, null);
			session.delete(e1);
			tx.commit();

		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
		
		Query q1=session.createQuery("from Employee");
		List<Employee> e1 = new ArrayList<>();
		e1=q1.getResultList();
		
		data.addAttribute("emps",e1);
		
		return "all-employees";
	}
	
}
