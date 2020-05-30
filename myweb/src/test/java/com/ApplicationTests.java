package com;

import com.config.AppConfig;
import com.github.pagehelper.PageInfo;
import com.mapper.DeviceMapper;
import com.model.RepairModel;
import com.model.UserInfo;
import com.service.*;
import com.util.MyPageInfo;
import com.util.MyUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {
    @Autowired
    DataSource dataSource;
    @Autowired
    WtService wtService;
    @Autowired
    DeviceService deviceService;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    MyUtil myUtil;
    @Autowired
    RepairService repairService;
    @Autowired
    AppConfig appConfig;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserInfoService userInfoService;
    @Autowired
    DeviceMapper deviceMapper;

    @Test
    public void contextLoads() throws SQLException, UnsupportedEncodingException {
//		System.out.println(dataSource.getClass());
//		Connection connection=dataSource.getConnection();
//		System.out.println(connection);
//		DemoModel demoModel =new DemoModel();
//		demoModel.setMykey("A");
//		demoModel.setMyvalue(LocalDate.now());
//		demoService.insert(demoModel);
//		connection.close();
//		System.out.println("hello world");
//
//		DeviceModel deviceModel =new DeviceModel();
//		deviceModel.setSbName("S");
//		deviceModel.setSbType("信号机");
//		deviceModel.setZname("大竹园");
//		deviceModel.setXb("正线");
//		deviceModel.setTc("上行");
//		deviceService.insert(deviceModel);
//
//
//		EmployeeModel employeeModel = new EmployeeModel();
//		employeeModel.setEmName("周鹏");
//		employeeModel.setDw("安康车间");
//		employeeModel.setZw("副工长");
//		employeeService.insert(employeeModel);
//		String[] tmp = deviceService.findByZName("大竹园");
////		System.out.println(Arrays.toString(tmp));
////		System.out.println(" 设备id "+ deviceService.findIdByZnameSbname("大竹园","SI"));
////		System.out.println(Arrays.toString(employeeService.getEmployeeList()) );
//		List<WtModel> wtModels = wtService.findAllbySbIdNoxj(3L);
//		for(WtModel wt:wtModels){
//			System.out.println(wt.toString());
//		}
//		List<EmployeeModel> employeeModels = employeeService.selectAll();
//		for(EmployeeModel e: employeeModels){
//			System.out.println(e.toString());
//		}
//		EmployeeModel employeeModel =employeeService.select(1);
//		System.out.println(employeeModel.toString());
//        String gxdw = deviceService.findGxdwByZname("安康");
//        List<EmployeeModel> employeeModelList = employeeService.selectAll();
//        Array<String> s = Linq.of(employeeModelList).where(x -> x.getDw().equals(gxdw)).select(y -> y.getEmName()).toArray();
//        for (String x : s) {
//            System.out.println(x);
//        }
//		String[] k =myUtil.getEmployeeList("石庙沟");
//		for(String s:k){
//			System.out.println(s);
//		}
//        String[]  s= myUtil.getZnameSbname(2L);
//        for(String c:s){
//            System.out.println(c.toString());
//        }
//        List<RepairModel> repairModels = repairService.selectByZname("安康");
//        for(RepairModel r:repairModels){
//            System.out.println(r.toString());
//        }
//        LocalDate n = LocalDate.now();
//        WeekFields weekFields =WeekFields.of(DayOfWeek.MONDAY,4);
//        System.out.println(n.get(weekFields.weekOfWeekBasedYear()));
//        List<RepairModel> repairModels =repairService.getRepairPage(4,2);
//        for(RepairModel r :repairModels){
//            System.out.println(r.toString());
//        }
//        List<RepairModel> repairModels = repairService.selectAllByYear();
//        for (RepairModel r : repairModels) {
//            System.out.println(r.toString());
//        }
//        for (String s : appConfig.getU_NAME()) {
//            System.out.println(s);
//        }
        String[] aa = employeeService.getEmployeeList();
        for (String s : aa) {
            System.out.println(s);
        }
    }

    @Test
    public void linqTest() {
//        List<DeviceModel> deviceModelList =myUtil.getNoRepairDevice("大竹园","","周计划");
//        for (DeviceModel model : deviceModelList) {
//            System.out.println(model.toString());
//        }
        List<RepairModel> repairModels = repairService.selectAllByYear();
        Stream<RepairModel> repairModelStream = repairModels.stream();
        Iterator<RepairModel> iterator = repairModelStream
                .filter(x -> x.getZn().equals("安康"))
                .iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

    }

    @Test
    public void initUserInfo() {
        UserInfo userInfo1 = new UserInfo();
        userInfo1.setUserName("admin");
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String pwdCode = bCryptPasswordEncoder.encode("123456");
        userInfo1.setPassword(pwdCode);
        userInfo1.setRole("ROLE_" + "admin");
        userInfoService.insertUser(userInfo1);
        List<UserInfo> userInfos = userInfoService.selectAll();
        for (UserInfo userInfo : userInfos) {
            System.out.println(userInfo);
        }
        UserInfo userInfo = userInfoService.findByUserName("admin");
        System.out.println(userInfo.toString());

    }

    @Test
    public void pageTest() {
        System.out.println(deviceService.getDevicePage(1, 2));
        System.out.println("-------------------------");
        System.out.println(deviceService.getDevicePage(2, 2));
    }

    @Test
    public void ArrayPageTest() {
        List<RepairModel> rps = repairService.selectAll();
        PageInfo<RepairModel> repairModelPageInfo = MyPageInfo.getMyPage(rps, 1, 2);
        System.out.println(repairModelPageInfo);
        System.out.println("--------------");
        PageInfo<RepairModel> repairModelPageInfo1 = MyPageInfo.getMyPage(rps, 2, 2);
        System.out.println(repairModelPageInfo1);
        System.out.println("-");

    }
}
