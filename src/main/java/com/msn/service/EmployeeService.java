package com.msn.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.msn.exception.RecordNotFoundException;
import com.msn.model.EmployeeEntity;
import com.msn.repo.EmployeeRepository;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	public List<EmployeeEntity> getAllEmployees() {
		List<EmployeeEntity> employeeList = employeeRepository.findAll();
		if (employeeList.size() > 0)
			return employeeList;
		else
			return new ArrayList<EmployeeEntity>();

	}

	public EmployeeEntity getEmployeeById(Long id) throws RecordNotFoundException {
		Optional<EmployeeEntity> employee = employeeRepository.findById(id);
		if (employee.isPresent())
			return employee.get();
		else
			throw new RecordNotFoundException("No employee record exist for given id");

	}

	public EmployeeEntity createOrUpdateEmployee(EmployeeEntity entity) {
		Optional<EmployeeEntity> employee = employeeRepository.findById(entity.getId());
		if (employee.isPresent()) {
			EmployeeEntity newEntity = employee.get();
			newEntity.setEmail(entity.getEmail());
			newEntity.setFirstName(entity.getFirstName());
			newEntity.setLastName(entity.getLastName());
			newEntity = employeeRepository.save(newEntity);
			return newEntity;
		} else
			return employeeRepository.save(entity);

	}

	public void deleteEmployeeById(Long id) throws RecordNotFoundException {
		Optional<EmployeeEntity> employee = employeeRepository.findById(id);
		if (employee.isPresent())
			employeeRepository.deleteById(id);
		else
			throw new RecordNotFoundException("No employee record exist for given id");

	}
}
