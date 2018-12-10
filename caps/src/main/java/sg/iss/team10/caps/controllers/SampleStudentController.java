//package sg.iss.team10.caps.controllers;
//
//import java.util.ArrayList;
//
//import javax.validation.Valid;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.WebDataBinder;
//import org.springframework.web.bind.annotation.InitBinder;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.servlet.ModelAndView;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import sg.iss.team10.caps.exception.SampleStudentNotFound;
//import sg.iss.team10.caps.model.SampleStudent;
//import sg.iss.team10.caps.services.SampleStudentService;
//import sg.iss.team10.caps.validator.SampleStudentValidator;
//
//@RequestMapping(value = "/student")
//@Controller
//public class SampleStudentController {
//
//	@Autowired
//	SampleStudentService sService;
//	@Autowired
//	private SampleStudentValidator sValidator;
//	
//	@InitBinder("student")
//	private void initDepartmentBinder(WebDataBinder binder) {
//		binder.addValidators(sValidator);
//	}
//
//	@RequestMapping(value = "/list", method = RequestMethod.GET)
//	public ModelAndView listAll() {
//		ModelAndView mav = new ModelAndView("StudentCRUD");
//		ArrayList<SampleStudent> students = sService.findAllStudents();
//		mav.addObject("students", students);
//		return mav;
//	}
//
//	@RequestMapping(value = "/create", method = RequestMethod.GET)
//	public ModelAndView newStudentPage() {
//		ModelAndView mav = new ModelAndView("StudentFormNew", "student", new SampleStudent());
//		return mav;
//	}
//
//	@RequestMapping(value = "/create", method = RequestMethod.POST)
//	public ModelAndView createNewStudent(@ModelAttribute @Valid SampleStudent student, BindingResult result,
//			final RedirectAttributes redirectAttributes) {
//		if (result.hasErrors())
//			return new ModelAndView("StudentFormNew");
//		ModelAndView mav = new ModelAndView();
//
//		sService.createStudent(student);
//		//String message = "New student " + student.getNric() + " was successfully created.";
//		mav.setViewName("redirect:/student/list");
//		return mav;
//	}
//
//	@RequestMapping(value = "/edit/{nric}", method = RequestMethod.GET)
//	public ModelAndView editStudentPage(@PathVariable String nric) {
//		ModelAndView mav = new ModelAndView("StudentFormEdit");
//		mav.addObject("student", sService.findStudent(nric));
//		return mav;
//	}
//
//	@RequestMapping(value = "/edit/{nric}", method = RequestMethod.POST)
//	public ModelAndView editStudent(@ModelAttribute @Valid SampleStudent student, @PathVariable String nric,
//			BindingResult result, final RedirectAttributes redirectAttributes) throws SampleStudentNotFound {
//		System.out.println("student"+student.toString());
//		if (result.hasErrors())
//			return new ModelAndView("StudentFormEdit");
//		sService.updateStudent(student);
//		ModelAndView mav = new ModelAndView("redirect:/student/list");
//		String message = "Student" + student.getNric() + " was successfully updated.";
//		redirectAttributes.addFlashAttribute("message", message);
//		return mav;
//	}
//
//	@RequestMapping(value = "/delete/{nric}", method = RequestMethod.GET)
//	public ModelAndView deleteStudent(@PathVariable String nric, final RedirectAttributes redirectAttributes)
//			throws SampleStudentNotFound {
//		SampleStudent student = sService.findStudent(nric);
//		sService.removeStudent(student);
//		ModelAndView mav = new ModelAndView("redirect:/student/list");
//		String message = "The student " + student.getNric() + " was successfully deleted.";
//		redirectAttributes.addFlashAttribute("message", message);
//		return mav;
//	}
//
//}
