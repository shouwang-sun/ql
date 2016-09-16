package com.hiekn.bridgehealth.service;

import java.util.List;

import com.hiekn.bridgehealth.bean.Department;

public interface IDepartmentService {
	/**
	 * 添加一个Department到数据库
	 * @param Department 要添加的Department
	 */
	public void addDepartment(Department department)throws Exception;
	/**
	 * 更新Department
	 * @param Department 更新后的Department
	 */
	public void updateDepartment(Department department)throws Exception;
	/**
	 * 根据Department的id删除Department
	 * 
	 * @param id 要删除Department的id
	 */
	public void deleteDepartment(Integer id)throws Exception;
	/**
	 * 更具Department的id查找Department
	 * @param id 
	 * @return a persistent instance or null
	 */
	public Department findById(Integer id)throws Exception;
	/**
	 * @param currentPage
	 * @return
	 */
	public List<Department> findByPage(Integer currentPage)throws Exception;
	/**
	 * 查找所有的Department
	 * @return Department list
	 */
	public List<Department> findAll()throws Exception;
}
