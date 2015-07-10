package com.sudarshan.jpa.controller;

import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sudarshan.jpa.dto.ContactDTO;
import com.sudarshan.jpa.exception.NotFoundException;
import com.sudarshan.jpa.model.Contact;
import com.sudarshan.jpa.service.ContactService;

@Controller
public class ContactController {
	
	private static final String HOME_VIEW = "list";
	
	private static final String MODEL_ATTRIBUTE_CONTACT = "contact";
	private static final String ADD_CONTACT_VIEW = "add";
	private static final String PARAMETER_CONTACT_ID = "id";
	private static final String FLASH_MESSAGE_KEY_FEEDBACK = "feedbackMessage";
	
	private static final String FEEDBACK_MESSAGE_KEY_CONTACT_ADDED = "feedback.message.contact.added";
	private static final String FEEDBACK_MESSAGE_KEY_CONTACT_DELETED = "feedback.message.contact.deleted";
	
	private static final String REQUEST_MAPPING_VIEW_CONTACT = "/contact/{id}";
	
	private static final String CONTACT_VIEW = "view";
	
	@Resource
	private ContactService contactService;

	@Resource
	private MessageSource messageSource;
	
	@RequestMapping(value="/",method=RequestMethod.GET)
	public String showHomePage(Model model) {
		List<Contact> allContacts = contactService.findAll();
		model.addAttribute("contacts", allContacts);
		return HOME_VIEW;
	}
	
	@RequestMapping(value="/contact/add", method=RequestMethod.GET)
	public String showAddContactPage(Model model) {
		ContactDTO formObj = new ContactDTO();
		model.addAttribute(MODEL_ATTRIBUTE_CONTACT, formObj);
		return ADD_CONTACT_VIEW;
	}
	
	@RequestMapping(value="/contact/add", method=RequestMethod.POST)
	public String addContact(@Valid @ModelAttribute(MODEL_ATTRIBUTE_CONTACT) ContactDTO contactDto, BindingResult result, RedirectAttributes redirectAttrs) {
		
		if(result.hasErrors()) {
			return ADD_CONTACT_VIEW;
		}
		
		Contact added = contactService.add(contactDto);
		
		redirectAttrs.addAttribute(PARAMETER_CONTACT_ID, added.getId());
		addFeedbackMessage(redirectAttrs, FEEDBACK_MESSAGE_KEY_CONTACT_ADDED, added.getFirstName(), added.getLastName());
		return createRedirectViewPath(REQUEST_MAPPING_VIEW_CONTACT);
	}
	
	@RequestMapping(value="/contact/{id}", method=RequestMethod.GET)
	public String showContactPage(@PathVariable("id") Long id, Model model) throws NotFoundException {
		Contact contact = contactService.findById(id);
		model.addAttribute(MODEL_ATTRIBUTE_CONTACT, contact);
		
		return CONTACT_VIEW;
	}
	
	@RequestMapping(value="/contact/{id}", method=RequestMethod.DELETE)
	@ResponseBody
	public String deleteContact(@PathVariable("id") Long id) throws NotFoundException {
		Contact contactDeleted = contactService.deleteById(id);
		return getMessage(FEEDBACK_MESSAGE_KEY_CONTACT_DELETED, contactDeleted.getFirstName(), contactDeleted.getLastName());
	}
	
	private void addFeedbackMessage(RedirectAttributes redirectAttrs, String code, Object... params) {
		String localizedMessageFeedback = getMessage(code, params);
		redirectAttrs.addAttribute(FLASH_MESSAGE_KEY_FEEDBACK, localizedMessageFeedback);
	}
	
	private String getMessage(String code, Object... params) {
		Locale locale = LocaleContextHolder.getLocale();
		return messageSource.getMessage(code, params, locale);
	}
	
	private String createRedirectViewPath(String requestMapping) {
		StringBuilder redirectViewPath = new StringBuilder();
		redirectViewPath.append("redirect:");
		redirectViewPath.append(requestMapping);
		return redirectViewPath.toString();
	}

}
