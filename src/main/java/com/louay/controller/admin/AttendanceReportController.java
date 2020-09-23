package com.louay.controller.admin;

import com.louay.controller.factory.EntitiesFactory;
import com.louay.controller.factory.ServicesFactory;
import com.louay.controller.factory.WrappersFactory;
import com.louay.model.entity.courses.members.UsersAttendance;
import com.louay.model.entity.wrapper.StudentAttendanceReport;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

@Controller
@CrossOrigin(origins = "https://localhost:8443")
@RequestMapping(value = "/admin/attendance-report")
public class AttendanceReportController implements Serializable {
    private static final long serialVersionUID = 8665330446895321937L;
    private final EntitiesFactory entitiesFactory;
    private final ServicesFactory servicesFactory;
    private final WrappersFactory wrappersFactory;

    public AttendanceReportController(EntitiesFactory entitiesFactory, ServicesFactory servicesFactory,
                                      WrappersFactory wrappersFactory) {
        Assert.notNull(entitiesFactory, "entitiesFactory cannot be null!.");
        Assert.notNull(servicesFactory, "servicesFactory cannot be null!.");
        Assert.notNull(wrappersFactory, "wrappersFactory cannot be null!.");

        this.entitiesFactory = entitiesFactory;
        this.servicesFactory = servicesFactory;
        this.wrappersFactory = wrappersFactory;
    }

    @GetMapping(value = "/by_course-date/course-id={courseId}&from-date={fromDate}&to-date={toDate:.+}")
    public ModelAndView getAttendanceByCourseAndDate(@PathVariable(value = "courseId") String courseIdInPath,
                                                     @PathVariable(value = "toDate") String toDateInPath,
                                                     @PathVariable(value = "fromDate") String fromDateInPath) {

        Assert.notNull(courseIdInPath, "course Id cannot be null!.");
        Assert.notNull(fromDateInPath, "from date cannot be null!.");
        Assert.notNull(toDateInPath, "to date cannot be null!.");
        Long courseId = Long.valueOf(courseIdInPath);
        Calendar fromDate = Calendar.getInstance();
        Calendar toDate = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        try {
            fromDate.setTimeInMillis(dateFormat.parse(fromDateInPath).getTime());
            toDate.setTimeInMillis(dateFormat.parse(toDateInPath).getTime());
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        StudentAttendanceReport studentAttendanceReport = buildStudentAttendanceReport(courseId, fromDate, toDate);
        List<UsersAttendance> usersAttendanceList = findUsersAttendanceListByCourseIdAndDate(studentAttendanceReport);

        return new ModelAndView(new ExcelBuilder(), "attendanceList", usersAttendanceList);
    }

    private List<UsersAttendance> findUsersAttendanceListByCourseIdAndDate(StudentAttendanceReport studentAttendanceReport) {
        return this.servicesFactory.getAttendanceService().findUsersAttendanceByCourseAndDate(studentAttendanceReport);
    }

    private StudentAttendanceReport buildStudentAttendanceReport(Long courseId, Calendar fromDate, Calendar toDate) {
        StudentAttendanceReport studentAttendanceReport = this.wrappersFactory.getStudentAttendanceReport();
        studentAttendanceReport.setUsersAttendance(this.entitiesFactory.getUsersAttendance());
        studentAttendanceReport.getUsersAttendance().setCourse(this.entitiesFactory.getCourses());
        studentAttendanceReport.getUsersAttendance().getCourse().setCourseID(courseId);
        studentAttendanceReport.setFromDate(fromDate);
        studentAttendanceReport.setToDate(toDate);

        return studentAttendanceReport;
    }

    @GetMapping(value = "/by_course/{courseId}")
    public ModelAndView getAttendanceByCourse(@PathVariable(value = "courseId") String courseIdInPath) {
        Assert.notNull(courseIdInPath, "course Id cannot be null!.");

        Long courseId = Long.valueOf(courseIdInPath);
        List<UsersAttendance> usersAttendanceList = findUsersAttendanceListByCourseId(courseId);

        return new ModelAndView(new ExcelBuilder(), "attendanceList", usersAttendanceList);
    }

    private List<UsersAttendance> findUsersAttendanceListByCourseId(Long courseId) {
        UsersAttendance usersAttendance = buildUsersAttendance(courseId);

        return this.servicesFactory.getAttendanceService().findUsersAttendanceByCourse(usersAttendance);
    }

    private UsersAttendance buildUsersAttendance(Long courseId) {
        UsersAttendance usersAttendance = this.entitiesFactory.getUsersAttendance();
        usersAttendance.setCourse(this.entitiesFactory.getCourses());
        usersAttendance.getCourse().setCourseID(courseId);

        return usersAttendance;
    }

}
