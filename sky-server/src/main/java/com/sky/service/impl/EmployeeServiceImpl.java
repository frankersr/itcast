package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.PasswordConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.exception.AccountLockedException;
import com.sky.exception.AccountNotFoundException;
import com.sky.exception.PasswordErrorException;
import com.sky.mapper.EmployeeMapper;
import com.sky.result.PageResult;
import com.sky.service.EmployeeService;
import lombok.val;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 员工登录
     *
     * @param employeeLoginDTO
     * @return
     */
    public Employee login(EmployeeLoginDTO employeeLoginDTO) {
        String username = employeeLoginDTO.getUsername();
        String password = employeeLoginDTO.getPassword();

        //1、根据用户名查询数据库中的数据
        Employee employee = employeeMapper.getByUsername(username);

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (employee == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //密码比对,先用md5加密,因为设置密码时，密码已经加密了
        String Password=DigestUtils.md5DigestAsHex(password.getBytes());


        if (!Password.equals(employee.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        if (employee.getStatus() == StatusConstant.DISABLE) {
            //账号被锁定
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        //3、返回实体对象
        return employee;
    }

    @Override
    public void save(EmployeeDTO employeeDTO) {
        //创建数据库po对象
        Employee employee=new Employee();
        //给po对象赋值
        BeanUtils.copyProperties(employeeDTO,employee);
        //其他值也赋上
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());

        //TODO 更新和创建人需要依据实际操作人，现是固定，只为演示(已解决）
        employee.setCreateUser(BaseContext.getCurrentId());
        employee.setUpdateUser(BaseContext.getCurrentId());

        employee.setStatus(StatusConstant.ENABLE);

        //记得给密码加密
        employee.setPassword(DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes()));

        //交给持久层
        employeeMapper.addEmployee(employee);
    }

    @Override
    public PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO) {
        //使用pagehelper插件进行分页查询
        PageHelper.startPage(employeePageQueryDTO.getPage(),employeePageQueryDTO.getPageSize());
        Page<Employee> page=employeeMapper.EmpPageQuery(employeePageQueryDTO);
        //将返回的数据经行封装
        Long total = page.getTotal();
        List<Employee> records = page.getResult();



        return new PageResult(total,records);
    }

    /**
     * 员工状态修改
     * @param status
     * @param id
     * @return
     */

    @Override
    public void startAndStop(Integer status, Long id) {
        Employee employee = Employee.builder()
                .status(status)
                .id(id)
                .build();
        employeeMapper.update(employee);


    }


    /**
     * 根据id查询员工信息
     * @param id
     * @return
     */

    @Override
    public Employee getById(Long id) {

       Employee e= employeeMapper.getById(id);

       //将密码设为不可见，确保安全
       e.setPassword("****");
        return e;
    }

    @Override
    public void changeEmpInfo(EmployeeDTO employeeDTO) {
            //为了可以代码复用，将参数变为实体类
            Employee employee=new Employee();
            //转换类型
            BeanUtils.copyProperties(employeeDTO,employee);
            //因为前端没传修改时间和修改人id，我们需要手动添加
            employee.setUpdateTime(LocalDateTime.now());
            employee.setUpdateUser(BaseContext.getCurrentId());

            employeeMapper.update(employee);

    }

}
