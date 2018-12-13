package sg.iss.team10.caps.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import sg.iss.team10.caps.model.Course;
import sg.iss.team10.caps.model.Enrollment;
import sg.iss.team10.caps.model.Student;
import sg.iss.team10.caps.services.CourseService;
import sg.iss.team10.caps.services.EnrollmentService;
import sg.iss.team10.caps.services.StudentService;
import sg.iss.team10.caps.util.CapsLogic;

@Controller
@RequestMapping(value = "/student")
public class StudentController {

	@Autowired
	private EnrollmentService eService;
	@Autowired
	private CourseService cService;
	@Autowired
	private StudentService sService;

// 	@InitBinder("department")
//	private void initDepartmentBinder(WebDataBinder binder) {
//		binder.addValidators(dValidator);
//	}
	/**
	 * DEPARTMENT CRUD OPERATIONS
	 * 
	 * @return
	 */

	// Creation after Confirmation
	@RequestMapping(value = "/create/{courseId}", method = RequestMethod.GET)
	public ModelAndView newEnrollmentPage(@PathVariable("courseId") Integer courseId) {
		ModelAndView mav = new ModelAndView("StudentEnrollmentNew");
		Course course = cService.findCourseById(courseId);
		mav.addObject("course", course);
		// mav.addObject("eidlist", eService.findAllEnrollmentID());
		return mav;
	}

	@RequestMapping(value = "/create/{courseId}", method = RequestMethod.POST)
	public ModelAndView createNewEnrollment(@ModelAttribute @Valid Enrollment enrollment,
			@PathVariable("courseId") Integer courseId, BindingResult result,
			final RedirectAttributes attributes, HttpSession Session) /* throw */ {
		
		
		//validation for Max capacity of course 1
		ModelAndView mav = new ModelAndView();
		ArrayList<Enrollment> en = new ArrayList<Enrollment>();
		en = eService.findEnrollmentByCourseID(courseId);	
		if(en.size()>= cService.findCourseById(courseId).getCapacity()) {
			String errormessage = "The Course is full.";
			Session.setAttribute("errormessage", errormessage);
			mav.setViewName("redirect:/student/search");
			return mav;
		}	
		
		
//		if (result.hasErrors())
//			return new ModelAndView("StudentEnrollmentNew");
		
		
		Enrollment em = new Enrollment();
		Student s = ((UserSession)Session.getAttribute("USERSESSION")).getStudent();
		String message = "Your enrollment is successful.";
		em.setStudentId(s.getStudentId());
		em.setCourseId(courseId);
		eService.createEnrollment(em);
		Session.setAttribute("message", message);
		mav.setViewName("redirect:/student/search");
		
		return mav;
	}

	
/*-------------------------- 1st Page / Landing -----------------------------------*/
	
	// 1st Page to be displayed when Student log in through ID
	@RequestMapping(value = "/landing", method = RequestMethod.GET)
	public ModelAndView StudentLandingPage(HttpSession Session) {
		
		ModelAndView mav = new ModelAndView("StudentEnrollmentList");
		int studentId = ((UserSession)Session.getAttribute("USERSESSION")).getStudent().getStudentId();
		ArrayList<Enrollment> GradeList = eService.findEnrollmentByStudentID(studentId);
		Student student = sService.findStudentByStudentID(studentId);
		String firstMidName = (student.getFirstMidName());
		String lastName = (student.getLastName());
		
		
		//Getting courseName
		//Creating an emptyArraylist to store Course objects with the same Id
		ArrayList<Course> courseName = new ArrayList<Course>();
		

		
	
		ArrayList<Character> grades = new ArrayList<Character>();
		for(Enrollment current: GradeList) {
			courseName.add(cService.findCourseById(current.getCourseId()));
			grades.add(CapsLogic.calculateGrade(current.getScore()));
		}
		

		mav.addObject("GradeList", GradeList);
		mav.addObject("grades", grades);
		mav.addObject("courseName", courseName);
		mav.addObject("firstMidName", firstMidName);
		mav.addObject("lastName", lastName);
		return mav;
	}
	
/* -------------------------Search Functions-----------------------------*/
	
	// 2.1 Page : ListofAllAvailableCourses(Complete)
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView StudentSearchCoursePage(HttpSession Session) {
		
		Student s = ((UserSession)Session.getAttribute("USERSESSION")).getStudent();
		ModelAndView mav = new ModelAndView("StudentSearchList");
		ArrayList<Course> courseList = cService.findAllCourse();//Get list of all courses
		ArrayList<Enrollment> elist = eService.findEnrollmentByStudentID(s.getStudentId()); //Get enrollment list w courseID
		for(int i=0;i<elist.size();i++){
			for(int j=0;j<courseList.size();j++) {
				if(courseList.get(j).getCourseId() == elist.get(i).getCourseId()) {
					courseList.remove(j);
				}
			}
		}

		mav.addObject("courseList", courseList);
		return mav;
		
	}
	

	// 2.2 Page : findCourseById (input)
	@RequestMapping(value = "/searchbyid", method = RequestMethod.POST)
	public ModelAndView StudentSearchCoursePageById(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("courseId");
		int i = Integer.parseInt(request.getParameter("Name"));
		Course courseList = cService.findCourseById(i);
		mav.addObject("courseId", courseList);
		return mav;
		
	}

//AOP
//Trying Join Point
	
	// 2.3 Page : FindCourseByName (input) get and set to session
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public ModelAndView StudentSearchCourseByName(HttpServletRequest request, HttpSession Session) {
		
		Student s = ((UserSession)Session.getAttribute("USERSESSION")).getStudent();
		ModelAndView mav = new ModelAndView("StudentSearchList");
		ArrayList<Course> courseList = cService.findCoursesByName(request.getParameter("Name"));//Get list of all courses
		ArrayList<Enrollment> elist = eService.findEnrollmentByStudentID(s.getStudentId()); //Get enrollment list w courseID
		for(int i=0;i<elist.size();i++){
			for(int j=0;j<courseList.size();j++) {
				if(courseList.get(j).getCourseId() == elist.get(i).getCourseId()) {
					courseList.remove(j);
				}
			}
		}
		//Invalid Return...Courselist.size = 0 (==)
		//Session.setAttribute("message", message);
		mav.addObject("courseList", courseList);
		return mav;

	}
	
	
	
	
	// 2.4 Page: findCourseByStaffId
	@RequestMapping(value = "/SearchByStaff", method = RequestMethod.GET)
	public ModelAndView findCourseByStaffId(Integer sid) {
		ModelAndView mav = new ModelAndView("courseStaff");
		ArrayList<Course> courseList = cService.findCourseByStaffId(sid);
		mav.addObject("courseStaffList", courseList);
		return mav;

	}

	
}
